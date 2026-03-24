/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates add-on service selection using Map + List.
 * Services are linked to reservation IDs without modifying booking logic.
 *
 * @author Kartikey
 * @version 7.0
 */

import java.util.*;

// Add-On Service class
class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (Service s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// Main Class
public class HotelBookingApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume these reservation IDs came from Use Case 6
        String res1 = "R-101";
        String res2 = "R-102";

        // Create services
        Service breakfast = new Service("Breakfast", 300);
        Service spa = new Service("Spa Access", 1000);
        Service pickup = new Service("Airport Pickup", 800);

        // Add services
        manager.addService(res1, breakfast);
        manager.addService(res1, spa);

        manager.addService(res2, pickup);

        // Display services
        manager.displayServices(res1);
        System.out.println("Total Add-On Cost: ₹" + manager.calculateTotalCost(res1));

        manager.displayServices(res2);
        System.out.println("Total Add-On Cost: ₹" + manager.calculateTotalCost(res2));
    }
}