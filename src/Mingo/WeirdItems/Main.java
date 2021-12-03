package Mingo.WeirdItems;

import Mingo.WeirdItems.Items.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public Vault vault;

    @Override
    public void onEnable() {
        this.vault = new Vault();
        new Commands(this);
        addItems();
    }

    public void addItems() {
        this.vault.addItem(new GrapplingHook(this).getItem());
        this.vault.addItem(new PoopSword(this).getItem());
        this.vault.addItem(new Hammer(this).getItem());
        this.vault.addItem(new HoneyPot(this).getItem());
        this.vault.addItem(new DeathNote(this).getItem());
        this.vault.addItem(new Cape(this).getItem());
        this.vault.addItem(new EnderButt(this).getItem());
        this.vault.addItem(new NinjaCap(this).getItem());
        this.vault.addItem(new Sandals(this).getItem());
        this.vault.addItem(new Shroom(this).getItem());
        this.vault.addItem(new Rock(this).getItem());
        this.vault.addItem(new CandyBag(this).getItem());
        //this.vault.addItem(new GrappleGun(this).getItem());
    }
}
