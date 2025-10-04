# Deployment Guide - AIU Trips and Events

## Table of Contents
1. [Local Development](#local-development)
2. [Docker Deployment](#docker-deployment)
3. [Production Deployment](#production-deployment)
4. [Environment Configuration](#environment-configuration)
5. [Database Migration](#database-migration)
6. [Monitoring and Maintenance](#monitoring-and-maintenance)

---

## Local Development

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- Maven 3.6+
- Git

### Backend Setup

1. Clone the repository:
```bash
git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events
```

2. Navigate to backend:
```bash
cd Project/backend
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

Backend runs on: `http://localhost:8080`

### Frontend Setup

1. Navigate to frontend:
```bash
cd Project/frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start development server:
```bash
npm run dev
```

Frontend runs on: `http://localhost:3000`

---

## Docker Deployment

### Prerequisites
- Docker 20.10+
- Docker Compose 2.0+

### Quick Start

1. Clone the repository:
```bash
git clone https://github.com/AIU-SoftWave/AIU-Trips-And-Events.git
cd AIU-Trips-And-Events
```

2. Create `.env` file:
```bash
cp .env.example .env
```

3. Build and run with Docker Compose:
```bash
docker-compose up --build
```

4. Access the application:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

### Docker Commands

**Start services:**
```bash
docker-compose up -d
```

**Stop services:**
```bash
docker-compose down
```

**View logs:**
```bash
docker-compose logs -f
```

**Rebuild services:**
```bash
docker-compose up --build
```

**Remove volumes:**
```bash
docker-compose down -v
```

---

## Production Deployment

### Cloud Deployment Options

#### 1. AWS Deployment

**Using EC2:**

1. Launch EC2 instance (t3.medium recommended)
2. Install Docker and Docker Compose
3. Clone repository
4. Set environment variables
5. Run with Docker Compose

**Using ECS (Elastic Container Service):**

1. Build Docker images
2. Push to ECR (Elastic Container Registry)
3. Create ECS task definitions
4. Deploy to ECS cluster

**Using Elastic Beanstalk:**

1. Package application
2. Create Elastic Beanstalk environment
3. Deploy via EB CLI or console

#### 2. Azure Deployment

**Using Azure App Service:**

1. Create App Service for backend (Java 17)
2. Create App Service for frontend (Node.js)
3. Configure application settings
4. Deploy via Azure CLI or GitHub Actions

**Using Azure Container Instances:**

1. Build and push images to Azure Container Registry
2. Create container group
3. Configure networking

#### 3. Google Cloud Platform

**Using Cloud Run:**

1. Build Docker images
2. Push to Google Container Registry
3. Deploy to Cloud Run
4. Configure custom domain

**Using Kubernetes (GKE):**

1. Create GKE cluster
2. Build and push images
3. Apply Kubernetes manifests
4. Configure ingress

#### 4. Heroku Deployment

**Backend:**
```bash
cd Project/backend
heroku create aiu-trips-backend
git push heroku main
```

**Frontend:**
```bash
cd Project/frontend
heroku create aiu-trips-frontend
git push heroku main
```

---

## Environment Configuration

### Production Environment Variables

Create a `.env` file with production values:

```env
# Backend Configuration
JWT_SECRET=<generate-secure-random-string>
JWT_EXPIRATION=86400000

# Database (Replace H2 with MongoDB in production)
MONGODB_URI=mongodb://username:password@host:27017/aiu-trips
MONGODB_DATABASE=aiu-trips

# Email Service (SendGrid example)
SMTP_HOST=smtp.sendgrid.net
SMTP_PORT=587
SMTP_USERNAME=apikey
SMTP_PASSWORD=<your-sendgrid-api-key>
EMAIL_FROM=noreply@aiu-trips.com

# Payment Gateway (Stripe example)
STRIPE_API_KEY=<your-stripe-api-key>
STRIPE_SECRET_KEY=<your-stripe-secret-key>

# Application URLs
BACKEND_URL=https://api.aiu-trips.com
FRONTEND_URL=https://aiu-trips.com

# CORS Configuration
CORS_ALLOWED_ORIGINS=https://aiu-trips.com,https://www.aiu-trips.com

# Monitoring
SENTRY_DSN=<your-sentry-dsn>
```

### Generating Secure JWT Secret

```bash
openssl rand -base64 64
```

---

## Database Migration

### From H2 to MongoDB

1. **Add MongoDB dependency to pom.xml:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

2. **Update application.properties:**

```properties
spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.database=${MONGODB_DATABASE}
```

3. **Convert JPA entities to MongoDB documents:**

```java
@Document(collection = "users")
public class User {
    @Id
    private String id;
    // ... rest of fields
}
```

4. **Update repositories to use MongoRepository:**

```java
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
```

### Database Backup

**MongoDB Backup:**
```bash
mongodump --uri="mongodb://username:password@host:27017/aiu-trips" --out=/backup
```

**MongoDB Restore:**
```bash
mongorestore --uri="mongodb://username:password@host:27017/aiu-trips" /backup/aiu-trips
```

---

## CI/CD Pipeline

### GitHub Actions Example

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  backend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          
      - name: Build with Maven
        run: |
          cd Project/backend
          mvn clean package -DskipTests
          
      - name: Build Docker image
        run: |
          cd Project/backend
          docker build -t aiu-trips-backend .
          
      - name: Push to registry
        run: |
          echo ${{ secrets.DOCKER_PASSWORD }} | docker login -u ${{ secrets.DOCKER_USERNAME }} --password-stdin
          docker push aiu-trips-backend
          
  frontend:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '20'
          
      - name: Install and Build
        run: |
          cd Project/frontend
          npm ci
          npm run build
          
      - name: Deploy to Vercel
        run: npx vercel --prod --token ${{ secrets.VERCEL_TOKEN }}
```

---

## Monitoring and Maintenance

### Health Checks

**Backend Health Endpoint:**
```bash
curl http://localhost:8080/actuator/health
```

**Docker Health Check:**
```yaml
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
  interval: 30s
  timeout: 10s
  retries: 3
```

### Logging

**Application Logs:**
```bash
# Docker logs
docker-compose logs -f backend

# File logs (if configured)
tail -f /var/log/aiu-trips/application.log
```

**Log Aggregation (Production):**
- ELK Stack (Elasticsearch, Logstash, Kibana)
- Splunk
- Datadog
- CloudWatch (AWS)

### Monitoring Tools

**Application Monitoring:**
- Spring Boot Actuator
- Prometheus + Grafana
- New Relic
- Datadog APM

**Infrastructure Monitoring:**
- AWS CloudWatch
- Azure Monitor
- Google Cloud Monitoring
- Prometheus

### Performance Optimization

**Backend:**
1. Enable database connection pooling
2. Implement caching (Redis)
3. Optimize database queries
4. Use async processing for emails

**Frontend:**
1. Enable Next.js image optimization
2. Implement code splitting
3. Use CDN for static assets
4. Enable gzip compression

### Security Best Practices

1. **Use HTTPS in production:**
```nginx
server {
    listen 443 ssl;
    server_name api.aiu-trips.com;
    
    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;
    
    location / {
        proxy_pass http://localhost:8080;
    }
}
```

2. **Enable rate limiting:**
```java
@Configuration
public class RateLimitConfig {
    @Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(100.0); // 100 requests per second
    }
}
```

3. **Regular security updates:**
```bash
# Update dependencies
mvn versions:display-dependency-updates
npm audit fix
```

### Backup Strategy

1. **Database Backups:**
   - Daily automated backups
   - Keep 30 days of backups
   - Test restore procedures monthly

2. **Application Backups:**
   - Version control (Git)
   - Docker image registry
   - Configuration backups

### Scaling

**Horizontal Scaling:**
```yaml
# docker-compose.yml with scale
services:
  backend:
    deploy:
      replicas: 3
      
  nginx:
    image: nginx
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    ports:
      - "80:80"
```

**Load Balancing (Nginx):**
```nginx
upstream backend {
    server backend1:8080;
    server backend2:8080;
    server backend3:8080;
}

server {
    location / {
        proxy_pass http://backend;
    }
}
```

---

## Troubleshooting

### Common Issues

**Issue: Port already in use**
```bash
# Find process using port
lsof -i :8080
# Kill process
kill -9 <PID>
```

**Issue: Database connection failed**
- Check database is running
- Verify connection string
- Check firewall rules

**Issue: Docker build fails**
```bash
# Clean Docker cache
docker system prune -a
# Rebuild without cache
docker-compose build --no-cache
```

### Support

For deployment issues, contact:
- Email: support@aiu-trips.com
- GitHub Issues: https://github.com/AIU-SoftWave/AIU-Trips-And-Events/issues

---

## Rollback Procedure

1. **Tag current version:**
```bash
git tag -a v1.0.0 -m "Release v1.0.0"
```

2. **Rollback to previous version:**
```bash
git checkout <previous-tag>
docker-compose up --build
```

3. **Database rollback:**
```bash
mongorestore --uri="mongodb://..." /backup/<timestamp>
```
