package zTesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import _main.Instance;
import global.Global;
import units.Block;
import units.Player;
import units_sub.Directional;
import units_sub.Location;

public class TestMovement {
	Instance instance;
	int rows = 5, columns = 5;
	
	/*
	 * 00 10 20 30 40
	 * 01 11 21 31 41
	 * 02 12 22 32 42
	 * 03 13 23 33 43
	 * 04 14 24 34 44
	 * 
	 */
	
	@Before 
	public void initialize(){
		assertEquals(1,1);
	}
	
	public static void main(String args[]){
		TestMovement testInstance = new TestMovement();
		testInstance.testPlayersMoveLargeBlock();
		//testInstance.testPlayerMoveSingleBlock();
	}
	
	@Test
	public void testPlayerMoveSingleBlock(){
		
		
		Player[] players = new Player[1];
		players[0] = new Player(new Location(2,2), 1);
		Block[] blocks = new Block[1];
		blocks[0] = new Block(new Location(2,1), 1, 1);
		
		instance = new Instance(players, blocks, rows, columns);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		players[0].move(0, instance.cellGrid(), Directional.UP, instance.frame());
	}
	
	@Test
	public void testPlayersMoveLargeBlock(){
		Player[] players = new Player[2];
		players[0] = new Player(new Location(1,3), 1);
		players[1] = new Player(new Location(2,3), 2);
		Block[] blocks = new Block[1];
		blocks[0] = new Block(new Location(1,2), 2, 1);
		//blocks[1] = new Block(new Location(0,1), 2, 2);
		
		instance = new Instance(players, blocks, rows, columns);
		instance.cellGrid().printCells();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		players[0].move(0, instance.cellGrid(), Directional.UP, instance.frame());
		instance.cellGrid().printCells();
		players[1].move(0, instance.cellGrid(), Directional.UP, instance.frame());
		instance.cellGrid().printCells();
		
		// Delete instance
	}
	
	@Test
	public void testPlayersFailToMoveLargeBlockWhereOffsetLargeBlockIs(){
		Player[] players = new Player[2];
		players[0] = new Player(new Location(1,3), 1);
		players[1] = new Player(new Location(2,3), 2);
		Block[] blocks = new Block[1];
		blocks[0] = new Block(new Location(1,2), 2, 1);
		
		instance = new Instance(players, blocks, rows, columns);
		Global.sleep(500);
		players[0].move(0, instance.cellGrid(), Directional.UP, instance.frame());
		instance.cellGrid().printCells();
		Global.sleep(500);
		players[1].move(0, instance.cellGrid(), Directional.UP, instance.frame());
		instance.cellGrid().printCells();
		Global.sleep(500);
		
		// Delete instance
	}
	
	@Test
	public void testPlayerMoveOutOfBounds(){
	}
	
	@Test
	public void testPlayerMovesSingleBlockOutOfBounds(){
	}
	
	@Test
	public void testPlayerMovesLargeBlockOutOfBounds(){
	}
}
