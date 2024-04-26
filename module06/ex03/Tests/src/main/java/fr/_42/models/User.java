package fr._42.models;

public class User {
    private long Identifier;
    private String Login;
    private String Password;
    private boolean AuthenticationSuccessStatus;

    public User(String login, String password, boolean authenticationSuccessStatus) {
        Login = login;
        Password = password;
        AuthenticationSuccessStatus = authenticationSuccessStatus;
    }

    public User(long identifier, String login, String password, boolean authenticationSuccessStatus) {
        Identifier = identifier;
        Login = login;
        Password = password;
        AuthenticationSuccessStatus = authenticationSuccessStatus;
    }

    public long getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(long identifier) {
        Identifier = identifier;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isAuthenticationSuccessStatus() {
        return AuthenticationSuccessStatus;
    }

    public void setAuthenticationSuccessStatus(boolean authenticationSuccessStatus) {
        AuthenticationSuccessStatus = authenticationSuccessStatus;
    }
}
