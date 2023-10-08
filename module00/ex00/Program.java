//import System.out;

import java.lang.System;
public class Program {
    static int calculate(int number) {
        if (number == 0)
            return number;
        return number % 10 + calculate(number / 10); // 8 + 9 + 5 + 9 + 7 + 4
    }
    public static void main(String[] args) {
        int number = 479598;
        
        System.out.println(calculate(number));  
    }
}