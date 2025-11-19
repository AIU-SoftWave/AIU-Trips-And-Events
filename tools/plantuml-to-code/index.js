#!/usr/bin/env node

/**
 * PlantUML to Code Generator (vibecoder)
 * 
 * A lightweight tool that converts PlantUML class diagrams into:
 * - TypeScript interfaces
 * - Java DTOs (Data Transfer Objects)
 * 
 * This tool provides scaffolding code from diagrams to accelerate development.
 */

const fs = require('fs');
const path = require('path');

// Paths
const DIAGRAMS_DIR = path.join(__dirname, '../../diagrams');
const GENERATED_TS_DIR = path.join(__dirname, '../../generated/ts');
const GENERATED_JAVA_DIR = path.join(__dirname, '../../generated/java');

/**
 * Parse a PlantUML file and extract class definitions
 */
function parsePlantUML(content) {
  const classes = [];
  const lines = content.split('\n');
  let currentClass = null;
  
  for (let i = 0; i < lines.length; i++) {
    const line = lines[i].trim();
    
    // Match class declaration: class ClassName {
    const classMatch = line.match(/^class\s+(\w+)\s*\{?$/);
    if (classMatch) {
      currentClass = {
        name: classMatch[1],
        properties: []
      };
      continue;
    }
    
    // Match property: +propertyName: type or -propertyName: type
    if (currentClass) {
      const propertyMatch = line.match(/^[+\-#~]?\s*(\w+)\s*:\s*(\w+(?:\[\])?)/);
      if (propertyMatch) {
        currentClass.properties.push({
          name: propertyMatch[1],
          type: propertyMatch[2]
        });
      }
      
      // End of class
      if (line === '}') {
        classes.push(currentClass);
        currentClass = null;
      }
    }
  }
  
  return classes;
}

/**
 * Map PlantUML types to TypeScript types
 */
function mapToTypeScriptType(pumlType) {
  const typeMap = {
    'String': 'string',
    'Integer': 'number',
    'int': 'number',
    'Long': 'number',
    'long': 'number',
    'Double': 'number',
    'double': 'number',
    'Float': 'number',
    'float': 'number',
    'Boolean': 'boolean',
    'boolean': 'boolean',
    'Date': 'Date',
    'LocalDate': 'string',
    'LocalDateTime': 'string',
    'Instant': 'string'
  };
  
  // Handle arrays
  if (pumlType.endsWith('[]')) {
    const baseType = pumlType.slice(0, -2);
    return `${mapToTypeScriptType(baseType)}[]`;
  }
  
  return typeMap[pumlType] || 'any';
}

/**
 * Map PlantUML types to Java types
 */
function mapToJavaType(pumlType) {
  const typeMap = {
    'String': 'String',
    'Integer': 'Integer',
    'int': 'int',
    'Long': 'Long',
    'long': 'long',
    'Double': 'Double',
    'double': 'double',
    'Float': 'Float',
    'float': 'float',
    'Boolean': 'Boolean',
    'boolean': 'boolean',
    'Date': 'java.util.Date',
    'LocalDate': 'java.time.LocalDate',
    'LocalDateTime': 'java.time.LocalDateTime',
    'Instant': 'java.time.Instant'
  };
  
  // Handle arrays/lists
  if (pumlType.endsWith('[]')) {
    const baseType = pumlType.slice(0, -2);
    return `java.util.List<${mapToJavaType(baseType)}>`;
  }
  
  return typeMap[pumlType] || pumlType;
}

/**
 * Generate TypeScript interface from class definition
 */
function generateTypeScriptInterface(classData) {
  let code = `/**\n * Generated from PlantUML diagram\n * @generated\n */\n`;
  code += `export interface ${classData.name} {\n`;
  
  for (const prop of classData.properties) {
    const tsType = mapToTypeScriptType(prop.type);
    code += `  ${prop.name}: ${tsType};\n`;
  }
  
  code += `}\n`;
  return code;
}

/**
 * Generate Java DTO from class definition
 */
function generateJavaDTO(classData) {
  const imports = new Set();
  const properties = [];
  
  for (const prop of classData.properties) {
    const javaType = mapToJavaType(prop.type);
    properties.push({ name: prop.name, type: javaType });
    
    // Collect imports
    if (javaType.includes('java.util') || javaType.includes('java.time')) {
      const importMatch = javaType.match(/^(java\.\w+\.\w+)/);
      if (importMatch) {
        imports.add(importMatch[1]);
      }
    }
  }
  
  let code = `package com.aiu.trips.dto.generated;\n\n`;
  
  // Add imports
  if (imports.size > 0) {
    Array.from(imports).sort().forEach(imp => {
      code += `import ${imp};\n`;
    });
    code += '\n';
  }
  
  code += `/**\n * Generated from PlantUML diagram\n * @generated\n */\n`;
  code += `public class ${classData.name} {\n\n`;
  
  // Add properties
  for (const prop of properties) {
    code += `    private ${prop.type} ${prop.name};\n`;
  }
  
  code += '\n';
  
  // Add getters and setters
  for (const prop of properties) {
    const capitalizedName = prop.name.charAt(0).toUpperCase() + prop.name.slice(1);
    
    // Getter
    code += `    public ${prop.type} get${capitalizedName}() {\n`;
    code += `        return ${prop.name};\n`;
    code += `    }\n\n`;
    
    // Setter
    code += `    public void set${capitalizedName}(${prop.type} ${prop.name}) {\n`;
    code += `        this.${prop.name} = ${prop.name};\n`;
    code += `    }\n\n`;
  }
  
  code += `}\n`;
  return code;
}

/**
 * Process all PlantUML files in the diagrams directory
 */
function processPlantUMLFiles() {
  // Create output directories
  if (!fs.existsSync(GENERATED_TS_DIR)) {
    fs.mkdirSync(GENERATED_TS_DIR, { recursive: true });
  }
  if (!fs.existsSync(GENERATED_JAVA_DIR)) {
    fs.mkdirSync(GENERATED_JAVA_DIR, { recursive: true });
  }
  
  // Check if diagrams directory exists
  if (!fs.existsSync(DIAGRAMS_DIR)) {
    console.log('⚠️  No diagrams directory found. Creating example...');
    fs.mkdirSync(DIAGRAMS_DIR, { recursive: true });
    
    // Create an example diagram
    const exampleDiagram = `@startuml
class User {
  +id: Long
  +username: String
  +email: String
  +createdAt: LocalDateTime
}

class Event {
  +id: Long
  +title: String
  +description: String
  +date: LocalDate
  +capacity: Integer
}
@enduml`;
    
    fs.writeFileSync(path.join(DIAGRAMS_DIR, 'example.puml'), exampleDiagram);
    console.log('✅ Created example diagram at diagrams/example.puml');
  }
  
  // Read all .puml files
  const files = fs.readdirSync(DIAGRAMS_DIR).filter(f => f.endsWith('.puml'));
  
  if (files.length === 0) {
    console.log('⚠️  No .puml files found in diagrams/');
    return;
  }
  
  console.log(`📊 Processing ${files.length} PlantUML file(s)...\n`);
  
  let totalClasses = 0;
  
  for (const file of files) {
    const filePath = path.join(DIAGRAMS_DIR, file);
    const content = fs.readFileSync(filePath, 'utf-8');
    
    console.log(`Processing: ${file}`);
    
    // Parse PlantUML
    const classes = parsePlantUML(content);
    
    if (classes.length === 0) {
      console.log(`  ⚠️  No classes found in ${file}`);
      continue;
    }
    
    console.log(`  Found ${classes.length} class(es)`);
    totalClasses += classes.length;
    
    // Generate code for each class
    for (const classData of classes) {
      console.log(`    - ${classData.name} (${classData.properties.length} properties)`);
      
      // Generate TypeScript interface
      const tsCode = generateTypeScriptInterface(classData);
      const tsPath = path.join(GENERATED_TS_DIR, `${classData.name}.ts`);
      fs.writeFileSync(tsPath, tsCode);
      
      // Generate Java DTO
      const javaCode = generateJavaDTO(classData);
      const javaPath = path.join(GENERATED_JAVA_DIR, `${classData.name}.java`);
      fs.writeFileSync(javaPath, javaCode);
    }
    
    console.log('');
  }
  
  console.log(`\n✅ Code generation complete!`);
  console.log(`   Generated ${totalClasses} TypeScript interfaces in ${GENERATED_TS_DIR}`);
  console.log(`   Generated ${totalClasses} Java DTOs in ${GENERATED_JAVA_DIR}`);
}

// Run the generator
try {
  processPlantUMLFiles();
} catch (error) {
  console.error('❌ Error:', error.message);
  process.exit(1);
}
