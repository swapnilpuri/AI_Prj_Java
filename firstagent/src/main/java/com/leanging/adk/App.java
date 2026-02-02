package com.leanging.adk;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.web.AdkWebServer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        AdkWebServer.start(
            LlmAgent.builder()
            .name("Java-Developer")
            .instruction("""
                    You are a Senior Java Architect.
                    When asked about a design pattern:
                    1. Explain the pattern.
                    2. YOU MUST call 'writeJavaFile' once for each class in the pattern.
                    Do not just output code blocks; call the tool.
                    
                    STRICT RULES for file creation:
                    - The package for ALL classes must be: com.example.<patternname>
                      (e.g., for Builder pattern use: com.example.builder)
                    - The directory path must match the package exactly:
                      src/main/java/com/example/<patternname>/
                    - The class containing the main() method MUST be named <PatternName>Main.java
                      (e.g., BuilderMain.java, SingletonMain.java, FactoryMain.java)
                    - Every .java file must have the correct package declaration matching its directory.
                    - All class files for a single pattern go in the same package/directory.
                    
                    Example for Builder pattern:
                      src/main/java/com/example/builder/Burger.java        -> package com.example.builder;
                      src/main/java/com/example/builder/Burger.Builder.java -> package com.example.builder;
                      src/main/java/com/example/builder/BuilderMain.java   -> package com.example.builder;
                    """)
            .model("gemini-2.5-flash")
            .tools(FunctionTool.create(App.class, "writeJavaFile"))
            .build()
        );
    }

    public static Map<String, String> writeJavaFile(
        @Schema(description="The relative path for the file, must follow the structure src/main/java/com/example/<patternname>/ClassName.java") String filePath,
        @Schema(description="The complete Java source code including the correct package declaration") String content
    ) {
        try {
            Path path = Paths.get(filePath);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            // Validate that the package declaration in the content matches the directory path
            String expectedPackage = path.getParent().toString()
                    .replace("src\\main\\java\\", "")  // Windows
                    .replace("src/main/java/", "")      // Unix
                    .replace('\\', '.')
                    .replace('/', '.');

            for (String line : content.lines().toList()) {
                String trimmed = line.trim();
                if (trimmed.startsWith("package ")) {
                    String declaredPackage = trimmed.replace("package ", "").replace(";", "").trim();
                    if (!declaredPackage.equals(expectedPackage)) {
                        return Map.of(
                            "status", "error",
                            "message", "Package mismatch! Declared: '" + declaredPackage +
                                       "' but expected: '" + expectedPackage + "' based on path: " + filePath
                        );
                    }
                    break;
                }
            }

            Files.writeString(path, content);
            return Map.of("status", "success", "path", path.toAbsolutePath().toString());
        } catch (IOException e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
    }
}