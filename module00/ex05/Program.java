package module00.ex05;

import java.util.Scanner;

public class Program {

  
    static boolean checkIfStudentExist(String studentName, String[] students, int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].equals(studentName))
                return true;
        }
        return false;
    }

    static int storeStudents(Scanner scanner, String[] students, int numberOfStudents) {
        String input;
        outer_loop: while (true) {
            System.out.print("->  ");

            if ((input = scanner.nextLine()).equals("."))
                break;

            if (input.length() > 10) {
                System.out.println("name of a student can't be more than 10 characters");
                continue;
            }
            for (char c : input.toCharArray()) {
                if (c == ' ') {
                    System.out.println("name of a student can't contain spaces");
                    continue outer_loop;
                }
            }
            if (numberOfStudents == 10) {
                System.out.println("you can't enter more than 10 students");
                continue;
            }
            if (checkIfStudentExist(input, students, numberOfStudents)) {
                System.out.println("Student's name already registered");
                continue;
            }
            students[numberOfStudents] = input;
            numberOfStudents++;
        }
        return numberOfStudents;
    }

    static int getStudentIndex(String studentName, String[] students, int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            if (students[i].equals(studentName))
                return i;
        }
        return -1;
    }

    static String getStudentNameByIndex(int index, String[] students, int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
           return students[index];
        }
        return "";
    }

    static boolean classesNumPerWeekValidity(int hour, int day_index, int[][] monthDays) {
        int count = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if (monthDays[i][j] == 1)
                    count++;
            }
        }
        if (count == 10)
            return false;
        return true;
    }

    static void populateTimeTable(Scanner scanner, int[][] monthDays) {
        while (true) {
            System.out.print("-> ");
            String inputLine = scanner.nextLine();
            Scanner inputLineScanning = new Scanner(inputLine);
            if (!inputLineScanning.hasNextInt()) {
                if (inputLineScanning.next().equals(".") && !inputLineScanning.hasNext())
                    break;
                else {
                    System.out.println("Error: Syntax error please enter . if you want to end operations sequence");
                    continue;
                }
            }
            int hour = inputLineScanning.nextInt();
            String day = inputLineScanning.next();
            if (inputLineScanning.hasNext()) {
                System.err.println("Error: wrong syntax to fill a class");
                continue ;
            }
            inputLineScanning.close();
            int day_index = 0;
            if (hour < 1 || hour > 5) {
                System.out.println("Error: Classes can only be held on any day of week between 1 pm and 6 pm");
                continue;
            }
            switch (day) {
                case "MO":
                    day_index = 0;
                    break;
                case "TU":
                    day_index = 1;
                    break;
                case "WE":
                    day_index = 2;
                    break;
                case "TH":
                    day_index = 3;
                    break;
                case "FR":
                    day_index = 4;
                    break;
                case "SA":
                    day_index = 5;
                    break;
                case "SU":
                    day_index = 6;
                    break;
                default:
                    System.out.println("Error: please enter a correct day abbreviation");
                    continue;
            }
            if (!classesNumPerWeekValidity(hour, day_index, monthDays)) {
                System.out.println("Error: total classes per week cannot exceed 10");
                continue;
            }
            monthDays[day_index][hour - 1] = 1;
        }
    }

    static void attendanceRecording(Scanner scanner, int[][][] attendanceStore, String[] students,
            int numberOfStudents) {

        while (true) {
            System.out.print("-> ");
            String inputLine = scanner.nextLine();
            Scanner inputLineScanning = new Scanner(inputLine);
            String firstToken = inputLineScanning.next();
            if (firstToken.equals(".")) {
                if (!inputLineScanning.hasNext()) {
                    break;
                }
                System.out.println("Error: Syntax error please enter . if you want to end operations sequence");
                continue;
            }
            String studentName = firstToken;
            int index = getStudentIndex(studentName, students, numberOfStudents);
            if (index == -1) {
                System.out.println("Error: student name non existant");
                continue;
            }
            int hour = inputLineScanning.nextInt();
            int day = inputLineScanning.nextInt();
            String isPresent = inputLineScanning.next();
            int isPresentInt = 0;
            if (isPresent.equals("NOT_HERE"))
                isPresentInt = -1;
            else if (isPresent.equals("HERE"))
                isPresentInt = 1;
            
            attendanceStore[index][hour - 1][day - 1] = isPresentInt;
            inputLineScanning.close();
        }

    }

    // we can use Zeller's Congruence algorithm to determine the day of the week for
    // a given date
    // h = (q + (13*(m+1)/5) + K + (K/4) + (J/4) - 2*J) % 7
    // h is the day of the week (0 = monday, 1 = tuesday, 2 = wednesday, ...)
    // q is the day of the month (1, 2, 3, ..., 31)
    // m is the month (3 = March, 4 = April, ..., 12 = December; January and
    // February are counted as months 13 and 14 of the previous year)
    // J is the year divided by 100 (integer division)
    // K is the year within the century (i.e., year % 100)
    public static int calculateDayOfWeek(int day, int month, int year) {
        if (month < 3) {
            month += 12;
            year--;
        }

        int Z = (13 * month + 3) / 5 + day + year + year / 4 - year / 100 + year / 400;
        int dayOfWeek = Z % 7;
        return dayOfWeek;
    }

    static String dayFromIndexToString(int i) {
        switch (i) {
            case 0:
                return "MO";
            case 1:
                return "TU";
            case 2:
                return "WE";
            case 3:
                return "TH";
            case 4:
                return "FR";
            case 5:
                return "SA";
            case 6:
                return "SU";
            default:
                return "";
        }
    }
  
    static void DisplayTimeTable(int[][] monthDays, String[] students, int numberOfStudents,  int[][][] attendanceStore) {

        int dayOfWeek;
        for (int i = 1; i <= 30; i++) {
            dayOfWeek = calculateDayOfWeek(i, 9, 2020);
            if (i == 1)
                System.out.printf("%8s", " ");
            for (int j = 0; j < 5; j++) {
                if (monthDays[dayOfWeek][j] == 1) {
                    String time = (j + 1) + ":00";
                    String day = dayFromIndexToString(dayOfWeek);
                    System.out.printf("%10s", time + " " + day + " " + i + "|");
                }
            }
        }
        System.out.println("");
        for (int k = 0; k < numberOfStudents; k++) {
            for (int i = 1; i <= 30; i++) {
                dayOfWeek = calculateDayOfWeek(i, 9, 2020);
                if (i == 1)
                    System.out.printf("%8s", getStudentNameByIndex(k, students, numberOfStudents));
                for (int j = 0; j < 5; j++) {
                    if (monthDays[dayOfWeek][j] == 1) {
                        int isPresent = attendanceStore[k][j][i - 1];
                        System.out.printf("%9s|", isPresent != 0? isPresent + "": "");
                    }
                }
            }
            System.out.println("");
        }
    }


    public static void main(String[] args) {
        String[] students = new String[10];
        int numberOfStudents = 0;
        int[][] monthDays = new int[7][5];
        int[][][] attendanceStore = new int[10][5][30];
        Scanner scanner = new Scanner(System.in);

        numberOfStudents = storeStudents(scanner, students, numberOfStudents);
        populateTimeTable(scanner, monthDays);
        attendanceRecording(scanner, attendanceStore, students, numberOfStudents);
        DisplayTimeTable(monthDays, students, numberOfStudents, attendanceStore);
        scanner.close();
    }
}
