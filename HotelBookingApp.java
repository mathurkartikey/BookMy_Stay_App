/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates booking request handling using Queue (FIFO).
 * Requests are stored and processed in arrival order.
 * No inventory updates happen at this stage.
 *
 * @author Kartikey
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class (Booking Request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Booking Queue Manager
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\nBooking Request Queue:\n");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Create queue system
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Riya", "Double Room"));
        bookingQueue.addRequest(new Reservation("John", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Neha", "Single Room"));

        // Display queue (FIFO order)
        bookingQueue.viewRequests();

        System.out.println("\nAll requests are stored in arrival order (FIFO).");
        System.out.println("No rooms have been allocated yet.");
    }
}