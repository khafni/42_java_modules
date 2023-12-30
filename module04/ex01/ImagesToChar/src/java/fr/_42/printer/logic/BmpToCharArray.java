package fr._42.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BmpToCharArray {
    private char whiteC;
    private  char blackC;
    private  String imagePath;
    private BufferedImage bufferedImage;
    public  BmpToCharArray(char whiteC, char blackC){
        this.whiteC = whiteC;
        this.blackC = blackC;
        this.imagePath = imagePath;
        try {
            InputStream in = BmpToCharArray.class.getResourceAsStream("/resources/image.bmp");
            this.bufferedImage = ImageIO.read(in);
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