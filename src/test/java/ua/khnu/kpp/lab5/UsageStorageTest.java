package ua.khnu.kpp.lab5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsageStorageTest {

    @Test
    void newStorageIsEmpty() {
        UsageStorage storage = new UsageStorage();
        assertEquals(0, storage.size());
        assertTrue(storage.isEmpty());
    }

    @Test
    void sizeGrowsWithEachAddedObject() {
        UsageStorage storage = new UsageStorage();
        storage.add(new ElectricityUsage(100, 250, 4.32, 30));
        storage.add(new ElectricityUsage(250, 300, 4.32, 10));
        assertEquals(2, storage.size());
    }

    @Test
    void nullIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new UsageStorage().add(null));
    }

    @Test
    void invalidUsageIsRejectedByConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ElectricityUsage(300, 100, 4.32, 30));
    }

    @Test
    void savesCollectionToFile(@TempDir Path dir) throws Exception {
        UsageStorage storage = new UsageStorage();
        storage.add(new ElectricityUsage(100, 250, 4.32, 30));
        storage.add(new ElectricityUsage(250, 300, 2, 10));

        Path file = dir.resolve("out.csv");
        storage.saveToFile(file);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(3, lines.size());
        assertEquals(UsageStorage.HEADER, lines.get(0));
        assertEquals("100.00;250.00;4.32;30;150.00;648.00;5.00", lines.get(1));
        assertEquals("250.00;300.00;2.00;10;50.00;100.00;5.00", lines.get(2));
    }
}
