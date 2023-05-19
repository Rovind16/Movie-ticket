import java.util.*;
class TicketBooking{
    int availableTickets;
    int cost = 300;
    int num;
    void setValue(int n){
        availableTickets = n;
    }
    boolean checkAvailability(int val){
        num = val;
        if(availableTickets>=num){
            availableTickets-=num;
            return true;
        }
        return false;
    }
    int ticketCost(int value){
        return value*cost;
    }
    int availableCount(){
        return availableTickets;
    }
}

public class Main
{
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
		TicketBooking p1 = new TicketBooking();
		System.out.print("Enter the total Ticket count: ");
		int n = sc.nextInt();
		p1.setValue(n);
		int tickets;
        char a = 'y';
        while(a=='y'){
        System.out.print("Enter the Number of tickets Needed: ");
		tickets = sc.nextInt();
		if(p1.checkAvailability(tickets)){
		    System.out.println("Ticket Booked Successfully. Amount to be Paid is " + p1.ticketCost(tickets) + " INR.");
		}
		else{
		    System.out.println("Sorry only " + p1.availableCount() + " ticket(s) is available.");
		}
		System.out.print("Do you want to continue? [y/n]: ");
		a =  sc.next().charAt(0);
		System.out.println("");
        }
        System.out.println("Session Ended!");
	}
}

