package me.fliegenpilz.SignPort.Commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.fliegenpilz.SignPort.Basic;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Command_create {
	
	Command cmd;
	String[] args;
	Player p;
	Basic Basic;
	
	public Command_create(Command cmd, String[] args, Player p, Basic Basic){
		this.cmd = cmd;
		this.args = args;
		this.p = p;		
		this.Basic = Basic;
	}
	
	public boolean run(){
		
		if(args.length == 1){
			p.sendMessage(ChatColor.RED + "Zu wenig Argumente");
			p.sendMessage(ChatColor.RED + "/sp create [Spawn Punkt]");
		}
		if(args.length == 2){
			Location Loc = (Location) p.getLocation();
			
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".enable", true);
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".X", Loc.getBlockX());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".Y", Loc.getBlockY());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".Z", Loc.getBlockZ());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".yaw", Loc.getYaw());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".pitch", Loc.getPitch());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".World", Loc.getWorld().getName());
			getCustomConfig().addDefault("Config.Spawn." + args[1] + ".OP", false);
						

			getCustomConfig().set("Config.Spawn." + args[1] + ".enable", true);
			getCustomConfig().set("Config.Spawn." + args[1] + ".X", Loc.getBlockX());
			getCustomConfig().set("Config.Spawn." + args[1] + ".Y", Loc.getBlockY());
			getCustomConfig().set("Config.Spawn." + args[1] + ".Z", Loc.getBlockZ());
			getCustomConfig().set("Config.Spawn." + args[1] + ".yaw", Loc.getYaw());
			getCustomConfig().set("Config.Spawn." + args[1] + ".pitch", Loc.getPitch());
			getCustomConfig().set("Config.Spawn." + args[1] + ".World", Loc.getWorld().getName());
			getCustomConfig().set("Config.Spawn." + args[1] + ".OP", false);

			
			getCustomConfig().options().copyDefaults(true);
			saveCustomConfig();
			reloadCustomConfig();
			
			p.sendMessage(ChatColor.GREEN + "[Sign Port] " + ChatColor.DARK_PURPLE + "SpawnPunkt " + ChatColor.BLUE + args[1] + ChatColor.DARK_PURPLE +" erstellt");
			
		}
		

		if(args.length == 3){
			if(args[2].equalsIgnoreCase("op")){
				if(p.isOp() == true){
					Location Loc = (Location) p.getLocation();
					
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".enable", true);
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".X", Loc.getBlockX());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".Y", Loc.getBlockY());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".Z", Loc.getBlockZ());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".yaw", Loc.getYaw());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".pitch", Loc.getPitch());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".World", Loc.getWorld().getName());
					getCustomConfig().addDefault("Config.Spawn." + args[1] + ".OP", true);
								
		
					getCustomConfig().set("Config.Spawn." + args[1] + ".enable", true);
					getCustomConfig().set("Config.Spawn." + args[1] + ".X", Loc.getBlockX());
					getCustomConfig().set("Config.Spawn." + args[1] + ".Y", Loc.getBlockY());
					getCustomConfig().set("Config.Spawn." + args[1] + ".Z", Loc.getBlockZ());
					getCustomConfig().set("Config.Spawn." + args[1] + ".yaw", Loc.getYaw());
					getCustomConfig().set("Config.Spawn." + args[1] + ".pitch", Loc.getPitch());
					getCustomConfig().set("Config.Spawn." + args[1] + ".World", Loc.getWorld().getName());
					getCustomConfig().set("Config.Spawn." + args[1] + ".OP", true);
		
					
					getCustomConfig().options().copyDefaults(true);
					saveCustomConfig();
					reloadCustomConfig();
					
					p.sendMessage(ChatColor.GREEN + "[Sign Port] " + ChatColor.DARK_PURPLE + "SpawnPunkt " + ChatColor.BLUE + args[1] + ChatColor.DARK_PURPLE +" erstellt");
				} else {
					p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.RED + "Diesen Befehl kann man nur als OP ausführen");
				}
			} else {
				p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.RED + "Falsche Argumente");
				p.sendMessage(ChatColor.RED + "/sp create [Spawn Punkt] <op>");
			}
		}
		
		if(args.length >= 4){
			p.sendMessage(ChatColor.RED + "Zu viele Argumente");
			p.sendMessage(ChatColor.RED + "/sp create [Spawn Punkt]");
		
		}
				
		return true;
	}
	
	
	
	
	

	// Spawn.yml befehle Start
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(Basic.getDataFolder(), "spawn.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Schaut nach den Standardwerten in der jar
	    InputStream defConfigStream = Basic.getResource("spawn.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}

	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	        reloadCustomConfig();
	    }
	    return customConfig;
	}

	public void saveCustomConfig() {
	    if (customConfig == null || customConfigFile == null) {
	    return;
	    }
	    try {
	        customConfig.save(customConfigFile);
	    } catch (IOException ex) {
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Konfiguration konnte nicht nach " + customConfigFile + " geschrieben werden.", ex);
	    }
	}
	
	// Spawn.yml befehle Ende
	
	
	
}

