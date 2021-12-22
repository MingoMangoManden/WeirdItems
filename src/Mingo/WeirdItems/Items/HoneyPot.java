package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HoneyPot extends WeirdItem implements Listener {

    ItemStack item;
    Item item_;

    Main plugin;

    public HoneyPot(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "NOM NOM I"); // this is how u make custom enchants
        lore.add("Just a spoonful...");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item pot = new Item("Winnie's Pot o' Honeh", Material.HONEY_BOTTLE, RarityType.RARE, lore, enchants, new Category[]{Category.MAGICAL, Category.TOOL});
        ItemMeta meta = pot.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        pot.getItem().setItemMeta(meta);

        this.item = pot.getItem();
        this.item_ = pot;
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();

        if (e.getItem().getItemMeta() == null) { return; }
        if (e.getItem().getItemMeta().equals(this.item.getItemMeta())) {
            PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 20*5, 1);
            PotionEffect effect2 = new PotionEffect(PotionEffectType.REGENERATION, 20*5, 4);

            p.addPotionEffect(effect);
            p.addPotionEffect(effect2);
            p.setFoodLevel(14);

            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 0.1f);
        }
    }

    public Item getItem() { return this.item_; }
}
