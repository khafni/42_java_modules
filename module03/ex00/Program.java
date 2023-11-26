public class Program {
    public static int count;
    public static void main(String[] args) {
        // System.out.println(Thread.currentThread().getName());
        // testT t = new testT();
        // t.start();
        if (args.length < 1 || args[0].split("=")[0].length() < 2)
        {
            System.err.println("count must be provided as follow:");
            System.err.println("java Program --count=50");
            return ;
        }
    
        Program.count = Integer.parseInt(args[0].split("=")[1]);
        Runnable r1 = new EggRunnable();
        Runnable r2 = new HenRunnable();
        Thread t1 = new Thread(r1, "T1");
        Thread t2 = new Thread(r2, "T2");

        t1.start();
        t2.start();
        try  {
        t1.join();
        t2.join();
        } catch (InterruptedException ie) {
            System.err.println(ie.getMessage());
        }
        for (int i = 0; i < Program.count; i++) {
         System.out.println("Human");
      }
    }
}
