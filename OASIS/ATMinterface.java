import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// 1. CLASS: User - Manages individual account data
class User {
  private String userId;
  private String userPin;
  private double balance;
  private ArrayList<String> transactionHistory;

  public User(String userId, String userPin, double initialBalance) {
    this.userId = userId;
    this.userPin = userPin;
    this.balance = initialBalance;
    this.transactionHistory = new ArrayList<>();
    transactionHistory.add("Account opened with balance: $" + initialBalance);
  }

  public String getUserId() {
    return userId;
  }

  public String getUserPin() {
    return userPin;
  }

  public double getBalance() {
    return balance;
  }

  public ArrayList<String> getHistory() {
    return transactionHistory;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void addTransaction(String message) {
    transactionHistory.add(message);
  }
}

// 2. CLASS: Transaction - Handles logging of actions
class Transaction {
  public static void log(User user, String type, double amount) {
    String entry = String.format("%s: $%.2f | New Balance: $%.2f", type, amount, user.getBalance());
    user.addTransaction(entry);
  }
}

// 3. CLASS: BankDatabase - Simulates the backend storage
class BankDatabase {
  private HashMap<String, User> accounts = new HashMap<>();

  public BankDatabase() {
    // Sample accounts: (ID, PIN, Initial Balance)
    accounts.put("user123", new User("user123", "1234", 1000.0));
    accounts.put("admin", new User("admin", "0000", 5000.0));
  }

  public User authenticate(String id, String pin) {
    User user = accounts.get(id);
    if (user != null && user.getUserPin().equals(pin)) {
      return user;
    }
    return null;
  }

  public User getUser(String id) {
    return accounts.get(id);
  }
}

// 4. CLASS: ATM - Handles the menu logic and operations
class ATM {
  private User currentUser;
  private BankDatabase db;
  private Scanner sc = new Scanner(System.in);

  public ATM(User user, BankDatabase db) {
    this.currentUser = user;
    this.db = db;
  }

  public void showMenu() {
    while (true) {
      System.out.println("\n********** ATM MENU **********");
      System.out.println("1. Transaction History");
      System.out.println("2. Withdraw");
      System.out.println("3. Deposit");
      System.out.println("4. Transfer");
      System.out.println("5. Quit");
      System.out.print("Select an option: ");

      if (!sc.hasNextInt()) {
        System.out.println("Invalid input. Please enter a number.");
        sc.next();
        continue;
      }

      int choice = sc.nextInt();
      switch (choice) {
        case 1:
          showHistory();
          break;
        case 2:
          withdraw();
          break;
        case 3:
          deposit();
          break;
        case 4:
          transfer();
          break;
        case 5:
          System.out.println("Exiting... Thank you for using our ATM!");
          return;
        default:
          System.out.println("Invalid selection.");
      }
    }
  }

  private void showHistory() {
    System.out.println("\n--- TRANSACTION HISTORY ---");
    for (String record : currentUser.getHistory()) {
      System.out.println(record);
    }
  }

  private void withdraw() {
    System.out.print("Enter amount to withdraw: ");
    double amount = sc.nextDouble();
    if (amount > 0 && amount <= currentUser.getBalance()) {
      currentUser.setBalance(currentUser.getBalance() - amount);
      Transaction.log(currentUser, "Withdraw", amount);
      System.out.println("Withdrawal successful. Please collect your cash.");
    } else {
      System.out.println("Insufficient funds or invalid amount.");
    }
  }

  private void deposit() {
    System.out.print("Enter amount to deposit: ");
    double amount = sc.nextDouble();
    if (amount > 0) {
      currentUser.setBalance(currentUser.getBalance() + amount);
      Transaction.log(currentUser, "Deposit", amount);
      System.out.println("Deposit successful.");
    } else {
      System.out.println("Invalid amount.");
    }
  }

  private void transfer() {
    System.out.print("Enter recipient User ID: ");
    String rid = sc.next();
    User recipient = db.getUser(rid);

    if (recipient != null && !rid.equals(currentUser.getUserId())) {
      System.out.print("Enter transfer amount: ");
      double amount = sc.nextDouble();
      if (amount > 0 && amount <= currentUser.getBalance()) {
        currentUser.setBalance(currentUser.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        Transaction.log(currentUser, "Transfer to " + rid, amount);
        recipient.addTransaction("Received from " + currentUser.getUserId() + ": $" + amount);
        System.out.println("Transfer successful.");
      } else {
        System.out.println("Transfer failed: Insufficient funds.");
      }
    } else {
      System.out.println("Error: Recipient ID not found or invalid.");
    }
  }
}

// 5. CLASS: ATMSystem - Entry point (Main)
public class ATMinterface {
  public static void main(String[] args) {
    BankDatabase database = new BankDatabase();
    Scanner input = new Scanner(System.in);

    System.out.println("=== ATM INTERFACE SYSTEM ===");
    System.out.print("User ID: ");
    String id = input.nextLine();
    System.out.print("User PIN: ");
    String pin = input.nextLine();

    User user = database.authenticate(id, pin);

    if (user != null) {
      System.out.println("Login Successful! Welcome, " + id);
      ATM atm = new ATM(user, database);
      atm.showMenu();
    } else {
      System.out.println("Authentication Error: Invalid ID or PIN.");
    }

    input.close();
  }
}
