@echo off
REM deployapp.bat - Start Tomcat 10, undeploy existing bootghcp, and deploy the latest WAR
REM Created: 2026-03-28

SETLOCAL ENABLEDELAYEDEXPANSION
:: Configuration - change if your Tomcat install path differs
SET "TOMCAT_BIN=C:\Savitha\Projects\SERVERS\apache-tomcat-10.0.11\bin"

REM Set the location of the WAR file
SET "TOMCAT_WEBAPPS=C:\Savitha\Projects\SERVERS\apache-tomcat-10.0.11\webapps"
:: Project WAR location (resolved relative to this script's directory)
SET "PROJECT_WAR=%~dp0target\bootghcp.war"
necho ==================================================
echo Deploy script starting: %DATE% %TIME%
echo Tomcat bin: %TOMCAT_BIN%
echo Tomcat webapps: %TOMCAT_WEBAPPS%
echo Project WAR: %PROJECT_WAR%
echo ==================================================
necho Starting Apache Tomcat (if not already running)...
if exist "%TOMCAT_BIN%\startup.bat" (
    pushd "%TOMCAT_BIN%"
    call "startup.bat"
    popd
) else (
    echo ERROR: Tomcat startup.bat not found in %TOMCAT_BIN%
    exit /b 1
)
necho Waiting for Tomcat to initialize (5 seconds)...
timeout /t 5 /nobreak >nul
necho Undeploying previous bootghcp application if present...
if exist "%TOMCAT_WEBAPPS%\bootghcp.war" (
    echo Removing existing WAR: %TOMCAT_WEBAPPS%\bootghcp.war
    del /f /q "%TOMCAT_WEBAPPS%\bootghcp.war" || echo Warning: could not delete existing WAR (might be in use)
) else (
    echo No existing WAR found.
)
nif exist "%TOMCAT_WEBAPPS%\bootghcp" (
    echo Removing exploded directory: %TOMCAT_WEBAPPS%\bootghcp
    rmdir /s /q "%TOMCAT_WEBAPPS%\bootghcp" || echo Warning: could not remove exploded directory (might be in use)
) else (
    echo No exploded directory found.
)
necho Deploying latest WAR from project target folder...
if exist "%PROJECT_WAR%" (
    copy /y "%PROJECT_WAR%" "%TOMCAT_WEBAPPS%\" >nul
    if %errorlevel% equ 0 (
        echo Deployment copy succeeded.
    ) else (
        echo ERROR: Failed to copy WAR to Tomcat webapps. Errorlevel=%errorlevel%
        exit /b %errorlevel%
    )
) else (
    echo ERROR: Project WAR not found at %PROJECT_WAR%
    exit /b 1
)
necho Deployment finished: %DATE% %TIME%
echo ==================================================
ENDLOCAL
exit /b 0
