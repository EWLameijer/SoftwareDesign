package softwaredesigndemo.side;

public class ManaBar {
    private int capacity = 0;
    
    private int availableMana = 0;

    void startTurn() {
        final int maxCapacity = 10;
        if (capacity < maxCapacity) capacity++;
        availableMana = capacity;
    }

    void consume(int amount) {
        if (amount < 0 || amount > availableMana) throw new IllegalArgumentException("ManaBar.consume() error: " + amount + "  is out of range!");
        availableMana -= amount;
    }

    void show() {
        System.out.printf("MANA: %d out of %d available\n", availableMana, capacity);
    }

    public int getAvailableMana() {
        return availableMana;
    }
}
