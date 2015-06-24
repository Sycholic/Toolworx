/**
 * 
 */
package net.boutopia.ToolWorx.TWxCore;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Lever;
/**
 * @author bulshavik
 *
 */
public class TWxListener implements Listener {

	TWxCore plugin ;
	public TWxListener(TWxCore TWxcore) {
		
		this.plugin =  TWxcore ; 
		plugin.getLogger().info("TWxListener construct");
		
		
	}
	
	   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	    public void playerInteract(PlayerInteractEvent event) {
	        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
	            return;

	        Block block = event.getClickedBlock();
	        if (block.getType() != Material.LEVER)
	            return;

	        Lever lever = (Lever) block.getState().getData();
	        BlockFace attachedFace = lever.getAttachedFace();
	        if (attachedFace == null) {
	            plugin.getLogger().warning("MachinaCore: Lever activated by " + event.getPlayer().getName() + " seems to be attached to nothing?");
	            return;
	        }

	        Block attachedTo = block.getRelative(attachedFace);
	       plugin.onLever(event.getPlayer(), attachedTo, attachedFace.getOppositeFace(), event.getItem());
	    }

}
