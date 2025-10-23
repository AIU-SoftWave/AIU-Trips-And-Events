Creating a complex object often requires careful construction, efficient cloning, and controlled
instantiation. This assignment challenges you to integrate the **Builder, Prototype, and
Singleton** design patterns into a cohesive software solution for a common engineering problem.

**You must discuss submitted solution with TA in order to take the mark.**

# Software Engineering Assignment: Complex Object

# Construction & Management

## 1. Assignment Goal

The primary goal of this assignment is for students to demonstrate a practical understanding
of the **Builder, Prototype, and Singleton** design patterns by applying them to construct and
manage a complex real-world object.

## 2. Scenario: "Space Probe Configuration"

You are tasked with designing a software module that manages the configuration of a highly
complex object: a **Space Probe**.
A SpaceProbe object has numerous, varied, and required configuration settings, and different
missions require identical copies of a baseline configuration. Furthermore, only one instance
of the central **Configuration Manager** should ever exist in the application.

## 3. Requirements

### A. The Complex Object: SpaceProbe

The SpaceProbe object must have the following required components/attributes:

1. **Propulsion System:** (e.g., IonThruster, ChemicalRocket)
2. **Power Source:** (e.g., RadioisotopeThermoelectricGenerator, SolarPanels)
3. **Scientific Instruments:** A _list_ of at least three different instruments (e.g.,
    Spectrometer, Magnetometer, Camera).
4. **Mission Target:** (e.g., Mars, Jupiter, DeepSpace)
5. **Payload Mass:** (A numerical value, e.g., 150.5 kg).
6. A method, describe(), that prints all configuration details in a human-readable format.


### B. Design Pattern Implementation

You must implement the following three design patterns:

1. **Builder Pattern (for SpaceProbe construction):**
    ○ Create a SpaceProbeBuilder interface (or abstract class).
    ○ Create at least **two concrete builders** : e.g., MarsProbeBuilder and
       JupiterProbeBuilder.
    ○ Implement a **Director** class, MissionControl, that uses the builders to construct a
       SpaceProbe with a standard configuration.
    ○ The construction process must be step-by-step and hide the internal
       representation from the client code.
2. **Prototype Pattern (for SpaceProbe cloning):**
    ○ The SpaceProbe class must implement a cloning mechanism (e.g., a clone()
       method) to support efficient **deep copying**.
    ○ Demonstrate creating a **"Template Probe"** instance and then cloning it to create
       several "Deployment Probes" with minor, _post-cloning_ modifications (e.g., slightly
       adjusting the PayloadMass).
    ○ Verify that modifying the cloned instance **does not** affect the original prototype
       instance.
3. **Singleton Pattern (for the ConfigurationManager):**
    ○ Implement a **ConfigurationManager** class responsible for holding and
       dispensing the "Template Probe" prototypes.
    ○ Ensure that only **one instance** of ConfigurationManager can ever exist in the
       application's lifecycle.
    ○ The client code must access the ConfigurationManager via a static getInstance()
       method.

### C. Demonstration and Testing

Your solution must include a main or client class that demonstrates the use of all three
patterns:

1. Use the **Singleton ConfigurationManager** to hold and retrieve the initial MarsProbe
    and JupiterProbe prototypes.
2. Use the **Director (MissionControl) and Builders** to initially construct the two complex
    prototypes.
3. Use the **Prototype pattern** to clone the MarsProbe prototype three times and the
    JupiterProbe prototype once, modifying at least one attribute on each clone.
4. Print the details of the original prototypes and all cloned instances to verify the correct
    configuration and the successful deep copy (i.e., they are independent objects).


## 4. Deliverables and Submission

```
Deliverable Description
Source Code, and working Demo All well-documented source files (e.g., .java,
.cs, .py files) for all classes. Demo as mp4 file.
UML Class Diagram A UML Class Diagram illustrating the
structure of your design, highlighting the
relationship between the Builder, Prototype,
and Singleton classes.
Brief Report A short (approx. 500 words) report
addressing: 1. Justification for using each
pattern in this scenario. 2. Challenges
encountered during implementation and how
they were overcome.
```
## 5. Grading Rubric

```
Criteria Weight Excellent (A) Good (B) Needs
Improvement
(C/D)
Builder Pattern 30 % Correctly
implements
Builder/Director/C
oncrete Builders.
Complex object
construction is
clear and step-
wise.
```
```
Builder is
implemented but
construction logic
is slightly
confusing or the
Director is
missing/weak.
```
```
Construction is
manual or the
pattern is
incomplete/incorr
ectly applied.
```
```
Prototype
Pattern
```
```
20 % Correct use of
deep copying.
Modifications to
clones clearly do
not affect the
prototype.
```
```
Correct
implementation
but the copy
might be a
shallow copy (fails
independence
test).
```
```
Cloning
mechanism is not
implemented or is
incorrect.
```
```
Singleton
Pattern
```
```
20% Correct
implementation of
```
```
Singleton is
implemented, but
```
```
The object is
instantiated
```

```
the Singleton
pattern (private
constructor, static
instance access,
thread-safe
consideration).
```
```
thread-safety is
neglected or the
access method is
non-standard.
```
```
multiple times.
```
**Working Demo** 20 % Client code
flawlessly
demonstrates all
three patterns
working together
as required.

```
Client code shows
the patterns but
misses one or two
required steps.
```
```
Demonstration is
incomplete or
contains errors.
```
**Documentation
& Report**

```
10% Code is well-
documented. UML
is accurate.
Report provides
strong
justification and
insight.
```
```
Code and
documentation
are fair. UML or
report is present
but has minor
deficiencies.
```
```
Minimal
documentation,
missing UML, or a
very weak/absent
report.
```

