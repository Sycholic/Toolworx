/**
 * 
 */
package net.boutopia.toolworx.twxcore;

/**
 * @author user
 *
 */
public class TwxDriverBlock extends TwxBlock {
	TwxTool tool;
	public TwxDriverBlock(TwxTool tool){
		this.tool = tool ; 
	}

	@Override
	public void Run() {
		tool.moveRelative(0, 0, 1) ;
	}



}
