public class DownloadFileRunnable implements Runnable{
    int number;
    public DownloadFileRunnable (int number) {
       this.number = number;
    }
    @Override
    public void run() {
        System.out.println("number " + 1);
    }
}
