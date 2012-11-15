package me.fliegenpilz.SignPort.Commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.fliegenpilz.SignPort.Basic;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Command_op {
	
	Command cmd;
	String[] args;
	Player p;
	Basic Basic;
	
	public Command_op(Command cmd, String[] args, Player p, Basic Basic){
		this.cmd = cmd;
		this.args = args;
		this.p = p;		
		this.Basic = Basic;
	}
	
	public boolean run(){
		
		if(args.length == 1){
			p.sendMessage(ChatColor.RED + "Zu wenig Argumente");
			p.sendMessage(ChatColor.RED + "/sp op [Spawn Punkt] <on/off>");
		}
		if(args.length == 2){
			Boolean OnlyOP = getCustomConfig().getBoolean("Config.Spawn." + args[1] + ".OP");
			if(OnlyOP == true){
				p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Der Punkt " + args[1] + " kann nur von OP's verwendet weren");
			} else {
				p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Der Punkt " + args[1] + " kann von jedem benutzt werden");
			}
						
		}
		if(args.length == 3){
			if(p.isOp() == true){
				if(args[2].equalsIgnoreCase("on")){
					getCustomConfig().set("Config.Spawn." + args[1] + ".OP", true);
					saveCustomConfig();
					reloadCustomConfig();
					p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Der Port wurde auf OP gesetzt");
				} 
				if(args[2].equalsIgnoreCase("off")){
					getCustomConfig().set("Config.Spawn." + args[1] + ".OP", false);
					saveCustomConfig();
					reloadCustomConfig();
					p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Der Port wurde auf Öffentlich gesetzt");
				}
			} else {
				p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Der Befehl kann nur von einem OP verwendet werden");
			}
			
		}
		if(args.length >= 4){
			p.sendMessage(ChatColor.RED + "Zu viele Argumente");
			p.sendMessage(ChatColor.RED + "/sp op [Spawn Punkt] <on/off>");
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
	    YamlConfiguration.loadConfiguration(Basic.getResource("spawn.yml")).loadConfiguration(customConfigFile);
	 
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
