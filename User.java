package Operating_System;

public class User {
    private int cardId;
    private String name;
    private String password;
    private String cardType;
    private int balance;
    private String role;
    
    public User(int cardId, String name, String password, String cardType, int balance, String role) {
        this.cardId = cardId;
        this.name = name;
        this.password = password;
        this.cardType = cardType;
        this.balance = balance;
        this.role = role;
    }
    
 // Getters and setters
    public int getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCardType() {
        return cardType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public String getRole() {
        return role;
    }
    
    
}