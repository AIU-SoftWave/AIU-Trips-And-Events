# Quick Start Guide - Space Probe Configuration System

## 🚀 Run the System in 3 Steps

### Step 1: Start Backend (Spring Boot)
```bash
cd assginments/1/backend
mvn spring-boot:run
```
✅ Backend running on http://localhost:8080

### Step 2: Start Frontend (Next.js)
```bash
cd assginments/1/frontend
npm run dev
```
✅ Frontend running on http://localhost:3000

### Step 3: Open Browser
Navigate to http://localhost:3000

---

## 📋 Alternative: Console Demo

Run the standalone demonstration:

```bash
cd assginments/1/backend
mvn exec:java -Dexec.mainClass="com.spaceprobe.SpaceMissionApp"
```

This will print a complete demonstration of all three design patterns to the console.

---

## 🧪 Test the API Directly

### Get All Templates
```bash
curl http://localhost:8080/api/probes/templates
```

### Clone a Probe
```bash
curl -X POST http://localhost:8080/api/probes/clone/MarsTemplate \
  -H "Content-Type: application/json" \
  -d '{"payloadMass": 875.0}'
```

### Run Demo via API
```bash
curl http://localhost:8080/api/probes/demo
```

---

## 📖 What to Explore

### In the Web Interface:

1. **View Templates Tab**
   - See Mars and Jupiter probe templates
   - Click "Clone This Template" to create copies
   - Modify the payload mass
   - Observe that clones are independent

2. **Run Demo Tab**
   - Click "Run Complete Demo"
   - See all three patterns working together
   - View templates built with Builder pattern
   - See clones created with Prototype pattern
   - Verify deep copy independence

### In the Console Demo:

- Template construction using Builder pattern
- Registration in Singleton ConfigurationManager
- Multiple clones created via Prototype pattern
- Deep copy verification showing independence

---

## 🎯 Key Design Patterns to Observe

### 1. Builder Pattern
- **Where:** Template creation
- **Classes:** `MissionControl`, `MarsProbeBuilder`, `JupiterProbeBuilder`
- **Look for:** Step-by-step construction of complex probes

### 2. Prototype Pattern
- **Where:** Cloning functionality
- **Classes:** `SpaceProbe.deepClone()`
- **Look for:** Independent copies with modified payload mass

### 3. Singleton Pattern
- **Where:** Template management
- **Classes:** `ConfigurationManager.getInstance()`
- **Look for:** Single instance managing all templates

---

## 🔧 Troubleshooting

### Backend won't start
- Check if port 8080 is available
- Ensure Java 17+ is installed: `java --version`
- Ensure Maven is installed: `mvn --version`

### Frontend won't start
- Check if port 3000 is available
- Ensure Node.js 18+ is installed: `node --version`
- Run `npm install` if dependencies are missing

### Frontend can't connect to backend
- Ensure backend is running on port 8080
- Check CORS configuration
- Verify API URL in frontend code

---

## 📁 Project Structure

```
assginments/1/
├── backend/               # Java Spring Boot
│   ├── src/main/java/
│   └── pom.xml
├── frontend/              # Next.js TypeScript
│   ├── app/
│   └── package.json
├── README.md              # Full documentation
├── QUICK_START.md         # This file
└── IMPLEMENTATION_SUMMARY.md
```

---

## 💡 Tips

- **First Time Setup:** Run `mvn clean install` in backend directory
- **Clean Build:** Delete `backend/target` and `frontend/.next` if needed
- **Port Conflicts:** Change ports in `application.properties` and `package.json`
- **Hot Reload:** Both backend and frontend support hot reloading during development

---

## ✅ Success Indicators

You know it's working when:

- ✅ Backend logs show "Started SpaceProbeApplication"
- ✅ Frontend shows "Ready in XXXms"
- ✅ Web interface displays two probe templates
- ✅ Cloning creates independent copies
- ✅ Console demo prints all probe configurations

---

## 🆘 Need Help?

1. Check the full README.md for detailed documentation
2. Review IMPLEMENTATION_SUMMARY.md for architecture details
3. Examine the source code - it's well-commented
4. Check API responses for error messages

---

**Ready to Explore!** 🎉

Start with the web interface at http://localhost:3000 for the best experience.
