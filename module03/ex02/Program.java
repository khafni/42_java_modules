import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
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

        threadCount = Integer.parseInt(args[1].split("=")[1]);
        arraySize = Integer.parseInt(args[0].split("=")[1]);
        Random random = new Random();
        array = IntStream.generate(() -> random.nextInt(2001) - 1000).limit(arraySize).toArray();
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Integer>> results = new ArrayList<>();
        int start = 0;
        int end = 0;
        int chunkSize = arraySize / threadCount;
        for (int i = 0; i < threadCount; i++) {
            end = start + chunkSize - 1;
            if (i == threadCount - 1)
                end = arraySize - 1;
            results.add(executorService.submit(new ToSumCallable(start, end, array)));
            start = end + 1;
        }

        try {
            start = 0;
            end = 0;
            long sum = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < threadCount; i++) {
                end = start + chunkSize - 1;
                if (i == threadCount - 1)
                    end = arraySize - 1;
                stringBuilder.append("Thread ")
                        .append(i + 1)
                        .append(": ")
                        .append(start)
                        .append(" to ")
                        .append(end)
                        .append(" sum is ");
                int sum_p = results.get(i).get();
                stringBuilder.append(sum_p);
                System.out.println(stringBuilder.toString());
                stringBuilder.setLength(0);
                sum += results.get(i).get();
                start = end + 1;
            }
            System.out.println("Sum by threads: " + sum);
        } catch (ExecutionException | InterruptedException | CancellationException e ) {
            System.err.println("program failed due to thread error: " + e.getCause().toString());

        }
        executorService.shutdown();
    }
}