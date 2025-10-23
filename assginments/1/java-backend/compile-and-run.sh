#!/bin/bash

# Space Probe Configuration System - Compile and Run Script

echo "============================================"
echo "Space Probe Configuration System"
echo "Compile and Run Script"
echo "============================================"
echo ""

# Set the source directory
SRC_DIR="src/main/java"
BUILD_DIR="build/classes"

# Create build directory
echo "Creating build directory..."
mkdir -p $BUILD_DIR

# Compile all Java files
echo "Compiling Java source files..."
find $SRC_DIR -name "*.java" > sources.txt

javac -d $BUILD_DIR -sourcepath $SRC_DIR @sources.txt

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "============================================"
    echo "Running Space Mission Application"
    echo "============================================"
    echo ""
    
    # Run the application
    java -cp $BUILD_DIR com.spaceprobe.client.SpaceMissionApp
    
    echo ""
    echo "============================================"
    echo "Execution completed!"
    echo "============================================"
else
    echo "Compilation failed!"
    exit 1
fi

# Clean up
rm sources.txt
