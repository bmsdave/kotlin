
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar

description = "Kotlin IDEA Ultimate plugin"

apply {
    plugin("kotlin")
}

val ideaProjectResources =  project(":idea").the<JavaPluginConvention>().sourceSets["main"].output.resourcesDir

evaluationDependsOn(":prepare:idea-plugin")

val intellijUltimateEnabled = true // : Boolean by rootProject.extra

val springClasspath by configurations.creating

dependencies {
    if (intellijUltimateEnabled) {
        testRuntime(intellijUltimateDep())
    }

    compileOnly(project(":kotlin-reflect-api"))
    compile(projectDist(":kotlin-stdlib"))
    compile(project(":core:descriptors")) { isTransitive = false }
    compile(project(":core:descriptors.jvm")) { isTransitive = false }
    compile(project(":core:util.runtime")) { isTransitive = false }
    compile(project(":compiler:light-classes")) { isTransitive = false }
    compile(project(":compiler:frontend")) { isTransitive = false }
    compile(project(":compiler:frontend.java")) { isTransitive = false }
    compile(project(":js:js.frontend")) { isTransitive = false }
    compile(projectClasses(":idea"))
    compile(project(":idea:idea-jvm")) { isTransitive = false }
    compile(project(":idea:idea-core")) { isTransitive = false }
    compile(project(":idea:ide-common")) { isTransitive = false }
    compile(project(":idea:idea-gradle")) { isTransitive = false }
    compileOnly(intellijCoreDep()) { includeJars("intellij-core") }

    if (intellijUltimateEnabled) {
        compileOnly(intellijUltimateDep()) { includeJars("annotations", "trove4j", "openapi", "idea", "util", "jdom") }
        compileOnly(intellijUltimatePluginDep("CSS"))
        compileOnly(intellijUltimatePluginDep("DatabaseTools"))
        compileOnly(intellijUltimatePluginDep("JavaEE"))
        compileOnly(intellijUltimatePluginDep("jsp"))
        compileOnly(intellijUltimatePluginDep("PersistenceSupport"))
        compileOnly(intellijUltimatePluginDep("Spring"))
        compileOnly(intellijUltimatePluginDep("uml"))
        compileOnly(intellijUltimatePluginDep("JavaScriptLanguage"))
        compileOnly(intellijUltimatePluginDep("JavaScriptDebugger"))
        compileOnly(intellijUltimatePluginDep("NodeJS"))
        compileOnly(intellijUltimatePluginDep("properties"))
        compileOnly(intellijUltimatePluginDep("java-i18n"))
        compileOnly(intellijUltimatePluginDep("gradle"))
        compileOnly(intellijUltimatePluginDep("Groovy"))
        compileOnly(intellijUltimatePluginDep("junit"))
    }

    testCompile(projectDist(":kotlin-test:kotlin-test-jvm"))
    testCompileOnly(project(":idea:idea-test-framework")) { isTransitive = false }
    testCompileOnly(project(":plugins:lint")) { isTransitive = false }
    testCompileOnly(project(":idea:idea-jvm")) { isTransitive = false }
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(projectTests(":idea")) { isTransitive = false }
    testCompile(projectTests(":generators:test-generator"))
    testCompile(commonDep("junit:junit"))
    testCompile(commonDep("org.jetbrains.kotlinx", "kotlinx-coroutines-core")) { isTransitive = false }

    testRuntime(projectDist(":kotlin-reflect"))
    testRuntime(projectDist(":kotlin-script-runtime"))
    testRuntime(projectRuntimeJar(":kotlin-compiler"))
    testRuntime(project(":plugins:android-extensions-ide"))
    testRuntime(project(":plugins:android-extensions-compiler"))
    testRuntime(project(":plugins:annotation-based-compiler-plugins-ide-support"))
    testRuntime(project(":idea:idea-android"))
    testRuntime(project(":idea:idea-maven"))
    testRuntime(project(":idea:idea-jps-common"))
    testRuntime(project(":idea:formatter"))
    testRuntime(project(":sam-with-receiver-ide-plugin"))
    testRuntime(project(":kotlin-sam-with-receiver-compiler-plugin"))
    testRuntime(project(":noarg-ide-plugin"))
    testRuntime(project(":kotlin-noarg-compiler-plugin"))
    testRuntime(project(":allopen-ide-plugin"))
    testRuntime(project(":kotlin-allopen-compiler-plugin"))
    testRuntime(project(":plugins:kapt3-idea"))
    testRuntime(files("${System.getProperty("java.home")}/../lib/tools.jar"))
    testRuntime(project(":plugins:kapt3-idea"))
    testRuntime(project(":idea:idea-test-framework"))
    testRuntime(project(":plugins:lint"))
    testRuntime(project(":idea:idea-jvm"))

    springClasspath(commonDep("org.springframework", "spring-core"))
    springClasspath(commonDep("org.springframework", "spring-beans"))
    springClasspath(commonDep("org.springframework", "spring-context"))
    springClasspath(commonDep("org.springframework", "spring-tx"))
    springClasspath(commonDep("org.springframework", "spring-web"))

    if (intellijUltimateEnabled) {
        testCompileOnly(intellijUltimateDep()) { includeJars("gson-2.5", "annotations", "trove4j", "openapi", "idea", "util", "jdom") }

        testCompile(intellijUltimatePluginDep("CSS"))
        testCompile(intellijUltimatePluginDep("DatabaseTools"))
        testCompile(intellijUltimatePluginDep("JavaEE"))
        testCompile(intellijUltimatePluginDep("jsp"))
        testCompile(intellijUltimatePluginDep("PersistenceSupport"))
        testCompile(intellijUltimatePluginDep("Spring"))
        testCompile(intellijUltimatePluginDep("uml"))
        testCompile(intellijUltimatePluginDep("JavaScriptLanguage"))
        testCompile(intellijUltimatePluginDep("JavaScriptDebugger"))
        testCompile(intellijUltimatePluginDep("NodeJS"))
        testCompile(intellijUltimatePluginDep("properties"))
        testCompile(intellijUltimatePluginDep("java-i18n"))
        testCompile(intellijUltimatePluginDep("gradle"))
        testCompile(intellijUltimatePluginDep("Groovy"))
        testCompile(intellijUltimatePluginDep("junit"))
        testRuntime(intellijUltimatePluginDep("coverage"))
        testRuntime(intellijUltimatePluginDep("maven"))
        testRuntime(intellijUltimatePluginDep("android"))
        testRuntime(intellijUltimatePluginDep("testng"))
        testRuntime(intellijUltimatePluginDep("IntelliLang"))
        testRuntime(intellijUltimatePluginDep("copyright"))
        testRuntime(intellijUltimatePluginDep("java-decompiler"))
    }
}

val preparedResources = File(buildDir, "prepResources")

sourceSets {
    "main" { projectDefault() }
    "test" {
        projectDefault()
        resources.srcDir(preparedResources)
    }
}

val ultimatePluginXmlContent: String by lazy {
    val sectRex = Regex("""^\s*</?idea-plugin>\s*$""")
    File(projectDir, "resources/META-INF/ultimate-plugin.xml")
            .readLines()
            .filterNot { it.matches(sectRex) }
            .joinToString("\n")
}

val prepareResources by task<Copy> {
    dependsOn(":idea:assemble")
    from(ideaProjectResources, {
        exclude("META-INF/plugin.xml")
    })
    into(preparedResources)
}

val preparePluginXml by task<Copy> {
    dependsOn(":idea:assemble")
    from(ideaProjectResources, { include("META-INF/plugin.xml") })
    into(preparedResources)
    filter {
        it?.replace("<!-- ULTIMATE-PLUGIN-PLACEHOLDER -->", ultimatePluginXmlContent)
    }
}

val communityPluginProject = ":prepare:idea-plugin"

val jar = runtimeJar(task<ShadowJar>("shadowJar")) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(preparePluginXml)
    dependsOn("$communityPluginProject:shadowJar")
    val communityPluginJar = project(communityPluginProject).configurations["runtimeJar"].artifacts.files.singleFile
    from(zipTree(communityPluginJar), { exclude("META-INF/plugin.xml") })
    from(preparedResources, { include("META-INF/plugin.xml") })
    from(the<JavaPluginConvention>().sourceSets.getByName("main").output)
    archiveName = "kotlin-plugin.jar"
}

val ideaPluginDir: File by rootProject.extra
val ideaUltimatePluginDir: File by rootProject.extra

task<Copy>("ideaUltimatePlugin") {
    dependsOn("$communityPluginProject:ideaPlugin")
    dependsOnTaskIfExistsRec("ideaPlugin", rootProject)
    into(ideaUltimatePluginDir)
    from(ideaPluginDir) { exclude("lib/kotlin-plugin.jar") }
    from(jar, { into("lib") })
}

task("idea-ultimate-plugin") {
    dependsOn("ideaUltimatePlugin")
    doFirst { logger.warn("'$name' task is deprecated, use '${dependsOn.last()}' instead") }
}

task("ideaUltimatePluginTest") {
    dependsOn("check")
}

projectTest {
    dependsOn(prepareResources)
    dependsOn(preparePluginXml)
    workingDir = rootDir
    doFirst {
        systemProperty("idea.home.path", intellijRootDir().canonicalPath)
        systemProperty("spring.classpath", springClasspath.asPath)
    }
}

val generateTests by generator("org.jetbrains.kotlin.tests.GenerateUltimateTestsKt")
