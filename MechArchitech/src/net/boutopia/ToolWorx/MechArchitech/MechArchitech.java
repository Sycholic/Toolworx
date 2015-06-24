/**
 * 
 */
/**
 * @author bulshavik
 *
 */
package net.boutopia.ToolWorx.MechArchitech ;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.command.*;
public final class MechArchitech extends JavaPlugin {
	ArchitechBlueprint blueprint ;
	public String CurrentName ; 
	public String LastName ;
	public int BlockEnum ;
	public Block CurrentBlock ;
	public World world ;
	public Location Drawloc ; 
	private BlueprintBlock SelectedBlueprintBlock ;
	private boolean buildingprint ;
	private boolean PickBlock ;
	static Server server ;
	String pluginFolder ;
	public void AddBlock(Block block){
		world = block.getWorld() ;
		if(!buildingprint)return ; 
		if (CurrentName.equals(LastName)){
		CurrentName = "Block" + Integer.toString(BlockEnum);
		BlockEnum = BlockEnum +1 ;
		}
		blueprint.SetBlock(CurrentName,block);
		blueprint.CalculateDimensions();
		getLogger().info(blueprint.DimensionString());
		LastName = CurrentName ;
		}
		
	public void RemoveBlock(Block block){
		if(!buildingprint)return ; 
		String BlockName = FindBlock(block,blueprint) ;
		if(!(BlockName==null)) 
			{
			getLogger().info("breaking block");
			blueprint.RemoveBlock(FindBlock(block,blueprint)) ;
			}
	}
	
	
	public void SelectBlock(Block block){
		if(!buildingprint)return ; 
		String foundblock = FindBlock(block,blueprint) ;
		if(!(foundblock==null))
		SelectedBlueprintBlock = blueprint.getBlock(FindBlock(block,blueprint));
	}
	
	
	public void Erase(Location loc,ArchitechBlueprint Blueprint){
		Block Anchor = world.getBlockAt(loc);
		Block B ;
		Anchor.setType(Material.AIR);
		Set<String> keys = Blueprint.getkeySet() ;
		Iterator<String> iterator = keys.iterator() ;
		String key ;
		while (iterator.hasNext()){
			   key = iterator.next() ;
			   B = Anchor.getRelative(Blueprint.getBlock(key).getXoffset(),
					   					Blueprint.getBlock(key).getYoffset(),
					   					Blueprint.getBlock(key).getZoffset());
			   B.setType(Material.AIR);
					
		}
	}
	
	public String FindBlock(Block block,ArchitechBlueprint Blueprint)
	{
		if (Blueprint.GetAnchorLocation() == null) return null ;
		Location Anchor = Blueprint.GetAnchorLocation() ;
		 getLogger().info("Anchor Location: X" );
		Location B ;// = Anchor ;
		BlueprintBlock BlueBlock ;
		Set<String> keys = Blueprint.getkeySet() ;
		Iterator<String> iterator = keys.iterator() ;
		int AnchorX = Anchor.getBlockX() ;
		int AnchorY = Anchor.getBlockY() ;
		int AnchorZ = Anchor.getBlockZ() ;
		int OffsetX = 0 ;
		int OffsetY = 0 ;
		int OffsetZ = 0 ;
		String key ;
		iterator = keys.iterator() ;
				while (iterator.hasNext()){
					key = iterator.next() ;
					BlueBlock = Blueprint.getBlock(key) ;
					OffsetX = BlueBlock.getXoffset() ;
					OffsetY = BlueBlock.getYoffset() ;
					OffsetZ = BlueBlock.getZoffset() ;
					
					B = new Location(world,OffsetX+AnchorX,OffsetY+AnchorY,OffsetZ+AnchorZ);
			   			   
			   getLogger().info(key + ":" + world.getBlockAt(B).toString()+ "\nB:"+ B.toString()) ;
			   getLogger().info(key +
					   ":"+
					   Blueprint.getBlock(key).getMaterial()+
					   " \nX:" +Integer.toString(Blueprint.getBlock(key).getXoffset()) +
					   " \nY:"+ Integer.toString(Blueprint.getBlock(key).getYoffset()) +
					   " \nZ:"+ Integer.toString(Blueprint.getBlock(key).getZoffset())  );
			   if(world.getBlockAt(B).equals(block)){ 
				   getLogger().info(key +" selected");
				   return key;
			   
			   }
					
		}
		
		   getLogger().info(block.toString() +" No Block selected -");
			
		return null;
		
	}
	
	
	public Boolean detect(Location loc,ArchitechBlueprint Blueprint){
		Block Anchor = world.getBlockAt(loc);
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
		return true ;
	}
	public void rotate(Location loc,ArchitechBlueprint Blueprint) {
		Erase(loc,Blueprint) ;
		Block Anchor = world.getBlockAt(loc);
		int holding ;
		Anchor.setType(Material.getMaterial(Blueprint.getBlock("Anchor").getMaterial()));
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
		
		Draw(loc,blueprint) ;
		
	}
	public void Draw(Location loc,ArchitechBlueprint Blueprint) {
		Block Anchor = world.getBlockAt(loc);
		Block B ;
		Anchor.setType(Material.getMaterial(Blueprint.getBlock("Anchor").getMaterial()));
		Set<String> keys = Blueprint.getkeySet() ;
		Iterator<String> iterator = keys.iterator() ;
		String key ;
		while (iterator.hasNext()){
			   key = iterator.next() ;
			   B = Anchor.getRelative(Blueprint.getBlock(key).getXoffset(),
					   					Blueprint.getBlock(key).getYoffset(),
					   					Blueprint.getBlock(key).getZoffset());
			   B.setType(Material.getMaterial(Blueprint.getBlock(key).getMaterial()));
					
		}
	}
	@Override
    public void onEnable(){
		File parent = getDataFolder();
	    if (!parent.exists())
            parent.mkdirs();
       buildingprint = false;
		getLogger().info("onEnable has been invoked!");
		server = this.getServer() ;
		pluginFolder = getDataFolder().getPath() ; 
	 getServer().getPluginManager().registerEvents(new ArchitechListener(this), this);
	 CurrentName = "Anchor" ;
	// ToolBlocks = new HashMap<String ,Block>() ;
	 blueprint = new ArchitechBlueprint(this) ;
	 BlockEnum = 1 ;
	 setPickBlock(false) ;
	}
 
    @Override
    public void onDisable() {
    	
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("onDisable has been invoked!");
    }
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("basic")){ // If the player typed /basic then do the following...
    		if(args.length < 1) return false ;
    		getLogger().info("Command Called\n" + args[0]);
    		if(args[0].equalsIgnoreCase("setblock"))
    				{
    			CurrentName = args[1] ;
    			    				}
    		if(args[0].equalsIgnoreCase("abort"))
			{
    			buildingprint= false ;
			}
    		
    		if(args[0].equalsIgnoreCase("list"))
			{
    			blueprint.listblocks();
			}
		    
		    				
    		if(args[0].equalsIgnoreCase("sethe"))
			{
    			blueprint.setHe(args[1]);
    			System.out.println("sethe to "+ blueprint.getHe());
			}
    		
    		if(args[0].equalsIgnoreCase("selblock"))
			{
    			setPickBlock(true) ; 
			}
    		
    		if(args[0].equalsIgnoreCase("draw"))
			{
    		
    			Drawloc = server.getPlayer(sender.getName()).getLocation() ;
    			Draw(Drawloc,blueprint) ;
			}
    		if(args[0].equalsIgnoreCase("move"))
			{
    			Erase(Drawloc,blueprint) ;
    			Drawloc.add(1, 0, 0) ;
    			Draw(Drawloc,blueprint) ;
			}
    		
    		if(args[0].equalsIgnoreCase("new"))
			{
    			buildingprint = true ;
    			blueprint= new ArchitechBlueprint(this);		
    			}
    		
    		
    		
    		if(args[0].equalsIgnoreCase("rotate"))
			{
    			
    			rotate(Drawloc,blueprint) ;
			}

    		if(args[0].equalsIgnoreCase("SetParam"))
			{
    			
    			SelectedBlueprintBlock.setParam(args[1], args[2]);
    			 getLogger().info(SelectedBlueprintBlock.getParam(args[1])+"set");
    			
			}
    		
			if(args[0].equalsIgnoreCase("save"))
			{
				
    	
		 	FileOutputStream fout;
				try {
					fout = new FileOutputStream(pluginFolder+ "\\a.txt");
					ObjectOutputStream oos = new ObjectOutputStream(fout);   
	    			oos.writeObject(blueprint);
	    			oos.close();
	    			System.out.println("Done");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				if(args[0].equalsIgnoreCase("load"))
				{ getLogger().info("load called");
				 getLogger().info("He "+ blueprint.getHe());
					blueprint = new ArchitechBlueprint(this);
						 try{
						       FileInputStream fin = new FileInputStream(pluginFolder+"\\a.txt");
							   ObjectInputStream ois = new ObjectInputStream(fin);
							   blueprint=(ArchitechBlueprint) ois.readObject();
							   ois.close();
							   getLogger().info("load done");
							 
					 
						   }catch(Exception ex){
							   getLogger().info("load fail");
							   ex.printStackTrace();
							 
						   } 
						 getLogger().info("He "+ blueprint.getHe());
					}

				

			
	

    		
    		return true;
    	}
           //If this has happened the function will return true. 
            // If this hasn't happened the a value of false will be returned.
    	return false; 
    }


	/**
	 * @return the pickBlock
	 */
	public boolean isPickBlock() {
		return PickBlock;
	}


	/**
	 * @param pickBlock the pickBlock to set
	 */
	public void setPickBlock(boolean pickBlock) {
		PickBlock = pickBlock;
	}

	}
