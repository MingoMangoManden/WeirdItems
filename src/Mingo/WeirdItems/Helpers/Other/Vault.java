package Mingo.WeirdItems.Helpers.Other;

import Mingo.WeirdItems.Item;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Vault {

    Inventory inv;
    static List<ItemStack> items = new ArrayList<>();

    public Vault() {
        Inventory inv = Bukkit.createInventory(null, 18, "AMOGIS");
        inv.setMaxStackSize(1);

        this.inv = inv;
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }
    public void addItem(Item item) {
        items.add(item.getItem());
    }

    public void removeItem(ItemStack item) { items.remove(item); }
    public void removeItem(Item item) { items.remove(item.getItem()); }

    public void loadItems(Inventory inv) {
        inv.clear();
        for (ItemStack item : items) {
            if (!inv.contains(item)) {
                inv.addItem(item);
            }
        }
    }

    public Inventory getInv() {
        loadItems(inv);
        return this.inv;
    }

    public ItemStack getItem(Item item) {
        ItemStack i = null;

        if (this.inv.contains(item.getItem())) {
            for (ItemStack itemstack : this.inv.getContents()) {
                if (itemstack.equals(item.getItem())) {
                    i = itemstack;
                }
            }
        }

        return i;
    }

    public ItemStack getItem(ItemStack item) {
        ItemStack i = null;

        if (this.inv.contains(item)) {
            for (ItemStack itemstack : this.inv.getContents()) {
                if (itemstack.equals(item)) {
                    i = itemstack;
                }
            }
        }

        return i;
    }
}
