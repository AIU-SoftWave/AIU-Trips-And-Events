#!/bin/bash

# Performance Testing Quick Setup Script
# Automates the setup and execution of performance tests

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print colored output
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check prerequisites
check_prerequisites() {
    print_info "Checking prerequisites..."
    
    # Check Docker
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed. Please install Docker first."
        exit 1
    fi
    print_success "Docker found: $(docker --version)"
    
    # Check Docker Compose
    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose is not installed. Please install Docker Compose first."
        exit 1
    fi
    print_success "Docker Compose found: $(docker-compose --version)"
    
    # Check k6
    if ! command -v k6 &> /dev/null; then
        print_warning "k6 is not installed. You can install it later or now."
        read -p "Install k6 now? (y/n) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            install_k6
        else
            print_info "You can install k6 later from: https://k6.io/docs/getting-started/installation/"
        fi
    else
        print_success "k6 found: $(k6 version)"
    fi
}

# Install k6
install_k6() {
    print_info "Installing k6..."
    
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew &> /dev/null; then
            brew install k6
        else
            print_error "Homebrew not found. Install from: https://k6.io/docs/getting-started/installation/"
            exit 1
        fi
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Linux
        sudo gpg -k
        sudo gpg --no-default-keyring --keyring /usr/share/keyrings/k6-archive-keyring.gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys C5AD17C747E3415A3642D57D77C6C491D6AC1D69
        echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
        sudo apt-get update
        sudo apt-get install k6
    else
        print_error "Unsupported OS. Please install k6 manually from: https://k6.io/docs/getting-started/installation/"
        exit 1
    fi
    
    print_success "k6 installed successfully"
}

# Start monitoring infrastructure
start_monitoring() {
    print_info "Starting monitoring infrastructure (Prometheus + Grafana)..."
    
    docker-compose -f docker-compose-monitoring.yml up -d
    
    print_info "Waiting for services to be healthy (30 seconds)..."
    sleep 30
    
    # Check service health
    if docker-compose -f docker-compose-monitoring.yml ps | grep -q "unhealthy"; then
        print_error "Some services are unhealthy. Check logs with: docker-compose logs"
        exit 1
    fi
    
    print_success "All services are running"
    print_info "Grafana: http://localhost:3001 (admin/admin123)"
    print_info "Prometheus: http://localhost:9090"
    print_info "Application: http://localhost:8080"
}

# Warm up cache
warmup_cache() {
    print_info "Warming up cache..."
    
    for i in {1..10}; do
        curl -s http://localhost:8080/api/v2/events > /dev/null
        curl -s http://localhost:8080/api/v2/events/upcoming > /dev/null
        echo -n "."
        sleep 1
    done
    echo
    
    print_success "Cache warmed up"
}

# Run load test
run_load_test() {
    print_info "Starting load test (this will take ~11 minutes)..."
    print_info "Watch Grafana dashboard at: http://localhost:3001"
    
    k6 run k6-load-test.js
    
    print_success "Load test completed!"
}

# Run quick test (2 minutes)
run_quick_test() {
    print_info "Starting quick test (2 minutes)..."
    print_info "Watch Grafana dashboard at: http://localhost:3001"
    
    k6 run --duration 2m k6-load-test.js
    
    print_success "Quick test completed!"
}

# Stop services
stop_services() {
    print_info "Stopping all services..."
    docker-compose -f docker-compose-monitoring.yml down
    print_success "Services stopped"
}

# Show logs
show_logs() {
    print_info "Showing application logs (Ctrl+C to exit)..."
    docker-compose -f docker-compose-monitoring.yml logs -f backend
}

# Main menu
show_menu() {
    echo
    echo "========================================"
    echo "  Performance Testing Quick Setup"
    echo "========================================"
    echo "1. Setup and start monitoring"
    echo "2. Run full load test (11 minutes)"
    echo "3. Run quick test (2 minutes)"
    echo "4. Warm up cache"
    echo "5. Show application logs"
    echo "6. Open Grafana dashboard"
    echo "7. Open Prometheus"
    echo "8. Stop all services"
    echo "9. Exit"
    echo "========================================"
    read -p "Select option: " choice
    
    case $choice in
        1)
            check_prerequisites
            start_monitoring
            warmup_cache
            print_success "Setup complete! Ready to run load tests."
            show_menu
            ;;
        2)
            if ! docker-compose -f docker-compose-monitoring.yml ps | grep -q "Up"; then
                print_error "Services are not running. Please run option 1 first."
            else
                run_load_test
            fi
            show_menu
            ;;
        3)
            if ! docker-compose -f docker-compose-monitoring.yml ps | grep -q "Up"; then
                print_error "Services are not running. Please run option 1 first."
            else
                run_quick_test
            fi
            show_menu
            ;;
        4)
            warmup_cache
            show_menu
            ;;
        5)
            show_logs
            show_menu
            ;;
        6)
            print_info "Opening Grafana dashboard..."
            if command -v open &> /dev/null; then
                open http://localhost:3001
            elif command -v xdg-open &> /dev/null; then
                xdg-open http://localhost:3001
            else
                print_info "Please open http://localhost:3001 in your browser"
            fi
            show_menu
            ;;
        7)
            print_info "Opening Prometheus..."
            if command -v open &> /dev/null; then
                open http://localhost:9090
            elif command -v xdg-open &> /dev/null; then
                xdg-open http://localhost:9090
            else
                print_info "Please open http://localhost:9090 in your browser"
            fi
            show_menu
            ;;
        8)
            stop_services
            show_menu
            ;;
        9)
            print_info "Goodbye!"
            exit 0
            ;;
        *)
            print_error "Invalid option"
            show_menu
            ;;
    esac
}

# Entry point
main() {
    clear
    echo
    echo "╔════════════════════════════════════════╗"
    echo "║  AIU Trips & Events                    ║"
    echo "║  Performance Testing Suite             ║"
    echo "║  CSE352 Extracurricular Activity       ║"
    echo "╚════════════════════════════════════════╝"
    echo
    
    # Check if in correct directory
    if [[ ! -f "k6-load-test.js" ]]; then
        print_error "Please run this script from the performance-testing directory"
        exit 1
    fi
    
    show_menu
}

# Run main
main
