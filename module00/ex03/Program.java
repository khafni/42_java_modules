package module00.ex03;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String whichWeek;
        int numberOfMonths = 18;
        Map<Integer, Integer> weeksGrades = new HashMap<>();

        for (int i = 1; i <= numberOfMonths; i++) {
            whichWeek = scanner.nextLine();
            if (whichWeek.equals("Week " + String.valueOf(i)))
            {
                
            }
        }
        scanner.close();
    }
}
