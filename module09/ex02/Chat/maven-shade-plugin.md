# Understanding the Maven Shade Plugin

The Maven Shade Plugin is a powerful tool used in Maven projects to create an "uber-jar" (also known as a "fat jar" or "self-contained executable jar"). This special JAR file contains not only the compiled classes of your project but also all of its dependencies, bundled into a single archive.

## Purpose

The primary purpose of the `maven-shade-plugin` is to simplify deployment and execution of Java applications. Instead of managing a separate directory of dependency JARs alongside your application's JAR, you can distribute a single, self-contained JAR that includes everything needed to run the application. This is particularly useful for:

*   **Executable applications:** You can run the application directly using `java -jar your-application.jar` without needing to specify a complex classpath.
*   **Command-line tools:** Easy distribution of tools that have multiple dependencies.
*   **Minimizing deployment complexity:** Reduces the number of files to deploy and manage.

## How it Works

The `maven-shade-plugin` operates by repackaging compiled classes and dependencies. When executed, it typically performs the following actions:

1.  **Dependency Collection:** It collects all the project's direct and transitive dependencies.
2.  **Class Relocation/Shading:** It can optionally relocate classes from different dependencies into a common namespace (e.g., `org.apache.commons.collections` might be shaded to `com.yourcompany.shaded.org.apache.commons.collections`). This helps avoid classpath conflicts when different dependencies rely on different versions of the same library.
3.  **Resource Transformation:** It can merge or transform resource files (like `META-INF/services`, `spring.handlers`, `spring.schemas`, etc.) from different JARs to ensure they are correctly resolved within the single uber-jar. This is crucial for frameworks like Spring.
4.  **Manifest Customization:** It typically configures the `MANIFEST.MF` file within the uber-jar to specify the `Main-Class` entry, making the JAR directly executable.

## The `original-` Prefixed JAR

When you use the `maven-shade-plugin` with its default configuration, you might observe two JAR files in your `target` directory after running `mvn package`:

1.  **`your-artifact-id-version.jar` (the shaded/uber-jar):** This is the main output of the `shade` plugin. It contains your project's classes and all its dependencies. This is the JAR you would typically use for deployment or execution.
2.  **`original-your-artifact-id-version.jar` (the unshaded/original jar):** This JAR is created by the standard `maven-jar-plugin` *before* the `maven-shade-plugin` runs. The `shade` plugin, by default, takes the JAR produced by `maven-jar-plugin`, renames it by prepending `original-`, and then places its own shaded output with the project's standard artifact name. This `original-` prefixed JAR contains only the compiled classes and resources of your project, *without* its dependencies. It's generally not used for deployment when the shaded JAR is present, but it serves as an intermediate artifact or can be useful if you specifically need the "thin" JAR for other purposes (e.g., as a library dependency in another project).

### Example Configuration Snippet (from `pom.xml`)

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.4</version> <!-- Use an appropriate version -->
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <transformers>
                            <!-- This transformer makes the JAR executable -->
                            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <mainClass>fr._42.sockets.app.Main</mainClass>
                            </transformer>
                            <!-- These transformers merge Spring-specific metadata files -->
                            <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                                <resource>META-INF/spring.handlers</resource>
                            </transformer>
                            <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                                <resource>META-INF/spring.schemas</resource>
                            </transformer>
                        </transformers>
                        <!-- Optional: Exclude specific files/dependencies if needed -->
                        <!--
                        <filters>
                            <filter>
                                <artifact>*:*</artifact>
                                <excludes>
                                    <exclude>META-INF/*.SF</exclude>
                                    <exclude>META-INF/*.DSA</exclude>
                                    <exclude>META-INF/*.RSA</exclude>
                                </excludes>
                            </filter>
                        </filters>
                        -->
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <!-- The maven-jar-plugin might also be configured, but shade plugin usually handles the final output naming -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <version>3.2.0</version>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>fr._42.sockets.app.Main</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
    </plugins>
</build>
