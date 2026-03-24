/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates saving and restoring system state using serialization.
 *
 * @author Kartikey
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// Inventory (Serializable)
class RoomInventory implements Serializable {
    Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
    }

    public void display() {
        System.out.println("Inventory: " + availability);
    }
}

// Wrapper for full system state
class SystemState implements Serializable {
    List<Reservation> history;
    RoomInventory inventory;

    public SystemState(List<Reservation> history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
    }
}

// 🔥 Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("State saved successfully ✅");

        } catch (IOException e) {
            System.out.println("Error saving state ❌: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("State loaded successfully ✅");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading state ❌: " + e.getMessage());
        }

        return null;
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Try loading previous state
        SystemState state = PersistenceService.load();

        if (state == null) {
            // First run → create fresh data
            System.out.println("\nCreating new system state...\n");

            List<Reservation> history = new ArrayList<>();
            history.add(new Reservation("R101", "Amit", "Single Room"));
            history.add(new Reservation("R102", "Riya", "Double Room"));

            RoomInventory inventory = new RoomInventory();

            state = new SystemState(history, inventory);

            // Save state
            PersistenceService.save(state);

        } else {
            // Recovery case
            System.out.println("\nRecovered System State:\n");

            for (Reservation r : state.history) {
                r.display();
            }

            state.inventory.display();
        }
    }
}