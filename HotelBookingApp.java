/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates thread-safe booking using synchronized blocks.
 * Prevents race conditions and double booking.
 *
 * @author Kartikey
 * @version 11.0
 */

import java.util.*;

// Reservation
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (critical resource)
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
    }

    // 🔥 Critical Section
    public synchronized boolean allocateRoom(String roomType) {

        int available = availability.getOrDefault(roomType, 0);

        if (available > 0) {
            availability.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void display() {
        System.out.println("Final Inventory: " + availability);
    }
}

// Booking Processor (Runnable)
class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            // 🔒 Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            // Try booking
            boolean success = inventory.allocateRoom(request.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking SUCCESS for " + request.guestName);
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " → Booking FAILED for " + request.guestName);
            }
        }
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Shared queue
        Queue<Reservation> queue = new LinkedList<>();

        // Multiple requests for SAME room → race condition test
        queue.offer(new Reservation("Amit", "Single Room"));
        queue.offer(new Reservation("Riya", "Single Room"));
        queue.offer(new Reservation("John", "Single Room"));

        // Shared inventory
        RoomInventory inventory = new RoomInventory();

        // Threads (simulate users)
        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        inventory.display();
    }
}