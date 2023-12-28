package fr._42.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BmpToCharArray {
    private char whiteC;
    private  char blackC;
    private  String imagePath;
    private BufferedImage bufferedImage;
    public  BmpToCharArray(char whiteC, char blackC, String imagePath){
        this.whiteC = whiteC;
        this.blackC = blackC;
        this.imagePath = imagePath;
        try {
            this.bufferedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public char[][] getBmpArray() {
        char [][] array = new char[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                int color = bufferedImage.getRGB(j, i);
                if (color == Color.BLACK.getRGB())
                    array[i][j] = blackC;
                else
                    array[i][j] = whiteC;

            }
        }
        return  array;
    }
}