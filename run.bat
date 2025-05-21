@echo off
REM Establece la ruta relativa al JavaFX SDK
set JAVA_FX_PATH=%~dp0javafx-sdk-23\lib

REM Ejecuta la aplicación
java --module-path "%JAVA_FX_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/ORS.jar
