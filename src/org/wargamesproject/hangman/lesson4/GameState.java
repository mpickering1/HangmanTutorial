package org.wargamesproject.hangman.lesson4;

import java.util.Set;
import java.util.HashSet;

public class GameState 
{
	// Member variables
	private int guessesAllowed,guessesRemaining,lettersRemaining;
	private String wordToGuess;
	private Set<Character> lettersGuessed,lettersFound;
	
	public GameState(String word,int guessesAllowed)
	{
		// Initialize the game state
		this.lettersGuessed = new HashSet<Character>();
		this.lettersFound = new HashSet<Character>();
		this.wordToGuess = word.toUpperCase();
		this.guessesAllowed = guessesAllowed;
		this.guessesRemaining = guessesAllowed;
		
		this.calculateLettersRemaining();
	}
	
	public boolean isGameOver()
	{
		return !(this.guessesRemaining > 0 && this.lettersRemaining > 0);
	}
	
	public boolean didPlayerWin()
	{
		return (this.lettersRemaining == 0);
	}
	
	public int getGuessesAllowed()
	{
		return this.guessesAllowed;
	}
	
	public int getGuessesRemaining()
	{
		return this.guessesRemaining;
	}
	
	public boolean guessLetter(char letter) throws LetterAlreadyGuessedException
	{
		boolean letterFound = false;
		
		letter = Character.toUpperCase(letter);
		
		if (this.lettersGuessed.contains(letter) == true)
		{
			throw new LetterAlreadyGuessedException("Letter '" + letter + "' has already been guessed!  Try again.");
		}
		else
		{
			// Add the letter to guessed set
			this.lettersGuessed.add(letter);
			
			if (this.wordToGuess.indexOf(Character.toString(letter)) != -1)
			{
				// The provided letter chosen by the player was found in the word.
				this.lettersFound.add(letter);
				this.calculateLettersRemaining();
				letterFound = true;
			}
			else
			{
				// The letter was not found in the word.  Decrement the number of guesses 
				// remaining.
				this.guessesRemaining--;
			}
		}
		
		return letterFound;
	}
	
	public String getMaskedWord()
	{
		StringBuilder maskedBuilder = new StringBuilder();
		
		for (char currentLetter : this.wordToGuess.toCharArray())
		{
			if (this.lettersFound.contains(currentLetter) == true)
			{
				maskedBuilder.append(currentLetter);
			}
			else
			{
				maskedBuilder.append('_');
			}
			maskedBuilder.append(' ');
		}
		
		return maskedBuilder.toString();
	}
	
	//
	// Private methods
	//
	
	private void calculateLettersRemaining()
	{
		this.lettersRemaining = this.wordToGuess.length();
		
		for (char currentLetter : this.wordToGuess.toCharArray())
		{
			if (this.lettersFound.contains(currentLetter) == true)
			{
				this.lettersRemaining--;
			}
		}
	}
}
