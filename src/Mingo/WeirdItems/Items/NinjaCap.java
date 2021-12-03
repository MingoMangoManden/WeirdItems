package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NinjaCap implements Listener {

    ItemStack item;
    Main plugin;

    int defaultCooldown = 10;
    HashMap<Player, BukkitTask> cooldownTasks = new HashMap<>();
    HashMap<Player, Integer> cooldown = new HashMap<>();

    public NinjaCap(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Silent I"); // this is how u make custom enchants
        lore.add("Crouch to become invisible to");
        lore.add("players in a 10 block radius of you.");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item cap = new Item("Ninja Cap", Material.LEATHER_HELMET, RarityType.UNCOMMON, lore, enchants);
        LeatherArmorMeta meta = (LeatherArmorMeta) cap.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE);
        meta.setColor(Color.BLACK);
        cap.getItem().setItemMeta(meta);

        this.item = cap.getItem();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getHelmet();

        if (item == null) { return; }
        if (p.isSneaking() && item.equals(this.item)) { // NOTE: CHECK IF PLAYER IS ON GROUND
            BukkitScheduler sched = Bukkit.getScheduler();

            if (this.cooldown.get(p) == null) { this.cooldown.put(p, 0); }
            if (this.cooldown.get(p) == 0) {
                goInvis(sched, p);

                // cooldown stuff
                this.cooldown.replace(p, defaultCooldown);
                manageCooldown(sched, p);

                /*sched.runTaskLater(this.plugin, () -> {
                    p.setWalkSpeed(walkSpeed);
                    for (Player player : finessed) {
                        player.showPlayer(this.plugin, p);
                    }
                }, 20L*4);*/
            } else if (this.cooldown.get(p) > 0 && p.isSneaking()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "WAIT " + this.cooldown.get(p) + " SECONDS"));
            }
        }
    }

    public void goInvis(BukkitScheduler sched, Player p) {
        float walkSpeed = p.getWalkSpeed();
        List<Player> finessed = new ArrayList<>();

        p.setWalkSpeed(0.75f); // im a shoe

        for (Entity en : p.getNearbyEntities(10, 10, 10))
            if (en instanceof Player) {
            	Player player = (Player) en;
            	finessed.add(player);
            }
        for (Player player : finessed)
            player.hidePlayer(this.plugin, p);

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "ABILITY ACTIVATED"));

        sched.runTaskLater(this.plugin, () -> {
            p.setWalkSpeed(walkSpeed);
            for (Player player : finessed) {
                player.showPlayer(this.plugin, p);
            }
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "ABILITY DISABLED"));
        }, 20L*4);
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
