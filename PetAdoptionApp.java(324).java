import java.sql.*;

public class PetAdoptionApp {

    // JDBC URL, username, and password for the database
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pet_adoption";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "sandip.1418sql"; // Replace with your MySQL password

    public static void main(String[] args) {
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Connected to the database successfully!");

            // Display all users
            System.out.println("Users:");
            displayUsers(connection);

            // Display all pets
            System.out.println("\nPets:");
            displayPets(connection);

            // Display available pets
            System.out.println("\nAvailable Pets:");
            displayAvailablePets(connection);

            // Display adoption requests with user and pet information
            System.out.println("\nAdoption Requests:");
            displayAdoptionRequests(connection);

        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    // Method to display all users
    private static void displayUsers(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                System.out.printf("User ID: %d, Username: %s%n", userId, username);
            }
        }
    }

    // Method to display all pets
    private static void displayPets(Connection connection) throws SQLException {
        String query = "SELECT * FROM pets";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int petId = resultSet.getInt("pet_id");
                String petName = resultSet.getString("pet_name");
                String petType = resultSet.getString("pet_type");
                int petAge = resultSet.getInt("pet_age");
                boolean available = resultSet.getBoolean("available");
                System.out.printf("Pet ID: %d, Name: %s, Type: %s, Age: %d, Available: %b%n",
                        petId, petName, petType, petAge, available);
            }
        }
    }

    // Method to display available pets
    private static void displayAvailablePets(Connection connection) throws SQLException {
        String query = "SELECT * FROM pets WHERE available = TRUE";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int petId = resultSet.getInt("pet_id");
                String petName = resultSet.getString("pet_name");
                String petType = resultSet.getString("pet_type");
                int petAge = resultSet.getInt("pet_age");
                System.out.printf("Pet ID: %d, Name: %s, Type: %s, Age: %d%n",
                        petId, petName, petType, petAge);
            }
        }
    }

    // Method to display adoption requests
    private static void displayAdoptionRequests(Connection connection) throws SQLException {
        String query = """
                SELECT r.request_id, u.username, p.pet_name, r.request_date
                FROM adoption_requests r
                JOIN users u ON r.user_id = u.user_id
                JOIN pets p ON r.pet_id = p.pet_id
                """;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int requestId = resultSet.getInt("request_id");
                String username = resultSet.getString("username");
                String petName = resultSet.getString("pet_name");
                Timestamp requestDate = resultSet.getTimestamp("request_date");
                System.out.printf("Request ID: %d, Username: %s, Pet Name: %s, Request Date: %s%n",
                        requestId, username, petName, requestDate);
            }
        }
    }
}
