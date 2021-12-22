package Mingo.WeirdItems.Helpers.Category;

public enum Category {
    // An item can have more than one Category that it belongs to.
    MOVEMENT("Movement"),
    WEAPON("Weapon"),
    ARMOR("Armor"),
    TOOL("Tool"),
    MAGICAL("Magical");

    String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }
}