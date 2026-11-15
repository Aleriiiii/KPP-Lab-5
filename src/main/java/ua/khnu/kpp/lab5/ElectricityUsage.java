package ua.khnu.kpp.lab5;

/**
 * Предметний клас з ЛР № 1 (варіант 6): облік споживання електроенергії.
 * Поля: попередні показники лічильника, поточні показники, тариф, кількість днів.
 * Для ЛР № 5 додано лише гетери та toString() — логіка лишилась без змін.
 */
public final class ElectricityUsage {
    private final double previous;
    private final double current;
    private final double tariff;
    private final int days;

    public ElectricityUsage(double previous, double current, double tariff, int days) {
        if (!Double.isFinite(previous) || previous < 0
                || !Double.isFinite(current) || current < previous
                || !Double.isFinite(tariff) || tariff <= 0
                || days <= 0) {
            throw new IllegalArgumentException("Invalid electricity usage parameters");
        }
        this.previous = previous;
        this.current = current;
        this.tariff = tariff;
        this.days = days;
    }

    public double previous() { return previous; }
    public double current() { return current; }
    public double tariff() { return tariff; }
    public int days() { return days; }

    public double consumption() {
        return current - previous;
    }

    public double cost() {
        return consumption() * tariff;
    }

    public double averageDailyConsumption() {
        return consumption() / days;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.ROOT,
                "Показники %.2f → %.2f кВт·год, тариф %.2f грн, %d дн. | "
                        + "спожито %.2f кВт·год, вартість %.2f грн, у середньому %.2f кВт·год/день",
                previous, current, tariff, days,
                consumption(), cost(), averageDailyConsumption());
    }
}
