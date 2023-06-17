package module00.ex01;

import java.util.Scanner;

public class Program {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        for (int i = 2; i < num; i++)
        {
            if (num % i == 0)
                System.err.printf("false %d", i);
        }
        
    }
}
