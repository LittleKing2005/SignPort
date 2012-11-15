package me.fliegenpilz.SignPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SignListener implements Listener {

	private Basic plugin;

	public SignListener(Basic plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
			BlockState Zustand = event.getClickedBlock().getState();
			if (Zustand instanceof Sign){
				Sign Schild = (Sign)Zustand;
				if(Schild.getLine(0).equalsIgnoreCase("[SignPort]")){
					String target = Schild.getLine(2).toString();
					String op = Schild.getLine(3);
					event.setCancelled(true);
					
					Boolean Spawn = getCustomConfig().getBoolean("Config.Spawn." + target + ".enable");
					if(Spawn == true){
						
						if(op.equalsIgnoreCase("Only OP")){
							if(p.isOp() == true){
						        Location spawnLoc = getHomeLoc(target);
						        p.teleport(spawnLoc);
								p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Sie wurden Teleportiert");
							} else {
								p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Dises Schild können nur OP´s Nutzen");
							}
						}
						
						if(op.equalsIgnoreCase("")){
							Location spawnLoc = getHomeLoc(target);
						    p.teleport(spawnLoc);
							p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Sie wurden Teleportiert");
						}
												
						
					} else {
						Zustand.getBlock().breakNaturally();
						p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.RED + "Dieser Spawn ist Leider nicht mehr verfügbar");
					}
				}
			}
		}
				
				
		if (event.getAction() == Action.LEFT_CLICK_BLOCK){
			BlockState Zustand = event.getClickedBlock().getState();
			if (Zustand instanceof Sign){
				Sign Schild = (Sign)Zustand;
				if(Schild.getLine(0).equalsIgnoreCase("[SignPort]")){
					Zustand.getBlock().breakNaturally();
					p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Schild wurde zerstört");
					
				}
			}
		}
	}
	
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		Player p = event.getPlayer();
		if(event.getLine(0).equalsIgnoreCase("[SignPort]")){
			if(!event.getLine(1).equalsIgnoreCase("")){
				String target = event.getLine(1).toString();
				String schild3 = event.getLine(2).toString();
				
				Boolean Spawn = getCustomConfig().getBoolean("Config.Spawn." + target + ".enable");
				Boolean OnlyOP = getCustomConfig().getBoolean("Config.Spawn." + target + ".OP");
				
				if(Spawn == true){
					if(OnlyOP == true){
						if(p.isOp() == true){
							event.setLine(0, "[SignPort]");
							event.setLine(1, "Port to:");
							event.setLine(2, target);
							event.setLine(3, "Only OP");
							p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Spawn Schild erstellt");
						} else {
							p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Dieses ist ein OP Schild und kann daher nur von OP's erstellt werden");
						}
					} else {
						if(schild3.equalsIgnoreCase("op")){
							if(p.isOp() == true){
								event.setLine(3, "Only OP");
							} else {
								p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Dieses ist ein OP Schild und kann daher nur von OP's erstellt werden");
							}
						}
						event.setLine(0, "[SignPort]");
						event.setLine(1, "Port to:");
						event.setLine(2, target);
						p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Spawn Schild erstellt");
					}
				} else {
					event.getBlock().breakNaturally();
					p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.RED + "Dieses Ziel Exestiert leider nicht");
				}
					
			}
			if(event.getLine(1).equalsIgnoreCase("")){
				event.getBlock().breakNaturally();
				p.sendMessage(ChatColor.GREEN + "[SignPort] " + ChatColor.DARK_PURPLE + "Bitte ein Ziel setzten");
			}
		}	
	}
	
	
	
	
	
    public Location getHomeLoc(String spawn) {
        Location location;
		
            String world = getCustomConfig().getString("Config.Spawn." + spawn + ".World");
			int LocX = getCustomConfig().getInt("Config.Spawn." + spawn + ".X");
			int LocY = getCustomConfig().getInt("Config.Spawn." + spawn + ".Y");
			int LocZ = getCustomConfig().getInt("Config.Spawn." + spawn + ".Z");
            float yaw = getCustomConfig().getInt("Config.Spawn." + spawn + ".yaw");
            float pitch = getCustomConfig().getInt("Config.Spawn." + spawn + ".pitch");
            
            location = new Location(plugin.getServer().getWorld(world), LocX, LocY, LocZ, yaw, pitch);
		
        return location;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

	// Spawn.yml befehle Start
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(plugin.getDataFolder(), "spawn.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Schaut nach den Standardwerten in der jar
	    InputStream defConfigStream = plugin.getResource("spawn.yml");
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