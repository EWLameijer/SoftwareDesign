package softwaredesigndemo.utils;

import softwaredesigndemo.side.characters.HearthstoneCharacter;

import java.util.function.UnaryOperator;

// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
public enum Color {
    RESET("\u001B[0m"), BLACK("\u001B[30m"), BLUE("\u001B[34m"), CYAN("\u001B[36m"),
    GREEN("\u001B[32m"), PURPLE("\u001B[35m"), RED("\u001B[31m"), YELLOW("\u001B[33m");

    private final String code;

    Color(String ansiCode) {
        code = ansiCode;
    }

    public void print(String text) {
        System.out.print(code + text + Color.RESET.code);
    }

    public void println(String text) {
        print(text + "\n");
    }

    public String color(String text) {
        return code + text + Color.RESET.code;
    }

    public static UnaryOperator<String> attackStatusColor(HearthstoneCharacter character) {
        if (character.canAttack()) return Color.YELLOW::color;
        if (character.isFrozen()) return Color.CYAN::color;
        return Color.BLUE::color;
    }
}
