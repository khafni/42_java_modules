public class Program {
    public static int count;
    public static void main(String[] args) {
        if (args.length < 1 || args[0].split("=")[0].length() < 2)
        {
            System.err.println("count must be provided as follow:");
            System.err.println("java Program --count=50");
            return ;
        }
    
        Program.count = Integer.parseInt(args[0].split("=")[1]);
        Runnable r1 = new EggRunnable();
        Thread t1 = new Thread(r1, "T1");
        Thread t2 = new HenThread();

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
