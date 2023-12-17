public class EggRunnable implements Runnable {
   @Override
   public void run () {
      for (int i = 0; i < Program.count; i++) {
         synchronized (System.out) {
            System.out.println("Egg");
            System.out.notify();
            try {
               if (i < Program.count - 1)
                  System.out.wait();

            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   }
}
