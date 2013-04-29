/*
 * AntiMobSpawn is a plugin for the Minecraft Server Mod Bukkit. AntiMobSpawn gives you ultimate control over the creatures that spawn in your Minecraft worlds
 * Copyright (C) 2011-2013 Euan J Hunter (SamLex) (Sam_Lex)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.samlex.ams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;
import org.bukkit.util.Vector;

public class AMSCommandHandler implements TabExecutor {

    AntiMobSpawn plugin;

    public AMSCommandHandler(AntiMobSpawn plugin) {
        this.plugin = plugin;
        plugin.getCommand("ams").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You must be Op to use this command");
            return true;
        }

        if (args.length < 1)
            return false;

        boolean hasSubArgs = false;
        boolean isPlayer = false;

        String subcmd = args[0].toLowerCase();
        String subargs[] = {};

        if (args.length > 1)
            hasSubArgs = true;

        if (hasSubArgs)
            subargs = Arrays.copyOfRange(args, 1, args.length);

        if (sender instanceof Player)
            isPlayer = true;

        switch (subcmd) {
            case "create":
                if (hasSubArgs && isPlayer) {
                    String zone_name = subargs[0];
                    AMSZone zone = plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", ((Player) sender).getWorld().getName()).ieq("zone_name", zone_name).findUnique();
                    if (zone == null) {
                        zone = new AMSZone();
                        zone.setWorld_name(((Player) sender).getWorld().getName());
                        zone.setZone_name(zone_name);
                        zone.setVec_p1_x(0);
                        zone.setVec_p1_y(0);
                        zone.setVec_p1_z(0);
                        zone.setVec_p2_x(0);
                        zone.setVec_p2_y(0);
                        zone.setVec_p2_z(0);
                        plugin.getDatabase().save(zone);
                        sender.sendMessage(ChatColor.GREEN + "Created zone called " + zone_name);
                    } else
                        sender.sendMessage(ChatColor.RED + "This zone has already been created");
                } else if (!hasSubArgs)
                    sender.sendMessage(ChatColor.RED + "No zone name specified");
                else if (!isPlayer)
                    sender.sendMessage(ChatColor.RED + "You must be ingame to do this");
                break;
            case "help":
                if (hasSubArgs) {
                    try {
                        int pageNumber = Integer.parseInt(subargs[0]);
                        if (pageNumber <= 0) {
                            sender.sendMessage(ChatColor.RED + "Invalid page number");
                            return true;
                        }
                        printUsage(sender, pageNumber);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Invalid page number");
                        return true;
                    }
                } else
                    printUsage(sender, 1);
                break;
            case "list":
                String world_name = "";
                if (hasSubArgs)
                    world_name = subargs[0];
                if (!hasSubArgs) {
                    if (isPlayer)
                        world_name = ((Player) sender).getWorld().getName();
                    else
                        world_name = "all";
                }
                if (world_name.equals("all")) {
                    for (World world : plugin.getServer().getWorlds())
                        printZones(world.getName(), sender);
                } else
                    printZones(world_name, sender);
                break;
            case "reload":
                plugin.getAMSConfig().reloadConfig();
                sender.sendMessage("Reloading config file");
                break;
            case "remove":
                if (hasSubArgs && isPlayer) {
                    String zone_name = subargs[0];
                    AMSZone zone = plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", ((Player) sender).getWorld().getName()).ieq("zone_name", zone_name).findUnique();
                    if (zone != null) {
                        plugin.getDatabase().delete(zone);
                        sender.sendMessage(ChatColor.GREEN + "Removed zone called " + zone_name);
                    } else
                        sender.sendMessage(ChatColor.RED + "This zone does not exist");

                } else if (!hasSubArgs)
                    sender.sendMessage(ChatColor.RED + "No zone name specified");
                else if (!isPlayer)
                    sender.sendMessage(ChatColor.RED + "You must be ingame to do this");
                break;
            case "set":
                if (hasSubArgs && isPlayer) {
                    String zone_name = subargs[0];
                    AMSZone zone = plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", ((Player) sender).getWorld().getName()).ieq("zone_name", zone_name).findUnique();
                    if (zone != null) {
                        Vector vec = ((Player) sender).getLocation().toVector();
                        if (subargs.length > 1) {
                            if (subargs.length > 2) {
                                if (subargs.length < 5)
                                    sender.sendMessage(ChatColor.RED + "Not enough coordinates specified");
                                try {
                                    switch (subargs[1]) {
                                        case "p1":
                                            zone.setVec_p1_x(Integer.parseInt(subargs[2]));
                                            zone.setVec_p1_y(Integer.parseInt(subargs[3]));
                                            zone.setVec_p1_z(Integer.parseInt(subargs[4]));
                                            break;
                                        case "p2":
                                            zone.setVec_p2_x(Integer.parseInt(subargs[2]));
                                            zone.setVec_p2_y(Integer.parseInt(subargs[3]));
                                            zone.setVec_p2_z(Integer.parseInt(subargs[4]));
                                            break;
                                        default:
                                            sender.sendMessage(ChatColor.RED + "Invalid point specified");
                                            break;
                                    }
                                } catch (NumberFormatException e) {
                                    sender.sendMessage(ChatColor.RED + "Invalid coordinate specified");
                                }
                            } else {
                                switch (subargs[1]) {
                                    case "p1":
                                        zone.setVec_p1_x(vec.getBlockX());
                                        zone.setVec_p1_y(vec.getBlockY());
                                        zone.setVec_p1_z(vec.getBlockZ());
                                        break;
                                    case "p2":
                                        zone.setVec_p2_x(vec.getBlockX());
                                        zone.setVec_p2_y(vec.getBlockY());
                                        zone.setVec_p2_z(vec.getBlockZ());
                                        break;
                                    default:
                                        sender.sendMessage(ChatColor.RED + "Invalid point specified");
                                        break;
                                }
                            }
                            plugin.getDatabase().update(zone);
                            sender.sendMessage(ChatColor.GREEN + "Set " + subargs[1] + " of zone called " + zone_name + " to " + vec.getBlockX() + "," + vec.getBlockY() + "," + vec.getBlockZ());
                        } else
                            sender.sendMessage(ChatColor.RED + "No point specified");
                    } else
                        sender.sendMessage(ChatColor.RED + "This zone does not exist");
                } else if (!hasSubArgs)
                    sender.sendMessage(ChatColor.RED + "No zone name specified");
                else if (!isPlayer)
                    sender.sendMessage(ChatColor.RED + "You must be ingame to do this");
                break;
            case "show":
                if (hasSubArgs && isPlayer) {
                    String zone_name = subargs[0];
                    AMSZone zone = plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", ((Player) sender).getWorld().getName()).ieq("zone_name", zone_name).findUnique();
                    if (zone != null) {
                        if (subargs.length > 1) {
                            if (subargs.length > 2) {
                                try {
                                    int blockid = Integer.parseInt(subargs[2]);
                                    if (blockid > 158)
                                        sender.sendMessage(ChatColor.RED + "Invalid block id specified");
                                    else
                                        showZone(sender, ((Player) sender).getWorld(), zone, subargs[1], blockid);
                                } catch (NumberFormatException e) {
                                    sender.sendMessage(ChatColor.RED + "Invalid block id specified");
                                }
                            } else
                                showZone(sender, ((Player) sender).getWorld(), zone, subargs[1], 41);
                        } else
                            sender.sendMessage(ChatColor.RED + "No type specified");
                    } else
                        sender.sendMessage(ChatColor.RED + "This zone does not exist");
                } else if (!hasSubArgs)
                    sender.sendMessage(ChatColor.RED + "No zone name specified");
                else if (!isPlayer)
                    sender.sendMessage(ChatColor.RED + "You must be ingame to do this");
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 1:
                String[] first_poss = { "create", "help", "list", "reload", "remove", "set", "show" };
                return checkPartialArgument(args[0], first_poss);
            case 2:
                switch (args[0]) {
                    case "list":
                        List<World> list_worlds = plugin.getServer().getWorlds();
                        ArrayList<String> worlds = new ArrayList<>();
                        worlds.add("all");
                        for (World world : list_worlds)
                            worlds.add(world.getName());
                        return checkPartialArgument(args[1], worlds.toArray(new String[1]));
                    case "remove":
                    case "set":
                    case "show":
                        List<AMSZone> zones = plugin.getDatabase().find(AMSZone.class).findList();
                        if (zones.size() == 0)
                            break;
                        ArrayList<String> zone_worlds = new ArrayList<>();
                        for (AMSZone zone : zones)
                            zone_worlds.add(zone.getZone_name());
                        return checkPartialArgument(args[1], zone_worlds.toArray(new String[1]));
                }
                break;
            case 3:
                switch (args[0]) {
                    case "set":
                        String[] points = { "p1", "p2" };
                        return checkPartialArgument(args[2], points);
                    case "show":
                        String[] types = { "corners", "fill", "outline", "walls" };
                        return checkPartialArgument(args[2], types);
                }
        }
        return new ArrayList<>();
    }

    private List<String> checkPartialArgument(String part, String[] possibles) {
        ArrayList<String> p = new ArrayList<>();
        for (String s : possibles) {
            if (part.length() < s.length())
                if (part.startsWith(s.substring(0, part.length())))
                    p.add(s);
        }
        return p;
    }

    private void printZones(String world_name, CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + world_name + ":");
        for (AMSZone zone : plugin.getDatabase().find(AMSZone.class).where().ieq("world_name", world_name).findList())
            sender.sendMessage(ChatColor.GOLD + "    " + zone.getZone_name() + " : " + "p1=" + zone.getVec_p1_x() + "," + zone.getVec_p1_y() + "," + zone.getVec_p1_z() + " p2=" + zone.getVec_p2_x() + "," + zone.getVec_p2_y() + "," + zone.getVec_p2_z());
    }

    private void showZone(CommandSender ply, World world, AMSZone zone, String type, int blockid) {
        ply.sendMessage(ChatColor.GREEN + "Showing zone called " + zone.getZone_name());
        Vector p1 = new Vector(zone.getVec_p1_x(), zone.getVec_p1_y(), zone.getVec_p1_z());
        Vector p2 = new Vector(zone.getVec_p2_x(), zone.getVec_p2_y(), zone.getVec_p2_z());
        Vector min = Vector.getMinimum(p1, p2);
        ply.sendMessage(min.toString());
        Vector max = Vector.getMaximum(p1, p2);
        ply.sendMessage(max.toString());
        switch (type) {
            case "corners":
                world.getBlockAt(min.getBlockX(), min.getBlockY(), min.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(max.getBlockX(), min.getBlockY(), min.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(max.getBlockX(), max.getBlockY(), min.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(min.getBlockX(), max.getBlockY(), min.getBlockZ()).setTypeId(blockid);

                world.getBlockAt(min.getBlockX(), min.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(max.getBlockX(), min.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(max.getBlockX(), max.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                world.getBlockAt(min.getBlockX(), max.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                break;
            case "fill":
                for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                            world.getBlockAt(x, y, z).setTypeId(blockid);
                        }
                    }
                }
                break;
            case "outline":
                for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    world.getBlockAt(x, min.getBlockY(), min.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(x, max.getBlockY(), min.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(x, min.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(x, max.getBlockY(), max.getBlockZ()).setTypeId(blockid);
                }
                for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    world.getBlockAt(min.getBlockX(), y, min.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(max.getBlockX(), y, min.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(min.getBlockX(), y, max.getBlockZ()).setTypeId(blockid);
                    world.getBlockAt(max.getBlockX(), y, max.getBlockZ()).setTypeId(blockid);
                }
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    world.getBlockAt(min.getBlockX(), min.getBlockY(), z).setTypeId(blockid);
                    world.getBlockAt(max.getBlockX(), min.getBlockY(), z).setTypeId(blockid);
                    world.getBlockAt(min.getBlockX(), max.getBlockY(), z).setTypeId(blockid);
                    world.getBlockAt(max.getBlockX(), max.getBlockY(), z).setTypeId(blockid);
                }
                break;
            case "walls":
                for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                        world.getBlockAt(x, y, min.getBlockZ()).setTypeId(blockid);
                        world.getBlockAt(x, y, max.getBlockZ()).setTypeId(blockid);
                    }
                }
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        world.getBlockAt(min.getBlockX(), y, z).setTypeId(blockid);
                        world.getBlockAt(max.getBlockX(), y, z).setTypeId(blockid);
                    }
                }
                for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                        world.getBlockAt(x, min.getBlockY(), z).setTypeId(blockid);
                        world.getBlockAt(x, max.getBlockY(), z).setTypeId(blockid);
                    }
                }
                break;
            default:
                ply.sendMessage(ChatColor.RED + "Invalid type specified");
                break;
        }
    }

    private void printUsage(CommandSender ply, int pageNumber) {
        //@formatter:off
        String usage = 
                ChatColor.GOLD + "    create <name> - " + ChatColor.WHITE + "creates a new zone with the the given name\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    help [n] - " + ChatColor.WHITE + "shows this help message. Specifyn to get n page of usage\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    list [world name] - " + ChatColor.WHITE + "lists all the zones on the given world. If no world is specified, the current is used. Use all to see all zones\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    reload - " + ChatColor.WHITE + "reloads the config file\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    remove <name> - " + ChatColor.WHITE + "removes the zone with the given name\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    set <name> p1|p2 [x] [y] [z] - " + ChatColor.WHITE + "sets a point in the given zone. p1 sets the first block. p2 sets the second block. If the exact coordinates are not specified, the block you stand in will be used\n" + ChatColor.RESET + 
                ChatColor.GOLD + "    show <name> <type> [block] - " + ChatColor.WHITE + "shows the given zone. Block must be a block id. If block is not specified, 41(gold block) will be used. Type be as follows:\n" + ChatColor.RESET + 
                ChatColor.GOLD + "        corners - " + ChatColor.WHITE + "the four corners of the zone will be 'highlighted' with the given block\n" + ChatColor.RESET + 
                ChatColor.GOLD + "        fill - " + ChatColor.WHITE + "the whole the zone will be 'highlighted' with the given block\n" + ChatColor.RESET +
                ChatColor.GOLD + "        outline - " + ChatColor.WHITE + "the outer edges of the zone will be 'highlighted' with the given block\n" + ChatColor.RESET + 
                ChatColor.GOLD + "        walls - " + ChatColor.WHITE + "the outer surfaces of the zone will be 'highlighted' with the given block\n" + ChatColor.RESET +  
                ChatColor.RED + "    -- WARNING -- the show command is NOT reversable. Any blocks replaced in the 'highlighting' process can not be put back to their orginal state other than by hand\n";
        //@formatter:on
        ChatPage page = ChatPaginator.paginate(usage, pageNumber);
        if (pageNumber > page.getTotalPages())
            pageNumber = page.getTotalPages();
        ply.sendMessage(ChatColor.YELLOW + " --- AntiMobSpawn Usage[" + pageNumber + "/" + page.getTotalPages() + "] ---\n");
        String indent = "    ";
        for (String line : page.getLines()) {
            boolean hasPrefix = false;
            if (line.contains("        ")) {
                indent = "         ";
                hasPrefix = true;
            } else if (line.contains("    ")) {
                indent = "     ";
                hasPrefix = true;
            }
            ply.sendMessage((hasPrefix ? "" : indent) + line);
        }
    }
}
