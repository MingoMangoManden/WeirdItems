package Mingo.WeirdItems;

import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.Helper;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Item {

    ItemStack item;
    ChatColor rarityColor;

    String name;
    Material type;
    RarityType rarity;
    List<String> lore;

    HashMap<Enchantment, Integer> enchants;
    Category[] categories;

    public Item(String name, Material type, RarityType rarity, List<String> lore, HashMap<Enchantment, Integer> enchants, Category[] categories) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.lore = lore;
        this.enchants = enchants;
        this.categories = Helper.sortCategories(categories);

        buildItem();
    }

    private void buildItem() {
        ItemStack item = new ItemStack(this.type);
        ItemMeta meta = item.getItemMeta();

        getRarityColor();

        meta.setDisplayName(this.rarityColor + this.name);
        meta.setLore(this.lore);

        // Wanna save this bc im proud of it - dont judge me
        /*for (int x = 0; x == this.enchants.size() - 1; x++) {
            if (this.lvls.size() >= x) {
                meta.addEnchant(this.enchants.get(x), this.lvls.get(x), true);
            }
        }*/

        for (Enchantment ench : this.enchants.keySet()) {
            meta.addEnchant(ench, this.enchants.get(ench), true); // im just so cool and smart
        }

        item.setItemMeta(meta);
        this.item = item;
    }

    public void getRarityColor() {
        switch (this.rarity) {
            case COMMON:
                this.rarityColor = ChatColor.GREEN;
                return;
            case UNCOMMON:
                this.rarityColor = ChatColor.DARK_GREEN;
                return;
            case RARE:
                this.rarityColor = ChatColor.AQUA;
                return; // remember this dummy
            case EPIC:
                this.rarityColor = ChatColor.LIGHT_PURPLE;
                return;
            case LEGENDARY:
                this.rarityColor = ChatColor.GOLD;
        }
    }

    public ItemStack getItem() { return this.item; }
    public Category[] getCategories() { return this.categories; }
}
