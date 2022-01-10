package mancala;
import java.util.ArrayList;

public class Board{
	
	Row[] rows;
	
	public Board(Player player1, Player player2) {
		rows = new Row[2];
		rows[0] = new Row(player1);
		rows[1] = new Row(player2);
		
	}
	
	public boolean pebblesAvailable(int playerNumber, int pit) {
		if(playerNumber ==1) {
			convertPitNumber(pit-1);  //This converter works with indices not pit numbers on the board so do pit-1
			pit++; 					  //because decrements pit to get the index not the pit number in next line of code
		}
		if(rows[playerNumber].getPitPebbles(pit-1) <1) {
			return false;
		}
		return true;
	}
	
	public boolean executeTurn(int player, int startingPit) {
		boolean myRow = true;
		Integer[] turnResults = rows[player % 2].distribute(0, startingPit, myRow);
		int pebblesLeft = turnResults[0];
		
		while(pebblesLeft > 0) {				//While there are still pebbles after distributing the pit in the player's row
			player++; 															//alternate between both players' rows
			myRow = !myRow; 													//to know if to fill the store or not
			turnResults = rows[(player % 2)].distribute(pebblesLeft, -1, myRow); //going to new row, so current pit is -1 since it will be incremented before distribution
			pebblesLeft = turnResults[0];
		}
		int lastPitFilled = (turnResults[1]);
		
		if(myRow && lastPitFilled == 6) { //if the user landed in the store
			return false;				  //turn isn't over
		}
		
		if(myRow && rows[(player % 2)].getPitPebbles(lastPitFilled)==1) { //If the last pit was empty			
			rows[(player % 2)].fillStore(rows[((player+1) % 2)].emptyPit(convertPitNumber(lastPitFilled))); //get the number of pebbles in the other player's corresponding pit
			rows[(player % 2)].fillStore(rows[player % 2].emptyPit(lastPitFilled)); //Add the last pebble that you were going to put in the empty pit
		}
		return true; //turn is over
	}
	
	//The pits stored in the row class have alternate indices as they're displayed. 
	//Meaning, what looks like the first pit for the second player, is actually pit[5]
	//This allows the pebbles to be distributed easily, but we must convert the pit number
	//When trying to access one pit's corresponding pit in the other row
	private int convertPitNumber(int pitNumber) {
		switch(pitNumber) {
		case 0:
			return 5;
		case 1:
			return 4;
		case 2: 
			return 3;
		case 3:
			return 2;
		case 4:
			return 1;
		case 5:
			return 0;
		}
		return 1;
	}
	
	//Check if the row is empty
	public boolean noMorePebbles(int player) {
		for(int i = 0; i< 6; i++) {
			if(rows[player].getPitPebbles(i) != 0) {
				return false;
			}
		}
		return true;
	}
	
	public void sweepRemainingPebbles(int player) {
		rows[player].sweep();
	}
	
	public int getWinner() {		
		if(rows[0].getStoreAmount() > rows[1].getStoreAmount()) {
			return 0;
		}
		else if (rows[1].getStoreAmount() > rows[0].getStoreAmount()) {
			return 1;
		}
		else return -1; //tie
	}
	
	public String printRow1() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("%-15s", rows[0].getPlayerName() + ":"));
		str.append("Store-" + rows[0].store.getPebbleCount() + "\t\t");
		for(int i=0; i<6; i++) {
			str.append(String.format("%-4s",String.valueOf(rows[0].getPitPebbles(i)) + "  "));
		}
		return str.toString();
	}
	
	public String printRow2() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("%-15s", rows[1].getPlayerName() + ":"));
		str.append("Store-" + rows[1].store.getPebbleCount() + "\t\t");
		for(int i=5; i>=0; i--) {
			str.append(String.format("%-4s",String.valueOf(rows[1].getPitPebbles(i)) + "  "));
		}
		return str.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("               Pit Number       ");
		str.append("1   2   3   4   5   6\n");
		str.append("               --------------------------------------\n");
		str.append(printRow1() + "\n" + printRow2() + "\n");
		return str.toString();
	}

}
