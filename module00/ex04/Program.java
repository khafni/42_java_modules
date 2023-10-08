//main class

import java.util.Scanner;

public class Program {

    
    static void printHistogram(char[] characters, int[] frequencies) {
        // Your histogram printing code goes here (same code as provided earlier)
        int maxFrequency = frequencies[0];
        double scale = 10.0 / maxFrequency;

        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j < 10; j++) {
                int height = (int)(frequencies[j] * scale);
                if (i == 11) 
                    System.out.print(" " + characters[j] + "  ");
                else if (i < 11 && i >= (11 - height))
                    System.out.print(" #" + "  ");
                else if (i < 11 && i >= (11 - height - 1))
                {
                    if (frequencies[j] > 10) {
                        System.out.print(frequencies[j] + "  ");
                    } else {
                        System.out.print(" " + frequencies[j] + "  ");
                    }
                }
            }
            System.out.print("\n");
        }  
       
       

    
    }

    public static void main(String[] args) {
        int[] store = new int[65535];
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine();
        char[] inputCharArr = input.toCharArray();
        scanner.close();
        for (int i = 0; i < input.length(); i++) {
                store[inputCharArr[i]]++;
        }
        char[] maxTenChars = new char[10];
        int[] maxTenCharsValues = new int[10];
        for (int i = 0; i < 10; i++) {
            char maxTenChar = '\0';
            int maxTenCharsValue = 0;
            for (int j = 0; j < 65535; j++ ) {
                if (store[j] > maxTenCharsValue) {
                    maxTenChar = (char)j;
                    maxTenCharsValue = store[j];
                    }
            }
            if (maxTenChar == 0)
                break;            
                maxTenChars[i] = maxTenChar;
                maxTenCharsValues[i] = maxTenCharsValue;
            store[maxTenChar] = 0;
        }
        printHistogram(maxTenChars, maxTenCharsValues);

    }
}