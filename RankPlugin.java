package com.Amit-Naik12.rankplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class RankPlugin extends JavaPlugin {
    private HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Rank Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Rank Plugin Disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setrank")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /setrank <player> <rank>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }

            String rank = args[1].toLowerCase();
            setRank(target, rank);
            sender.sendMessage(ChatColor.GREEN + "Set rank of " + target.getName() + " to " + rank);
            return true;
        }
        return false;
    }

    private void setRank(Player player, String rank) {
        if (!playerPermissions.containsKey(player.getUniqueId())) {
            playerPermissions.put(player.getUniqueId(), player.addAttachment(this));
        }
        PermissionAttachment attachment = playerPermissions.get(player.getUniqueId());

        // Remove old permissions
        attachment.getPermissions().clear();

        // Add new rank permissions
        switch (rank) {
            case "vip":
                attachment.setPermission("rank.vip", true);
                player.sendMessage(ChatColor.GOLD + "You are now VIP!");
                break;
            case "mvp":
                attachment.setPermission("rank.mvp", true);
                player.sendMessage(ChatColor.AQUA + "You are now MVP!");
                break;
            case "admin":
                attachment.setPermission("rank.admin", true);
                player.sendMessage(ChatColor.RED + "You are now an Admin!");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Unknown rank!");
        }
    }
}
