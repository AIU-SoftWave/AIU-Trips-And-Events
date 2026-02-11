# AIU Trips & Events - Monitoring Stack Manager (PowerShell)
# Quick commands to manage the monitoring infrastructure

param(
    [Parameter(Position=0)]
    [string]$Command = "help",
    [Parameter(Position=1)]
    [string]$Service = ""
)

function Start-Stack {
    Write-Host "Starting monitoring stack..." -ForegroundColor Blue
    docker-compose up -d
    Write-Host ""
    Write-Host "[SUCCESS] Services started" -ForegroundColor Green
    Write-Host ""
    Show-Status
}

function Stop-Stack {
    Write-Host "Stopping monitoring stack..." -ForegroundColor Yellow
    docker-compose stop
    Write-Host "[SUCCESS] Services stopped" -ForegroundColor Green
}

function Restart-Stack {
    Write-Host "Restarting monitoring stack..." -ForegroundColor Yellow
    docker-compose restart
    Write-Host "[SUCCESS] Services restarted" -ForegroundColor Green
}

function Remove-Stack {
    Write-Host "[WARNING] Removing all services..." -ForegroundColor Red
    $confirm = Read-Host "This will remove all containers. Continue? (y/N)"
    if ($confirm -eq "y" -or $confirm -eq "Y") {
        docker-compose down
        Write-Host "[SUCCESS] Services removed" -ForegroundColor Green
    } else {
        Write-Host "Cancelled"
    }
}

function Clean-Stack {
    Write-Host "[WARNING] Removing all services AND volumes (all data will be lost)..." -ForegroundColor Red
    $confirm = Read-Host "This will DELETE all data including Prometheus metrics and Grafana dashboards. Continue? (y/N)"
    if ($confirm -eq "y" -or $confirm -eq "Y") {
        docker-compose down -v
        Write-Host "[SUCCESS] Services and volumes removed" -ForegroundColor Green
    } else {
        Write-Host "Cancelled"
    }
}

function Show-Status {
    Write-Host "Service Status:" -ForegroundColor Blue
    docker-compose ps
}

function Show-Logs {
    if ($Service -eq "") {
        docker-compose logs -f
    } else {
        docker-compose logs -f $Service
    }
}

function Test-Health {
    Write-Host "Checking service health..." -ForegroundColor Blue
    Write-Host ""
    
    # Backend
    Write-Host "Backend (8080): " -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "[OK] Healthy" -ForegroundColor Green
        } else {
            Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
        }
    } catch {
        Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
    }
    
    # Frontend
    Write-Host "Frontend (3000): " -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:3000" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "[OK] Healthy" -ForegroundColor Green
        } else {
            Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
        }
    } catch {
        Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
    }
    
    # Prometheus
    Write-Host "Prometheus (9090): " -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:9090/-/healthy" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "[OK] Healthy" -ForegroundColor Green
        } else {
            Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
        }
    } catch {
        Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
    }
    
    # Grafana
    Write-Host "Grafana (3001): " -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:3001/api/health" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "[OK] Healthy" -ForegroundColor Green
        } else {
            Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
        }
    } catch {
        Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
    }
    
    # cAdvisor
    Write-Host "cAdvisor (8081): " -NoNewline
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8081" -Method Get -TimeoutSec 5 -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host "[OK] Healthy" -ForegroundColor Green
        } else {
            Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
        }
    } catch {
        Write-Host "[FAIL] Unhealthy" -ForegroundColor Red
    }
    
    Write-Host ""
}

function Show-URLs {
    Write-Host "Service URLs:" -ForegroundColor Blue
    Write-Host ""
    Write-Host "Application:     " -NoNewline; Write-Host "http://localhost:8080" -ForegroundColor Green
    Write-Host "Frontend:        " -NoNewline; Write-Host "http://localhost:3000" -ForegroundColor Green
    Write-Host "Grafana:         " -NoNewline; Write-Host "http://localhost:3001" -ForegroundColor Green; Write-Host " (admin/admin123)"
    Write-Host "Prometheus:      " -NoNewline; Write-Host "http://localhost:9090" -ForegroundColor Green
    Write-Host "cAdvisor:        " -NoNewline; Write-Host "http://localhost:8081" -ForegroundColor Green
    Write-Host "Node Exporter:   " -NoNewline; Write-Host "http://localhost:9100" -ForegroundColor Green
    Write-Host "DB Exporter:     " -NoNewline; Write-Host "http://localhost:9187" -ForegroundColor Green
    Write-Host ""
}

function Show-Metrics {
    Write-Host "Fetching current metrics..." -ForegroundColor Blue
    Write-Host ""
    
    Write-Host "P95 Response Time:"
    try {
        $query = "histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))"
        $response = Invoke-RestMethod -Uri "http://localhost:9090/api/v1/query?query=$([uri]::EscapeDataString($query))" -Method Get -TimeoutSec 5
        if ($response.data.result.Count -gt 0) {
            $value = [math]::Round([double]$response.data.result[0].value[1] * 1000, 2)
            Write-Host "  $value ms" -ForegroundColor Green
        } else {
            Write-Host "  N/A (no data yet)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "  N/A (no data yet)" -ForegroundColor Yellow
    }
    
    Write-Host "Request Rate:"
    try {
        $query = "sum(rate(http_server_requests_seconds_count[1m]))"
        $response = Invoke-RestMethod -Uri "http://localhost:9090/api/v1/query?query=$([uri]::EscapeDataString($query))" -Method Get -TimeoutSec 5
        if ($response.data.result.Count -gt 0) {
            $value = [math]::Round([double]$response.data.result[0].value[1], 2)
            Write-Host "  $value RPS" -ForegroundColor Green
        } else {
            Write-Host "  N/A (no data yet)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "  N/A (no data yet)" -ForegroundColor Yellow
    }
    
    Write-Host "Error Rate:"
    try {
        $query = "(sum(rate(http_server_requests_seconds_count{status=~`"5..`"}[1m])) / sum(rate(http_server_requests_seconds_count[1m]))) * 100"
        $response = Invoke-RestMethod -Uri "http://localhost:9090/api/v1/query?query=$([uri]::EscapeDataString($query))" -Method Get -TimeoutSec 5
        if ($response.data.result.Count -gt 0) {
            $value = [math]::Round([double]$response.data.result[0].value[1], 2)
            Write-Host "  $value%" -ForegroundColor Green
        } else {
            Write-Host "  0.00%" -ForegroundColor Green
        }
    } catch {
        Write-Host "  0.00%" -ForegroundColor Green
    }
    
    Write-Host ""
}

function Show-Help {
    Write-Host "AIU Trips & Events - Monitoring Stack Manager"
    Write-Host ""
    Write-Host "Usage: .\monitoring-manager.ps1 [command]"
    Write-Host ""
    Write-Host "Commands:"
    Write-Host "  start       Start all services"
    Write-Host "  stop        Stop all services"
    Write-Host "  restart     Restart all services"
    Write-Host "  down        Remove all services"
    Write-Host "  clean       Remove services and volumes (deletes all data)"
    Write-Host "  status      Show service status"
    Write-Host "  logs [svc]  Show logs (optionally for specific service)"
    Write-Host "  health      Check health of all services"
    Write-Host "  urls        Show service URLs"
    Write-Host "  metrics     Show current performance metrics"
    Write-Host "  help        Show this help"
    Write-Host ""
    Write-Host "Examples:"
    Write-Host "  .\monitoring-manager.ps1 start                 # Start the stack"
    Write-Host "  .\monitoring-manager.ps1 logs backend          # Show backend logs"
    Write-Host "  .\monitoring-manager.ps1 health                # Check all services"
    Write-Host ""
}

# Main
switch ($Command.ToLower()) {
    "start"   { Start-Stack }
    "stop"    { Stop-Stack }
    "restart" { Restart-Stack }
    "down"    { Remove-Stack }
    "clean"   { Clean-Stack }
    "status"  { Show-Status }
    "logs"    { Show-Logs }
    "health"  { Test-Health }
    "urls"    { Show-URLs }
    "metrics" { Show-Metrics }
    "help"    { Show-Help }
    "-h"      { Show-Help }
    "--help"  { Show-Help }
    default   {
        Write-Host "[ERROR] Unknown command: $Command" -ForegroundColor Red
        Write-Host ""
        Show-Help
        exit 1
    }
}
