package moviebooking;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class TicketBooking {
    int availableTickets;
    int cost = 300;
    int num;
    List<Integer> bookedTickets = new ArrayList<>();
    Map<Integer, String> ticketHolders = new HashMap<>(); // Map to store ticket holders' names
    Map<Integer, Integer> ticketAges = new HashMap<>(); // Map to store ticket holders' ages
    Connection connection;

        public TicketBooking() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","1234");  
            //System.out.print("Connected");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    void setValue(int n) {
        availableTickets = n;
    }

    boolean checkAvailability(int val) {
        num = val;
        if (availableTickets >= num) {
            for (int i = 0; i < num; i++) {
                bookedTickets.add(availableTickets - i);
            }
            availableTickets -= num;
            return true;
        }
        return false;
    }

    int ticketCost(int value) {
        return value * cost;
    }

    int availableCount() {
        return availableTickets;
    }

    void displayBookedTickets() {
        System.out.println("Booked Tickets: ");
        for (int ticket : bookedTickets) {
            System.out.println("Ticket Number: " + ticket);
        }
    }

    int calculateRevenue() {
        return (num - availableTickets) * cost;
    }

    void displayAvailableCount() {
        System.out.println("Available Tickets: " + availableCount());
    }

    void cancelTickets(int num) {
        for (int i = 0; i < num; i++) {
            int ticketNumber = bookedTickets.remove(bookedTickets.size() - 1);
            availableTickets++;
            System.out.println("Ticket Number " + ticketNumber + " has been canceled.");
        }
    }

    void displayBookingHistory() {
        System.out.println("Booking History: ");
        for (int ticket : bookedTickets) {
            System.out.println("Ticket Number: " + ticket);
            System.out.println("Ticket Holder Name: " + ticketHolders.get(ticket));
            System.out.println("Ticket Holder Age: " + ticketAges.get(ticket));
            System.out.println("---------------------------");
        }
    }

    void modifyTicketCost(int newCost) {
        cost = newCost;
        System.out.println("Ticket cost has been updated to " + cost + " INR.");
    }

    void reset() {
        availableTickets = 0;
        bookedTickets.clear();
        ticketHolders.clear();
        ticketAges.clear();
        System.out.println("Ticket booking system has been reset.");
    }

    boolean searchTicket(int ticketNumber) {
        if (bookedTickets.contains(ticketNumber)) {
            System.out.println("Ticket Number " + ticketNumber + " is booked.");
            System.out.println("Ticket Holder Name: " + ticketHolders.get(ticketNumber));
            System.out.println("Ticket Holder Age: " + ticketAges.get(ticketNumber));
            return true;
        } else {
            System.out.println("Ticket Number " + ticketNumber + " is not booked.");
            return false;
        }
    }
    void storeTicketDetailsInDB(String name, int age){
       try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","1234");  
                    PreparedStatement pst = con.prepareStatement("insert into  person values(?,?)");
                    pst.setString(1, name);   
                    pst.setInt(2, age);            
                    pst.execute();
        System.out.println("Ticket details stored in the database.");
    } catch (SQLException e) {
        e.printStackTrace();
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(TicketBooking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class MovieBooking {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketBooking p1 = new TicketBooking();
        System.out.print("Enter the total Ticket count: ");
        int n = sc.nextInt();
        p1.setValue(n);
        int tickets;
        char a = 'y';
        while (a == 'y') {
            System.out.print("Enter the Number of tickets Needed: ");
            tickets = sc.nextInt();
            if (p1.checkAvailability(tickets)) {
                System.out.println("Ticket(s) Booked Successfully!");

               
                for (int i = 1; i <= tickets; i++) {
                    sc.nextLine(); // Consume the newline character
                    System.out.print("Enter the name for ticket holder " + i + ": ");
                    String name = sc.nextLine();
                    System.out.print("Enter the age for ticket holder " + i + ": ");
                    int age = sc.nextInt();
                    p1.ticketHolders.put(p1.bookedTickets.get(i - 1), name); // Store ticket holder's name
                    p1.ticketAges.put(p1.bookedTickets.get(i - 1), age); // Store ticket holder's age
                    p1.storeTicketDetailsInDB(name, age);
                }
                  
                System.out.println("Ticket Booked Successfully. Amount to be Paid is " + p1.ticketCost(tickets) + " INR.");
            } else {
                System.out.println("Sorry, only " + p1.availableCount() + " ticket(s) is available.");
            }
            System.out.print("Do you want to continue? [y/n]: ");
            a = sc.next().charAt(0);
            System.out.println("");
        }
        System.out.println("Session Ended!");
        p1.displayBookingHistory();
        p1.displayAvailableCount();
        System.out.println("Total Revenue: " + p1.calculateRevenue() + " INR.");

       
       
        System.out.println("\nAdditional Features:");
        System.out.println("1. Cancel Tickets");
        System.out.println("2. Display Booking History");
        System.out.println("3. Modify Ticket Cost");
        System.out.println("4. Reset Ticket Booking System");
        System.out.println("5. Search for a Ticket");

       
        int choice;
        do {
            System.out.print("Enter your choice (1-5) or 0 to exit: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the number of tickets to cancel: ");
                    int cancelTickets = sc.nextInt();
                    p1.cancelTickets(cancelTickets);
                    break;
                case 2:
                    p1.displayBookingHistory();
                    break;
                case 3:
                    System.out.print("Enter the new ticket cost: ");
                    int newCost = sc.nextInt();
                    p1.modifyTicketCost(newCost);
                    break;
                case 4:
                    p1.reset();
                    break;
                case 5:
                    System.out.print("Enter the ticket number to search: ");
                    int ticketNumber = sc.nextInt();
                    p1.searchTicket(ticketNumber);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println("");
        } while (choice != 0);

        System.out.println("Thank you for using the Movie Booking System!");
       
        p1.closeConnection();
    }
}
