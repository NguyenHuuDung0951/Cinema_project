package entity;

import java.util.Objects;

public class Account {

    private String accountID;
    private String username;
    private String password;

    public Account(String accountID) {
        this.accountID = accountID;
    }

    
    
    public Account(String accountID, String username, String password) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account other = (Account) o;
        return Objects.equals(accountID, other.accountID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID);
    }
}
