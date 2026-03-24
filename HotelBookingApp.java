/**
 * UseCase1HotelBookingApp
 *
 * Now includes:
 * - Use Case 1: Entry point
 * - Use Case 2: Room modeling
 * - Use Case 3: Centralized inventory using HashMap
 *
 * @author Kartikey
 * @version 3.0
 */

import java.util.HashMap;

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println(roomType + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

// Room types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250, 1500);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400, 2500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 5000);
    }
}

// 🔥 Inventory Class (NEW)
class RoomInventory {
    private HashMap<String, Integer> availability;

    // Constructor
    public RoomInventory() {
        availability = new HashMap<>();

        // Initialize inventory
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Hotel Room Inventory Status\n");

        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " Available Rooms: " + availability.get(roomType));
        }
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Version 3.0");
        System.out.println("=====================================\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Room Inventory Status\n");

        // Display rooms + availability
        single.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailability("Single Room") + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailability("Double Room") + "\n");

        suite.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailability("Suite Room") + "\n");
    }
}