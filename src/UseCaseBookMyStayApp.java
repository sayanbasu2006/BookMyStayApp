import java.util.*;

/**
 * ===============================================================
 * MAIN CLASS - UseCaseBookMyStayApp
 * ===============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates how booking requests are processed and rooms
 * are allocated safely while preventing double booking.
 *
 * @version 6.0
 */

public class UseCaseBookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process requests
        bookingService.processBookings(queue);
    }
}


/**
 * ===============================================================
 * CLASS - Reservation
 * ===============================================================
 */

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


/**
 * ===============================================================
 * CLASS - BookingRequestQueue
 * ===============================================================
 *
 * Stores booking requests in FIFO order.
 */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Booking request received from "
                + reservation.getGuestName()
                + " for "
                + reservation.getRoomType()
                + " room.");
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}


/**
 * ===============================================================
 * CLASS - RoomInventory
 * ===============================================================
 *
 * Maintains room availability.
 */

class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {

        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}


/**
 * ===============================================================
 * CLASS - BookingService
 * ===============================================================
 *
 * Handles reservation confirmation and room allocation.
 */

class BookingService {

    private RoomInventory inventory;

    // Map room type -> assigned room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Track all allocated IDs globally
    private Set<String> usedRoomIds = new HashSet<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Process booking requests in FIFO order
     */
    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation reservation = queue.getNextRequest();

            String roomType = reservation.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                allocateRoom(roomType, roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation confirmed for "
                        + reservation.getGuestName()
                        + " | Room ID: "
                        + roomId);

            } else {

                System.out.println("Reservation failed for "
                        + reservation.getGuestName()
                        + " | No "
                        + roomType
                        + " rooms available.");
            }
        }
    }

    /**
     * Generate unique room ID
     */
    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.substring(0,1).toUpperCase() + (int)(Math.random() * 1000);
        } while (usedRoomIds.contains(roomId));

        usedRoomIds.add(roomId);

        return roomId;
    }

    /**
     * Store allocated room
     */
    private void allocateRoom(String roomType, String roomId) {

        allocatedRooms
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);
    }
}