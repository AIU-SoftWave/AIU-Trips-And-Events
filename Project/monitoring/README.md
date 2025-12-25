# Monitoring Infrastructure

This directory contains the configuration for the complete monitoring stack for the AIU Trips and Events system.

## 📊 Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Monitoring Stack                         │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐         ┌──────────────┐                 │
│  │   Backend    │────────▶│  Prometheus  │                 │
│  │  (Actuator)  │ metrics │   (9090)     │                 │
│  └──────────────┘         └──────┬───────┘                 │
│                                   │                          │
│  ┌──────────────┐                │        ┌──────────────┐ │
│  │  PostgreSQL  │                │        │   Grafana    │ │
│  │  (Exporter)  │────────────────┼───────▶│   (3001)     │ │
│  └──────────────┘                │        └──────────────┘ │
│                                   │                          │
│  ┌──────────────┐                │                          │
│  │   cAdvisor   │────────────────┤                          │
│  │   (8081)     │   containers   │                          │
│  └──────────────┘                │                          │
│                                   │                          │
│  ┌──────────────┐                │                          │
│  │     Node     │────────────────┘                          │
│  │  Exporter    │    system                                 │
│  │   (9100)     │                                           │
│  └──────────────┘                                           │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Directory Structure

```
monitoring/
├── prometheus/
│   ├── prometheus.yml      # Prometheus configuration
│   └── alerts.yml          # Alert rules
├── grafana/
│   ├── provisioning/
│   │   ├── datasources/
│   │   │   └── prometheus.yml
│   │   └── dashboards/
│   │       └── dashboard.yml
│   └── dashboards/
│       └── performance-dashboard.json
└── README.md               # This file
```

## 🔧 Components

### Prometheus (Port 9090)
- **Purpose:** Metrics collection and storage
- **Scrape Interval:** 5-15 seconds
- **Retention:** 30 days
- **Configuration:** `prometheus/prometheus.yml`

**Scraped Targets:**
- Spring Boot Backend (`/actuator/prometheus`)
- PostgreSQL Exporter
- cAdvisor (container metrics)
- Node Exporter (system metrics)

### Grafana (Port 3001)
- **Purpose:** Metrics visualization
- **Credentials:** admin / admin123
- **Pre-configured Dashboard:** "AIU Trips & Events - Performance Dashboard"
- **Data Source:** Prometheus

### cAdvisor (Port 8081)
- **Purpose:** Container-level metrics
- **Metrics:** CPU, memory, network, disk I/O per container

### Node Exporter (Port 9100)
- **Purpose:** System-level metrics
- **Metrics:** CPU, memory, disk, network for the host system

### Postgres Exporter (Port 9187)
- **Purpose:** PostgreSQL-specific metrics
- **Metrics:** Connections, transactions, query performance

## 📊 Key Metrics Collected

### Application Metrics (Spring Boot Actuator)
```
# HTTP Request Metrics
http_server_requests_seconds_count
http_server_requests_seconds_sum
http_server_requests_seconds_bucket

# JVM Metrics
jvm_memory_used_bytes
jvm_memory_max_bytes
jvm_gc_pause_seconds_count
jvm_gc_pause_seconds_sum
jvm_threads_live
jvm_threads_daemon

# System Metrics
process_cpu_usage
system_cpu_usage
system_cpu_count

# Database Connection Pool (HikariCP)
hikaricp_connections_active
hikaricp_connections_idle
hikaricp_connections_max
hikaricp_connections_acquire_seconds
```

### Database Metrics (PostgreSQL)
```
pg_up                           # Database up status
pg_stat_database_tup_returned   # Rows returned
pg_stat_database_tup_fetched    # Rows fetched
pg_stat_database_tup_inserted   # Rows inserted
pg_stat_database_tup_updated    # Rows updated
pg_stat_database_xact_commit    # Transactions committed
pg_stat_activity_count          # Active connections
```

### Container Metrics (cAdvisor)
```
container_cpu_usage_seconds_total
container_memory_usage_bytes
container_network_receive_bytes_total
container_network_transmit_bytes_total
```

### System Metrics (Node Exporter)
```
node_cpu_seconds_total
node_memory_MemTotal_bytes
node_memory_MemAvailable_bytes
node_disk_io_time_seconds_total
node_network_receive_bytes_total
```

## 🎯 Performance Monitoring

### Critical SLOs to Monitor

1. **Response Time SLO: P95 < 200ms**
   ```promql
   histogram_quantile(0.95, 
     sum(rate(http_server_requests_seconds_bucket[1m])) by (le, uri)
   )
   ```

2. **Error Rate SLO: < 5%**
   ```promql
   (sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m])) 
    / sum(rate(http_server_requests_seconds_count[1m]))) * 100
   ```

3. **Throughput Target: 100 RPS**
   ```promql
   sum(rate(http_server_requests_seconds_count[1m]))
   ```

### Resource Utilization Thresholds

```
CPU Usage:        < 80%
Memory Usage:     < 85%
Connection Pool:  < 80%
GC Pause Time:    < 50ms
```

## 🚨 Alert Rules

Configured in `prometheus/alerts.yml`:

### Latency Alerts
- **HighP95Latency:** Fires when P95 > 200ms for 1 minute
- **HighP99Latency:** Warning when P99 > 500ms

### Throughput Alerts
- **LowRequestRate:** Warning when RPS < 10
- **HighErrorRate:** Critical when error rate > 5%

### Resource Alerts
- **HighCPUUsage:** Warning when CPU > 80%
- **HighMemoryUsage:** Warning when heap > 85%
- **FrequentGarbageCollection:** Warning when GC rate > 5/sec

### Database Alerts
- **HighDatabaseConnections:** Warning when pool > 80% utilized
- **SlowDatabaseQueries:** Warning when P95 acquisition > 100ms

### Health Alerts
- **ServiceDown:** Critical when backend is unreachable
- **ContainerRestarted:** Warning on container restarts

## 📈 Grafana Dashboard Panels

The pre-configured dashboard includes:

1. **Request Rate (RPS)**
   - Total requests per second
   - Success (2xx) vs Errors (5xx)

2. **Response Time Percentiles**
   - P50 (Median)
   - P95 (Target threshold)
   - P99

3. **Response Time by Endpoint**
   - Per-endpoint P95 latency
   - Identify slow endpoints

4. **JVM Memory Usage**
   - Heap usage vs max
   - Non-heap usage
   - Memory pressure indication

5. **Garbage Collection**
   - GC frequency
   - GC pause duration
   - Impact on performance

6. **Database Connection Pool**
   - Active connections
   - Idle connections
   - Pool saturation

7. **Database Connection Acquisition**
   - P95 connection wait time
   - Connection pool performance

8. **CPU Usage**
   - Application CPU %
   - System CPU %

9. **Thread Count**
   - Live threads
   - Daemon threads

10. **Error Rate**
    - Percentage of failed requests
    - Trend over time

## 🔍 Accessing Monitoring

### Grafana
1. Open http://localhost:3001
2. Login with `admin` / `admin123`
3. Navigate to "Dashboards" → "AIU Trips & Events - Performance Dashboard"
4. Set time range to match your test period

### Prometheus
1. Open http://localhost:9090
2. Go to "Status" → "Targets" to verify all targets are UP
3. Use "Graph" tab to execute custom queries
4. Check "Alerts" tab for fired alerts

### cAdvisor
1. Open http://localhost:8081
2. Browse container-specific metrics
3. View resource usage per container

## 🛠️ Configuration

### Customizing Prometheus Scrape Intervals

Edit `prometheus/prometheus.yml`:
```yaml
scrape_configs:
  - job_name: 'spring-boot-backend'
    scrape_interval: 5s  # Change this value
    static_configs:
      - targets: ['backend:8080']
```

### Adding Custom Alerts

Edit `prometheus/alerts.yml`:
```yaml
groups:
  - name: custom_alerts
    rules:
      - alert: CustomAlert
        expr: your_prometheus_query > threshold
        for: 1m
        labels:
          severity: warning
        annotations:
          summary: "Custom alert description"
```

Reload Prometheus configuration:
```bash
docker-compose exec prometheus kill -HUP 1
```

### Adding Grafana Panels

1. Open Grafana at http://localhost:3001
2. Navigate to the dashboard
3. Click "Add Panel"
4. Configure visualization and query
5. Save dashboard
6. Export JSON and save to `grafana/dashboards/`

## 📊 Useful Prometheus Queries

### Response Time Analysis
```promql
# Average response time
avg(rate(http_server_requests_seconds_sum[1m]) / rate(http_server_requests_seconds_count[1m]))

# P50 response time
histogram_quantile(0.50, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))

# P95 response time
histogram_quantile(0.95, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))

# P99 response time
histogram_quantile(0.99, sum(rate(http_server_requests_seconds_bucket[1m])) by (le))
```

### Request Rate Analysis
```promql
# Total requests per second
sum(rate(http_server_requests_seconds_count[1m]))

# Requests per second by status
sum(rate(http_server_requests_seconds_count[1m])) by (status)

# Requests per second by endpoint
sum(rate(http_server_requests_seconds_count[1m])) by (uri)
```

### Error Analysis
```promql
# Error rate percentage
(sum(rate(http_server_requests_seconds_count{status=~"5.."}[1m])) / sum(rate(http_server_requests_seconds_count[1m]))) * 100

# Error count
sum(increase(http_server_requests_seconds_count{status=~"5.."}[1m]))
```

### Resource Utilization
```promql
# CPU usage percentage
rate(process_cpu_usage[1m]) * 100

# Heap usage percentage
(jvm_memory_used_bytes{area="heap"} / jvm_memory_max_bytes{area="heap"}) * 100

# Active database connections
hikaricp_connections_active

# Connection pool utilization
(hikaricp_connections_active / hikaricp_connections_max) * 100
```

## 🐛 Troubleshooting

### No Data in Grafana
1. Check Prometheus is scraping: http://localhost:9090/targets
2. Verify backend actuator: `curl http://localhost:8080/actuator/prometheus`
3. Check Grafana data source connection
4. Restart services: `docker-compose restart prometheus grafana`

### Prometheus Not Scraping
1. Check backend is accessible from Prometheus container
2. Verify configuration in `prometheus.yml`
3. Check Prometheus logs: `docker-compose logs prometheus`
4. Validate YAML syntax

### Missing Metrics
1. Ensure Spring Boot Actuator is configured
2. Verify `management.metrics.export.prometheus.enabled=true`
3. Check application.properties settings
4. Restart backend: `docker-compose restart backend`

## 📚 References

- **Prometheus Documentation:** https://prometheus.io/docs/
- **Grafana Documentation:** https://grafana.com/docs/
- **Spring Boot Actuator:** https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
- **Micrometer:** https://micrometer.io/docs/
- **cAdvisor:** https://github.com/google/cadvisor
- **Node Exporter:** https://github.com/prometheus/node_exporter
- **Postgres Exporter:** https://github.com/prometheus-community/postgres_exporter

---

**Monitoring Stack Version:** 1.0  
**Last Updated:** 2024
