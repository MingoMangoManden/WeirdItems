package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnderButt implements Listener {

    ItemStack item;
    List<Player> flyingPlayers = new ArrayList<>();
    HashMap<Player, EnderPearl> butts = new HashMap<>();

    public EnderButt(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Ride I"); // this is how u make custom enchants
        lore.add("Soar through the skies on your butt!");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item butt = new Item("Ender Butt", Material.ENDER_PEARL, RarityType.EPIC, lore, enchants);
        ItemMeta meta = butt.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        butt.getItem().setItemMeta(meta);

        this.item = butt.getItem();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /*
        dude fix this goddamn garbage code
     */

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();
        Action action = e.getAction();

        Location loc = p.getLocation();
        Vector vec = loc.getDirection();

        if (heldItem.getItemMeta() == null) { return; }
        if (action.equals(Action.RIGHT_CLICK_AIR) && heldItem.getItemMeta().equals(this.item.getItemMeta())) {
            e.setCancelled(true);
            EnderPearl pearl = p.launchProjectile(EnderPearl.class, vec.multiply(1D));
            p.playSound(loc, Sound.ENTITY_ENDER_PEARL_THROW, 5.0f, 2.0f);
            //pearl.setItem(new ItemStack(Material.HONEY_BOTTLE));
            //pearl.setGlowing(true);
            pearl.setCustomName("butt");
            pearl.addPassenger(p);

            flyingPlayers.add(p);
            butts.put(p, pearl);

            p.getInventory().remove(this.item);
        } else if (action.equals(Action.RIGHT_CLICK_BLOCK) && heldItem.equals(this.item)) {
            e.setCancelled(true);
        }
     }

    @EventHandler
    public void shiftClick(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (!isGrounded(p) && isOnButt(p) && p.isSneaking()) {
            if (butts.get(p) != null) {
                butts.get(p).remove();
                butts.remove(p);
            }
            flyingPlayers.remove(p);

            giveButt(p);
        }
    }

    @EventHandler
    public void onButtLand(ProjectileHitEvent e) {
    	if (e.getEntity() instanceof EnderPearl) { // <-- this somehow works?
        	EnderPearl butt = (EnderPearl) e.getEntity();
            if (butt.getCustomName() == null) { return; }

            if (butt.getCustomName().equalsIgnoreCase("butt")) {
            	if (butt.getPassengers().size() == 0) {
            		e.setCancelled(true);
                    butt.remove();
                }
                if (butt.getShooter() instanceof Player) { // just to be safe
                	Player p = (Player) butt.getShooter();
                	giveButt(p);
                }
            }
        }
    }

    public void giveButt(Player p) {
        Inventory inv = p.getInventory();
        Location loc = p.getLocation();

        //inv.addItem(this.item);

        /*if (!inv.contains(this.item)) {
            p.getWorld().dropItemNaturally(loc, this.item); // maybe find some other way to do this
        }*/
    }

    public boolean isGrounded(Player p) {
        return !p.isFlying() && p.getLocation().subtract(0, 0.1, 0).getBlock().getType().isSolid();
    }

    public boolean isOnButt(Player p) {
        return flyingPlayers.contains(p);
    }

    public ItemStack getItem() { return this.item; }
}
