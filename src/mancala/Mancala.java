package mancala;

import java.util.Scanner;

public class Mancala {
	
	Player[] players;
	Board board;
	Scanner input = new Scanner(System.in);
	
	public Mancala() {
		String[] names = getPlayerNames();
		players = new Player[2];
		players[0] = new Player(names[0]);
		players[1] = new Player(names[1]);
		board = new Board(players[0], players[1]);
		System.out.println(board);
	}
	
	public String[] getPlayerNames() {
		String[] names = new String[2];
		for(int i = 0; i<2; i++) {
		System.out.println("Please enter the name of player " + (i+1));
		names[i] = input.nextLine();
		}
		return names;
	}
	
	public void playGame() {
		boolean gameEnded = false;
		while(!gameEnded) {
			gameEnded = turn(0);
			if(!gameEnded) { //check if game ended between player1 and player2's turns
				gameEnded = turn(1);
			}		
		}
		informWinner();
	}
	
	public boolean turn(int playerNumber) {
		boolean turnEnded = false;
		System.out.println(players[playerNumber].getName() + " it is your turn");
		while(!turnEnded) {
			int pitNumber = whichPit(playerNumber);
			if(playerNumber == 1) { //If the second player is going, the pits shown are in the reverse order, so need to convert to real index
				pitNumber = convertPitNumber(pitNumber);
			}
			turnEnded = board.executeTurn(playerNumber, pitNumber-1);
			System.out.println(board);
			
			if(board.noMorePebbles(playerNumber)) { //If the player has no more pebbles in his row
				board.sweepRemainingPebbles((playerNumber +1)%2);
				return true; //Game is over
			}
			if(!turnEnded) System.out.println(players[playerNumber].getName() + " you get to go again");
		}
		return false; //Game is not over
	}
	
	public int whichPit(int playerNumber) {
		int pit = getPitNumber();
		while(!board.pebblesAvailable(playerNumber, pit)) {
			System.out.println("That pit has no pebbles. Please choose a different pit");
			pit = getPitNumber();
		}
		return pit;
	}
	
	public int getPitNumber() {
		System.out.println("Which pit would you like to pick up?");
		int pit = input.nextInt();
		while(pit < 1 || pit >6) {
			System.out.println("Please enter a pit number 1-6");
			pit = input.nextInt();
		}
		return pit;
	}
	
	private int convertPitNumber(int pitNumber) {
		switch(pitNumber) {
		case 1:
			return 6;
		case 2:
			return 5;
		case 3: 
			return 4;
		case 4:
			return 3;
		case 5:
			return 2;
		case 6:
			return 1;
		}
		return 1;
	}
	
	public void informWinner() {
		int winner = board.getWinner();
		System.out.println(board);
		if(winner == -1) {
			System.out.println("The game tied");
		}
		else {
			System.out.println("Congratulations " + players[winner].getName() + " you won!");
		}
		System.out.println("Game over");
	}

}
