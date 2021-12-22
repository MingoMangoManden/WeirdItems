package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.Helper;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import Mingo.WeirdItems.Item;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrappleGun extends WeirdItem implements Listener {

    ItemStack item;
    Item item_;

    Main plugin;

    public GrappleGun(Main plugin) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Shoot I"); // this is how u make custom enchants

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item gun = new Item("Grapple Gun", Material.BOW, RarityType.RARE, lore, enchants, new Category[]{Category.MOVEMENT});
        ItemMeta meta = gun.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        gun.getItem().setItemMeta(meta);

        this.item = gun.getItem();
        this.item_ = gun;
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(EntityShootBowEvent e) {
        ItemStack bow = e.getBow();

        if (e.getEntity() instanceof Player && Helper.isItem(bow, this.item)) {
            Player p = (Player) e.getEntity();
            Location loc = p.getLocation();
            Vector dir = loc.getDirection();

            Entity ar = e.getProjectile();
            Location arLoc = ar.getLocation();
            Vector arDir = arLoc.getDirection();

            p.setVelocity(dir.subtract(arDir));
        }
    }

    public Item getItem() { return this.item_; }
}
