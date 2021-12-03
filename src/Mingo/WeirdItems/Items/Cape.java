package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cape implements Listener {

    ItemStack item;
    Main plugin;

    int defaultCooldown = 3;
    HashMap<Player, Integer> cooldown = new HashMap<>();
    HashMap<Player, BukkitTask> cooldownTasks = new HashMap<>();

    public Cape(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "FLYIIING I"); // this is how u make custom enchants
        lore.add("Crouch to jump HIGH!");
        lore.add("Beware of cooldown.");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item cape = new Item("Superman's cape", Material.LEATHER_CHESTPLATE, RarityType.EPIC, lore, enchants);
        LeatherArmorMeta meta = (LeatherArmorMeta) cape.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE);
        meta.setColor(Color.RED);
        cape.getItem().setItemMeta(meta);

        this.item = cape.getItem();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getChestplate();

        if (item == null) { return; }
        if (this.cooldown.get(p) == null) { this.cooldown.put(p, 0); }
        if (item.equals(this.item) && p.isSneaking() && this.cooldown.get(p) == 0) { // NOTE: CHECK IF PLAYER IS ON GROUND
            Location loc = p.getLocation();
            Vector vec = loc.getDirection();

            p.setVelocity(vec.multiply(1.5D));
            p.sendMessage("mamamaa it workie");

            // cooldown stuff
            this.cooldown.replace(p, this.defaultCooldown);
            manageCooldown(p.getServer().getScheduler(), p);
        } else if (this.cooldown.get(p) > 0 && p.isSneaking()) {
            p.sendMessage("wait " + String.valueOf(this.cooldown.get(p)) + " seconds pwease");
        }
    }

    public void manageCooldown(BukkitScheduler sched, Player p) {
        BukkitTask task = sched.runTaskTimer(this.plugin, () -> {
            if (this.cooldown.get(p) != 0) {
                this.cooldown.replace(p, this.cooldown.get(p)-1);
            } else {
                this.cooldownTasks.get(p).cancel();
            }
        }, 20L, 20L);
        this.cooldownTasks.put(p, task);
    }

    public ItemStack getItem() { return this.item; }
}
