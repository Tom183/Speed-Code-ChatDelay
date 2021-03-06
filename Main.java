package nl.ItsCodex.ChatDelay;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static ConfigYML c = ConfigYML.getInstance();
	public static Plugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		c.setup(this);
		c.getConfig().options().copyDefaults(true);
		
		c.getConfig().addDefault("Message", "&eJe moet nog &7%cooldown% &ewachten met typen!");
		c.getConfig().addDefault("Cooldown", 5);
		
		c.saveConfig();
	}
	@Override
	public void onDisable() {
	}
	
	@EventHandler
	public void on(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		
		if(p.hasPermission("ChatDelay.bypass") || p.isOp()){
			e.setCancelled(false);
		}else{
			if(Cooldowns.has(p)){
				e.setCancelled(true);
			}else{
				e.setCancelled(false);
				Cooldowns.add(p, c.getConfig().getInt("Cooldown"));
			}
		}
	}
	public static Main getInstance(){
		return Main.getPlugin(Main.class);
	}
}
