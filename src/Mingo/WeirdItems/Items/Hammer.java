package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
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

public class Hammer {

    ItemStack item;

    public Hammer(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "WROOM I"); // this is how u make custom enchants
        lore.add("this thing hits HARD!");
        lore.add("WROOOM!");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item hammer = new Item("Hammer", Material.NETHERITE_AXE, RarityType.EPIC, lore, enchants);
        ItemMeta meta = hammer.getItem().getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("attackDamageModifier", 17.0D, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("attackSpeedModifier", -3.6D, AttributeModifier.Operation.ADD_NUMBER));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        hammer.getItem().setItemMeta(meta);

        this.item = hammer.getItem();
    }

    public ItemStack getItem() { return this.item; }
}
