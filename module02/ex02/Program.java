import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Program {
    public  static void main(String[] args) {
        if (args.length < 1 || args[0].split("=").length < 2) {
            System.err.println("correct usage: java Program --current-folder=[Absolute Path]");
            return ;
        }
        Menu menu = new Menu(args[0].split("=")[1]);
        menu.run();
    }
    
}
