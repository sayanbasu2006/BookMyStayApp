import java.util.LinkedList;
import java.util.Queue;

/**
 * ===============================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ===============================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates how booking requests are accepted and stored
 * in a queue to preserve arrival order.
 *
 * @version 5.0
 */

public class UseCaseBookMyStayApp {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        // Display current queue
        queue.displayQueue();

        // Show next request to process
        Reservation next = queue.peekRequest();

        if (next != null) {
            System.out.println("\nNext request to process:");
            System.out.println(next.getGuestName() + " requested " + next.getRoomType() + " room.");
        }
    }
}


/**
 * ===============================================================
 * CLASS - Reservation
 * ===============================================================
 *
 * Represents a booking request made by a guest.
 *
 * At this stage, a reservation captures only intent,
 * not confirmation or room allocation.
 *
 * @version 5.0
 */

class Reservation {

    /** Name of the guest making the booking. */
    private String guestName;

    /** Requested room type. */
    private String roomType;

    /**
     * Creates a new booking request.
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** @return guest name */
    public String getGuestName() {
        return guestName;
    }

    /** @return requested room type */
    public String getRoomType() {
        return roomType;
    }
}


/**
 * ===============================================================
 * CLASS - BookingRequestQueue
 * ===============================================================
 *
 * Manages booking requests using FIFO queue.
 *
 * Requests are processed strictly in the
 * order they are received.
 *
 * @version 5.0
 */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    /**
     * Constructor initializes queue.
     */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the queue.
     */
    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Request received from "
                + reservation.getGuestName()
                + " for "
                + reservation.getRoomType()
                + " room.");
    }

    /**
     * Displays all queued booking requests.
     */
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : requestQueue) {
            System.out.println(r.getGuestName() + " -> " + r.getRoomType());
        }
    }

    /**
     * Returns the next request without removing it.
     */
    public Reservation peekRequest() {
        return requestQueue.peek();
    }
}