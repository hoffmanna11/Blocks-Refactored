package units;

import units_sub.Location;

public class Cell { 
	private Location loc;
	// References to the player/block at this cell location
	// Is null if no player/block is here
	private Player player = null;
	private Block block = null;
	
	public Cell(Location loc){
		this.loc = loc;
	}
	public Cell(int x, int y){
		this.loc = new Location(x,y);
	}
	
	public boolean hasPlayer(){
		if(null == player){
			return false;
		}
		return true;
	}
	
	public boolean hasBlock(){
		if(null == block){
			return false;
		}
		return true;
	}

	public boolean isFree(){
		if((hasPlayer() == false) &&
				(hasBlock()) == false){
			return true;
		}
		return false;
	}
	
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = new Location(loc.x(), loc.y());
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}
}
