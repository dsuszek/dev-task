package com.example.stars.controller;

import com.example.stars.exception.NoStarsAvailableException;
import com.example.stars.exception.StarNotFoundException;
import com.example.stars.model.Star;
import com.example.stars.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing stars.
 * Provides endpoints for CRUD operations
 * and additional functionalities defined in the 3rd step of the task:
 * <ul>
 * <li>findClosestStar</li>
 * <li>getNumberOfStarsByDistances</li>
 * <li>getUniqueStars</li>
 * </ul>

 */

@RestController
@RequestMapping("/api/stars")
public class StarController {

    @Autowired
    private StarService starService;

    /**
     * Retrieves a star by its ID.
     *
     * @param id the ID of the star to retrieve
     * @return a ResponseEntity containing the star if found, otherwise throws a StarNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Star> getStarById(@PathVariable Long id) {
        return starService.getStarById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StarNotFoundException("Star with id " + id + " not found"));
    }

    /**
     * Creates a new star.
     *
     * @param star the star object to be created
     * @return a ResponseEntity containing the created star
     */
    @PostMapping
    public ResponseEntity<Star> createStar(@RequestBody Star star) {
        Star savedStar = starService.saveStar(star);
        return ResponseEntity.ok(savedStar);
    }

    /**
     * Updates an existing star.
     *
     * @param id the ID of the star to update
     * @param starDetails the new details for the star
     * @return a ResponseEntity containing the updated star if found, otherwise throws a StarNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<Star> updateStar(@PathVariable Long id, @RequestBody Star starDetails) {
        return starService.getStarById(id).map(star -> {
            star.setName(starDetails.getName());
            star.setDistance(starDetails.getDistance());
            Star updatedStar = starService.saveStar(star);
            return ResponseEntity.ok(updatedStar);
        }).orElseThrow(() -> new StarNotFoundException("Star with id " + id + " not found"));
    }

    /**
     * Deletes a star by its ID.
     *
     * @param id the ID of the star to delete
     * @return a ResponseEntity with status 200 OK if the star was deleted, otherwise throws a StarNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStar(@PathVariable Long id) {
        return starService.getStarById(id).map(star -> {
            starService.deleteStar(id);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new StarNotFoundException("Star with id " + id + " not found"));
    }

    /**
     * Finds the closest stars based on distance.
     *
     * @param size the number of closest stars to retrieve
     * @return a ResponseEntity containing a list of the closest stars
     */
    @GetMapping("/closest")
    public ResponseEntity<List<Star>> findClosestStar(@RequestParam int size) {
        List<Star> allStars = starService.findAllStars();
        if (allStars.isEmpty()) {
            throw new NoStarsAvailableException("List of stars is null or empty");
        }
        List<Star> closestStars = starService.findClosestStars(allStars, size);
        return ResponseEntity.ok(closestStars);
    }

    /**
     * Gets the number of stars by their distances.
     *
     * @return a ResponseEntity containing a map where the key is the distance and the value is the number of stars at that distance
     */
    @GetMapping("/distances")
    public ResponseEntity<Map<Long, Integer>> getNumberOfStarsByDistances() {
        List<Star> allStars = starService.findAllStars();
        if (allStars.isEmpty()) {
            throw new NoStarsAvailableException("List of stars is null or empty");
        }
        Map<Long, Integer> result = starService.getNumberOfStarsByDistances(allStars);
        return ResponseEntity.ok(result);
    }

    /**
     * Gets a collection of unique stars.
     *
     * @return a ResponseEntity containing a collection of stars with unique names
     */
    @GetMapping("/unique")
    public ResponseEntity<Collection<Star>> getUniqueStars() {
        List<Star> allStars = starService.findAllStars();
        if (allStars.isEmpty()) {
            throw new NoStarsAvailableException("List of stars is null or empty");
        }
        Collection<Star> uniqueStars = starService.getUniqueStars(allStars);
        return ResponseEntity.ok(uniqueStars);
    }
}
