package com.example.stars.service;

import com.example.stars.model.Star;
import com.example.stars.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing operations related to stars.
 */
@Service
public class StarService {

    @Autowired
    private StarRepository starRepository;

    /**
     * Retrieves a star by its ID.
     *
     * @param id the ID of the star to retrieve
     * @return an Optional containing the star if found, or an empty Optional if not found
     */
    public Optional<Star> getStarById(Long id) {
        return starRepository.findById(id);
    }

    /**
     * Save a star to the repository.
     *
     * @param star the star entity to save
     * @return the star entity that has just been saved
     */
    public Star saveStar(Star star) {
        return starRepository.save(star);
    }

    /**
     * Deletes a star by its ID.
     *
     * @param id the ID of the star to delete
     */
    public void deleteStar(Long id) {
        starRepository.deleteById(id);
    }

    /**
     * Retrieve all stars from the repository.
     *
     * @return a list of all star entities
     */
    public List<Star> findAllStars() {
        return starRepository.findAll();
    }

    /**
     * Finds the closest stars to the Sun, sorted by distance.
     *
     * @param stars a list of stars to search through
     * @param size the number of closest stars to return
     * @return a list of the closest stars, limited to the specified size
     */
    public List<Star> findClosestStars(List<Star> stars, int size) {
        return stars.stream()
                .sorted(Comparator.comparingLong(Star::getDistance))
                .limit(size)
                .collect(Collectors.toList());
    }

    /**
     * Gets the number of stars grouped by their distance from the Sun.
     *
     * @param stars a list of stars to analyze
     * @return a map where the key is the distance and the value is the number of stars at that distance
     */
    public Map<Long, Integer> getNumberOfStarsByDistances(List<Star> stars) {
        return stars.stream()
                .collect(Collectors.groupingBy(Star::getDistance, TreeMap::new, Collectors.summingInt(e -> 1)));
    }

    /**
     * Retrieves unique stars based on their names. If multiple stars have the same name, only one is kept.
     *
     * @param stars a collection of stars to process
     * @return a collection of stars with unique names
     */
    public Collection<Star> getUniqueStars(Collection<Star> stars) {
        Map<String, Star> uniqueStarsMap = new LinkedHashMap<>();
        for (Star star : stars) {
            uniqueStarsMap.put(star.getName(), star);
        }
        return uniqueStarsMap.values();
    }

}
