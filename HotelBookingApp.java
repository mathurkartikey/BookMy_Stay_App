/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates safe room allocation using Queue, HashMap, and Set.
 * Prevents double booking and ensures inventory consistency.
 *
 * @author Kartikey
 * @version 6.0
 */

import java.util.*;

// Reservation class
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
}

// Inventory Service
class RoomInventory {
    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

// Booking Service
class BookingService {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds;

    // Map room type → allocated room IDs
    private HashMap<String, Set<String>> allocationMap;

    public BookingService(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.allocationMap = new HashMap<>();
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 1) + "-" + UUID.randomUUID().toString().substring(0, 4);
    }

    // Process bookings
    public void processBookings() {
        System.out.println("Processing Booking Requests...\n");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll();

            String type = request.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId;

                // Ensure uniqueness
                do {
                    roomId = generateRoomId(type);
                } while (allocatedRoomIds.contains(roomId));

                // Add to set
                allocatedRoomIds.add(roomId);

                // Map room type → IDs
                allocationMap.putIfAbsent(type, new HashSet<>());
                allocationMap.get(type).add(roomId);

                // Reduce inventory
                inventory.reduceAvailability(type);

                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Assigned Room ID: " + roomId + "\n");

            } else {
                System.out.println("Booking Failed (No Availability)");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + type + "\n");
            }
        }
    }
}

// Main Class
public class HotelBookingApp {
    public static void main(String[] args) {

        // Create booking queue
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Amit", "Single Room"));
        queue.offer(new Reservation("Riya", "Single Room"));
        queue.offer(new Reservation("John", "Single Room")); // will fail
        queue.offer(new Reservation("Neha", "Suite Room"));

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Booking service
        BookingService service = new BookingService(queue, inventory);

        // Process bookings
        service.processBookings();
    }
}