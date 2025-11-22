package fr._42.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int serverPort = 8081;

        // Parse command line arguments
        for (String arg : args) {
            if (arg.startsWith("--server-port=")) {
                try {
                    serverPort = Integer.parseInt(arg.substring("--server-port=".length()));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid port number: " + arg);
                    System.exit(1);
                }
            }
        }

        try (
            Socket socket = new Socket("localhost", serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            // Read welcome message
            String welcomeMessage = in.readLine();
            System.out.println(welcomeMessage);

            // Read and send command
            System.out.print("> ");
            String command = scanner.nextLine();
            out.println(command);

            if ("signUp".equals(command)) {
                // Read prompt for username
                String usernamePrompt = in.readLine();
                System.out.println(usernamePrompt);

                // Send username
                System.out.print("> ");
                String username = scanner.nextLine();
                out.println(username);

                // Read prompt for password
                String passwordPrompt = in.readLine();
                System.out.println(passwordPrompt);

                // Send password
                System.out.print("> ");
                String password = scanner.nextLine();
                out.println(password);

                // Read result
                String result = in.readLine();
                System.out.println(result);
            } else {
                // Read response for unknown command
                String response = in.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }
}
