package com.airportapi.service;

import com.airportapi.model.Airport;
import com.airportapi.model.Route;
import com.airportapi.model.Pair;
import com.airportapi.util.CSVUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class DijkstraService {

    private Map<String, Airport> airportData;
    private Map<Pair, Route> routeData;
    private Map<String, String> previous;

    @PostConstruct
    public void init() throws IOException {
        loadAirports();
        loadRoutes();
    }

    private void loadAirports() throws IOException {
        airportData = new HashMap<>();
        List<Airport> airports = CSVUtils.loadAirports("airport.csv");
        for (Airport airport : airports) {
            airportData.put(airport.getCode(), airport);
        }
    }

    private void loadRoutes() throws IOException {
        routeData = new HashMap<>();
        List<String[]> routeRows = CSVUtils.loadRawRoutes("routes.csv");
        for (String[] parts : routeRows) {
            if (parts.length >= 4) {
                Airport departure = airportData.get(parts[0].trim());
                Airport arrival = airportData.get(parts[1].trim());
                if (departure != null && arrival != null) {
                    String aircraft = parts[2].trim();
                    String operator = parts[3].trim();
                    Pair pair = new Pair(departure.getCode(), arrival.getCode());
                    Route route = new Route(departure, arrival, aircraft, operator);
                    routeData.put(pair, route);
                }
            }
        }
    }

    public List<String> findShortestPath(String source, String destination) {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Double> distance = new HashMap<>();
        previous = new HashMap<>();
        for (String code : airportData.keySet()) {
            visited.put(code, false);
            distance.put(code, Double.MAX_VALUE);
        }
        distance.put(source, 0.0);
        for (int i = 0; i < airportData.size(); i++) {
            String u = findMinDistance(distance, visited);
            if (u == null) break;
            visited.put(u, true);
            for (String v : airportData.keySet()) {
                Pair edge = new Pair(u, v);
                if (!visited.get(v) && routeData.containsKey(edge)) {
                    double alt = distance.get(u) + routeData.get(edge).getDistance();
                    if (alt < distance.get(v)) {
                        distance.put(v, alt);
                        previous.put(v, u);
                    }
                }
            }
        }
        // Build itinerary
        List<String> itinerary = new ArrayList<>();
        if (!previous.containsKey(destination) && !source.equals(destination)) {
            itinerary.add("No path found");
            return itinerary;
        }
        Stack<String> path = new Stack<>();
        String curr = destination;
        path.push(curr);
        while (previous.containsKey(curr)) {
            curr = previous.get(curr);
            path.push(curr);
        }
        double totalDistance = distance.get(destination);
        while (!path.isEmpty()) {
            String code = path.pop();
            Airport airport = airportData.get(code);
            itinerary.add(code + ": " + (airport != null ? airport.getName() : "Unknown"));
        }
        itinerary.add("\nShortest Distance from " + source + " to " + destination + " is " + String.format("%.2f", totalDistance) + " Km");
        return itinerary;
    }

    private String findMinDistance(Map<String, Double> distance, Map<String, Boolean> visited) {
        double min = Double.MAX_VALUE;
        String minCode = null;
        for (String code : distance.keySet()) {
            if (!visited.get(code) && distance.get(code) < min) {
                min = distance.get(code);
                minCode = code;
            }
        }
        return minCode;
    }

    public List<Airport> getAirports() {
        return new ArrayList<>(airportData.values());
    }
}