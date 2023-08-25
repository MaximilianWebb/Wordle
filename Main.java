/*************************************************************************
Created By: Max Webb and Stephen Buch
Help from: Jonathon Cihlar
Word List Credit: https://github.com/tabatkins/wordle-list/blob/main/words
*************************************************************************/

import java.util.*;

class Main {
	public static void gameLoop(String theWord,boolean guesser) {
		// if the helper is active 
		boolean guesserActive = guesser;
		// all the letters that arent in the word
		List<Character> badLetters = new ArrayList<Character>();
		// wordle object 
		Wordle worde = new Wordle();
		// the word that you are guessing to get
		String word = theWord;
		// your guess
		String guess = new String();
		// last guess
		// X = right letter right spot
		// O = right letter wrong spot
		String lastGuess = new String();
		// all the guessed words
		List<String> guessedWords = new ArrayList<String>();
		// array list of last guesses
		List<String> guessedScore = new ArrayList<String>();
		// for input
		Scanner help = new Scanner(System.in);
		// Amount of guesses you have 
		int numGuess = 6;
		// outputs the start screen
		Wordle.outputStartScreen();
		
		while (!(guess.equals(word))) {
			// if it is the first guess skip 
			if(numGuess != 6 && guesserActive){
			  // outputs the guesses that are still possible
  			worde.getBestGuess(badLetters,guessedWords,guessedScore);
			}
			 // prompts for a guess
			System.out.println("\n\nMake a Guess! (" + numGuess + " remaining):");
			// gets guess
			guess = worde.getGuess();
			guessedWords.add(guess);
			// if they get it right it ends
			// if they guessed the word
			if (guess.equals(word)) {
			  break;
			}
			if (guess.equals("end")) {
			  System.out.println("\nEnding the program.");
			  return;
			}
			// outputs the guess and the solution

			// outputs the guess and gets the letters that were changed
			lastGuess = Wordle.outputGuess(word, guess);
			
			guessedScore.add(lastGuess);
			
			// gets all of the letters not in the word
			badLetters.addAll(worde.badLetters(lastGuess,guess));

			
			// Decrement numGuess
			numGuess--;
			
			// if for guess limit
			if (numGuess == 0) {

				// Output stuff
				System.out.println("\n\nYou have reached the maximum amount of guesses!\nThe word was " + word + "!");
				// Terminate if they run out of guesses
				break;
			}
		}
    
		if (guess.equals(word)) {
			// ouputs correct if they got the word
		  System.out.println("\n\nCorrect!\n\n");
		}
		// prompts for a play again
		System.out.println("\n\nDo you want to play again?(y/n)");
		// gets input
		String playAgain = help.nextLine();
		// if they want to play again reset everything
		if (playAgain.equals("y")) {
			
		  int getChoice = worde.optionScreen();
			  
  		if (getChoice == 2) {
  		  guesserActive = true;	
  		}
      else if (getChoice == 3) {
  		  Wordle.addWord();
  		  return;
  		}
      else if (getChoice == 1) {
  		  guesserActive = false;
  		}
      else {
  		  return;	
  		}
  		// recursive stuff 
  		gameLoop(worde.getWord(),guesserActive);
  		
		}
	} // end of gameLoop

  // MAIN
	public static void main(String[] args) {
    
		// Gets word from file
    Wordle wordus = new Wordle();
		String word = new String();
		word = wordus.getWord();
    
		// guesserActive checks if they want help or not
		boolean guesserActive = false;
    
		// gets input and displays options
		int getChoice = wordus.optionScreen();
		
		if (getChoice == 2) { // chose wordle with helper
		  guesserActive = true;	
		}
    else if (getChoice == 3) { // chose to add words
		  Wordle.addWord();
		return;
		}
    else if (getChoice == 1) { // just chose wordle
		  guesserActive = false;
		}
    else { // chose toQuit
		  return;	
		}
		
		// main game loop
		gameLoop(word,guesserActive);
		
		return;
	}
}