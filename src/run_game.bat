@echo off
cd "C:\Users\YourUser\Downloads\COMPSCI-PROJECT-main (3)\COMPSCI-PROJECT-main"
javac -d bin src\*.java
IF %ERRORLEVEL% NEQ 0 (
    echo Compilation failed.
    pause
    exit /b
)
java -cp bin Main | more
pause
