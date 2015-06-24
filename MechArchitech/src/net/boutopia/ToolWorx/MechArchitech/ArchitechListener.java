package net.boutopia.ToolWorx.MechArchitech;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class ArchitechListener implements Listener {

	MechArchitech plugin ;
	public ArchitechListener(MechArchitech mechArchitech) {
		this.plugin = mechArchitech ; 
		
		
		
	}
	
	 @EventHandler
	    public void onClick(BlockPlaceEvent event) {
		   Block block = event.getBlockPlaced() ;
		 plugin.AddBlock(block);
		 
	 }
	
		
	
	 @EventHandler
	    public void onClick(PlayerInteractEvent event) {
		
			if (event.getPlayer().getItemInHand().getType().equals(Material.STICK)){
				 plugin.getLogger().info("Pick Block fired ") ;
				 Block block = event.getClickedBlock() ;
				 plugin.SelectBlock(block);
					}
		
	 }
		
	 
	

@EventHandler
public void onClick(BlockBreakEvent event) {
	plugin.RemoveBlock(event.getBlock());
}
}