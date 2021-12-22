package Mingo.WeirdItems.Helpers.Category;

import Mingo.WeirdItems.Helpers.Category.Category;
import Mingo.WeirdItems.Item;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

public class CategoryManager {

    public static HashMap<Category, Inventory> invs = new HashMap<>();
    public static List<Item> items;

    public CategoryManager() {
        createCategoryInvs();
    }

    public Inventory createCategoryInv(Category category) {
        Inventory inv = Bukkit.createInventory(null, items.size(), category.getName());

        // just a little safety check
        if (items.size() != 0 && items != null) {
            for (Item item : items) { // loop through Item objects
                for (int x = 0; x < item.getCategories().length; x++) { // loop through Item's categories
                    if (item.getCategories()[x].equals(category)) { // if its == to the target category
                        inv.addItem(item.getItem()); // add item to inv
                    }
                }
            }
        }

        return inv;
    }

    // smart
    public void createCategoryInvs() {
        HashMap<Category, Inventory> invs = new HashMap<>();

        for (Category category : Category.values()) {
            invs.put(category, createCategoryInv(category));
        }

        this.invs = invs;
    }
}
