package Mingo.WeirdItems.Helpers.Candy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Candy {

    String name;
    CandyType type;

    Material mat;
    ItemStack item;
    ChatColor color;

    public Candy(String name, CandyType type) {
        this.name = name;
        this.type = type;
    }

    public void giveToPlayer(Player p) {
        makeItem();
        p.getInventory().addItem(this.item);
    }

    public void makeItem() {
        this.color = this.type.getColor();
        this.mat = this.type.getMaterial();

        ItemStack item = new ItemStack(this.mat);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(this.color + this.name);
        item.setItemMeta(meta);

        this.item = item;
    }
    public String getName() { return this.name; }
    public CandyType getType() { return this.type; }
    public Material getMaterial() { return this.mat; }
    public ItemStack getItem() { return this.item; }
    public ChatColor getColor() { return this.color; }
}
