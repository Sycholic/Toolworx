/**
 * 
 */
package net.boutopia.ToolWorx.TWxCore;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.boutopia.ToolWorx.MechArchitech.ArchitechBlueprint;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;




/**
 * @author bulshavik
 *
 */
public class TWxCore extends JavaPlugin {

	
	static Server server ;
	private List <ArchitechBlueprint>  blueprints;
	private List <TWxTool> tools ;
	private List <String> AvailibleBlocks ; 
	/**
	 * 
	 */
	public TWxCore() {
		
	}
	 public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	    	if(cmd.getName().equalsIgnoreCase("tw")){ // If the player typed /basic then do the following...
	    		if(args.length < 1) return false ;
	    		getLogger().info("Command Called:" + args[0]);
	    		
	    		if(args[0].equalsIgnoreCase("list")){
	    			ArchitechBlueprint bprint ;
	    			Iterator<ArchitechBlueprint> iterator = blueprints.iterator();
	    			while (iterator.hasNext()){
	    				bprint = iterator.next() ;
	    				bprint.listblocks();
	    				getLogger().info(bprint.DimensionString());
	    				
	    				
	    			}
	    		}
	    			
	    		if(args[0].equalsIgnoreCase("Draw")){
	    			TWxTool tool ;
	    			Player player = server.getPlayer(sender.getName());
	    			getLogger().info(player.getDisplayName()) ;
	    			Iterator<TWxTool> iterator = tools.iterator();
	    			 getLogger().info(player.getLocation().toString());
	    			while (iterator.hasNext()){
	    				tool = iterator.next() ;
	    				tool.SetLocation( player.getLocation() );
	    				getLogger().info("11");
	    				
	    				getLogger().info(tool.getLocation().toString());
	    				tool.listblocks();
	    				getLogger().info("22");
	    				tool.Draw();
	    				getLogger().info("33");
	    			}
	    		}		
	    	
	    }return true;
	    	}
	 
	 public ArchitechBlueprint rotateBP(Location loc,ArchitechBlueprint Blueprint) {
		
			Block Anchor = loc.getWorld().getBlockAt(loc);
			int holding ;
			Set<String> keys = Blueprint.getkeySet() ;
			Iterator<String> iterator = keys.iterator() ;
			String key ;
			while (iterator.hasNext()){
				   key = iterator.next() ;
				   holding = Blueprint.getBlock(key).getXoffset() ;
				   Blueprint.getBlock(key).setXoffset(-Blueprint.getBlock(key).getZoffset());
				   //if (holding < 0) Blueprint.getBlock(key).setZoffset(-holding); 
				   //else
				   Blueprint.getBlock(key).setZoffset(holding); 
			}		  
	   return Blueprint ;
	 					
			}
	public Boolean detect(Location loc,ArchitechBlueprint Blueprint){
	Block Anchor = loc.getWorld().getBlockAt(loc);
		Block B ;
		if (!Anchor.getType().toString().equals(Blueprint.getBlock("Anchor").getMaterial())) return false ;
		Set<String> keys = Blueprint.getkeySet() ;
		Iterator<String> iterator = keys.iterator() ;
		String key ;
		while (iterator.hasNext()){
			   key = iterator.next() ;
			   B = Anchor.getRelative(Blueprint.getBlock(key).getXoffset(),
					   					Blueprint.getBlock(key).getYoffset(),
					   					Blueprint.getBlock(key).getZoffset());
			   if (!B.getType().toString().equals(Blueprint.getBlock(key).getMaterial())) return false ;
							
		} 
		getLogger().info("Print De tected!!!");
		Blueprint.listblocks() ;
			
		try {
			tools.add(new TWxTool(this,Blueprint,loc));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getLogger().info("tool added");
		
		return true ;

	}
	@Override
    public void onEnable(){
		setAvailibleBlocks(new ArrayList <String>());
		AvailibleBlocks.add("net.boutopia.ToolWorx.TWxCore.TWxDriverBlock");
		server = getServer() ;
		getLogger().info("server set");
		getLogger().info("onEnable has been invoked!");
		server = this.getServer() ;
		File parent = getDataFolder();
	      
	        if (!parent.exists())
	            parent.mkdirs();

	 getServer().getPluginManager().registerEvents(new TWxListener(this), this);
		getLogger().info("TWxListener registered!");
	blueprints = new ArrayList <ArchitechBlueprint>();
	ArchitechBlueprint bprint = new ArchitechBlueprint(this) ;
	tools = new ArrayList <TWxTool>();
    getLogger().info("load called");
	try{
			       FileInputStream fin = new FileInputStream("C:\\Users\\user\\Downloads\\bukkit\\plugins\\MechArchitech\\a.txt");
				   ObjectInputStream ois = new ObjectInputStream(fin);
				   bprint = (ArchitechBlueprint) ois.readObject();
				   ois.close();
				   bprint.setPlugin(this);
				   blueprints.add(bprint) ;
				   getLogger().info(" Core load done");
				 
		 
			   }catch(Exception ex){
				   getLogger().info("load fail");
				   ex.printStackTrace();
				 
			   } 
		

	
	 BukkitScheduler scheduler = server.getScheduler();
     scheduler.scheduleSyncRepeatingTask(this, new BukkitRunnable() {

		@Override
		public void run() {
			 
			   TWxTool tool ;
			   Iterator<TWxTool> iterator = tools.iterator() ;
					   while (iterator.hasNext()){
						   tool = iterator.next() ;
						   tool.update() ;
					   }
			
		}
     
     }, 20L,20L);
	
	}
 
    @Override
    public void onDisable() {
    	
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("onDisable has been invoked!");
    }
	public void onLever(Player player, Block attachedTo,
			BlockFace oppositeFace, ItemStack item) {
		getLogger().info("onLever Called");
		ArchitechBlueprint bprint ;
		Iterator<ArchitechBlueprint> iterator = blueprints.iterator();
		while (iterator.hasNext()){
			bprint = iterator.next() ;
			getLogger().info(bprint.DimensionString());
			if (detect(attachedTo.getLocation(),bprint))return;
			rotateBP(attachedTo.getLocation(),bprint);
			if (detect(attachedTo.getLocation(),bprint))return;
			rotateBP(attachedTo.getLocation(),bprint);
			if (detect(attachedTo.getLocation(),bprint))return;
			rotateBP(attachedTo.getLocation(),bprint);
			if (detect(attachedTo.getLocation(),bprint))return;
			
			
		}
		
		
		
	}
	/**
	 * @return the availibleBlocks
	 */
	public List <String> getAvailibleBlocks() {
		return AvailibleBlocks;
	}
	/**
	 * @param availibleBlocks the availibleBlocks to set
	 */
	public void setAvailibleBlocks(List <String> availibleBlocks) {
		AvailibleBlocks = availibleBlocks;
	}

}
