package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrapplingHook implements Listener {

    ItemStack item;
    boolean ready = false;

    public GrapplingHook(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add("Right click to go WOOOSHHH!");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.KNOCKBACK, 2);

        Item grap = new Item("Grappling Hook", Material.FISHING_ROD, RarityType.LEGENDARY, lore, enchants);

        //p.getInventory().addItem(grap.getItem());

        this.item = grap.getItem();

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerFishEvent e) {
        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();

        if (heldItem.equals(this.item) && this.ready) {
            //https://www.spigotmc.org/wiki/vector-programming-for-beginners/
            //p.setVelocity(p.getLocation().getDirection().multiply(3));
            Vector aim = e.getHook().getLocation().toVector().subtract(p.getLocation().toVector()).clone();
            p.setVelocity(aim.multiply(0.25));
            ready = false;
        } else {
            ready = true;
        }
    }

    public ItemStack getItem() { return this.item; }
}
