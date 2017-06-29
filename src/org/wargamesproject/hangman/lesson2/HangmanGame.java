package org.wargamesproject.hangman.lesson2;

import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HangmanGame 
{
	// Member variables
	private Player guessingPlayer,choosingPlayer;
	private List<String> wordList;
	private String wordToGuess;
	// Constants
	private static final Player COMPUTER_PLAYER = new Player("Computer");
	// Path to word list file.  Not ideal to hardcode this.  There is a better way to do this...
	private static final String WORD_LIST_PATH = "I:/jee-workspace/HangmanTutorial/src/org/wargamesproject/hangman/lesson2/wordList.txt";
	
	public HangmanGame(Player guessingPlayer)
	{
		this.guessingPlayer = guessingPlayer;
		this.choosingPlayer = COMPUTER_PLAYER;
		
		// Load the word list
		this.wordList = new ArrayList<String>();
		if (this.loadWords() == false)
		{
			System.out.println("ERROR: Unable to load words from path: " + WORD_LIST_PATH);
		}
	}
	
	public void playGame()
	{
		if (this.wordList.size() > 0)
		{
			System.out.println("Loaded " + this.wordList.size() + " words to play with!");
			
			// Choose a word at random
			Random random = new Random();
			int wordIndex = random.nextInt(this.wordList.size());
			
			this.wordToGuess = this.wordList.get(wordIndex);
			
			System.out.println(this.wordToGuess);
		}
	}
	
	//
	// Private methods
	//
	
	private boolean loadWords()
	{
		boolean success = false;
		
		try
		{
			InputStream wordStream = new FileInputStream(WORD_LIST_PATH);
			Scanner wordScanner = new Scanner(wordStream);
			
			while (wordScanner.hasNext() == true)
			{
				String nextWord = wordScanner.nextLine();
				this.wordList.add(nextWord);
			}
			wordScanner.close();
			
			success = true;
		}
		catch (FileNotFoundException e)
		{
			// The word list file did not exist.  
		}
		
		return success;
	}
	
	//
	// Program entry point
	//
	
	
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
			
			if (playerName.length() < 2)
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
