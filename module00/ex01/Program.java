package module00.ex01;

import java.util.Scanner;

public class Program {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        String err_msg = "IllegalArgument";
        int num = scanner.nextInt();
        int iter = 1;
        if (num <= 1) {
            System.err.printf(err_msg);
            scanner.close();
            return ;
        }
        for (int i = 2; i * i  <=  num; i++) // if number is not a prime, it must have a divisor less than or equal to its square root
        {
            if (num % i == 0)
            {
                System.out.printf("-> false %d\n", iter);
                scanner.close();
                return ;
            }
            iter++;
        }
        System.out.printf("-> true %d\n", iter);
        scanner.close(); 
    }
}
