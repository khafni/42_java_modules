class User {
    private int identifier;
    private String name;
    private double balance;

    
    public User (int identifier, String name, double balance) {
        this.identifier = identifier;
        this.name = name;
        this.balance = balance;
        if (balance < 0) {
            System.out.printf("can't create user with negative balance, setting new user with id %d balance to 0", identifier);
            this.balance = 0;
        }
    }
    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    public void setBalance(double balance) {
        if (balance < 0)
            System.out.printf("can't set user %d with a negative balance", identifier); 
        else 
            this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{identifier: " + identifier
        + ", name: " + name + ", balance: " + balance + "}";
    }
}