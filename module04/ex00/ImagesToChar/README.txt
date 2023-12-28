Steps:
1 - be sure you're at project root folder ImagesToChar
2 - javac -d target src/java/fr/_42/printer/app/Main.java  src/java/fr/_42/printer/logic/BmpToCharArray.java
3 - java -cp target  fr._42.printer.app.Main [character to be displayed in place of white pixels] [character to be displayed in place of black pixels] [path of the bmp image]
