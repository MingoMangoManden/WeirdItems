package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.*;
import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Helpers.Other.RarityType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import java.util.Random;

public class Rock extends WeirdItem implements Listener {

    ItemStack item;
    Item item_;

    public Rock(Main plugin) {
        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Silent I"); // this is how u make custom enchants
        lore.add(ChatColor.ITALIC + "hooman make fire, get warm");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item rock = new Item("Rock", Material.FLINT, RarityType.LEGENDARY, lore, enchants, new Category[]{Category.TOOL});
        ItemMeta meta = rock.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE);
        rock.getItem().setItemMeta(meta);

        this.item = rock.getItem();
        this.item_ = rock;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void rightClickBlock(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        Block b = e.getClickedBlock();
        Random rand = new Random();

        if (b == null) { return; }
        if (item.getItemMeta() == null) { return; }

        Block b2 = b.getLocation().add(0, 1, 0).getBlock();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                b2.getType().equals(Material.AIR) &&
                e.getBlockFace().equals(BlockFace.UP) &&
                item.getItemMeta().equals(this.item.getItemMeta())) {
            b2.setType(Material.FIRE);
            p.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1f, 1f);

            if (rand.nextInt(100) > 90) {
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
            }
        }
    }

    public Item getItem() { return this.item_; }
}
