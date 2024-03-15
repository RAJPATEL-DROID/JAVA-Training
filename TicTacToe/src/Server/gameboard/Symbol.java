package Server.gameboard;

public enum Symbol
{   // to save as "Seed.java"
    CROSS("X"), ZERO("O"), NO_SYMBOL(" ");

    // Private variable
    private final String icon;
    // Constructor (must be private)
    Symbol(String icon) {
        this.icon = icon;
    }
    // Public Getter
    public String getIcon() {
        return icon;
    }
}