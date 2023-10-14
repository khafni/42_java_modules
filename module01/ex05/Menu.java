import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    TransactionsService transactionsService;
    boolean devMode;

    public Menu(String[] args) {
        transactionsService = new TransactionsService();
        devMode = false;
        if (args.length > 0 && args[0].equals("--profile=dev")){
            devMode = true;
        }
    }

    private void printCommands() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (devMode == true) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
        }
        System.out.println("7. Finish execution");
        System.out.print("-> ");
    }

    private void addUser(Scanner scanner) {
        System.out.println("Enter a user name and a balance");
        String inputLine = scanner.nextLine();
        Scanner inputScanner = new Scanner(inputLine);
        if (!inputScanner.hasNext()) {
            System.err.println("Ïnvalid Input");
            inputScanner.close();
            return ;
        }
        String name = inputScanner.next();
        double balance = 0;
        try {
            balance = inputScanner.nextDouble();
        } catch (InputMismatchException e) {
            System.err.println("entered user balance is wrong please enter a number");
         inputScanner.close();   
         return;
        }
        if (inputScanner.hasNext()) {
            System.err.println("invalid input\n");
            inputScanner.close();
            return ;
        }
        inputScanner.close();
        User newUser = transactionsService.addUser(name, balance);
        System.out.printf("User with id = %d is added \n\n", newUser.getIdentifier());
    }

    private void viewUserBalance(Scanner scanner) {
        System.out.println("Enter a user ID");
        String inputLine = scanner.nextLine();
        Scanner inputScanner = new Scanner(inputLine);
        if (!inputScanner.hasNext()) {
            System.err.println("Ïnvalid Input");
            inputScanner.close();
            return ;
        }
        int id = 0;
        try {
            id = inputScanner.nextInt();
            inputScanner.close();   
            System.out.print(transactionsService.getUserNameById(id) + " - ");
            System.out.println(transactionsService.getUserBalance(id) + "\n");
        } 
        catch (InputMismatchException e) {
            System.err.println("entered user ID is wrong please enter an Integer\n");
            inputScanner.close();   
         return;
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            inputScanner.close();
        }
    }
    
    private void performTransfer(Scanner scanner) {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        String inputLine = scanner.nextLine();
        Scanner inputScanner = new Scanner(inputLine);
        if (!inputScanner.hasNext()) {
            System.err.println("Ïnvalid Input");
            inputScanner.close();
            return ;
        }
        int senderId;
        int recipientId;
        double transferAmount;
        try {
            senderId = inputScanner.nextInt();
            recipientId = inputScanner.nextInt();
            transferAmount = inputScanner.nextDouble();
            transactionsService.performTransfer(senderId, recipientId, transferAmount);
            System.out.println("The transfer is completed\n");
        }
        catch (InputMismatchException e) {
            System.err.println("Invalid input\n");
            inputScanner.close();   
         return;
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            inputScanner.close();
        }
        catch (IllegalTransactionException e) {
            System.out.println(e.getMessage());
            inputScanner.close();
        }
    }
    private void viewTransactionsOfUserById(Scanner scanner) {
        System.out.println("Enter a user ID");
        String inputLine = scanner.nextLine();
        Scanner inputScanner = new Scanner(inputLine);
        int id = 0;
        try {
            if (!inputScanner.hasNext()) {
                System.err.println("invalid input\n");
                inputScanner.close();
                return ;
            }
            id = inputScanner.nextInt();
            
            inputScanner.close();
            
            for (Transaction transaction : transactionsService.retrieveTransactionsByUserById(id)) {
                User sender = transaction.getSender();
                User reciecpient = transaction.getRecipipient();
                if (id == sender.getIdentifier()) 
                    System.out.println("To " + reciecpient.getName() + "(id = " + reciecpient.getIdentifier() + ") " +  transaction.getTransferAmount() + " with id = " +  transaction.getIdentifier().toString());
                else
                    System.out.println("From " + sender.getName() + "(id =  " + sender.getIdentifier() + ") " +  (-1 * transaction.getTransferAmount()) + " with id = " +  transaction.getIdentifier().toString());
            }
        }
        catch (InputMismatchException e) {
            System.err.println("entered user ID is wrong please enter an Integer\n");
            inputScanner.close();
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            inputScanner.close();
        }
    }

    private void removeTransferById(Scanner scanner) {
        System.out.println("Enter a user ID and a transfer ID");
        String inputLine = scanner.nextLine();
        Scanner inputScanner = new Scanner(inputLine); 
        if (!inputScanner.hasNext()) {
                System.err.println("invalid input\n");
                inputScanner.close();
                return ;
            }
        int userId;
        String transferId;
        Transaction transaction;
        try {
            userId = inputScanner.nextInt();
            transferId = inputScanner.next();
            transaction = transactionsService.removeTransferById(userId, transferId);
            System.out.printf("Transfer To %s(id = %d) %.2f removed\n",
            transaction.getRecipipient().getName(),
            transaction.getRecipipient().getIdentifier(),
            transaction.getTransferAmount());
        }
        catch (InputMismatchException e) {
            System.err.println("entered user ID is wrong please enter an Integer\n");
            inputScanner.close();
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            inputScanner.close();
        }
    }

    private void checkTransferValidity() {
        Transaction[] uncheckTransactions = transactionsService.checkTransferValidity();
        System.out.println("Check results:");
        for (Transaction transaction: uncheckTransactions)
            System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %.1f",
                transaction.getRecipipient().getName(),
                transaction.getRecipipient().getIdentifier(),
                transaction.getIdentifier().toString(),
                transaction.getSender().getName(),
                transaction.getSender().getIdentifier(),
                transaction.getTransferAmount()
            );
    }



    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printCommands();
            String menuItem = scanner.nextLine();
            menuItem = menuItem.replace(" ", "");
            if (menuItem.equals("1"))
                addUser(scanner);
            else if (menuItem.equals("2"))
                viewUserBalance(scanner);
            else if (menuItem.equals("3"))
                performTransfer(scanner);
            else if (menuItem.equals("4"))
                viewTransactionsOfUserById(scanner);
            else if (menuItem.equals("5") && devMode == true)
                removeTransferById(scanner);
            else if (menuItem.equals("6") && devMode == true)
                checkTransferValidity();
            else if(menuItem.equals("7")) {
                scanner.close();
                break;
            }
            else {
                System.err.println("wrong input: please insert a correct menu item");
            }



        }
    }
}
