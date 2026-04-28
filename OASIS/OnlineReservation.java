import java.util.*;

public class OnlineReservation {
  // Mock Database
  private static Map<String, String> users = new HashMap<>();
  private static Map<Integer, Reservation> reservations = new HashMap<>();
  private static int pnrCounter = 1001;
  private static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    // Pre-defined user for login
    users.put("admin", "password123");
    users.put("user01", "java2026");

    System.out.println("--- WELCOME TO ONLINE RESERVATION SYSTEM ---");

    if (login()) {
      boolean exit = false;
      while (!exit) {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Reservation Form");
        System.out.println("2. Cancellation Form");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
          case 1 -> reservationForm();
          case 2 -> cancellationForm();
          case 3 -> {
            System.out.println("Thank you for using the system!");
            exit = true;
          }
          default -> System.out.println("Invalid choice. Try again.");
        }
      }
    } else {
      System.out.println("Access Denied. Invalid Credentials.");
    }
  }

  // Module 1: Login Form
  private static boolean login() {
    System.out.println("\n--- LOGIN FORM ---");
    System.out.print("Enter Login ID: ");
    String username = sc.nextLine();
    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    return users.containsKey(username) && users.get(username).equals(password);
  }

  // Module 2: Reservation Form
  private static void reservationForm() {
    System.out.println("\n--- RESERVATION FORM ---");
    System.out.print("Enter Passenger Name: ");
    String name = sc.nextLine();
    System.out.print("Enter Train Number: ");
    String trainNumber = sc.nextLine();

    // Auto-filling Train Name logic (Mock)
    String trainName = "Express-" + trainNumber;
    System.out.println("Train Name (Auto-filled): " + trainName);

    System.out.print("Enter Class Type (e.g., AC, Sleeper): ");
    String classType = sc.nextLine();
    System.out.print("Enter Date of Journey (DD-MM-YYYY): ");
    String date = sc.nextLine();
    System.out.print("From (Place): ");
    String from = sc.nextLine();
    System.out.print("Destination: ");
    String destination = sc.nextLine();

    System.out.print("Press 'I' to Insert/Confirm: ");
    String confirm = sc.next();

    if (confirm.equalsIgnoreCase("I")) {
      Reservation res = new Reservation(pnrCounter, name, trainNumber, trainName, classType, date, from, destination);
      reservations.put(pnrCounter, res);
      System.out.println("\nReservation Successful!");
      System.out.println("Your PNR Number is: " + pnrCounter);
      pnrCounter++;
    } else {
      System.out.println("Reservation Cancelled.");
    }
  }

  // Module 3: Cancellation Form
  private static void cancellationForm() {
    System.out.println("\n--- CANCELLATION FORM ---");
    System.out.print("Enter PNR Number: ");
    int pnr = sc.nextInt();

    if (reservations.containsKey(pnr)) {
      System.out.println("\nTicket Found:");
      System.out.println(reservations.get(pnr));
      System.out.print("\nPress 'OK' to confirm cancellation: ");
      String confirm = sc.next();

      if (confirm.equalsIgnoreCase("OK")) {
        reservations.remove(pnr);
        System.out.println("Cancellation Confirmed.");
      } else {
        System.out.println("Cancellation Aborted.");
      }
    } else {
      System.out.println("Invalid PNR Number. No record found.");
    }
  }
}

// Helper Class to store Reservation Details
class Reservation {
  int pnr;
  String name, trainNo, trainName, classType, date, from, to;

  public Reservation(int pnr, String name, String trainNo, String trainName, String classType, String date, String from,
      String to) {
    this.pnr = pnr;
    this.name = name;
    this.trainNo = trainNo;
    this.trainName = trainName;
    this.classType = classType;
    this.date = date;
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return "PNR: " + pnr + " | Name: " + name + " | Train: " + trainName + " (" + trainNo + ")\n" +
        "From: " + from + " To: " + to + " | Date: " + date + " | Class: " + classType;
  }
}