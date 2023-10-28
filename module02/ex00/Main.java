import java.io.FileInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Signatures signatures = new Signatures();
        signatures.readSignatures();   
        signatures.runner();
    }
}