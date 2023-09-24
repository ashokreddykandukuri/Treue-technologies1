import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class ParkingSpot {
    private int spotNumber;
    private boolean isOccupied;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.isOccupied = false;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void vacate() {
        isOccupied = false;
    }
}

class ParkingSystem {
    private List<ParkingSpot> parkingSpots;
    private Map<String, User> users;
    private User currentUser;

    public ParkingSystem(int totalSpots) {
        parkingSpots = new ArrayList<>();
        users = new HashMap<>();
        currentUser = null;

        for (int i = 1; i <= totalSpots; i++) {
            parkingSpots.add(new ParkingSpot(i));
        }
    }

    public boolean registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password));
            return true;
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public boolean logoutUser() {
        currentUser = null;
        return true;
    }

    public List<ParkingSpot> searchAvailableSpots() {
        List<ParkingSpot> availableSpots = new ArrayList<>();
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied()) {
                availableSpots.add(spot);
            }
        }
        return availableSpots;
    }

    public boolean bookSpot(int spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber && !spot.isOccupied()) {
                spot.occupy();
                return true;
            }
        }
        return false;
    }

    public boolean cancelBooking(int spotNumber) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotNumber() == spotNumber && spot.isOccupied()) {
                spot.vacate();
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

public class onlineparking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the parking system with 10 parking spots
        ParkingSystem parkingSystem = new ParkingSystem(10);

        while (true) {
            User currentUser = parkingSystem.getCurrentUser();
            if (currentUser == null) {
                System.out.println("");
                System.out.println(">>>>> Online Parking System <<<<<");
                System.out.println("1. Register User");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int initialChoice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (initialChoice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        boolean registrationResult = parkingSystem.registerUser(regUsername, regPassword);
                        if (registrationResult) {
                            System.out.println("User registered successfully.");
                            System.out.println("");
                        } else {
                            System.out.println("User registration failed. User already exists.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        boolean loginResult = parkingSystem.loginUser(loginUsername, loginPassword);
                        if (loginResult) {
                            System.out.println("Login successful.");
                            System.out.println("");
                        } else {
                            System.out.println("Login failed. Invalid credentials.");
                        }
                        break;
                    case 4:
                        System.out.println("Exiting the Online Parking System. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Welcome, " + currentUser.getUsername() + "!");
                System.out.println("");
                System.out.println(">>>>> Online Parking System <<<<<");
                System.out.println("1. Search Available Spots");
                System.out.println("2. Book a Spot");
                System.out.println("3. Cancel Booking");
                System.out.println("4. Logout");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        List<ParkingSpot> availableSpots = parkingSystem.searchAvailableSpots();
                        System.out.println("Available Parking Spots:");
                        for (ParkingSpot spot : availableSpots) {
                            System.out.println("Spot " + spot.getSpotNumber());
                        }
                        break;
                    case 2:
                        System.out.print("Enter the spot number to book: ");
                        int spotToBook = scanner.nextInt();
                        boolean bookingResult = parkingSystem.bookSpot(spotToBook);
                        if (bookingResult) {
                            System.out.println("Booking successful for Spot " + spotToBook);
                        } else {
                            System.out.println("Booking failed. Spot " + spotToBook + " is occupied or does not exist.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter the spot number to cancel booking: ");
                        int spotToCancel = scanner.nextInt();
                        boolean cancelResult = parkingSystem.cancelBooking(spotToCancel);
                        if (cancelResult) {
                            System.out.println("Booking for Spot " + spotToCancel + " canceled.");
                        } else {
                            System.out.println("Cancellation failed. Spot " + spotToCancel + " is not booked.");
                        }
                        break;
                    case 4:
                        parkingSystem.logoutUser();
                        System.out.println("Logout successful. Goodbye, " + currentUser.getUsername() + "!");
                        break;
                    case 5:
                        System.out.println("Exiting the Online Parking System. Goodbye, " + currentUser.getUsername() + "!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }
}