package module00.ex03;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Program {

    static int getLowestnum(String s) {
        String[] nums = s.split("\\s+");
        int min = Integer.MAX_VALUE;
        for (String num: nums)
        {
            if (Integer.valueOf(num) < min)
                min = Integer.valueOf(num);
        }
        return min;
    }
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int numberOfMonths = 18;
        String errMsg = "IllegalArgument";
        Map<Integer, Integer> weeksGrades = new TreeMap<>();

        for (int i = 1; i <= numberOfMonths; i++) {
            String input = scanner.nextLine();
            if (input.equals("42"))
                break;
            if (input.equals("Week " + String.valueOf(i)) == false)
            {
                System.err.println(errMsg);
                System.exit(1);
            }
            input = scanner.nextLine();
            int minGrade =  getLowestnum(input);
            weeksGrades.put(i, minGrade);
        }

        for (Integer key: weeksGrades.keySet())
        {
            System.out.printf("Week %d ", key);
            int equals = weeksGrades.get(key);
            for (int i = 0; i < equals; i++)
                System.out.printf("=");
            System.out.printf(">\n");
        }
        scanner.close();
    }
}
