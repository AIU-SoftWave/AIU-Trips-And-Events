# Deployment Guide

This guide provides detailed instructions for deploying the AIU Trips and Events application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Local Development](#local-development)
- [Production Deployment](#production-deployment)
- [CI/CD Setup](#cicd-setup)
- [Troubleshooting](#troubleshooting)

## Prerequisites

### For Local Development
- Docker Desktop installed (or Docker Engine + Docker Compose)
- Git

### For Production Deployment
- VM with Docker and Docker Compose installed
- SSH access to the VM
- GitHub repository with secrets configured

## Local Development

### Quick Start with Docker

1. Clone the repository:
   ```bash
   git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
   cd AIU-Trips-And-Events
   ```

2. Start all services:
   ```bash
   docker-compose -f docker-compose.dev.yml up --build
   ```

3. Access the application:
   - Frontend: http://localhost:3000
   - Backend: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console

4. Stop all services:
   ```bash
   docker-compose -f docker-compose.dev.yml down
   ```

### Manual Development Setup

See the main [README.md](README.md) for manual setup instructions.

## Production Deployment

### VM Setup

1. **Install Docker and Docker Compose** on your VM:
   ```bash
   # Update package index
   sudo apt-get update
   
   # Install Docker
   curl -fsSL https://get.docker.com -o get-docker.sh
   sudo sh get-docker.sh
   
   # Install Docker Compose
   sudo apt-get install docker-compose-plugin
   
   # Add your user to the docker group
   sudo usermod -aG docker $USER
   ```

2. **Create deployment directory**:
   ```bash
   sudo mkdir -p /opt/aiu-trips-events
   sudo chown $USER:$USER /opt/aiu-trips-events
   cd /opt/aiu-trips-events
   ```

3. **Copy required files to the VM**:
   ```bash
   # On your local machine
   scp docker-compose.yml deploy.sh user@your-vm:/opt/aiu-trips-events/
   ```

4. **Make the deploy script executable**:
   ```bash
   chmod +x /opt/aiu-trips-events/deploy.sh
   ```

### Manual Deployment

If you want to deploy manually without GitHub Actions:

1. Build and push images to a registry (e.g., Docker Hub or GHCR):
   ```bash
   # Build backend
   cd Project/backend
   docker build -t your-registry/aiu-backend:latest .
   docker push your-registry/aiu-backend:latest
   
   # Build frontend
   cd ../frontend
   docker build -t your-registry/aiu-frontend:latest .
   docker push your-registry/aiu-frontend:latest
   ```

2. SSH to your VM and deploy:
   ```bash
   ssh user@your-vm
   cd /opt/aiu-trips-events
   export BACKEND_IMAGE=your-registry/aiu-backend:latest
   export FRONTEND_IMAGE=your-registry/aiu-frontend:latest
   docker-compose pull
   docker-compose up -d
   ```

## CI/CD Setup

### GitHub Secrets Configuration

Configure the following secrets in your GitHub repository (Settings → Secrets and variables → Actions):

| Secret Name | Description | How to Obtain |
|------------|-------------|---------------|
| `SSH_HOST` | VM hostname or IP | Your VM's public IP or domain |
| `SSH_USER` | SSH username | User account on the VM with Docker permissions |
| `SSH_PRIVATE_KEY` | SSH private key | Generate with `ssh-keygen -t ed25519 -C "github-actions"` and add public key to VM's `~/.ssh/authorized_keys` |
| `SSH_PORT` | SSH port (optional) | Default: 22, or your custom SSH port |

### Setting up SSH Keys

1. **Generate an SSH key pair** (on your local machine):
   ```bash
   ssh-keygen -t ed25519 -C "github-actions-deploy" -f ~/.ssh/aiu_deploy_key
   ```

2. **Add the public key to your VM**:
   ```bash
   ssh-copy-id -i ~/.ssh/aiu_deploy_key.pub user@your-vm
   # Or manually:
   cat ~/.ssh/aiu_deploy_key.pub | ssh user@your-vm "mkdir -p ~/.ssh && cat >> ~/.ssh/authorized_keys"
   ```

3. **Add the private key to GitHub Secrets**:
   - Go to your repository → Settings → Secrets and variables → Actions
   - Click "New repository secret"
   - Name: `SSH_PRIVATE_KEY`
   - Value: Copy the entire contents of `~/.ssh/aiu_deploy_key` (the private key)

### GitHub Container Registry (GHCR)

The deployment workflow automatically uses GitHub Container Registry (GHCR) to store Docker images.

- Images are automatically pushed on every commit to `main`
- Public access: Images can be public or private (configure in package settings)
- No additional authentication needed (uses `GITHUB_TOKEN`)

### Workflow Triggers

#### CI Workflow
- Triggers on: Push and Pull Request to `main` and `develop` branches
- Runs: Backend tests (Maven) and Frontend tests (npm)

#### Deployment Workflow
- Triggers on: Push to `main` branch or manual workflow dispatch
- Builds: Multi-arch Docker images (linux/amd64, linux/arm64)
- Deploys: Automatically deploys to configured VM

#### Diagram Code Generation Workflow
- Triggers on: Changes to `diagrams/*.puml` files
- Generates: TypeScript interfaces and Java DTOs
- Creates: Pull request with generated code

## Troubleshooting

### Common Issues

#### "Permission denied" when deploying via SSH
- Ensure the deploy user is in the `docker` group:
  ```bash
  sudo usermod -aG docker $USER
  newgrp docker  # Refresh group membership
  ```
- Verify SSH key is correctly configured in GitHub secrets

#### Container fails to start
- Check logs:
  ```bash
  docker-compose logs backend
  docker-compose logs frontend
  ```
- Ensure ports 8080 and 3000 are not already in use
- Verify environment variables are correctly set

#### Frontend can't connect to backend
- Check `NEXT_PUBLIC_API_URL` environment variable
- Ensure backend is running: `docker-compose ps`
- Check network connectivity: `docker network ls`

#### Deployment workflow fails
- Verify all GitHub secrets are configured
- Check SSH connectivity: `ssh user@your-vm`
- Ensure Docker daemon is running on VM: `sudo systemctl status docker`

#### Code generation fails
- Ensure PlantUML syntax is correct in `.puml` files
- Check Node.js version (should be 18+)
- Verify `tools/plantuml-to-code/index.js` has execute permissions

### Viewing Logs

#### On the VM
```bash
# View all logs
docker-compose logs

# Follow logs in real-time
docker-compose logs -f

# View logs for specific service
docker-compose logs backend
docker-compose logs frontend
```

#### GitHub Actions Logs
- Go to repository → Actions
- Click on the failed workflow
- Click on the failed job to view detailed logs

### Rollback

To rollback to a previous version:

```bash
# On the VM
cd /opt/aiu-trips-events

# Set the image tag to a previous commit SHA
export BACKEND_IMAGE=ghcr.io/aiu-softwave/aiu-backend:PREVIOUS_SHA
export FRONTEND_IMAGE=ghcr.io/aiu-softwave/aiu-frontend:PREVIOUS_SHA

# Redeploy
docker-compose down
docker-compose up -d
```

## Security Considerations

1. **SSH Keys**: Use dedicated SSH keys for deployment, not your personal keys
2. **Secrets**: Never commit secrets to the repository
3. **Firewall**: Configure firewall rules to restrict access to necessary ports only
4. **Updates**: Keep Docker and system packages updated
5. **HTTPS**: Use a reverse proxy (nginx/Caddy) with SSL certificates for production

## Performance Optimization

1. **Enable Docker build cache** in GitHub Actions (already configured)
2. **Use multi-stage builds** to minimize image size (already implemented)
3. **Configure resource limits** in docker-compose.yml if needed
4. **Enable gzip compression** in reverse proxy
5. **Use CDN** for static assets if traffic increases

## Monitoring

Consider adding monitoring tools:

- **Container metrics**: Use `docker stats` or Prometheus
- **Application logs**: Centralize with ELK stack or Loki
- **Uptime monitoring**: Use UptimeRobot or similar service
- **Alerts**: Configure alerts for service failures

## Backup and Recovery

### Database Backup

Since the application uses H2 database with file storage:

```bash
# Backup database
docker exec aiu-backend tar czf /tmp/db-backup.tar.gz /data
docker cp aiu-backend:/tmp/db-backup.tar.gz ./backup-$(date +%Y%m%d).tar.gz

# Restore database
docker cp backup-YYYYMMDD.tar.gz aiu-backend:/tmp/
docker exec aiu-backend tar xzf /tmp/backup-YYYYMMDD.tar.gz -C /
docker-compose restart backend
```

### Configuration Backup

Keep a backup of:
- `docker-compose.yml`
- Environment files (if any)
- Deployment scripts

## Next Steps

After successful deployment:

1. Configure domain name and SSL certificate
2. Set up automated backups
3. Configure monitoring and alerts
4. Review and optimize application performance
5. Set up staging environment for testing
