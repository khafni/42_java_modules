import java.io.*;
import java.net.*;

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
        URI uri = null;
        HttpURLConnection httpURLConnection = null;
        try {
            uri = new URI(urlString);
            URL url = uri.toURL();
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {

                uri = new URI(httpURLConnection.getHeaderField("Location"));
                url = uri.toURL();
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        ) {
            System.out.println(Thread.currentThread().getName() + " start download file number " + number);
            String fileName = new URI(urlString).getPath();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            FileOutputStream fos = new FileOutputStream("./" + fileName);
            byte[] buffer = new byte[40000];
            int bytesRead;

            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        catch (IOException | URISyntaxException e) {
            System.err.println("url: " + urlString + " is invalid");
        }
        System.out.println(Thread.currentThread().getName() + " finish download file number " + number);
    }
}
