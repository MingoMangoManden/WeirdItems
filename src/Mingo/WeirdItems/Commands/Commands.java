package Mingo.WeirdItems.Commands;

import Mingo.WeirdItems.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    Main plugin;

    public Commands(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("weirditems").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            this.plugin.vault.loadItems(this.plugin.vault.getInv());
            p.openInventory(this.plugin.vault.getInv());

            /*switch (cmd.getName().toLowerCase()) {
                case "weirditems":
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("rl") && p.isOp()) {
                            p.getServer().getPluginManager().disablePlugin(this.plugin);
                            p.getServer().getPluginManager().enablePlugin(this.plugin);
                            p.sendMessage(ChatColor.GREEN + "WeirdItems was reloaded!");
                        }
                    }
                    else {
                        this.plugin.vault.loadItems(this.plugin.vault.getInv());
                        p.openInventory(this.plugin.vault.getInv());
                    }
                    break;
            }*/
        }

        return true;
    }
}
