set MODULE_PATH=%1
set INPUT=%2
set OUTPUT=%3
set JAR=%4
set VERSION=%5
set APP_ICON=%6

call "%JAVA_HOME%\bin\java.exe" ^
    -Xmx512M ^
    --module-path "%JAVA_HOME%\jmods" ^
    --add-opens jdk.jlink/jdk.tools.jlink.internal.packager=jdk.packager ^
    -m jdk.packager/jdk.packager.Main ^
    create-image ^
    --module-path "%MODULE_PATH%" ^
    --verbose ^
    --echo-mode ^
    --add-modules "java.base,java.datatransfer,java.desktop,java.instrument,java.logging,java.management,java.scripting,java.sql,java.xml,jdk.attach,jdk.jdi,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom,javafx.controls,javafx.graphics,javafx.fxml,java.naming,jdk.charsets" ^
    --input "%INPUT%" ^
    --output "%OUTPUT%" ^
    --name "VDM" ^
    --main-jar "%JAR%" ^
    --version "%VERSION%" ^
    --jvm-args "-Djdk.tls.client.protocols=TLSv1.2 --add-opens javafx.base/com.sun.javafx.reflect=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED" ^
    --icon "%APP_ICON%" ^
    --class "vdm.Main"
