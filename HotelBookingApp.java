/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates validation and custom error handling.
 * Prevents invalid bookings and protects system state.
 *
 * @author Kartikey
 * @version 9.0
 */

import java.util.*;

// 🔥 Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 1);
        availability.put("Double Room", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {
        int current = availability.getOrDefault(roomType, -1);

        if (current <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        availability.put(roomType, current - 1);
    }

    public boolean isValidRoomType(String roomType) {
        return availability.containsKey(roomType);
    }
}

// 🔥 Validator
class InvalidBookingValidator {

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate null / empty
        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        // Validate room type
        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("No availability for " + reservation.getRoomType());
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(Reservation reservation) {

        try {
            // 🔥 Validate first (fail-fast)
            InvalidBookingValidator.validate(reservation, inventory);

            // If valid → allocate
            inventory.reduceAvailability(reservation.getRoomType());

            System.out.println("Booking Successful!");
            System.out.println("Guest: " + reservation.getGuestName());
            System.out.println("Room: " + reservation.getRoomType());
            System.out.println();

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed ❌");
            System.out.println("Reason: " + e.getMessage());
            System.out.println();
        }
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // ✅ Valid booking
        service.bookRoom(new Reservation("Amit", "Single Room"));

        // ❌ Invalid room type
        service.bookRoom(new Reservation("Riya", "Luxury Room"));

        // ❌ No availability
        service.bookRoom(new Reservation("John", "Single Room"));

        // ❌ Empty name
        service.bookRoom(new Reservation("", "Double Room"));
    }
}