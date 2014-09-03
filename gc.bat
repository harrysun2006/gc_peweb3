@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Run Script for the JantekDemo
rem
rem Environment Variable Prequisites
rem
rem   JAVA_HOME       Must point at your Java Development Kit installation.
rem                   Required to run the with the "debug" argument.
rem
rem   JRE_HOME        Must point at your Java Development Kit installation.
rem                   Defaults to JAVA_HOME if empty.
rem
rem   JAVA_OPTS       (Optional) Java runtime options used when the tool runs.
rem
rem ---------------------------------------------------------------------------

rem Make sure prerequisite environment variables are set
if not "%JAVA_HOME%" == "" goto gotJdkHome
if not "%JRE_HOME%" == "" goto gotJreHome
set JRE_HOME=%CD%\jre
goto gotJreHome
echo Neither the JAVA_HOME nor the JRE_HOME environment variable is defined
echo At least one of these environment variable is needed to run this program
goto exit

:gotJreHome
if not exist "%JRE_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JRE_HOME%\bin\javaw.exe" goto noJavaHome
if not ""%1"" == ""debug"" goto okJavaHome
echo JAVA_HOME should point to a JDK in order to run in debug mode.
goto exit

:gotJdkHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javaw.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\jdb.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
if not "%JRE_HOME%" == "" goto okJavaHome
set JRE_HOME=%JAVA_HOME%
goto okJavaHome

:noJavaHome
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo NB: JAVA_HOME should point to a JDK not a JRE
goto exit
:okJavaHome

rem Set standard CLASSPATH
rem Note that there are no quotes as we do not want to introduce random
rem quotes into the CLASSPATH
set CLASSPATH=%JAVA_HOME%\lib\tools.jar

rem Set standard command for invoking Java.
rem Note that NT requires a window name argument when using start.
rem Also note the quoting as JAVA_HOME may contain spaces.
set _RUNJAVA="%JRE_HOME%\bin\java"
set _RUNJAVAW="%JRE_HOME%\bin\javaw"
set _RUNJDB="%JAVA_HOME%\bin\jdb"
set _RUNJAVAC="%JAVA_HOME%\bin\javac"

set PRGDIR=%CD%
set JAVA_OPTS=-Dfile.encoding=UTF8
set CLASSPATH=%CLASSPATH%;%PRGDIR%;%PRGDIR%\sylla.jar
set MAINCLASS=com.sylla.Niffler

rem Get remaining unshifted command line arguments and save them in the
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

set VAR=after
if "!VAR!" == "after" goto setClassPath
rem Enable Delayed Environment Variable Expansion (Windows 2000/XP)
set REG_FILE=%PRGDIR%\_edeve.reg
echo Windows Registry Editor Version 5.00 > %REG_FILE%
echo [HKEY_CURRENT_USER\Software\Microsoft\Command Processor] >> %REG_FILE%
echo "DelayedExpansion"=dword:00000001 >> %REG_FILE%
regedit /s %REG_FILE%
del %REG_FILE%
set TITLE=
if "%OS%" == "Windows_NT" set TITLE="SYLLA"
start %TITLE% %0 %CMD_LINE_ARGS%
goto exit

:setClassPath
for %%i in (%PRGDIR%\lib\*.jar) do set CLASSPATH=!CLASSPATH!;%%i

:run
echo Using JAVA:          %_RUNJAVA%
echo Using JAVA_OPTS:     %JAVA_OPTS%
echo Using CLASSPATH:     %CLASSPATH%
echo Using MAINCLASS:     %MAINCLASS%
echo Using CMD_LINE_ARGS: %CMD_LINE_ARGS%

start "%TITLE%" %_RUNJAVA% %JAVA_OPTS% -classpath %CLASSPATH% %MAINCLASS% %CMD_LINE_ARGS%
if not "%TITLE%" == "" exit
goto end

:exit
exit /b 1

:end