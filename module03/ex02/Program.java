import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Program {
    public static int threadCount;
    public static int arraySize;
//    public static
    public static void main(String[] args) {
        if (args.length < 2)
        {
            System.err.println("wrong usage of the program:");
            System.err.println("java Program --arraySize=[number] --threadsCount=[number]");
            return;
        }
        int[] array;
        threadCount = Integer.parseInt(args[0].split("=")[1]);
        arraySize = Integer.parseInt(args[1].split("=")[1]);
        Random random = new Random();
//        Arrays.stream(array).map(e -> random.nextInt()).
        array = IntStream.generate(random::nextInt).limit(arraySize).toArray();
//        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        int tmpEnd = 0;
        for (int i = 0; i < arraySize; i++) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.
            int start =
            System.out.println("Thread" + (i + 1) + "from " i);
        }
//        System.out.println("koka");
    }
}