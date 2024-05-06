package fr._42;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<String> classNames = new ArrayList<>();

//    private void printCommands() {
//        System.out.println("1. list classes in the classes package");
//        System.out.println("2. inspect class");
//        System.out.println("3. create an object");
//    }
    private void setClassesStrings() {
        String projectPath = System.getProperty("user.dir");
        Path currentPath = Paths.get(projectPath , "src/main/java/fr/_42/classes").toAbsolutePath().normalize();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath);) {
            List<Path> paths = new ArrayList<>();
            stream.forEach(paths::add);
            for (Path path : paths) {
//                System.out.println(path.getFileName());
                if (path.getFileName().toString().endsWith(".java"))
                    classNames.add(path.getFileName().toString().replace(".java", ""));
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    public Menu() {
      setClassesStrings();
//      System.out.println("Classes: ");
//      for
//      while (true) {
//
//      }
    }

    public List<String> getClassNames() {
        return classNames;
    }
}
