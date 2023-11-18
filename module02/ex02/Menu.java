import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Path currentPath;

    public Menu(String abosultePath) {
        currentPath = Paths.get(abosultePath).toAbsolutePath().normalize();
    }

    private void ls() {
        // Path currentPath = Paths.get("");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)) {
            List<Path> sortedPaths = new ArrayList<>();
            stream.forEach(sortedPaths::add);
            Collections.sort(sortedPaths);

            for (Path path : sortedPaths) {
                StringBuilder stringBuilder = new StringBuilder();
                long size = Files.size(path);

                stringBuilder.append(path.getFileName()).append(" ").append(size);
                if (size < 1024)
                    stringBuilder.append(" B");
                else if (size < 1048576)
                    stringBuilder.append(" KB");
                else if (size < 1073741824)
                    stringBuilder.append(" MB");
                else if (size < 536870912000L)
                    stringBuilder.append(" GB");
                stringBuilder.append("\n");
                System.out.print(stringBuilder.toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cd(String[] commandLineSplitted) {
        if (commandLineSplitted.length < 2)
            return;
        Path currPathTmp = currentPath.resolve(commandLineSplitted[1]).normalize().toAbsolutePath();
        if (Files.isDirectory(currPathTmp) == false) {
            System.out.println("cd: not a directory: " + currPathTmp);
            return;
        }
        currentPath = currPathTmp;
        System.out.println(currentPath);
    }

    private void mv(String[] commandLineSplitted) {
        if (commandLineSplitted.length < 3) {
            return;
        }
        Path source = currentPath.resolve(commandLineSplitted[1]).normalize();
        Path target = currentPath.resolve(commandLineSplitted[2]).normalize();
        try {
            Files.move(source, target);
        } catch (IOException e) {
            System.err.println("Error: mv failed");
        }
    }

    public void run() {
        if (Files.isDirectory(currentPath) == false) {
            System.err.println("init path is not an absolute path");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("-> ");
            String commandLine = scanner.nextLine();
            String[] commandLineSplitted = commandLine.split(" ");
            String command = commandLineSplitted[0];
            if (command.equals("ls"))
                ls();
            else if (command.equals("cd"))
                cd(commandLineSplitted);
            else if (command.equals("mv"))
                mv(commandLineSplitted);
            else if (command.equals("exit")) {
                scanner.close();
                break;
            }
        }
    }
}
