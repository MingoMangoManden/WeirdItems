package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sandals implements Listener {

    ItemStack item;
    Main plugin;

    public Sandals(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Magical I"); // this is how u make custom enchants
        lore.add("Crouch to stand in the air!");
        lore.add(ChatColor.ITALIC + "Pros spam crouch and hold space...");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item sandals = new Item("Hermes' Sandals", Material.GOLDEN_BOOTS, RarityType.RARE, lore, enchants);
        ItemMeta meta = sandals.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        sandals.getItem().setItemMeta(meta);

        this.item = sandals.getItem();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getBoots();
        Location loc = p.getLocation();
        Block b = loc.subtract(0, 1, 0).getBlock();

        if (item == null) { return; }
        if (!p.isSneaking() && item.equals(this.item)
                && b.getType().equals(Material.AIR)
                && !p.isFlying()) {
            BukkitScheduler sched = Bukkit.getScheduler();

            b.setType(Material.BARRIER);
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 10.0F);
            p.spawnParticle(Particle.REDSTONE, b.getLocation().add(0.5, 0.5, 0.5), 5, dustOptions);

            sched.runTaskLater(this.plugin, () -> {
                b.setType(Material.AIR);
                Particle.DustOptions dustOptions2 = new Particle.DustOptions(Color.fromRGB(255, 255, 255), 10.0F);
                p.spawnParticle(Particle.REDSTONE, b.getLocation().add(0.5, 0.5, 0.5), 5, dustOptions2);
            }, 20L*3);
        }
    }

    public ItemStack getItem() { return this.item; }
}
