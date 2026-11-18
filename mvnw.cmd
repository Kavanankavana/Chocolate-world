@IF "%DEBUG%" == "" @ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION

SET "DIR=%~dp0"
IF "%DIR:~-1%"=="\" SET "DIR=%DIR:~0,-1%"

IF EXIST "C:\Users\Administrator\apache-maven-3.9.9\bin\mvn.cmd" (
    SET "MVN_CMD=C:\Users\Administrator\apache-maven-3.9.9\bin\mvn.cmd"
    GOTO :RUN
)

WHERE mvn >nul 2>&1
IF %ERRORLEVEL% EQU 0 (
    SET "MVN_CMD=mvn"
    GOTO :RUN
)

ECHO Maven was not found at C:\Users\Administrator\apache-maven-3.9.9 or in PATH.
EXIT /B 1

:RUN
CALL "%MVN_CMD%" %*
EXIT /B %ERRORLEVEL%
