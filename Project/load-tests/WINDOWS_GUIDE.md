# Windows Setup Guide for Performance Testing

This guide provides Windows-specific instructions for running the performance testing infrastructure.

## Prerequisites for Windows

### Required Software

1. **Docker Desktop for Windows**
   - Download from: https://www.docker.com/products/docker-desktop/
   - Ensure WSL 2 is enabled
   - After installation, verify:
     ```powershell
     docker --version
     docker-compose --version
     ```

2. **k6 Load Testing Tool**
   - **Using Chocolatey:**
     ```powershell
     choco install k6
     ```
   
   - **Using Winget:**
     ```powershell
     winget install k6
     ```
   
   - **Manual Installation:**
     - Download from: https://github.com/grafana/k6/releases
     - Extract to a folder (e.g., `C:\k6`)
     - Add to PATH environment variable
   
   - Verify installation:
     ```powershell
     k6 version
     ```

3. **PowerShell or Command Prompt**
   - PowerShell 5.1+ (pre-installed on Windows 10/11)
   - Or use Command Prompt for `.bat` scripts

## Running on Windows

### Option 1: Using PowerShell (Recommended)

```powershell
# Navigate to Project directory
cd Project

# Start monitoring stack
.\monitoring-manager.ps1 start

# Check service health
.\monitoring-manager.ps1 health

# Show service URLs
.\monitoring-manager.ps1 urls

# Run performance tests
cd load-tests
.\run-tests.ps1

# View metrics
cd ..
.\monitoring-manager.ps1 metrics
```

### Option 2: Using Command Prompt (Batch Files)

```cmd
REM Navigate to Project directory
cd Project

REM Start monitoring stack
monitoring-manager.bat start

REM Check service health
monitoring-manager.bat health

REM Show service URLs
monitoring-manager.bat urls

REM Run performance tests
cd load-tests
run-tests.bat

REM View metrics
cd ..
monitoring-manager.bat metrics
```

## Available Commands

### Monitoring Manager

**PowerShell:**
```powershell
.\monitoring-manager.ps1 [command]
```

**Batch:**
```cmd
monitoring-manager.bat [command]
```

**Commands:**
- `start` - Start all services
- `stop` - Stop all services
- `restart` - Restart all services
- `down` - Remove all services
- `clean` - Remove services and volumes (deletes data)
- `status` - Show service status
- `logs [service]` - Show logs
- `health` - Check health of all services
- `urls` - Show service URLs
- `metrics` - Show current metrics
- `help` - Show help

### Test Runner

**PowerShell:**
```powershell
.\run-tests.ps1 [-BaseUrl "http://localhost:8080"]
```

**Batch:**
```cmd
run-tests.bat
REM Or with custom URL:
set BASE_URL=http://localhost:8080
run-tests.bat
```

## Windows-Specific Considerations

### Docker Desktop Settings

1. **Enable WSL 2 Integration**
   - Docker Desktop → Settings → General
   - Check "Use the WSL 2 based engine"

2. **Resource Limits**
   - Docker Desktop → Settings → Resources
   - Recommended: 4 CPU, 8 GB RAM

3. **File Sharing**
   - Docker Desktop → Settings → Resources → File Sharing
   - Ensure your project directory is accessible

### Firewall Settings

If services are not accessible:

1. Open Windows Defender Firewall
2. Allow Docker Desktop and WSL 2
3. Or temporarily disable firewall for testing

### Port Conflicts

Check if ports are already in use:

```powershell
# PowerShell
Get-NetTCPConnection | Where-Object {$_.LocalPort -eq 8080}

# Or use netstat
netstat -ano | findstr :8080
```

If a port is in use, either:
- Stop the conflicting service
- Or modify `docker-compose.yml` to use different ports

## Accessing Services

All services use `localhost`:

- **Application:** http://localhost:8080
- **Frontend:** http://localhost:3000
- **Grafana:** http://localhost:3001 (admin/admin123)
- **Prometheus:** http://localhost:9090
- **cAdvisor:** http://localhost:8081

**Opening in Browser:**

```powershell
# PowerShell
Start-Process "http://localhost:3001"

# Or use the urls command
.\monitoring-manager.ps1 urls
```

## Troubleshooting Windows Issues

### Issue: Docker Compose Not Found

**Solution:**
```powershell
# Check if Docker is running
docker ps

# Restart Docker Desktop
# Right-click Docker Desktop icon → Restart
```

### Issue: WSL 2 Error

**Solution:**
1. Enable WSL 2:
   ```powershell
   wsl --install
   wsl --set-default-version 2
   ```

2. Restart computer
3. Restart Docker Desktop

### Issue: Port Already in Use

**Solution:**
```powershell
# Find process using port 8080
netstat -ano | findstr :8080

# Kill process (replace PID)
taskkill /PID <PID> /F
```

### Issue: Permission Denied

**Solution:**
```powershell
# Run PowerShell as Administrator
# Right-click PowerShell → Run as Administrator

# Or set execution policy
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Issue: Scripts Not Running

**Solution for PowerShell:**
```powershell
# Enable script execution
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Or bypass for single script
powershell -ExecutionPolicy Bypass -File .\monitoring-manager.ps1 start
```

### Issue: curl Not Found

**Solution:**
- curl is built into Windows 10/11 PowerShell
- For older Windows, install: `choco install curl`
- Or use PowerShell's `Invoke-WebRequest` instead

### Issue: Services Not Healthy

**Solution:**
```powershell
# Check Docker logs
docker-compose logs backend

# Restart specific service
docker-compose restart backend

# Full restart
.\monitoring-manager.ps1 down
.\monitoring-manager.ps1 start
```

## File Paths on Windows

Windows uses backslashes (`\`) in paths:

- Correct: `load-tests\scripts\auth-login-test.js`
- Also works: `load-tests/scripts/auth-login-test.js` (in most tools)

## Line Endings

Windows uses CRLF (`\r\n`) while Linux uses LF (`\n`).

If you get errors about line endings:

```powershell
# Configure Git to handle line endings
git config core.autocrlf true
```

## Performance Notes

### Windows vs Linux Performance

- Docker on Windows uses WSL 2, which may have slightly higher overhead
- Expected P95 times might be 5-10% higher than Linux
- This is normal and acceptable for the assignment

### Optimization Tips

1. **Close Unnecessary Apps:** Free up RAM and CPU
2. **Use SSD:** Ensure Docker uses SSD storage
3. **Increase Docker Resources:** 4+ CPU, 8+ GB RAM
4. **Disable Antivirus Temporarily:** Can interfere with Docker

## Quick Reference

### Start Everything
```powershell
cd Project
.\monitoring-manager.ps1 start
```

### Run Tests
```powershell
cd load-tests
.\run-tests.ps1
```

### View Results
```powershell
# Open Grafana
Start-Process "http://localhost:3001"

# Check latest results
cd results
dir | sort LastWriteTime -Descending | select -First 1
```

### Stop Everything
```powershell
cd Project
.\monitoring-manager.ps1 stop
```

## Alternative: Git Bash on Windows

If you have Git Bash installed, you can also use the Linux scripts (`.sh`):

```bash
# In Git Bash
cd Project
./monitoring-manager.sh start
cd load-tests
./run-tests.sh
```

## Next Steps

After setup is complete:

1. Follow [SETUP_GUIDE.md](./SETUP_GUIDE.md) for general instructions
2. Use [QUICK_FILL_GUIDE.md](./QUICK_FILL_GUIDE.md) to fill the report
3. Refer to [ASSIGNMENT_COMPLETE.md](./ASSIGNMENT_COMPLETE.md) for full workflow

## Getting Help

If you encounter Windows-specific issues:

1. Check Docker Desktop is running
2. Verify WSL 2 is enabled and working
3. Ensure ports are not blocked by firewall
4. Try running PowerShell as Administrator
5. Check the general troubleshooting in [SETUP_GUIDE.md](./SETUP_GUIDE.md)

---

**Platform:** Windows 10/11  
**Docker:** Docker Desktop with WSL 2  
**Scripts:** PowerShell (.ps1) and Batch (.bat) versions available
