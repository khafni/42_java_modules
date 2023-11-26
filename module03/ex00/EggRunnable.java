public class EggRunnable implements Runnable {
   @Override
   public void run () {
      for (int i = 0; i < Program.count; i++) {
         System.out.println("Egg");
      }
   }
}
