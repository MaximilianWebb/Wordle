import java.util.*;
import java.io.*;

public class Wordle {
	// some variables
	private HashMap<Integer,String> wordsFromFile = new HashMap<Integer,String>();
	private int WORD_AMT = 0;
	private HashMap<Integer,String> remainingWords = new HashMap<Integer,String>();

  
  // Constructer
	public Wordle() {
	  // loads everthing from the txt
	  getWordFromFile();
	}

  
	public int wordsEliminated(String str){
    
	  int score = 0;
    // str is the possible word that we are checking how many words it will eliminate 
		String word = str;
		char[] words = str.toCharArray();
		String currentword = new String("");
		Map<Character, Integer> map = new HashMap<>();
    
    for(char c : words){
				
      if (map.containsKey(c)) {
			  return 1;  
			}	
			else {
        map.put(c, 1);
      }
			
    }
		
  	for (int i = 0; i < remainingWords.size(); ++i) {
			currentword = remainingWords.get(i);
			words = currentword.toCharArray();
			
			for (int j = 0; j < 5; ++j) {
        
		    if (currentword.indexOf(word.charAt(j)) != -1) {
  				score++;
					currentword = "end";
  				break;
				}
				
				if (currentword.equals("end")) {
				  break;
        }
      }
    }
		return score;
    
	}
  

	public void getWordFromFile() {
    
		BufferedReader reader;
		// trys if error will catch
		try {
      
			// gets the reader loaded up
			reader = new BufferedReader(new FileReader("WordList.txt"));
			// reads the first line
			String line = reader.readLine();
      
		  // while there is a word
			while (line != null) {
				// adds the line
				wordsFromFile.put(WORD_AMT,line);
				// gets new line
				line = reader.readLine();
				WORD_AMT++;
			}
      
			// closes the txt
			reader.close();
      
		} catch (Exception e) { // only if an error occurs
			// prints error
			e.printStackTrace();
		}
    
	}
  
	
	public int optionScreen() {
    
		// Scanner to get input
		Scanner s = new Scanner(System.in);
    
		// Displays options
		System.out.println("1) Play wordle \n2) Play wordle with a helper \n3) Add more words \n4) Exit");
    
		// receive input
		int getChoice = s.nextInt();
		// return input
		return getChoice;
	}

	
	public boolean validGuess(String str) {
    
	  // the input being validated
  	String guess = str;
  	// if there is a valid guess = true 
  	boolean validGuess = false;
  
  	if (wordsFromFile.containsValue(guess)) {
  	  validGuess = true;
  	}
  	
  	// returns false
  	return validGuess;
    
	}

  
	public String getGuess() {
    
		// gets input
		Scanner kb = new Scanner(System.in);
		// the string where the input goes
		String guess = new String();
		// while there is no valid guess
		boolean validGuess = false;

    // VALIDATION LOOP
		while (!validGuess) { 
			// gets input
			guess = kb.nextLine();
			// makes it all lowercase
			guess = guess.toLowerCase();
			
			// if guess equals end end the program
			if (guess.equals("end")) {
			  return guess;
			}
			// if the guess is in the wordlis
			// if the guess if 5 letters
			if (guess.length() != 5) {
				System.out.println("\nYour word must be 5 letters\n");
				continue;
			}
			// if the guess is numeric
			if (isNumeric(guess)) {
				System.out.println("\nYour word must not contain numbers\n");
				continue;
			}
			// if the guess has non alpha characters
			if (isAlpha(guess)) {
				System.out.println("\nYour word must not contain special characters\n");
				continue;
			}
			if (!validGuess(guess)) {
			  System.out.println("\nYour word must be in the wordle word list\n");
			  continue;	
			}
			
			break;
		}
    
		// returns the guess if the guess is good
		return guess;
    
	}

  
	public void getBestGuess(List<Character> badLetters,List<String> guessedWords,List<String> guessedScore) {
    
	  String currentScore; // if = b then its bad if a its good may implement a points system later to give a better guess
  	int counter = 0; // counts how many words work 
  	int bestScore = 0;
  	String bestWord = new String();
    
  	// if there is a word or there have been less than 20 words outputted 
  	for (int i = 0; i < WORD_AMT; ++i) {
  	  // gets b for bad, a for good
    	currentScore = getScore(wordsFromFile.get(i),badLetters,guessedWords,guessedScore);
    		
    	// if it is a word
    	if (currentScore != "b") {
    		
    	  remainingWords.put(counter,wordsFromFile.get(i));
      	int score = wordsEliminated(wordsFromFile.get(i));	
      		
      	if (score > bestScore) {
      	  bestScore = score;
        	bestWord = wordsFromFile.get(i);	
      	}
      	
      	counter++; // increments the counter
    	}		
  	}
    
  	// prints how many words there are that still work
  	System.out.println("\n\nThe best word to guess is " + bestWord);
  	System.out.println("\n\nThere are " + counter + " words left");	
	}
  
	
	public String getScore(String word,List<Character> badLetters,List<String> guessedWords,List<String> guessedScore) {

	  // RETURN B IF IT IS NOT THE WORD
  	// RETURN A IF IT COULD BE THE WORD 
  		
  	String theWord = word; // i wonder what the word is. maybe its the word
  	char[] wordFromFile = word.toCharArray(); // the word from the file fitting right?
  	char[] lastGuessScore = new char[5]; // the word that has the Xs and the Os
  	char[] lastGuess = new char[5]; // the guess 
  	List<Character> badLetter = badLetters;	
  		
  	// if the word has already been guessed
  	for (int i = 0; i < guessedWords.size(); ++i) {
  	  if ( theWord.equals(guessedWords.get(i)) ) {
    	  return "b";	
    	}
  	}
  		
  	for (int i = 0; i < 5; ++i) {	
      
  	  for (int k = 0; k < badLetter.size(); ++k) {
        
    	  // if the word has a letter that isnt in the correct word
      	if (wordFromFile[i] == badLetter.get(k)) {
      	  return "b";  
    	  }	
        
  	  }
  
  	  for (int t = 0; t < guessedWords.size(); ++t) {
    		
    	  lastGuessScore = guessedScore.get(t).toCharArray();	
    	  lastGuess = guessedWords.get(t).toCharArray();
    		
    	  // right letter right spot but it isnt in the word 
      	if (lastGuessScore[i] == 'X' && (lastGuess[i] != wordFromFile[i])) {
      	  return "b";	
      	}
  		
  	    // same with right letter wrong spot but in the same spot
      	if (lastGuessScore[i] == 'O' && (lastGuess[i] == wordFromFile[i])) {	
      	  return "b";
      	}
  		
  	    // if it doesnt have the  right letter  -  wrong spot  in any of its characters 
  	    if (lastGuessScore[i] == 'O' && ((lastGuess[i] != wordFromFile[0]) &&
      		lastGuessScore[i] == 'O' && (lastGuess[i] != wordFromFile[1]) &&
      		lastGuessScore[i] == 'O' && (lastGuess[i] != wordFromFile[2]) &&		
      		lastGuessScore[i] == 'O' && (lastGuess[i] != wordFromFile[3]) &&		
      		lastGuessScore[i] == 'O' && (lastGuess[i] != wordFromFile[4]))) 
        {
      	  return "b";	
      	}
  	  }	
  	}
    
  	// returns a cause its good 
  	return "a";
  	
	}	

  
	public static void addWord() {
		FileWriter writer;
		String word = new String();
		Scanner kb = new Scanner(System.in);
    
		try {
			
			writer = new FileWriter("WordList.txt", true); // the second paramer "append"
			// gets the input
			System.out.println("Type end to stop adding words");
			
			while ( !(word.equals("end")) ) {
			  word = kb.nextLine();
        
  			// closes the input	
  			if (word.equals("end")) {
  			  break;
  			}	
        
  			// adds the text to a new line at the end of the file
  			writer.write("\n" + word);
			}
      
			// closes input and the writer
			kb.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
  

	public List<Character> badLetters(String word,String guess) {
		
	  List<Character> badLetter = new ArrayList<>();
  	char[] letters = word.toCharArray();
  	char[] guessed = guess.toCharArray();	
    
  	for (int i = 0; i < letters.length; ++i) {
  
  	  if (letters[i] != 'O' || letters[i] != 'X') {
    	  // could be duplicate letters
      	// adds all letters that arent in the word to the arraylist
      	badLetter.add(letters[i]);	
    	}
      
  	}
  	
  	for (int i = 0; i < letters.length; ++i) {
  	  // need to remove any words that may show up b/c there were dupes in the guess 
    	if ((letters[i] == 'X' || letters[i] == 'O')) {	
    	
    	  if (badLetter.contains(guessed[i])) {	
      	  badLetter.remove(badLetter.indexOf(guessed[i]));	
      	}
    	}	
  	}
    
  	return badLetter;	
	}
  

	public static boolean isNumeric(String guess) {
    
		String sample = guess;
		char[] chars = sample.toCharArray();
    
		for (char c : chars) { // checks if guess had numbers
			if (Character.isDigit(c)) {
				return true;
			}
		}
    
		return false;
	}

  
	public static boolean isAlpha(String guess) {
    
		return !(guess.matches("^[a-zA-Z]+$")); // uses regex
    
	}

	public String getWord() {
    
	  return wordsFromFile.get(randomNumber(WORD_AMT));
    
	}

	public static int randomNumber(int i) {
    
		Random rand = new Random();
		return rand.nextInt(i); // returns random number 0 to i -1
    
	}
	
	public static HashMap<Character,Integer> characterCount(String inputString) {
    
    // Creating a HashMap containing char
    // as a key and occurrences as  a value
    HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();

    // Converting given string to char array

    char[] strArray = inputString.toCharArray();

    // checking each char of strArray
    for (char c : strArray) {
    
      if (charCountMap.containsKey(c)) {

        // If char is present in charCountMap,
        // incrementing it's count by 1
        charCountMap.put(c, charCountMap.get(c) + 1);
      }
      else {

        // If char is not present in charCountMap,
        // putting this char to charCountMap with 1 as it's value
        charCountMap.put(c, 1);
      }
      
    }
    
    return charCountMap;
    
  }
  
	
	public static String outputGuess(String word, String guess) {
		
		final int LETTERS = 5;
		char[] wordle = word.toCharArray();
		char[] guessle = guess.toCharArray();
		char[] guessed = wordle.clone();
		char[] helper = guessle.clone();
		HashMap<Character,Integer> letterCount = characterCount(word);
		
		// checking
		for (int i = 0; i < LETTERS; i++) {
      
			// if they get it right and in the right spot
			if (guessle[i] == guessed[i]) {
        
				guessle[i] = 'X';
				letterCount.put(helper[i],letterCount.get(helper[i]) - 1);
				
			}
		}

		for (int i = 0; i < LETTERS; i++) {
			// right letter wrong spot
			if (guessle[i] == guessed[0] ||
				guessle[i] == guessed[1] ||
				guessle[i] == guessed[2] ||
				guessle[i] == guessed[3] ||
				guessle[i] == guessed[4]) 
      {
				
				if (letterCount.get(helper[i]) != 0) {
					
				  guessle[i] = 'O';
  				letterCount.put(helper[i],letterCount.get(helper[i]) - 1);
            
				}
				else {
				  letterCount.remove(helper[i]);
				}
				
			}
		}
		
		// output
		
		for (int i = 0; i < LETTERS; i++) {
				
			if (guessle[i] == 'X') {
				
				System.out.print("{" + helper[i] + "} ");
				
			}
      else if (guessle[i] == 'O') {
	
				System.out.print("[" + helper[i] + "]");

			}
      else {
			  System.out.print(helper[i] + " ");	
			}
		}
	
	  String str = String.valueOf(guessle);
	  return str;
	}

  
	public static void outputStartScreen() {
    
		System.out.println("Guess the 5 letter word");
		System.out.println("\nIf your letter is wrong, it will have nothing around it");
		System.out.println("\nIf it has [] around it, your guess is correct, \nbut in the wrong spot");
		System.out.println("\nIf it has {} around it, your guess is correct, \nand in the right spot");
		System.out.println("\nType end to end the program");
    
	}
  
} // end of class