package com.airportapi.controller;

import com.airportapi.model.Airport;
import com.airportapi.service.DijkstraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")

public class AirportController {

    @Autowired
    private DijkstraService dijkstraService;

    @GetMapping("/airports")
    public List<Airport> getAllAirports() {
        return dijkstraService.getAirports();
    }

    @GetMapping("/shortest-path")
    public List<String> getShortestPath(@RequestParam String source, @RequestParam String destination) {
        return dijkstraService.findShortestPath(source, destination);
    }
}

