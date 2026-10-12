package Flight_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightDAO {

    String url = "jdbc:mysql://localhost:3306/flight_booking";
    String username = "root";
    String password = "root";

    public void bookFlight(String passengerName,String flightNumber,String source,String destination,
    		String travelDate,String seatNumber) throws SQLException 
    {
        Connection con = DriverManager.getConnection( url, username, password);
        
        String query = "INSERT INTO booking_flight " +
        "(passenger_name, flight_number, source, destination, travel_date, seat_number) " +"VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, passengerName);
        ps.setString(2, flightNumber);
        ps.setString(3, source);
        ps.setString(4, destination);
        ps.setString(5, travelDate);
        ps.setString(6, seatNumber);

        ps.executeUpdate();

        ps.close();
        con.close();
    }

    public String getFlights() throws SQLException {

        Connection con = DriverManager.getConnection( url, username, password);

        String query = "SELECT * FROM booking_flight";

        PreparedStatement ps = con.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        String result = "";

        while (rs.next()) {

            result = result
                    + rs.getInt("id") + "|"
                    + rs.getString("passenger_name") + "|"
                    + rs.getString("flight_number") + "|"
                    + rs.getString("source") + "|"
                    + rs.getString("destination") + "|"
                    + rs.getString("travel_date") + "|"
                    + rs.getString("seat_number") + "\n";
        }

        rs.close();
        ps.close();
        con.close();
        return result;
    }
}