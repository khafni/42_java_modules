package module00.ex02;

import java.util.Scanner;

public class Program {

    public static boolean checkIfPrime(int num)
    {
        for (int i = 2; i * i  <  num; i++) // if number is not a prime, it must have a divisor less than or equal to its square root
        {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    public static int sumOfDigits(int number)
    {
        int res = 0;
        while (number != 0) {
            res += number % 10; //take the last digit and add it to res
            number = number / 10; //remove the last digit from number
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        long coffeRequestCount = 0;
        while (num != 42) {
          if (checkIfPrime(sumOfDigits(num)))
            coffeRequestCount++;
            num = scanner.nextInt();
        }
        System.out.printf("Count of coffee-request : %d\n", coffeRequestCount);
        scanner.close();
    }
}
