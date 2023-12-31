package fr._42.printer.app;

import com.beust.jcommander.JCommander;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import fr._42.printer.logic.BmpToCharArray;

public class Main {

    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        JCommander jCommander = JCommander.newBuilder().addObject(arguments)
                .build();
        jCommander.parse(args);

        ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                .foreground(Ansi.FColor.valueOf(arguments.getBlackColor().toUpperCase()))
                .background(Ansi.BColor.valueOf(arguments.getWhiteColor().toUpperCase()))
                .build();
        cp.setForegroundColor(Ansi.FColor.NONE);
        cp.setAttribute(Ansi.Attribute.NONE);

        Ansi.BColor whiteReplc = Ansi.BColor.valueOf(arguments.getWhiteColor().toUpperCase());
        Ansi.BColor blackReplc = Ansi.BColor.valueOf(arguments.getBlackColor().toUpperCase());

       BmpToCharArray bmpToCharArray = new BmpToCharArray();
       char[][] array = bmpToCharArray.getBmpArray();
       for (char[] line : array) {
           for (char j : line) {
               if (j == 0)
               {
                   cp.setBackgroundColor(whiteReplc);
               }
               else {
                   cp.setBackgroundColor(blackReplc);
               }
               cp.print(" ");
           }
           System.out.println("");
       }
    }
}