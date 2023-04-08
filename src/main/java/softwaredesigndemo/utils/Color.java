package softwaredesigndemo.utils;

// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
public enum Color {
    RESET("\u001B[0m"), BLUE("\u001B[34m"), PURPLE("\u001B[35m"), RED("\u001B[31m"),
    YELLOW("\u001B[33m");

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
}
