import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public  static void main(String[] args) {
         List<String> words = Arrays.asList("Hello", "World", "Java");

        IntStream wordLengthsStream = words.stream()
            .flatMapToInt(word -> word.chars());
            // .distinct();

        wordLengthsStream.forEach(i -> System.out.println((char) i));
    }
    
}
