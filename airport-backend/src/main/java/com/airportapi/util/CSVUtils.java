package com.airportapi.util;

import com.airportapi.model.Airport;
import com.airportapi.model.Route;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static List<Route> loadRoutes() {
        List<Route> routes = new ArrayList<>();

        try {
            InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream("routes.csv");
            if (is == null) {
                throw new RuntimeException("routes.csv file not found in resources folder");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }

                String[] parts = line.split(",");  // Tab-delimited CSV

                if (parts.length >= 4) {
                    String departureCode = parts[0].trim();
                    String arrivalCode = parts[1].trim();
                    String aircraft = parts[2].trim();
                    String operator = parts[3].trim();

                    Airport departureAirport = new Airport(departureCode, "Unknown", 0.0, 0.0);
                    Airport arrivalAirport = new Airport(arrivalCode, "Unknown", 0.0, 0.0);

                    Route route = new Route(departureAirport, arrivalAirport, aircraft, operator);
                    routes.add(route);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading routes from CSV", e);
        }

        return routes;
    }

    public static List<Airport> loadAirports(String path) {
        List<Airport> airports = new ArrayList<>();
        try {
            InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new RuntimeException(path + " file not found in resources folder");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] parts = line.split(","); // Comma-delimited CSV
                if (parts.length >= 4) {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    double latitude = Double.parseDouble(parts[2].trim());
                    double longitude = Double.parseDouble(parts[3].trim());
                    Airport airport = new Airport(code, name, latitude, longitude);
                    airports.add(airport);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading airports from CSV", e);
        }
        return airports;
    }

    public static List<String[]> loadRawRoutes(String path) {
        List<String[]> rows = new ArrayList<>();
        try {
            InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream(path);
            if (is == null) {
                throw new RuntimeException(path + " file not found in resources folder");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                String[] parts = line.split(",");
                rows.add(parts);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading raw routes from CSV", e);
        }
        return rows;
    }
}