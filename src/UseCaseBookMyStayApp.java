import java.util.HashMap;
import java.util.Map;

/**
 * ===============================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ===============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * Allows guests to view available rooms without modifying
 * system state. Demonstrates read-only access to inventory.
 *
 * @author Developer
 * @version 4.0
 */

public class UseCaseBookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Perform room search
        searchService.searchAvailableRooms();
    }
}


/**
 * ===============================================================
 * DOMAIN CLASS - Room
 * ===============================================================
 *
 * Represents room details in the system.
 *
 * @version 4.0
 */

class Room {

    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}


/**
 * ===============================================================
 * CLASS - RoomInventory
 * ===============================================================
 *
 * Stores room availability using HashMap.
 *
 * @version 4.0
 */

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {

        availability = new HashMap<>();

        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}


/**
 * ===============================================================
 * CLASS - SearchService
 * ===============================================================
 *
 * Provides read-only access to room inventory.
 *
 * @version 4.0
 */

class SearchService {

    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public SearchService(RoomInventory inventory) {

        this.inventory = inventory;

        rooms = new HashMap<>();

        rooms.put("Single", new Room("Single Room", 1, 250, 1500.0));
        rooms.put("Double", new Room("Double Room", 2, 400, 2500.0));
        rooms.put("Suite", new Room("Suite Room", 3, 750, 5000.0));
    }

    /**
     * Displays available rooms without modifying inventory.
     */
    public void searchAvailableRooms() {

        System.out.println("Available Rooms\n");

        for (String roomType : rooms.keySet()) {

            int available = inventory.getAvailability(roomType);

            // Defensive programming: show only available rooms
            if (available > 0) {

                Room room = rooms.get(roomType);

                System.out.println(room.getType() + ":");
                System.out.println("Beds: " + room.getBeds());
                System.out.println("Size: " + room.getSize() + " sqft");
                System.out.println("Price per night: " + room.getPrice());
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}