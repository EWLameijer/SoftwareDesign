package softwaredesigndemo.spells;

public enum TargetType {
    CHARACTER, HERO, MINION;

    boolean matches(TargetType other) {
        if (this == CHARACTER) return true;
        else return this == other;
    }
}
