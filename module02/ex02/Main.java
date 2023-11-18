import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public  static void main(String[] args) {
        Menu menu = new Menu(args[0]);
        menu.run();
    }
    
}
