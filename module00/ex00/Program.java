import java.lang.System;

public class Program {
    static int calculate(int number) {
        int sum = 0;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        return sum;
    }
    public static void main(String[] args) {
        int number = 479598;
        
        System.out.println(calculate(number));  
    }
}