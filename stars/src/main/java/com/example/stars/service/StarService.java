package com.example.stars.service;

import com.example.stars.model.Star;
import com.example.stars.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StarService {

    @Autowired
    private StarRepository starRepository;

    public Optional<Star> getStarById(Long id) {
        return starRepository.findById(id);
    }

    public Star saveStar(Star star) {
        return starRepository.save(star);
    }

    public void deleteStar(Long id) {
        starRepository.deleteById(id);
    }

    public List<Star> findAllStars() {
        return starRepository.findAll();
    }
    public List<Star> findClosestStar(List<Star> stars, int size) {
        return stars.stream()
                .sorted(Comparator.comparingLong(Star::getDistance))
                .limit(size)
                .collect(Collectors.toList());
    }

    public Map<Long, Integer> getNumberOfStarsByDistances(List<Star> stars) {
        return stars.stream()
                .collect(Collectors.groupingBy(Star::getDistance, TreeMap::new, Collectors.summingInt(e -> 1)));
    }

    public Collection<Star> getUniqueStars(Collection<Star> stars) {
        Map<String, Star> uniqueStarsMap = new LinkedHashMap<>();
        for (Star star : stars) {
            uniqueStarsMap.put(star.getName(), star);
        }
        return uniqueStarsMap.values();
    }

}
