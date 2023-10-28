
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Signatures {
    Map<String, String> signaturesMap;

    public Signatures() {
        signaturesMap = new HashMap<>();
    }

    public Map<String, String> getSignatures() {
        return signaturesMap;
    }

    public void readSignatures() {
        String filePath = "signatures.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                signaturesMap.put(words[1].trim(), words[0].trim());
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

   public void writeSignatureToResultFile(String fileType) {
    String filePath = "./result.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.append(fileType);
        writer.newLine();
    } catch (IOException io) {
        System.out.println(io.getMessage());
    }
   } 


    public void runner() {
        Scanner scanner = new Scanner(System.in);
        byte[] bytesArray = new byte[50];
        while (true) {
            System.out.print("-> ");
            String line = scanner.nextLine();
            if (line.trim().equals("42"))
                break;
            try (FileInputStream fis = new FileInputStream(line.trim())) {
                fis.read(bytesArray);
                StringBuilder resultBuilder = new StringBuilder();
                for (byte b: bytesArray) {
                    int intValue = b & 0xFF;
                    String hexNumber = Integer.toHexString(intValue).toUpperCase();
                    if (hexNumber.length() == 1)
                        hexNumber = "0" + hexNumber;
                    resultBuilder.append(hexNumber).append(" ");
                }
                String result = resultBuilder.toString();
                for (String key: signaturesMap.keySet()) {
                    // System.out.println("key |" + key + "|");
                    if (result.startsWith(key, 0) == true) {
                        writeSignatureToResultFile(signaturesMap.get(key));
                        System.out.println("PROCESSED");
                        // System.out.println(signaturesMap.get(key));
                    }
                }
            } catch(IOException io) {
                System.out.println(io.getMessage());
            }
        }
        scanner.close();
    }
}
