application {
    mainClass.set("MainKt")
}

tasks.named<JavaExec>("run") {
    workingDir = projectDir
}
