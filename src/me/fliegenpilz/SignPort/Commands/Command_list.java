package me.fliegenpilz.SignPort.Commands;

import java.util.Set;

import me.fliegenpilz.SignPort.Basic;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class Command_list {
	
	Command cmd;
	String[] args;
	Player p;
	Basic Basic;
	
	public Command_list(Command cmd, String[] args, Player p, Basic Basic){
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
			
			//Set<String> test = Basic.getConfig().getConfigurationSection("Config.Spawn").getKeys(false);
			Object[] hui = Basic.getConfig().getStringList("Config.Spawn").toArray();
			
			
			
			String test = Basic.getConfig().getStringList("Config.Spawn").get(1);
			
			//getConfigurationSection("Config.Spawn").
			
			p.sendMessage("" + test);
							
		}

		if(args.length >= 4){
			p.sendMessage(ChatColor.RED + "Zu viele Argumente");
			p.sendMessage(ChatColor.RED + "/sp op [Spawn Punkt] <on/off>");
		}
				
		return true;
	}
}
