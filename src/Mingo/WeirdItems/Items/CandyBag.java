package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Candy.Bag;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CandyBag extends WeirdItem implements Listener {

    ItemStack item;
    Item item_;

    Bag bag;

    public CandyBag(Main plugin) {
        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Silent I"); // this is how u make custom enchants
        lore.add(ChatColor.ITALIC + "Let's have a look of what's inside...");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item candyBag = new Item("Candy Bag", Material.RABBIT_STEW, RarityType.RARE, lore, enchants, new Category[]{Category.MAGICAL});
        ItemMeta meta = candyBag.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE);
        candyBag.getItem().setItemMeta(meta);

        this.item = candyBag.getItem();
        this.item_ = candyBag;
        this.bag = new Bag();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (item.getItemMeta() == null) { return; }

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) && item.getItemMeta().equals(this.item.getItemMeta())
                || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && item.getItemMeta().equals(this.item.getItemMeta())) {
            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
            p.playSound(loc, Sound.ITEM_DYE_USE, 1f, 1f);
            this.bag.getRandomCandy().giveToPlayer(p);
            e.setCancelled(true);
        }
    }

    public Item getItem() { return this.item_; }
}
