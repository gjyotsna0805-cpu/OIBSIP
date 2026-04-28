import java.util.*;
import java.util.stream.Collectors;

// --- Book Model ---
class Book {
  private int id;
  private String title;
  private String author;
  private boolean isIssued;

  public Book(int id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.isIssued = false;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public boolean isIssued() {
    return isIssued;
  }

  public void setIssued(boolean issued) {
    isIssued = issued;
  }

  @Override
  public String toString() {
    return String.format("[%d] %-20s | Author: %-15s | Status: %s",
        id, title, author, (isIssued ? "ISSUED" : "AVAILABLE"));
  }
}

// --- Digital Library Controller ---
class LibraryController {
  private List<Book> inventory = new ArrayList<>();
  private Scanner scanner = new Scanner(System.in);

  // Initial Data
  public LibraryController() {
    inventory.add(new Book(101, "Java Programming", "James Gosling"));
    inventory.add(new Book(102, "Clean Code", "Robert Martin"));
    inventory.add(new Book(103, "Data Structures", "Ellis Horowitz"));
  }

  // Admin: Add/Delete
  public void manageInventory() {
    System.out.println("\n--- Admin: Inventory Management ---");
    System.out.println("1. Add New Book\n2. Delete Book Record\n3. Back");
    int choice = scanner.nextInt();
    scanner.nextLine();

    if (choice == 1) {
      System.out.print("Enter ID: ");
      int id = scanner.nextInt();
      scanner.nextLine();
      System.out.print("Enter Title: ");
      String t = scanner.nextLine();
      System.out.print("Enter Author: ");
      String a = scanner.nextLine();
      inventory.add(new Book(id, t, a));
      System.out.println("Success: Book added to database.");
    } else if (choice == 2) {
      System.out.print("Enter ID to remove: ");
      int id = scanner.nextInt();
      inventory.removeIf(b -> b.getId() == id);
      System.out.println("Success: Record updated.");
    }
  }

  // User: Search/Issue/Return
  public void userPortal() {
    System.out.println("\n--- User Portal ---");
    System.out.println("1. View All Books\n2. Search by Title\n3. Issue Book\n4. Return Book");
    int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice) {
      case 1 -> inventory.forEach(System.out::println);
      case 2 -> {
        System.out.print("Enter search keyword: ");
        String key = scanner.nextLine().toLowerCase();
        inventory.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(key))
            .forEach(System.out::println);
      }
      case 3 -> processTransaction(true);
      case 4 -> processTransaction(false);
    }
  }

  private void processTransaction(boolean issuing) {
    System.out.print("Enter Book ID: ");
    int id = scanner.nextInt();
    for (Book b : inventory) {
      if (b.getId() == id) {
        if (issuing && !b.isIssued()) {
          b.setIssued(true);
          System.out.println("Book Issued Successfully!");
        } else if (!issuing && b.isIssued()) {
          b.setIssued(false);
          System.out.println("Book Returned Successfully!");
        } else {
          System.out.println("Error: Action not possible (Check status).");
        }
        return;
      }
    }
    System.out.println("Error: Book ID not found.");
  }
}

// --- Main Application ---
public class Digitallibrary {
  public static void main(String[] args) {
    LibraryController app = new LibraryController();
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("\n==============================");
      System.out.println(" DIGITAL LIBRARY SYSTEM v1.0 ");
      System.out.println("==============================");
      System.out.println("1. ADMIN LOGIN\n2. USER LOGIN\n3. SHUTDOWN");
      System.out.print("Selection: ");

      try {
        int mode = sc.nextInt();
        if (mode == 1)
          app.manageInventory();
        else if (mode == 2)
          app.userPortal();
        else
          break;
      } catch (Exception e) {
        System.out.println("Invalid input. Please use numeric keys.");
        sc.nextLine();
      }
    }
    System.out.println("System offline.");
  }
}
