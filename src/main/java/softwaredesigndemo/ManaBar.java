package softwaredesigndemo;

public class ManaBar {
    private int maxCapacity = 0;
    private int currentCapacity = 0;

    void startTurn() {
        if (maxCapacity < 10) maxCapacity++;
        currentCapacity = maxCapacity;
    }

    void consume(int amount) {
        if (amount < 0 || amount >currentCapacity) throw new IllegalArgumentException("ManaBar.consume() error: " + amount + "  is out of range!");
        currentCapacity -= amount;
    }

    void show() {
        System.out.printf("MANA: %d out of %d available\n", currentCapacity, maxCapacity);
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }
}
