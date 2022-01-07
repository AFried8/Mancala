package mancala;

import java.util.ArrayList;

public class Row {

	Pit store;
	Pit[] pits;
	Player player;
	
	public Row(Player player) {
		this.player = player;
		pits = new Pit[6];
		store = new Pit(0);
		for(int i = 0; i < 6; i++) {
			pits[i] = new Pit();
		}
	}
	
	public Integer[] distribute(int pebbleAmount, int currentPit, boolean fillStore) {
		if(pebbleAmount == 0) { //If its the first distribution is the turn execution. 
			pebbleAmount = pits[currentPit].getPebbleCount();
			pits[currentPit].clearPit();
		}
		
		while(currentPit < 5 && pebbleAmount > 0) { //while still more pebbles to distribute
			currentPit++;
			pits[currentPit].addPebble();
			pebbleAmount--;
		}
		if(pebbleAmount > 0 && fillStore) {
			store.addPebble();
			pebbleAmount--;
			currentPit++;
		}
		Integer[] placeHolder = {pebbleAmount, currentPit};
		return placeHolder;
	}
	
	
	public int emptyPit(int n) {
		int pebbles = pits[n].getPebbleCount();
		pits[n].clearPit();
		return pebbles;		
	}
	
	public void fillStore(int n) {
		store.addPebbles(n);
	}
	
	public int getStoreAmount() {
		return store.getPebbleCount();
	}
	
	public int getPitPebbles(int id) {
		return pits[id].getPebbleCount();
	}
	
	public String getPlayerName() {
		return player.getName();
	}
	
	public void sweep() {
		for(int i = 0; i<6; i++) {
			store.addPebbles(emptyPit(i));
		}
	}
}
