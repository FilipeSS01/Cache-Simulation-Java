# Memory Mapping Strategies in Java

This repository provides Java implementations of various memory mapping strategies commonly used in computer architecture. The code includes abstract classes and concrete implementations for Direct Mapping, Associative Mapping, and Set-Associative Mapping.

## Files

- `Mappings.java`: Abstract class providing common functionalities and properties for memory mappings.
- `Associative.java`: Concrete implementation of Associative Mapping strategy.
- `Direct.java`: Concrete implementation of Direct Mapping strategy.
- `SetAssociative.java`: Concrete implementation of Set-Associative Mapping strategy.
- `Replacement.java`: Utility class providing different replacement algorithms used in cache memory management.

## Usage

1. Clone the repository or download the files.
2. Import the necessary Java files into your project.
3. Utilize the provided classes to implement memory mapping in your application.

## Functionality

- **Direct Mapping**: Maps each block of main memory to exactly one cache location.
- **Associative Mapping**: Allows a block of main memory to be loaded into any cache location.
- **Set-Associative Mapping**: Divides the cache into a number of sets, each containing a number of lines. Each block of main memory can only be mapped to one set of cache locations.

## Customization

- Each mapping class allows customization of replacement algorithms like LFU (Least Frequently Used), FIFO (First In, First Out), LRU (Least Recently Used), and Random.
- Configuration parameters such as memory size, cache size, block size, etc., can be adjusted according to the specific requirements.

## Note

- Ensure to provide valid data configurations and memory data when instantiating mapping classes.
- The code includes logging statements to display initialization time and mapping results.

## Contributors

- This code was developed by Filipe Souza Silva for educational purposes.
- Contributions and feedback are welcome.


## VSCode Configuration (Visual Studio Code)

If you're using Visual Studio Code with Java extensions, you'll need to configure the `launch.json` file inside the `.vscode` folder. If this file doesn't exist, you can create it manually and add the following code:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Interface",
            "request": "launch",
            "mainClass": "interfaces.Main",
            "projectName": "Cache-Simulation-Java_886036d1",
            "vmArgs": "--module-path \"${workspaceFolder}/lib/javafx/lib\" --add-modules javafx.controls,javafx.fxml"
        }
    ]
}