package nl.Steffion.DropIce;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DropIceBlockListener implements Listener {
	public static DropIce plugin;
	public DropIceBlockListener(DropIce instance) {
		plugin = instance;
	}
	 @EventHandler(priority = EventPriority.NORMAL)
	    public void onBlockBreak(BlockBreakEvent event) {
	    	Material block = event.getBlock().getType();
	    	Player player = event.getPlayer();
	    	if (block == Material.ICE && event.getPlayer().getInventory().getItemInHand().getType().name().toLowerCase().contains("gold_pickaxe") && player.hasPermission("dropice.use")) {
	    		Location blockLocation = event.getBlock().getLocation();
	    		double y = blockLocation.getBlockY();
	    		double x = blockLocation.getBlockX();
	    		double z = blockLocation.getBlockZ();
	    		World currentWorld = event.getPlayer().getWorld();
	    		int a = plugin.dropicedropblockamount;
	    		int b = 1;
	    		
	    		while (b <= a) {
    				ItemStack droppedItem = new ItemStack(plugin.dropicedropblockblockid, 1);
    				currentWorld.dropItem(blockLocation, droppedItem);
    				b++;
	    		}
	    		//for (int a = 1; a <= plugin.dropicedropblockamount; a++) {
	    		//	if (plugin.dropicedropblockamount / 64 >= 1) {
	    		//		a = a + 64;
	    		//		player.sendMessage("¤c" + a);
	    		//		ItemStack droppedItem = new ItemStack(plugin.dropicedropblockblockid, 64);
	    		//		currentWorld.dropItem(blockLocation, droppedItem);
	    		//	} else {
	    		//		ItemStack droppedItem = new ItemStack(plugin.dropicedropblockblockid, 1);
	    		//		currentWorld.dropItem(blockLocation, droppedItem);
	    		//	}
	    		//	player.sendMessage("¤a" + a);
	    		//}
	    		//ItemStack droppedItem = new ItemStack(plugin.dropicedropblockblockid, plugin.dropicedropblockamount);
	    		//currentWorld.dropItem(blockLocation, droppedItem);
	    		currentWorld.playEffect(blockLocation, Effect.MOBSPAWNER_FLAMES, 5, 5);
	    		currentWorld.playEffect(blockLocation, Effect.SMOKE, 5, 5);
	    		Location WaterBlock = new Location (currentWorld, x, y -1, z);
	    		if (plugin.dropicemessageenabled == true) {
	    			player.sendMessage(plugin.dropicemessagemessage
	    										.replace("&0","¤0")
	    										.replace("&1","¤1")
	    										.replace("&2","¤2")
	    										.replace("&3","¤3")
	    										.replace("&4","¤4")
	    										.replace("&5","¤5")
	    										.replace("&6","¤6")
	    										.replace("&7","¤7")
	    										.replace("&8","¤8")
	    										.replace("&9","¤9")
	    										.replace("&a","¤a")
	    										.replace("&b","¤b")
	    										.replace("&c","¤c")
	    										.replace("&d","¤d")
	    										.replace("&e","¤e")
	    										.replace("&f","¤f")
	    										.replace("&k","¤k"));
	    		}
	    		if (WaterBlock.getBlock().getType() == Material.STATIONARY_WATER || WaterBlock.getBlock().getType() == Material.WATER) {
	    			blockLocation.getBlock().setType(Material.WATER);
	    		} else {
	    			blockLocation.getBlock().setType(Material.AIR);
	    		}
	    	}
	 }
}