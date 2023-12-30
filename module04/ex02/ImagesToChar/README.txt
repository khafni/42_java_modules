Steps:
1 - be sure you're at project root folder ImagesToChar
2 - javac -cp target -d target src/java/fr/_42/printer/app/Main.java src/java/fr/_42/printer/app/Arguments.java  src/java/fr/_42/printer/logic/BmpToCharArray.java
3 - jar  cfvm  target/images-to-chars-printer.jar src/manifest.txt $(find target/fr -name "*.class" | tr '\n' ' ')  -C target/ fr/_42/printer/app/Main.class -C target/ fr/_42/printer/logic/BmpToCharArray.class -C target/ resources/image.bmp  $(find target/com -name "*.class" | tr '\n' ' '
4 - java -jar target/images-to-chars-printer.jar --white [BackgroundColor --black [ForegroundColor]
