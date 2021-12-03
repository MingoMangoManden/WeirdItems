package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrappleGun implements Listener {

    ItemStack item;
    Main plugin;

    public GrappleGun(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Shoot I"); // this is how u make custom enchants

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item sandals = new Item("Grapple Gun", Material.BOW, RarityType.RARE, lore, enchants);
        ItemMeta meta = sandals.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        sandals.getItem().setItemMeta(meta);

        this.item = sandals.getItem();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(EntityShootBowEvent e) {
        ItemStack item = e.getBow();

        if (e.getEntity() instanceof Player p && item.equals(this.item)) {
            LivingEntity bat = (LivingEntity) p.getWorld().spawn(p.getLocation(), Bat.class);
            Entity ar = e.getProjectile();

            bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 999999, true, false));
            bat.setAI(false);
            bat.setLeashHolder(ar);

            ar.setVelocity(p.getLocation().getDirection().multiply(2));
        }
    }

    public ItemStack getItem() { return this.item; }
}
