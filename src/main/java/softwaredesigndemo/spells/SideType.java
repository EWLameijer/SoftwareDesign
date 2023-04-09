package softwaredesigndemo.spells;

public enum SideType {
    ALL, ALLY, ENEMY;

    boolean matches(SideType other) {
        if (this == ALL) return true;
        else return this == other;
    }
}
