package softwaredesigndemo.utils;

public enum Color { YELLOW ("\u001B[33m"), BLUE ("\u001B[34m"), RESET ("\u001B[0m");
    private final String code;

    Color(String ansiCode) {
        code = ansiCode;
    }

    public String display(String text) {
        return code + text + Color.RESET.code;
    }
}
