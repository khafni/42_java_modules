public class HenRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < Program.count; i++) {
            System.out.println("Hen");
        }
    }
}
