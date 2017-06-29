package org.wargamesproject.hangman.lesson4;

public class LetterAlreadyGuessedException extends Exception 
{
	public LetterAlreadyGuessedException() 
	{
		super();
	}

	public LetterAlreadyGuessedException(String msg) 
	{
		super(msg);
	}

	public LetterAlreadyGuessedException(Throwable e) 
	{
		super(e);
	}

	public LetterAlreadyGuessedException(String arg0, Throwable arg1) 
	{
		super(arg0, arg1);
	}

	public LetterAlreadyGuessedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) 
	{
		super(arg0, arg1, arg2, arg3);
	}
}
