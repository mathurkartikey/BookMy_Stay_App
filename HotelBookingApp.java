/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting.
 * Stores confirmed bookings and generates summary reports.
 *
 * @author Kartikey
 * @version 8.0
 */

import java.util.*;

// Reservation class (final confirmed booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// 📦 Booking History (Storage)
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: " + reservation.getReservationId());
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// 📊 Report Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> history) {
        System.out.println("\n--- Booking History ---\n");

        for (Reservation r : history) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {
        System.out.println("\n--- Booking Summary Report ---\n");

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + " Bookings: " + countMap.get(type));
        }

        System.out.println("\nTotal Bookings: " + history.size());
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Booking history storage
        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings
        history.addReservation(new Reservation("R101", "Amit", "Single Room"));
        history.addReservation(new Reservation("R102", "Riya", "Double Room"));
        history.addReservation(new Reservation("R103", "John", "Single Room"));
        history.addReservation(new Reservation("R104", "Neha", "Suite Room"));

        // Reporting
        BookingReportService reportService = new BookingReportService();

        // Show all bookings
        reportService.showAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());
    }
}