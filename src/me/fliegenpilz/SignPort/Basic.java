package me.fliegenpilz.SignPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.fliegenpilz.SignPort.Commands.Command_create;
import me.fliegenpilz.SignPort.Commands.Command_del;
import me.fliegenpilz.SignPort.Commands.Command_list;
import me.fliegenpilz.SignPort.Commands.Command_op;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Basic extends JavaPlugin{

	@Override
	public void onDisable() {
		//Plugin Deaktivieren
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " Dissabled");
	}
	
	@Override
	public void onEnable() {
		//Config Laden
		LoadConfig();
		
		
		
		//Events laden
		registerEvent();
		
		//Enable Schrift
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println("[" + pdfFile.getName() + "] " + pdfFile.getAuthors() + " Plugin Core v" + pdfFile.getVersion() + " is Enabled");
	}

	//Komandos
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		
		// überprüfung ob von Spieler gesendet
		if (!(sender instanceof Player))
		{	
			return true;
		}

		//grundkomando
		if (cmd.getName().equalsIgnoreCase("SignPort"))
			if (args.length == 0){
				PluginDescriptionFile pdfFile = this.getDescription();
				p.sendMessage(pdfFile.getName() + " Comandos");
				return true;
			}
		
		//grundkomando kürzel
		if (cmd.getName().equalsIgnoreCase("sp"))
			if (args.length == 0){
				PluginDescriptionFile pdfFile = this.getDescription();
				p.sendMessage(pdfFile.getName() + " Comandos");
				return true;
			}
		
			if (args.length >= 1){

				if (args[0].equalsIgnoreCase("create")){
					return new Command_create(cmd, args, p, this).run();
				}
				if (args[0].equalsIgnoreCase("del")){
					return new Command_del(cmd, args, p, this).run();
				}
				if (args[0].equalsIgnoreCase("op")){
					return new Command_op(cmd, args, p, this).run();
				}
				if (args[0].equalsIgnoreCase("list")){
					return new Command_list(cmd, args, p, this).run();
				}
				
			}
		
		return true;
	}
	

	//Event-Teil
	private void registerEvent() {
		sl = new SignListener(this);
	}

	private SignListener sl;
	
	
	//Config´s laden
	private void LoadConfig() {
		//config.yml
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		
		getCustomConfig().options().copyDefaults(true);
		saveCustomConfig();
	}

	
	
	
	// Spawn.yml befehle Start
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public void reloadCustomConfig() {
	    if (customConfigFile == null) {
	    customConfigFile = new File(getDataFolder(), "spawn.yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Schaut nach den Standardwerten in der jar
	    InputStream defConfigStream = getResource("spawn.yml");
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
