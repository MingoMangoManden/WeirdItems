package Mingo.WeirdItems.Items;

import Mingo.WeirdItems.DeathCauses.DeathCause;
import Mingo.WeirdItems.Item;
import Mingo.WeirdItems.Main;
import Mingo.WeirdItems.RarityType;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeathNote implements Listener {

    public static ItemStack item;
    Main plugin;

    //List<Player> dyingPlayers = new ArrayList<>();
    HashMap<Player, DeathCause> dyingPlayers = new HashMap<>();
    List<String> guideAndRules;

    HashMap<Player, BukkitTask> playersGettingConsumed = new HashMap<>();

    public DeathNote(Main plugin) {
        List<String> lore = new ArrayList<>();
        //lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "enchant I"); // this is how u make custom enchants
        lore.add(ChatColor.GRAY + "Rules & guide are provided in the book.");
        lore.add(ChatColor.GRAY + "(Shift + Right Click to see them)");

        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        Item book = new Item("Death Note", Material.WRITABLE_BOOK, RarityType.LEGENDARY, lore, enchants);
        BookMeta meta = (BookMeta) book.getItem().getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        // RULES FOR USE
        List<String> rules = new ArrayList<>();
        rules.add(
                ChatColor.DARK_GRAY + "           Rules " +
                        ChatColor.GRAY + "\n\n- The player whose name is written in this note shall die." +
                        ChatColor.GRAY + "\n- Within 40 seconds of writing a name, the player will die." +
                        ChatColor.GRAY + "\n- Only one player's name can be written in this note at a time." +
                        ChatColor.DARK_GRAY + "\n\n\nMade with ❤ by Mingo"
        );
        rules.add(
                ChatColor.DARK_GRAY + "           Guide" +
                        ChatColor.GRAY + "\n\nWrite a player's name in the note. " +
                        ChatColor.GRAY + "If you do not specify a death cause, " +
                        ChatColor.GRAY + "the player will simply die of a heart attack." +
                        ChatColor.DARK_GRAY + "\n\nExample:" +
                        ChatColor.GRAY + "\nname deathcause" +
                        ChatColor.GOLD + "\nSteve 1"
        );
        rules.add(
                ChatColor.DARK_GRAY + "   Causes of Death" +
                        ChatColor.GRAY + "\n\n0. Heart Attack" +
                        ChatColor.GRAY + "\n1. Lightning Strike" +
                        ChatColor.GRAY + "\n2. Eaten by Ground" +
                        ChatColor.GRAY + "\n3. Explosion"
        );
        this.guideAndRules = rules;
        //meta.setPages(rules);
        //
        book.getItem().setItemMeta(meta);

        this.item = book.getItem();
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWrite(PlayerEditBookEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (isNote(item)) {
            if (!p.isSneaking()) {
                BookMeta before = e.getPreviousBookMeta();
                BookMeta after = e.getNewBookMeta();

                if (e.isSigning()) { e.setSigning(false); }
                if (getChanges(before, after) == null || getChanges(before, after).size() == 0) { return; }
                //p.sendMessage("true");

                // isolate the name and death cause.
                String victimName = getChanges(before, after).get(0);
                DeathCause cause = calculateCause(before, after);

                Player victim = p.getServer().getPlayer(victimName.replace("\n", "").trim()); // remove spaces and weird new lines

                if (victimName != "" && victim != null) { // also check if player is not in dyingplayers
                    if (!dyingPlayers.containsKey(p)) { // run if victim is not already dying
                        BukkitScheduler sched = p.getServer().getScheduler();

                        p.sendMessage(ChatColor.DARK_RED + "The victim will die soon...");
                        kill(sched, victim, p,cause);
                    }
                }// else if (victimName != "" && victim == null)
            } else { // player is sneaking
                e.setCancelled(true);
                showGuideAndRules(p);
            }
        }
    }

    @EventHandler
    public void openBook(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack heldItem = p.getInventory().getItemInMainHand();

        if (heldItem == null) { return; }

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (isNote(heldItem) && p.isSneaking()) {
                e.setCancelled(true);
                showGuideAndRules(p);
            }
        }
    }

    @EventHandler
    public void playerKilledByNote(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (this.dyingPlayers.containsKey(p)) {
            e.setDeathMessage(ChatColor.DARK_RED + p.getDisplayName() + " " + this.dyingPlayers.get(p).getMessage());
        }
    }

    public List<String> getChanges(BookMeta before, BookMeta after) {
        if (after.getPageCount() == 0) { return null; }
        List<String> change = new ArrayList<>();

        String oldContent = "";
        String newContent = "";

        if (before.hasPages()) {
            oldContent = before.getPage(before.getPageCount());
        }
        if (after.hasPages()) {
            //this.plugin.getServer().broadcastMessage(String.valueOf(after.getPageCount()));
            newContent = after.getPage(after.getPageCount());
        }

        //if (oldPage == null && newPage == null) { return null; } // This is not the problem
        if (!newContent.equalsIgnoreCase(oldContent)) {
            // a change has been made
            String changedContent = newContent.replace(oldContent, "");
            List<String> changedContentList = new ArrayList<>(Arrays.asList(changedContent.split(" "))); // thanks, stackoverflow
            //Bukkit.getServer().broadcastMessage(String.valueOf(changedContentList));
            if (changedContentList.size() > 0) {
                return changedContentList;
            }
        }

        return change;
    }

    public void showGuideAndRules(Player p) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setPages(this.guideAndRules);
        meta.setTitle("Death Note Rules and Guide");
        meta.setAuthor("Mingo");
        book.setItemMeta(meta);

        p.openBook(book);
        destroyNote(p, true);
    }

    public void kill(BukkitScheduler sched, Player victim, Player attacker, DeathCause cause) {
        dyingPlayers.put(victim, cause);

        sched.runTaskLater(this.plugin, () -> {
            switch (cause) {
                case HEART_ATTACK:
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20*6, 1);
                    PotionEffect effect2 = new PotionEffect(PotionEffectType.BLINDNESS, 20*6, 1);
                    PotionEffect effect3 = new PotionEffect(PotionEffectType.CONFUSION, 20*6, 1);

                    victim.addPotionEffect(effect);
                    victim.addPotionEffect(effect2);
                    victim.addPotionEffect(effect3);

                    sched.runTaskLater(this.plugin, () -> {
                        directKill(victim, attacker);
                        dyingPlayers.remove(victim);
                    }, 20L*3);
                    break;
                case LIGHTNING:
                    Location loc = victim.getLocation();
                    World w = loc.getWorld();

                    w.strikeLightning(loc);
                    directKill(victim, attacker);
                    dyingPlayers.remove(victim);
                    //strike.addPassenger
                    break;
                case EATEN_BY_GROUND:
                    this.dyingPlayers.put(victim, DeathCause.EATEN_BY_GROUND);
                    BukkitTask task = sched.runTaskTimer(this.plugin, () -> {
                        if (victim.isDead()) {
                            sched.cancelTask(playersGettingConsumed.get(victim).getTaskId());
                            playersGettingConsumed.remove(victim);
                            dyingPlayers.remove(victim);
                        }
                        if (!victim.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.BEDROCK)) {
                            Location loc2 = victim.getLocation().add(0.0D, -0.0625000000001D, 0.0D);
                            if (victim.getLocation() != loc2) {
                                victim.teleport(loc2);
                            }
                        }
                    }, 0L, 2L);
                    playersGettingConsumed.put(victim, task);
                    break;
                case EXPLODE:
                    this.dyingPlayers.put(victim, DeathCause.EXPLODE);
                    victim.getWorld().createExplosion(victim.getLocation(), 1, false, false);
                    directKill(victim, attacker);
                    this.dyingPlayers.remove(victim);
            }
        }, 20L*40);
    }

    public DeathCause calculateCause(BookMeta before, BookMeta after) {
        if (getChanges(before, after).size() > 1 && !getChanges(before, after).get(1).equalsIgnoreCase("")) {
            return getCause(Integer.parseInt(getChanges(before, after).get(1).replace("\n", "").trim())); // String -> Int -> DeathCause
        } else {
            return DeathCause.HEART_ATTACK;
        }
    }

    public DeathCause getCause(int i) {
        DeathCause deathCause = DeathCause.HEART_ATTACK;
        List<DeathCause> causes = Arrays.asList(DeathCause.values()); // get it as a List<> so we can modify it - remove later maybe

        for (DeathCause cause : causes) {
            if (cause.getId() == i) {
                deathCause = cause;
                break;
            }
        }

        return deathCause;
    }

    public void destroyNote(Player p, boolean inHand) {
        if (inHand) {
            p.getInventory().getItemInMainHand().setAmount(0);
        } else {
            p.getInventory().removeItem(this.item);
        }
    }

    public boolean isNote(ItemStack item) {
        if (item == null || item.getItemMeta() == null) { return false; }
        return item.getItemMeta().getDisplayName().equalsIgnoreCase(this.item.getItemMeta().getDisplayName());
    }

    public void directKill(Player victim, Player attacker) {
        victim.damage(victim.getHealth(), attacker);
    }

    public ItemStack getItem() { return this.item; }
}
