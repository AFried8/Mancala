package mancala;

public class Pit {
	
	private int pebbleCount;

	public Pit() {
		pebbleCount = 4;
	}
	
	public Pit(int pebbles) {
		pebbleCount = pebbles;
	}
	
	public void addPebble () {
		pebbleCount++;
	}
	
	public void addPebbles (int addAmount) {
		pebbleCount += addAmount;
	}
	
	public int getPebbleCount(){
		return pebbleCount;
	}
	
	public void clearPit() {
		pebbleCount = 0;
	}
	
	

}
