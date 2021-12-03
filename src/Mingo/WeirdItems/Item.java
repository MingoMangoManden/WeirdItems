package Mingo.WeirdItems;

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

    HashMap<Enchantment, Integer> enchants_;
    List<Enchantment> enchants;
    List<Integer> lvls;

    public Item(String name, Material type, RarityType rarity, List<String> lore, HashMap<Enchantment, Integer> enchants_) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.lore = lore;
        this.enchants_ = enchants_;

        buildItem();
    }

    private void buildItem() {
        ItemStack item = new ItemStack(this.type);
        ItemMeta meta = item.getItemMeta();

        getRarityColor();

        meta.setDisplayName(this.rarityColor + this.name);

        // Add rarity
        //this.lore.add("");
        //this.lore.add(this.rarityColor + this.rarity);

        //
        meta.setLore(this.lore);

        // Add every enchant with the correct levels
        /*for (int x = 0; x == this.enchants.size() - 1; x++) {
            if (this.lvls.size() >= x) {
                meta.addEnchant(this.enchants.get(x), this.lvls.get(x), true);
            }
        }*/

        for (Enchantment ench : this.enchants_.keySet()) {
            meta.addEnchant(ench, this.enchants_.get(ench), true); // im just so cool and smart
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
    /*public String getName() { return this.name; }
    public Material getType() { return this.type; }
    public List<String> getLore() { return this.lore; }
    public List<Enchantment> getEnchants() { return this.enchants; }
    public List<Integer> getLvls() { return this.lvls; }
    public String getRarity() { return this.rarity; }*/
}
