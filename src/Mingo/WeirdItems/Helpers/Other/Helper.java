package Mingo.WeirdItems.Helpers.Other;

import Mingo.WeirdItems.Helpers.Category.Category;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class Helper {

    public static boolean isItem(ItemStack item, ItemStack item2) {
        return item.getItemMeta().getDisplayName().equalsIgnoreCase(item2.getItemMeta().getDisplayName()) && item.getItemMeta().getLore().equals(item2.getItemMeta().getLore()) || item.equals(item2);
    }

    public static Category[] sortCategories(Category[] categories) {
        Collections.sort(Arrays.asList(categories));
        return categories;
    }
}
