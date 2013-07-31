package nl.Steffion.DropIce;

import java.net.URL;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import nl.Steffion.DropIce.DropIcePlayerListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DropIce extends JavaPlugin{
	public static DropIce plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public final DropIceBlockListener blockListener = new DropIceBlockListener(this);
	public final DropIcePlayerListener playerListener = new DropIcePlayerListener(this);
	
	public String dropicemessagemessage = "&bThe ice broke and dropped a block!";
	public Boolean dropicemessageenabled = true;
	public int dropicedropblockblockid = 8;
	public int dropicedropblockamount = 1;
	private double newVersion;
	private double currentVersion;
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " by Steffion is now disabled.");
	}
	public void onEnable() {
		final PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " by Steffion is now enabled.");
		getServer().getPluginManager().registerEvents(blockListener, this);
		getServer().getPluginManager().registerEvents(playerListener, this);
		loadConfig();
		currentVersion = Double.valueOf(getDescription().getVersion().replaceFirst("\\.", ""));
		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				try {
					newVersion = updateCheck(currentVersion);
					if (newVersion > currentVersion) {
						log.warning("* ! * ! * ! * ! * ! * ! * ! * ! ");
						log.warning(pdfFile.getName() + " v" + newVersion + " is out! You are running: " + pdfFile.getName() + " v" + currentVersion);
						log.warning("Update" + pdfFile.getName() + " at: http://dev.bukkit.org/server-mods/dropice");
						log.warning("* ! * ! * ! * ! * ! * ! * ! * ! ");
					}
				} catch (Exception e) {
					log.severe("[" + pdfFile.getName() + "] Error tring to show updates. Error code: Z102");
				}
			}

		}, 0, 432000);
	}
	public void loadConfig()
	{
	    getConfig().options().copyDefaults(true);
	    saveConfig();

	    dropicemessageenabled = getConfig().getBoolean("dropice.message.enabled", dropicemessageenabled);
	    dropicemessagemessage = getConfig().getString("dropice.message.message", dropicemessagemessage);
	    dropicedropblockblockid = getConfig().getInt("dropice.dropblock.blockid", dropicedropblockblockid);
	    dropicedropblockamount = getConfig().getInt("dropice.dropblock.amount", dropicedropblockamount);
	    
	    saveConfig();
	}
	
	public double updateCheck(double currentVersion) throws Exception {
		final PluginDescriptionFile pdfFile = this.getDescription();
		String pluginUrlString = "http://dev.bukkit.org/server-mods/dropice/files.rss";
		try {
			URL url = new URL(pluginUrlString);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1) {
				Element firstElement = (Element)firstNode;
				NodeList firstElementTagName = firstElement.getElementsByTagName("title");
				Element firstNameElement = (Element) firstElementTagName.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();
				return Double.valueOf(firstNodes.item(0).getNodeValue().replace("DropIce", "").replaceFirst(".", "").trim());
			}
		} catch (Exception localException) {
			log.severe("[" + pdfFile.getName() + "] Error tring to check for updates. Error code: Z101");
		}
		return currentVersion;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (command.getName().equalsIgnoreCase("dropice")){ 
			if (args.length == 0) {
				if (player == null) {
					log.info("[DropIce]==================================");
	               	log.info("/dropice - Displays DropIce info.");
	               	log.info("/dropice reload - Reloads the DropIce config.");
	               	log.info("[Steffion]==================================");
	               	return true;
				} else if (player.hasPermission("dropice.info")) {
	               	player.sendMessage("¤6[¤aDropIce¤6]==================================");
	               	player.sendMessage("¤f/dropice - Displays DropIce info.");
	               	if (player.hasPermission("dropice.reload")) {
	               		player.sendMessage("¤f/dropice reload - Reloads the DropIce config.");
	               	}
	               	player.sendMessage("¤6[¤aSteffion¤6]==================================");
	               	log.info("[PLAYER_COMMAND] " + player + " used command: /dropice");
	               	return true;
				} else {
					player.sendMessage("¤cYou have no permission to do that!");
					return true;
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player == null) {
						reloadConfig();
						loadConfig();
						log.info("Reload succesful.");
						return true;
					} else if (player.hasPermission("dropice.reload")){
						reloadConfig();
						loadConfig();
						player.sendMessage("¤6Reload succesful.");
						log.info("[PLAYER_COMMAND] " + player + " used command: /dropice reload");
						return true;
					} else {
						player.sendMessage("¤cYou have no permission to do that!");
						return true;
					}
				} else {
					if (player == null) {
						log.info("Unknown command. Please do /dropice!");
						return true;
					} else {
						player.sendMessage("¤cUnknown command. Please do /dropice!");
						return true;
					}
				}
			} else {
				if (player == null) {
					log.info("Unknown command. Please do /dropice!");
					return true;
				} else {
					player.sendMessage("¤cUnknown command. Please do /dropice!");
					return true;
				}
			}
		}
		return false;
	}
	

}
