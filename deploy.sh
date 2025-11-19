#!/bin/bash

###############################################################################
# AIU Trips and Events - VM Deployment Script
#
# This script is designed to be run on the target VM to deploy the application.
# It pulls the latest Docker images and starts the containers using docker-compose.
#
# Prerequisites:
# - Docker and docker-compose installed on the VM
# - This script should be located in /opt/aiu-trips-events/
# - docker-compose.yml should be in the same directory
###############################################################################

set -e  # Exit on error

# Configuration
DEPLOY_DIR="/opt/aiu-trips-events"
REGISTRY="ghcr.io"
OWNER="aiu-softwave"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if running in the correct directory
if [ "$(pwd)" != "$DEPLOY_DIR" ]; then
    log_warn "Not in deployment directory. Changing to $DEPLOY_DIR"
    cd "$DEPLOY_DIR" || {
        log_error "Failed to change to deployment directory"
        exit 1
    }
fi

# Check if docker-compose.yml exists
if [ ! -f "docker-compose.yml" ]; then
    log_error "docker-compose.yml not found in $DEPLOY_DIR"
    exit 1
fi

log_info "Starting deployment process..."

# Pull the latest images
log_info "Pulling latest Docker images..."
if [ -n "$BACKEND_IMAGE" ]; then
    docker pull "$BACKEND_IMAGE" || {
        log_error "Failed to pull backend image"
        exit 1
    }
fi

if [ -n "$FRONTEND_IMAGE" ]; then
    docker pull "$FRONTEND_IMAGE" || {
        log_error "Failed to pull frontend image"
        exit 1
    }
fi

# Stop existing containers
log_info "Stopping existing containers..."
docker-compose down || log_warn "No containers to stop"

# Start new containers
log_info "Starting new containers..."
docker-compose up -d || {
    log_error "Failed to start containers"
    exit 1
}

# Wait for containers to be healthy
log_info "Waiting for containers to be healthy..."
sleep 10

# Check container status
log_info "Container status:"
docker-compose ps

# Show logs
log_info "Recent logs:"
docker-compose logs --tail=20

# Clean up old images
log_info "Cleaning up old images..."
docker image prune -f || log_warn "Failed to prune images"

log_info "Deployment completed successfully!"
log_info "Backend is running on port 8080"
log_info "Frontend is running on port 3000"

exit 0
