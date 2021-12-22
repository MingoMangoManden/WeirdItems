package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shroom extends WeirdItem implements Listener {

    ItemStack item;
    Item item_;

    Main plugin;

    public Shroom(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Magical I"); // this is how u make custom enchants

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item shroom = new Item("Berserker Shroom", Material.WARPED_FUNGUS, RarityType.RARE, lore, enchants, new Category[]{Category.MAGICAL, Category.WEAPON, Category.TOOL});
        ItemMeta meta = shroom.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        shroom.getItem().setItemMeta(meta);

        this.item = shroom.getItem();
        this.item_ = shroom;
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        ItemStack item = e.getItemInHand();

        if (b.getType().equals(Material.WARPED_FUNGUS) && item.getItemMeta().equals(this.item.getItemMeta())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        ItemStack item = p.getInventory().getItemInMainHand();
        BukkitScheduler sched = Bukkit.getScheduler();

        if (item.getItemMeta() == null) { return; }
        if (item.getItemMeta().equals(this.item.getItemMeta()) && e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            PotionEffect effect2 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*10, 2);
            PotionEffect effect3 = new PotionEffect(PotionEffectType.CONFUSION, 20*10, 1);
            PotionEffect effect4 = new PotionEffect(PotionEffectType.BLINDNESS, 20*10, 0);
            PotionEffect effect5 = new PotionEffect(PotionEffectType.GLOWING, 20*10, 1);
            PotionEffect effect6 = new PotionEffect(PotionEffectType.SPEED, 20*10, 2);

            p.addPotionEffect(effect2);
            p.addPotionEffect(effect3);
            p.addPotionEffect(effect4);
            p.addPotionEffect(effect5);
            p.addPotionEffect(effect6);

            p.playSound(loc, Sound.ENTITY_WITHER_SPAWN, 1f, 1f);
            p.playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 0.6f);
            p.playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 0.3f);
            p.playSound(loc, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);

            sched.runTaskLater(this.plugin, () -> {
                p.getInventory().removeItem(this.item);
            }, 2L);
        } else if (item.equals(this.item)) {
            e.setCancelled(true);
        }
    }

    public Item getItem() { return this.item_; }
}
