package me.fliegenpilz.SignPort.Commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class bsp {
	
	Command cmd;
	String[] args;
	Player p;
	
	public bsp(Command cmd, String[] args, Player p){
		this.cmd = cmd;
		this.args = args;
		this.p = p;		
	}
	
	public boolean run(){
		//Ausfüren
				
		return true;
	}
}

