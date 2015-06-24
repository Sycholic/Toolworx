/**
 * 
 */
package net.boutopia.ToolWorx.TWxCore;

/**
 * @author user
 *
 */
public class TWxDriverBlock extends TWxBlock {
	TWxTool tool ;
	public TWxDriverBlock(TWxTool tool){
		this.tool = tool ; 
	}

	@Override
	public void Run() {
		tool.moveRelative(0, 0, 1) ;
	}



}
