package ua.khnu.kpp.lab5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Колекція створених об'єктів ElectricityUsage (рівень 2)
 * та збереження її вмісту у файл (рівень 3).
 */
public final class UsageStorage {
    static final String HEADER =
            "previous;current;tariff;days;consumption;cost;averageDaily";

    private final List<ElectricityUsage> items = new ArrayList<>();

    public void add(ElectricityUsage usage) {
        if (usage == null) {
            throw new IllegalArgumentException("usage must not be null");
        }
        items.add(usage);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<ElectricityUsage> items() {
        return Collections.unmodifiableList(items);
    }

    /** Записує всю колекцію у файл у форматі CSV (UTF-8, роздільник ";"). */
    public void saveToFile(Path file) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER);
            writer.newLine();
            for (ElectricityUsage u : items) {
                writer.write(toCsv(u));
                writer.newLine();
            }
        }
    }

    static String toCsv(ElectricityUsage u) {
        return String.format(Locale.ROOT, "%.2f;%.2f;%.2f;%d;%.2f;%.2f;%.2f",
                u.previous(), u.current(), u.tariff(), u.days(),
                u.consumption(), u.cost(), u.averageDailyConsumption());
    }
}
