package net.engineeringdigest.journalapp.HelperClasses;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
@Component
public class GenerateEmployeeID {
    private HashSet<Integer> usedIds = new HashSet<>();
    private int minRange = 1;
    private int maxRange = 100;
    private Random random = new Random();

    public int generateEmployeeId() {
        // If all IDs in current range are used, increase the range
        if (usedIds.size() >= (maxRange - minRange + 1)) {
            int newMin = maxRange + 1;
            maxRange = maxRange * 10;
            minRange = newMin;
        }

        int id;
        do {
            id = random.nextInt(maxRange - minRange + 1) + minRange;
        } while (usedIds.contains(id));

        usedIds.add(id);
        return id;
    }
}
