package org.wargamesproject.hangman.lesson1;

import java.util.Scanner;

public class HangmanGame 
{
	// Member variables
	private Player guessingPlayer,choosingPlayer;
	// Constants
	private static final Player COMPUTER_PLAYER = new Player("Computer"); 
	
	public HangmanGame(Player guessingPlayer)
	{
		this.guessingPlayer = guessingPlayer;
		this.choosingPlayer = COMPUTER_PLAYER;
	}
	
	public void playGame()
	{
		System.out.println("Game play goes here.");
	}
	
	public static void main(String[] args)
	{
		System.out.println("Welcome to Hangman!");
		
		// Prompt for the player's name.  Make sure they enter at least two characters
		String playerName = null;
		
		Scanner nameScan = new Scanner(System.in);
		
		do
		{
			System.out.print("Enter player name: ");
			playerName = nameScan.nextLine();
			
			if (playerName.length() >= 2)
			{
				nameScan.close();
			}
			else
			{
				System.out.println("ERROR: Player name must be at least 2 characters!  Try again...");
			}
		}
		while (playerName.length() < 2);
		
		// Create the Player object instance passing in the player's name
		Player humanPlayer = new Player(playerName);
		
		// Create the instance of the HangmanGame object, passing the Player in the constructor
		HangmanGame hangman = new HangmanGame(humanPlayer);
		
		// Play a new game of Hangman by calling playGame() on the new game instance
		hangman.playGame();
	}
}
