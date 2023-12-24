import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFileRunnable implements Runnable{
    int number;
    String urlString;
    public DownloadFileRunnable (int number, String urlString) {
       this.number = number;
       this.urlString = urlString;
    }

//    private
    @Override
    public void run() {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                url = new URL(httpURLConnection.getHeaderField("Location"));
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        ) {
            System.out.println(Thread.currentThread().getName() + " start download file number " + number);
            String fileName = new URL(urlString).getPath();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            FileOutputStream fos = new FileOutputStream("./" + fileName);
            byte[] buffer = new byte[40000];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        catch (IOException  e) {
            System.err.println("url: " + urlString + " is invalid");
        }
        System.out.println(Thread.currentThread().getName() + " finish download file number " + number);
    }
}
