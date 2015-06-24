/**
 * 
 */
package net.boutopia.ToolWorx.MechArchitech;



//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.block.Block;

/**
 * @author user
 *
 */
public class BlueprintBlock implements  java.io.Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private int Xoffset;
	private int Yoffset;
	private int Zoffset;
	private float pitch ;
	private float yaw ;
	private String material ;
	private Map<String,String> Params ;
	
	
//	private Map<String,String> Blockdata ;
	
	/**
	 * 
	 */
	
BlueprintBlock(Block block,MechArchitech plugin,int x,int y,int z) {
		setMaterial(block.getType().toString());
		setPitch(block.getLocation().getPitch()) ;
		setYaw(block.getLocation().getYaw()) ;
		setXoffset(x) ;
		setYoffset(y) ;
		setZoffset(z) ;
		Params = new HashMap<String,String>();
		}


BlueprintBlock(String Material,int x,int y,int z) {
	material = Material;
	setXoffset(x) ;
	setYoffset(y) ;
	setZoffset(z) ;
	Params = new HashMap<String,String>();
	}

	

	/* (non-Javadoc)
 * @see java.lang.Object#clone()
 */
@Override
protected Object clone() throws CloneNotSupportedException {
			
	
	return super.clone();
}
	

	/**
	 * @return the zoffset
	 */
	public int getZoffset() {
		return Zoffset;
	}
public String getParam(String key){
	return Params.get(key);
}

public void setParam(String key,String value){
	Params.put(key, value);
}
	/**
	 * @param zoffset the zoffset to set
	 */
	public void setZoffset(int zoffset) {
		Zoffset = zoffset;
	}

	/**
	 * @return the yoffset
	 */
	public int getYoffset() {
		return Yoffset;
	}

	/**
	 * @param yoffset the yoffset to set
	 */
	public void setYoffset(int yoffset) {
		Yoffset = yoffset;
	}

	/**
	 * @return the xoffset
	 */
	public int getXoffset() {
		return Xoffset;
	}

	/**
	 * @param xoffset the xoffset to set
	 */
	public void setXoffset(int xoffset) {
		Xoffset = xoffset;
	}

	/**
	 * @return the material
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}


	public Set<String> getParamkeys() {
		return Params.keySet() ;

	}


	/**
	 * @return the pitch
	 */
	public float getPitch() {
		return pitch;
	}


	/**
	 * @param pitch the pitch to set
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}


	/**
	 * @return the yaw
	 */
	public float getYaw() {
		return yaw;
	}


	/**
	 * @param yaw the yaw to set
	 */
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	

}

