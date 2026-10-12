package Flight_Project;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FlightHandler implements HttpHandler {

    FlightDAO dao = new FlightDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();

        if (path.equals("/")) {

            sendFile(exchange, "web/index.html", "text/html");

        }
        else if (path.equals("/style.css")) 
        {
            sendFile(exchange, "web/style.css", "text/css");
        } 
        else if (path.equals("/script.js")) 
        {
            sendFile(exchange, "web/script.js", "application/javascript");
        }
        else if (path.equals("/book") && exchange.getRequestMethod().equalsIgnoreCase("POST")) 
        {
            String data = readData(exchange);
            Map<String, String> values = convertData(data);
            
        try {
                dao.bookFlight( values.get("passengerName"),values.get("flightNumber"),values.get("source"),
                        values.get("destination"),values.get("travelDate"), values.get("seatNumber"));

                sendResponse(exchange, "Flight booked successfully!");
            } catch (Exception e) 
        		{
                	sendResponse(exchange,"Booking failed: " + e.getMessage());
        		}

        	} 
        else if (path.equals("/flights")
                && exchange.getRequestMethod()
                .equalsIgnoreCase("GET")) {

        try {
        		String result = dao.getFlights();
        		
                sendResponse(exchange, result);
            } catch (Exception e) 
        		{
                	sendResponse(exchange,"Error: " + e.getMessage());
        		}
        	} 
        else {
            sendResponse(exchange, "Page not found");
        }
    }

    private String readData(HttpExchange exchange) throws IOException {

        InputStream input = exchange.getRequestBody();

        String data = "";
        int value;

        while ((value = input.read()) != -1) 
        {
            data = data + (char) value;
        }

        input.close();
        return data;
    }

    private Map<String, String> convertData(String data) throws IOException {

        Map<String, String> map = new HashMap<>();

        String[] parts = data.split("&");
        int i = 0;

        while (i < parts.length) 
        {
            String[] value = parts[i].split("=");
            
            if (value.length == 2) 
            {
                String key = URLDecoder.decode(value[0], "UTF-8");

                String val = URLDecoder.decode(value[1],"UTF-8");
                map.put(key, val);
            }
            i++;
        }
        return map;
    }

    private void sendFile(HttpExchange exchange, String fileName, String contentType) throws IOException {

        File file = new File(fileName);

        if (!file.exists()) 
        {
            sendResponse(exchange,"File not found: " + fileName);
            return;
        }

        FileInputStream input = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        
        input.read(data);
        input.close();

        exchange.getResponseHeaders().set("Content-Type",contentType);

        exchange.sendResponseHeaders(200,data.length);

        OutputStream output = exchange.getResponseBody();

        output.write(data);
        output.close();
    }

    private void sendResponse(HttpExchange exchange,String message) throws IOException {

        byte[] data = message.getBytes("UTF-8");

        exchange.getResponseHeaders().set("Content-Type","text/plain");
        exchange.sendResponseHeaders(200, data.length);

        OutputStream output = exchange.getResponseBody();

        output.write(data);
        output.close();
    }
}
