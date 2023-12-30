Steps:
1 - be sure you're at project root folder ImagesToChar
2 - javac -d target src/java/fr/_42/printer/app/Main.java  src/java/fr/_42/printer/logic/BmpToCharArray.java
3 - jar  cfvm  target/images-to-chars-printer.jar src/manifest.txt  -C target/ fr/_42/printer/app/Main.class -C target/ fr/_42/printer/logic/BmpToCharArray.class -C target/ resources/image.bmp
4 - java -jar target/images-to-chars-printer.jar [character to be displayed in place of white pixels] [character to be displayed in place of black pixels] [path of the bmp image]
