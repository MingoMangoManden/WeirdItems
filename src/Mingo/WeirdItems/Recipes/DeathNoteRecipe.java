package Mingo.WeirdItems.Recipes;

import Mingo.WeirdItems.Items.DeathNote;
import Mingo.WeirdItems.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class DeathNoteRecipe {

    public DeathNoteRecipe(Main plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "deathnote");

        ShapedRecipe recipe = new ShapedRecipe(key, DeathNote.item);

        recipe.shape("NNN", "NBN", "NNN");

        recipe.setIngredient('N', Material.NETHERITE_BLOCK);
        recipe.setIngredient('B', Material.BOOK);

        Bukkit.addRecipe(recipe);
    }
}
