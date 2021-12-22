package Mingo.WeirdItems;

import Mingo.WeirdItems.Commands.Commands;
import Mingo.WeirdItems.Helpers.Category.CategoryManager;
import Mingo.WeirdItems.Helpers.Other.Vault;
import Mingo.WeirdItems.Items.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Vault vault;

    @Override
    public void onEnable() {
        this.vault = new Vault();
        new Commands(this);
        addItems();
    }

    public void addItems() {
        List<Item> items = new ArrayList<>();

        items.add(new GrapplingHook(this).getItem());
        items.add(new GrappleGun(this).getItem());
        items.add(new PoopSword(this).getItem());
        items.add(new Hammer(this).getItem());
        items.add(new HoneyPot(this).getItem());
        items.add(new DeathNote(this).getItem());
        items.add(new Cape(this).getItem());
        items.add(new EnderButt(this).getItem());
        items.add(new NinjaCap(this).getItem());
        items.add(new NinjaCap(this).getItem());
        items.add(new Sandals(this).getItem());
        items.add(new Shroom(this).getItem());
        items.add(new Rock(this).getItem());
        items.add(new CandyBag(this).getItem());

        for (Item item : items) {
            this.vault.addItem(item);
        }

        CategoryManager.items = items;
    }
}
