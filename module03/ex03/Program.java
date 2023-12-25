import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Program {
    static int threadCount;

    public static void main (String[] args) {
        if (args.length < 1)
        {
            System.err.println("wrong usage of the program:");
            System.err.println("examle:");
            System.err.println("java Program  --threadsCount=[number]");
            return;
        }
        threadCount = Integer.parseInt(args[0].split("=")[1]);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
//        for (int i = 0; i < threadCount; i++) {
//            executorService.execute(new DownloadFileRunnable(i));
//        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("files_urls.txt")))
        {
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null)
            {
//               System.out.println(line);
                executorService.execute(new DownloadFileRunnable(i, line));
                i++;
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getLocalizedMessage());
        }
        executorService.shutdown();


    }
}