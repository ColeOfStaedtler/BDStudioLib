Dependency information can be found here: https://www.jitpack.io/#ColeOfStaedtler/BDStudioLib
## Usage
### Loading a model from a bdstudio file:
```java
File modelFile = new File("replace/this/with/a/valid/path/some_name.bdstudio"); // ensure that file ends with .bdstudio

try {
  DisplayModelSchematic model = DisplayModelSchematic.fromBDStudioFile(modelFile);
} catch (IOException e) {
  // handle exception
}
```

### Basic Spawning:
```java
DisplayModelSchematic model = ...;
Location spawnLocation = ...;
model.spawn(spawnLocation, (str, display) -> {});
```
