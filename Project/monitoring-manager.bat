@echo off
REM AIU Trips & Events - Monitoring Stack Manager (Windows)
REM Quick commands to manage the monitoring infrastructure

setlocal enabledelayedexpansion

if "%1"=="" goto help
if "%1"=="start" goto start
if "%1"=="stop" goto stop
if "%1"=="restart" goto restart
if "%1"=="down" goto down
if "%1"=="clean" goto clean
if "%1"=="status" goto status
if "%1"=="logs" goto logs
if "%1"=="health" goto health
if "%1"=="urls" goto urls
if "%1"=="metrics" goto metrics
if "%1"=="help" goto help
if "%1"=="-h" goto help
if "%1"=="--help" goto help
goto unknown

:start
echo Starting monitoring stack...
docker-compose up -d
echo.
echo [SUCCESS] Services started
echo.
goto status

:stop
echo Stopping monitoring stack...
docker-compose stop
echo [SUCCESS] Services stopped
goto :eof

:restart
echo Restarting monitoring stack...
docker-compose restart
echo [SUCCESS] Services restarted
goto :eof

:down
echo [WARNING] Removing all services...
set /p confirm="This will remove all containers. Continue? (y/N): "
if /i "%confirm%"=="y" (
    docker-compose down
    echo [SUCCESS] Services removed
) else (
    echo Cancelled
)
goto :eof

:clean
echo [WARNING] Removing all services AND volumes (all data will be lost)...
set /p confirm="This will DELETE all data including Prometheus metrics and Grafana dashboards. Continue? (y/N): "
if /i "%confirm%"=="y" (
    docker-compose down -v
    echo [SUCCESS] Services and volumes removed
) else (
    echo Cancelled
)
goto :eof

:status
echo Service Status:
docker-compose ps
goto :eof

:logs
if "%2"=="" (
    docker-compose logs -f
) else (
    docker-compose logs -f %2
)
goto :eof

:health
echo Checking service health...
echo.

REM Backend
echo|set /p="Backend (8080): "
curl -f -s http://localhost:8080/actuator/health >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Healthy
) else (
    echo [FAIL] Unhealthy
)

REM Frontend
echo|set /p="Frontend (3000): "
curl -f -s http://localhost:3000 >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Healthy
) else (
    echo [FAIL] Unhealthy
)

REM Prometheus
echo|set /p="Prometheus (9090): "
curl -f -s http://localhost:9090/-/healthy >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Healthy
) else (
    echo [FAIL] Unhealthy
)

REM Grafana
echo|set /p="Grafana (3001): "
curl -f -s http://localhost:3001/api/health >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Healthy
) else (
    echo [FAIL] Unhealthy
)

REM cAdvisor
echo|set /p="cAdvisor (8081): "
curl -f -s http://localhost:8081 >nul 2>&1
if %errorlevel%==0 (
    echo [OK] Healthy
) else (
    echo [FAIL] Unhealthy
)

echo.
goto :eof

:urls
echo Service URLs:
echo.
echo Application:     http://localhost:8080
echo Frontend:        http://localhost:3000
echo Grafana:         http://localhost:3001 (admin/admin123)
echo Prometheus:      http://localhost:9090
echo cAdvisor:        http://localhost:8081
echo Node Exporter:   http://localhost:9100
echo DB Exporter:     http://localhost:9187
echo.
goto :eof

:metrics
echo Fetching current metrics...
echo.

echo P95 Response Time:
curl -s -G "http://localhost:9090/api/v1/query" --data-urlencode "query=histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))" 2>nul | findstr /r "\"value\"" >nul
if %errorlevel%==0 (
    echo   Check Prometheus at http://localhost:9090
) else (
    echo   N/A (no data yet)
)

echo Request Rate:
curl -s -G "http://localhost:9090/api/v1/query" --data-urlencode "query=sum(rate(http_server_requests_seconds_count[1m]))" 2>nul | findstr /r "\"value\"" >nul
if %errorlevel%==0 (
    echo   Check Prometheus at http://localhost:9090
) else (
    echo   N/A (no data yet)
)

echo.
echo For detailed metrics, visit: http://localhost:9090
echo Or use Grafana dashboard: http://localhost:3001
echo.
goto :eof

:help
echo AIU Trips ^& Events - Monitoring Stack Manager
echo.
echo Usage: %~nx0 [command]
echo.
echo Commands:
echo   start       Start all services
echo   stop        Stop all services
echo   restart     Restart all services
echo   down        Remove all services
echo   clean       Remove services and volumes (deletes all data)
echo   status      Show service status
echo   logs [svc]  Show logs (optionally for specific service)
echo   health      Check health of all services
echo   urls        Show service URLs
echo   metrics     Show current performance metrics
echo   help        Show this help
echo.
echo Examples:
echo   %~nx0 start                 # Start the stack
echo   %~nx0 logs backend          # Show backend logs
echo   %~nx0 health                # Check all services
echo.
goto :eof

:unknown
echo [ERROR] Unknown command: %1
echo.
goto help
