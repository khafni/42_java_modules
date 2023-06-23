//import System.out;

import java.lang.System;
public class Program {
    public static void main(String[] args) {
        int number = 479598;
        int res = 0;
        while (number != 0) {
            res += number % 10; //take the last digit and add it to res
            number = number / 10; //remove the last digit from number
        }
        System.out.println(res);
    }
}