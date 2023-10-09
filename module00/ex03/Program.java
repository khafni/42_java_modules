import java.util.Scanner;


public class Program {


    //store the digit in targeted index
    static long storeValueAtIndex(long originalValue, int index, int valueDigit) {
        // 0xf is just 1111
        // we did move 1111 index * 4 times right and bitwise not it to turn those 4 bits empty and available
        long clearMask = ~(0xF << (index * 4)); //clear the targeted digit to 0
        //originalValue & clearMask gives us the original number with specific index 4 bits emptied out
        //(valueDigit << (index * 4) we just bitshift right index * 4 times so we can put the digit in targeted index
        return (originalValue & clearMask) | (valueDigit << (index * 4)); 
    }
    //extract the digit in targeted index
    static int extractDigitAtIndex(long originalValue, int index) {
        originalValue = originalValue >> (index * 4); //bitshift right index * 4 times so we can get the digit in targeted index
        return (int)(originalValue & 0xF);
    }

  
    
    static void displayWeeksGrade(long minGradeStore, int lastWeek) {
        int minGrade = 0;
        for (int i = 0; i < lastWeek; i++) {
            minGrade = extractDigitAtIndex(minGradeStore, i);
            System.out.print("Week " + i + " ");
            for(int j = 0; j < minGrade; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            
        }
    }

    static int getMinGrade(Scanner scanner)
    {
        int min = 10;
        int tmp = 0;
        for (int i = 0; i < 5; i++)
        {
            tmp = scanner.nextInt();
            if (tmp < 1 || tmp > 9) {
                System.err.println("IllegalArgument");
                scanner.close();
                System.exit(1); 
            }
            if (tmp < min)
                min = tmp;
        }
        return min;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int numberOfMonths = 18;
        long minGradeStore = 0;
        int lastWeek = 0;
        
        for (int i = 0; i < numberOfMonths; i++) {
            if (scanner.next().equals("42"))
            {
                displayWeeksGrade(minGradeStore, lastWeek);
                System.exit(1);
            }
            if (scanner.nextInt() != lastWeek + 1) {
                System.err.println("IllegalArgument");
                scanner.close();
                System.exit(1);
            }
            minGradeStore = storeValueAtIndex(minGradeStore, i, getMinGrade(scanner));
        lastWeek++;
        }
        scanner.close();
    }
}
