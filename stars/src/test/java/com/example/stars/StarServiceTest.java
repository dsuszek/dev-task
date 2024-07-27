package com.example.stars;

import com.example.stars.model.Star;
import com.example.stars.service.StarService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class StarServiceTest {

    @Test
    public void findClosestStarsTest() {
        StarService service = new StarService();
        List<Star> stars = Arrays.asList(
                new Star("StarA", 10),
                new Star("StarB", 5),
                new Star("StarC", 15),
                new Star("StarD", 20),
                new Star("StarE", 3)
        );

        List<Star> closestStars = service.findClosestStars(stars, 3);
        assertEquals(3, closestStars.size());
        assertEquals("StarE", closestStars.get(0).getName());
        assertEquals("StarB", closestStars.get(1).getName());
        assertEquals("StarA", closestStars.get(2).getName());
    }

    @Test
    public void getNumberOfStarsByDistancesTest() {
        StarService service = new StarService();
        List<Star> stars = Arrays.asList(
                new Star("StarA", 10),
                new Star("StarB", 5),
                new Star("StarC", 15),
                new Star("StarD", 10),
                new Star("StarE", 5)
        );

        Map<Long, Integer> result = service.getNumberOfStarsByDistances(stars);
        assertEquals(3, result.size());
        assertEquals(2, result.get(5L));
        assertEquals(2, result.get(10L));
        assertEquals(1, result.get(15L));
    }

    @Test
    public void getUniqueStarsTest() {
        StarService service = new StarService();
        List<Star> stars = Arrays.asList(
                new Star("StarA", 10),
                new Star("StarB", 5),
                new Star("StarA", 15),
                new Star("StarC", 20),
                new Star("StarB", 3)
        );

        Collection<Star> uniqueStars = service.getUniqueStars(stars);
        assertEquals(3, uniqueStars.size());

        Set<String> starNames = new HashSet<>();
        for (Star star : uniqueStars) {
            starNames.add(star.getName());
        }

        assertTrue(starNames.contains("StarA"));
        assertTrue(starNames.contains("StarB"));
        assertTrue(starNames.contains("StarC"));
    }
}
