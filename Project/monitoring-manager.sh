#!/bin/bash

# AIU Trips & Events - Monitoring Stack Manager
# Quick commands to manage the monitoring infrastructure

set -e

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Commands
cmd_start() {
    echo -e "${BLUE}Starting monitoring stack...${NC}"
    docker-compose up -d
    echo ""
    echo -e "${GREEN}✓ Services started${NC}"
    echo ""
    cmd_status
}

cmd_stop() {
    echo -e "${YELLOW}Stopping monitoring stack...${NC}"
    docker-compose stop
    echo -e "${GREEN}✓ Services stopped${NC}"
}

cmd_restart() {
    echo -e "${YELLOW}Restarting monitoring stack...${NC}"
    docker-compose restart
    echo -e "${GREEN}✓ Services restarted${NC}"
}

cmd_down() {
    echo -e "${RED}Removing all services...${NC}"
    read -p "This will remove all containers. Continue? (y/N) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        docker-compose down
        echo -e "${GREEN}✓ Services removed${NC}"
    else
        echo "Cancelled"
    fi
}

cmd_clean() {
    echo -e "${RED}Removing all services AND volumes (all data will be lost)...${NC}"
    read -p "This will DELETE all data including Prometheus metrics and Grafana dashboards. Continue? (y/N) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        docker-compose down -v
        echo -e "${GREEN}✓ Services and volumes removed${NC}"
    else
        echo "Cancelled"
    fi
}

cmd_status() {
    echo -e "${BLUE}Service Status:${NC}"
    docker-compose ps
}

cmd_logs() {
    service=${1:-""}
    if [ -z "$service" ]; then
        docker-compose logs -f
    else
        docker-compose logs -f "$service"
    fi
}

cmd_health() {
    echo -e "${BLUE}Checking service health...${NC}"
    echo ""
    
    # Backend
    echo -n "Backend (8080): "
    if curl -f -s http://localhost:8080/actuator/health > /dev/null; then
        echo -e "${GREEN}✓ Healthy${NC}"
    else
        echo -e "${RED}✗ Unhealthy${NC}"
    fi
    
    # Frontend
    echo -n "Frontend (3000): "
    if curl -f -s http://localhost:3000 > /dev/null; then
        echo -e "${GREEN}✓ Healthy${NC}"
    else
        echo -e "${RED}✗ Unhealthy${NC}"
    fi
    
    # Prometheus
    echo -n "Prometheus (9090): "
    if curl -f -s http://localhost:9090/-/healthy > /dev/null; then
        echo -e "${GREEN}✓ Healthy${NC}"
    else
        echo -e "${RED}✗ Unhealthy${NC}"
    fi
    
    # Grafana
    echo -n "Grafana (3001): "
    if curl -f -s http://localhost:3001/api/health > /dev/null; then
        echo -e "${GREEN}✓ Healthy${NC}"
    else
        echo -e "${RED}✗ Unhealthy${NC}"
    fi
    
    # cAdvisor
    echo -n "cAdvisor (8081): "
    if curl -f -s http://localhost:8081 > /dev/null; then
        echo -e "${GREEN}✓ Healthy${NC}"
    else
        echo -e "${RED}✗ Unhealthy${NC}"
    fi
    
    echo ""
}

cmd_urls() {
    echo -e "${BLUE}Service URLs:${NC}"
    echo ""
    echo -e "Application:     ${GREEN}http://localhost:8080${NC}"
    echo -e "Frontend:        ${GREEN}http://localhost:3000${NC}"
    echo -e "Grafana:         ${GREEN}http://localhost:3001${NC} (admin/admin123)"
    echo -e "Prometheus:      ${GREEN}http://localhost:9090${NC}"
    echo -e "cAdvisor:        ${GREEN}http://localhost:8081${NC}"
    echo -e "Node Exporter:   ${GREEN}http://localhost:9100${NC}"
    echo -e "DB Exporter:     ${GREEN}http://localhost:9187${NC}"
    echo ""
}

cmd_metrics() {
    echo -e "${BLUE}Fetching current metrics...${NC}"
    echo ""
    
    # P95 latency
    echo "P95 Response Time:"
    curl -s -G 'http://localhost:9090/api/v1/query' \
        --data-urlencode 'query=histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))' \
        | jq -r '.data.result[0].value[1] // "N/A"' \
        | awk '{printf "  %.2f ms\n", $1 * 1000}'
    
    # Request rate
    echo "Request Rate:"
    curl -s -G 'http://localhost:9090/api/v1/query' \
        --data-urlencode 'query=sum(rate(http_server_requests_seconds_count[1m]))' \
        | jq -r '.data.result[0].value[1] // "N/A"' \
        | awk '{printf "  %.2f RPS\n", $1}'
    
    # Error rate
    echo "Error Rate:"
    curl -s -G 'http://localhost:9090/api/v1/query' \
        --data-urlencode 'query=(sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m])) / sum(rate(http_server_requests_seconds_count[1m]))) * 100' \
        | jq -r '.data.result[0].value[1] // "0"' \
        | awk '{printf "  %.2f%%\n", $1}'
    
    echo ""
}

cmd_help() {
    echo "AIU Trips & Events - Monitoring Stack Manager"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  start       Start all services"
    echo "  stop        Stop all services"
    echo "  restart     Restart all services"
    echo "  down        Remove all services"
    echo "  clean       Remove services and volumes (deletes all data)"
    echo "  status      Show service status"
    echo "  logs [svc]  Show logs (optionally for specific service)"
    echo "  health      Check health of all services"
    echo "  urls        Show service URLs"
    echo "  metrics     Show current performance metrics"
    echo "  help        Show this help"
    echo ""
    echo "Examples:"
    echo "  $0 start                 # Start the stack"
    echo "  $0 logs backend          # Show backend logs"
    echo "  $0 health                # Check all services"
    echo ""
}

# Main
command=${1:-help}

case "$command" in
    start)
        cmd_start
        ;;
    stop)
        cmd_stop
        ;;
    restart)
        cmd_restart
        ;;
    down)
        cmd_down
        ;;
    clean)
        cmd_clean
        ;;
    status)
        cmd_status
        ;;
    logs)
        cmd_logs "$2"
        ;;
    health)
        cmd_health
        ;;
    urls)
        cmd_urls
        ;;
    metrics)
        cmd_metrics
        ;;
    help|--help|-h)
        cmd_help
        ;;
    *)
        echo -e "${RED}Unknown command: $command${NC}"
        echo ""
        cmd_help
        exit 1
        ;;
esac
