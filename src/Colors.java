public enum Colors {
    RESET("\033[0m"),
    RED("\033[1;31m"),
    YELLOW("\033[1;33m"),
    GREEN("\033[1;32m"),
    CYAN("\033[1;36m"),
    WHITE("\033[1;37m"),
    PINK("\u001B[35m");

    private final String code;

    Colors(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
