package fr._42.printer.app;
import  fr._42.printer.logic.BmpToCharArray;


public class Main {
    public static void main(String[] args) {
        if (args.length < 3){
            System.err.println("wrong usage of the application");
            return ;
        }
        char whiteC = args[0].charAt(0);
        char blackC = args[1].charAt(0);
        String imagePath = args[2];
       BmpToCharArray bmpToCharArray = new BmpToCharArray(whiteC, blackC, imagePath);
       char[][] array = bmpToCharArray.getBmpArray();
       for (char[] line : array) {
           for (char j : line) {
               System.out.print(j);
               System.out.print(' ');
           }
           System.out.println("");
       }
    }
}