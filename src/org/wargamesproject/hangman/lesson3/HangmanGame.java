package org.wargamesproject.hangman.lesson3;

import java.util.Scanner;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class HangmanGame 
{
	// Member variables
	private Player guessingPlayer,choosingPlayer;
	private List<String> wordList;
	// Constants
	private static final Player COMPUTER_PLAYER = new Player("Computer");
	private static final int DEFAULT_GUESSES = 10;
	// Path to word list as a class resource.  Makes the game platform-independent
	private static final String WORD_LIST_PATH = "/org/wargamesproject/hangman/lesson3/wordList.txt";
	
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
			
			String wordToGuess = this.wordList.get(wordIndex).toUpperCase();
			
			// Create the game state object
			GameState gameState = new GameState(wordToGuess,DEFAULT_GUESSES);
			Scanner guessScan = new Scanner(System.in);
			
			// Begin the game.  The game state controls when the game ends.  The game loop controls
			// receiving the guesses from the player and showing how many guesses are remaining.
			while (gameState.isGameOver() == false)
			{
				System.out.println("You have " + gameState.getGuessesRemaining() + " out of " + gameState.getGuessesAllowed() + " guesses remaining before you are hung!");
				System.out.println("Word progress: " + gameState.getMaskedWord());
				System.out.println();
				
				try
				{
					char guess = getPlayerGuess(guessScan,this.guessingPlayer);
					
					if (gameState.guessLetter(guess) == true)
					{
						System.out.println("Good guess!  The word contains the letter '" + guess + "'!");
					}
					else
					{
						System.out.println("You feel the noose tighten a bit!  The letter '" + guess + "' was not in the word.");
					}
				}
				catch (LetterAlreadyGuessedException e)
				{
					// The letter entered by the player was already guessed!  Display an error message.
					System.out.println(e.getMessage());
				}
			}
			
			// The game is over.  Determine the result!
			System.out.println("\nThe word was: " + wordToGuess);
			
			if (gameState.didPlayerWin() == true)
			{
				System.out.println("With an angry growl, Executioner " + this.choosingPlayer.getName() + " cuts you free of the rope!");
				System.out.println("Congratulations, your word guessing skills have saved your life!");
			}
			else
			{
				System.out.println("As the last letter leaves your lips, a smile crosses Executioner " + this.choosingPlayer.getName() + "'s lips");
				System.out.println("and they reach up and pull the lever...");
				System.out.println("SNAP!");
				System.out.println("Game Over!");
			}
		}
	}
	
	//
	// Private methods
	//
	
	private boolean loadWords()
	{
		boolean success = false;
		
		InputStream wordStream = this.getClass().getResourceAsStream(WORD_LIST_PATH);
			
		if (wordStream != null)
		{
			Scanner wordScanner = new Scanner(wordStream);
		
			while (wordScanner.hasNext() == true)
			{
				String nextWord = wordScanner.nextLine();
				this.wordList.add(nextWord);
			}
			wordScanner.close();
			
			success = true;
		}
		
		return success;
	}

	private char getPlayerGuess(Scanner guessScanner,Player player)
	{
		Character guess = null;
		boolean goodGuess = false;
		
		do
		{
			System.out.print(player.getName() + ", enter your guess: ");
			
			// Read the player's guess
			String nextLetter = guessScanner.nextLine();
			
			if (nextLetter.length() > 1)
			{
				// The guess was longer than one character.  Display an error message.
				System.out.println("Please enter only one letter!");
			}
			else
			{
				// The length was one character.  Make sure it was a letter.
				if (Character.isAlphabetic(nextLetter.charAt(0)) == false)
				{
					// The guess was not a character.  Display an error message.
					System.out.println("You may only guess letters from A to Z!  Please try again.");
				}
				else
				{
					// A single character was entered and it was a letter from A-Z.  This is 
					// a good guess.
					guess = nextLetter.charAt(0);
					goodGuess = true;
				}
			}

		}
		while (goodGuess == false);
		
		return guess;
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
