package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PoopSword extends WeirdItem {

    ItemStack item;
    Item item_;

    public PoopSword(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Poop I"); // this is how u make custom enchants
        lore.add("it's so bad please don't use it");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.SWEEPING_EDGE, 1);

        Item cactus = new Item("Poop Sword", Material.NETHERITE_SWORD, RarityType.COMMON, lore, enchants, new Category[]{Category.WEAPON});
        ItemMeta meta = cactus.getItem().getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("attackDamageModifier", -0.6D, AttributeModifier.Operation.ADD_NUMBER));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        cactus.getItem().setItemMeta(meta);

        this.item = cactus.getItem();
        this.item_ = cactus;
    }

    public Item getItem() { return this.item_; }
}
