package Mingo.WeirdItems.Helpers.Candy;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum CandyType {
    WHITE(Material.WHITE_DYE, ChatColor.WHITE),
    ORANGE(Material.ORANGE_DYE, ChatColor.GOLD),
    MAGENTA(Material.MAGENTA_DYE, ChatColor.MAGIC),
    LIGHT_BLUE(Material.LIGHT_BLUE_DYE, ChatColor.BLUE),
    YELLOW(Material.YELLOW_DYE, ChatColor.YELLOW),
    LIME(Material.LIME_DYE, ChatColor.GREEN),
    PINK(Material.PINK_DYE, ChatColor.LIGHT_PURPLE),
    GREY(Material.GRAY_DYE, ChatColor.GRAY),
    LIGHT_GREY(Material.LIGHT_GRAY_DYE, ChatColor.GRAY),
    CYAN(Material.CYAN_DYE, ChatColor.AQUA),
    PURPLE(Material.PURPLE_DYE, ChatColor.DARK_PURPLE),
    BLUE(Material.BLUE_DYE, ChatColor.DARK_BLUE),
    BROWN(Material.COCOA_BEANS, ChatColor.GOLD),
    GREEN(Material.GREEN_DYE, ChatColor.DARK_GREEN),
    RED(Material.RED_DYE, ChatColor.RED);

    private Material mat;
    private ChatColor color;

    CandyType(Material mat, ChatColor color) {
        this.mat = mat;
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }
    public Material getMaterial() {
        return mat;
    }
}
