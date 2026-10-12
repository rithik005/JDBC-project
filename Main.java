package Flight_Project;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args)
            throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );

        server.createContext(
                "/",
                new FlightHandler()
        );

        server.start();

        System.out.println(
                "Flight Booking Server Started"
        );

        System.out.println(
                "Open: http://localhost:8080"
        );
    }
}
