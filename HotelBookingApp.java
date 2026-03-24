/**
 * UseCase10BookingCancellation
 *
 * Demonstrates booking cancellation with inventory rollback using Stack.
 *
 * @author Kartikey
 * @version 10.0
 */

import java.util.*;

// Reservation
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
        availability.put("Double Room", 1);
    }

    public void increaseAvailability(String roomType) {
        availability.put(roomType, availability.get(roomType) + 1);
    }

    public void display() {
        System.out.println("Current Inventory:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
        System.out.println();
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void add(Reservation r) {
        bookings.put(r.reservationId, r);
    }

    public Reservation get(String id) {
        return bookings.get(id);
    }

    public void remove(String id) {
        bookings.remove(id);
    }
}

// 🔥 Cancellation Service
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancel(String reservationId) {

        // Validate existence
        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed ❌");
            System.out.println("Reason: Reservation not found\n");
            return;
        }

        // Push to stack (rollback tracking)
        releasedRoomIds.push(r.roomId);

        // Restore inventory
        inventory.increaseAvailability(r.roomType);

        // Remove booking
        history.remove(reservationId);

        System.out.println("Cancellation Successful ✅");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + r.roomId + "\n");
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack (Recent Releases): " + releasedRoomIds);
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Setup
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.add(new Reservation("R101", "Amit", "Single Room", "S-001"));
        history.add(new Reservation("R102", "Riya", "Double Room", "D-001"));

        CancellationService service = new CancellationService(inventory, history);

        // Initial inventory
        inventory.display();

        // Cancel booking
        service.cancel("R101");

        // After cancellation
        inventory.display();

        // Try invalid cancellation
        service.cancel("R999");

        // Show rollback stack
        service.showRollbackStack();
    }
}