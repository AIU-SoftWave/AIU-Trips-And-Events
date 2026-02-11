@echo off
setlocal EnableExtensions EnableDelayedExpansion

REM Usage: start.bat <duration_minutes> [base_url]
REM - duration_minutes: required, how long to sustain load (in minutes)
REM - base_url: optional, API base URL (default http://localhost:8080)

if "%~1"=="" (
  echo Usage: %~nx0 ^<duration_minutes^> [base_url]
  echo Example: %~nx0 10 http://localhost:8080
  exit /b 1
)

for /f "tokens=*" %%A in ("%~1") do set DURATION_MINUTES=%%~A
set /a TEST_NUM_CHECK=%DURATION_MINUTES% 2>nul
if errorlevel 1 (
  echo Error: duration_minutes must be a positive integer.
  exit /b 1
)
if %DURATION_MINUTES% LEQ 0 (
  echo Error: duration_minutes must be greater than 0.
  exit /b 1
)

set BASE_URL=%~2
if "%BASE_URL%"=="" set BASE_URL=http://localhost:8080

REM Resolve paths
set SCRIPT_DIR=%~dp0
set SCRIPTS_DIR=%SCRIPT_DIR%scripts
set RESULTS_DIR=%SCRIPT_DIR%results

if not exist "%RESULTS_DIR%" mkdir "%RESULTS_DIR%"

REM Verify k6 availability
where k6 >nul 2>&1
if errorlevel 1 (
  echo Error: k6 not found on PATH. Please install k6 or add it to PATH.
  echo See SETUP_GUIDE.md for installation steps.
  exit /b 1
)

REM Generate timestamp: yyyyMMdd_HHmmss using PowerShell for locale-independence
for /f %%i in ('powershell -NoProfile -Command "(Get-Date).ToString(^\"yyyyMMdd_HHmmss^\")"') do set TS=%%i

REM Iterate all test scripts and run each with isolated output folder
set RC=0
for %%F in ("%SCRIPTS_DIR%\*.js") do (
  set TEST_NAME=%%~nF
  set TARGET_DIR=%RESULTS_DIR%\!TEST_NAME!_!TS!
  if not exist "!TARGET_DIR!" mkdir "!TARGET_DIR!"

  echo Running !TEST_NAME! for %DURATION_MINUTES% minute^(s^) ...
  echo Output: !TARGET_DIR!

  k6 run "%%F" --out "json=!TARGET_DIR!\!TEST_NAME!.json" --summary-export "!TARGET_DIR!\!TEST_NAME!_summary.json" -e DURATION_MINUTES=%DURATION_MINUTES% -e BASE_URL=%BASE_URL%
  if errorlevel 1 (
    echo Test !TEST_NAME! failed.
    set RC=1
  ) else (
    echo Test !TEST_NAME! completed successfully.
  )
  echo.
)

if %RC% NEQ 0 (
  echo One or more tests failed. See output folders in %RESULTS_DIR%.
  exit /b %RC%
)

echo All tests completed. Results saved under %RESULTS_DIR%.
exit /b 0
