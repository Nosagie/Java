//Print in main, not Hangman.java


import java.util.*;

public class Hangman {

	private int numwords = 10;							//number of words in 
	private String[] words = new String[numwords];
	private int iGuesses = 0;
	private static ArrayList<String> iNguesses = new ArrayList<String>(10);
	
	/*Method to display the Hangman given the number of body parts to show
	 * Player gets at most 10 turns. */
	public void showMan(int numParts)
	{
		if (numParts == 250){
			System.out.println("Correct!" + '\n' + "You Win!");
		}
		else
		{
			if (numParts == 0){	
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
			}
			if (numParts == 1){	
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
			}
			if (numParts == 2){	
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|        |");
				System.out.println("|        |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
			}
	
			if (numParts == 3){	
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|      __|");
				System.out.println("|        |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
			}
			if (numParts == 4){	
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|");
				System.out.println("|     	 |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
				}
			if (numParts == 5){
				
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|__");
				System.out.println("|     	 |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
				}
			if (numParts == 6){
				
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|__o");
				System.out.println("|     	 |");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
				}
			if (numParts == 7){
				
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|__o");
				System.out.println("|     	 |");
				System.out.println("|       /");
				System.out.println("|      /  ");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
				}
				
			if (numParts == 8){
				
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|__o");
				System.out.println("|     	 |");
				System.out.println("|       / \\");
				System.out.println("|      /   \\");
				System.out.println("|");
				System.out.println("|");
				System.out.println("____");
			}
				
			if (numParts == 9){
					
				System.out.println("________");
				System.out.println("|       |");
				System.out.println("|      ____");
				System.out.println("|     / .. \\");
				System.out.println("|    <   .  >");
				System.out.println("|     \\__^_/");
				System.out.println("|        |");
				System.out.println("|     o__|__o");
				System.out.println("|     	 |");
				System.out.println("|       / \\");
				System.out.println("|      /   \\");
				System.out.println("|    O/    ");
				System.out.println("|");
				System.out.println("____");
			
			}
			if (numParts == 10){
				
			System.out.println("________");
			System.out.println("|       |");
			System.out.println("|      ____");
			System.out.println("|     / .. \\");
			System.out.println("|    <   .  >");
			System.out.println("|     \\__^_/");
			System.out.println("|        |");
			System.out.println("|     o__|__o");
			System.out.println("|     	 |");
			System.out.println("|       / \\");
			System.out.println("|      /   \\");
			System.out.println("|    O/     \\O");
			System.out.println("|");
			System.out.println("____");
			}
		}
		
	}
	
	/**
	  Sets the list of candidate words for the player to guess
	**/
	public void setWords()
	{
		
		words[0] = "notions";
		words[1] = "measure";
		words[2] = "product";
		words[3] = "foliage";
		words[4] = "garbage";
		words[5] = "minutes";
		words[6] = "chowder";
		words[7] = "recital";
		words[8] = "concoct";
		words[9] = "brownie";		
	}
	
	/*
	 * Returns the number of words to choose from
	 */
	public int getNumWords()
	{
		return numwords;
	}

	/*
	 * Returns the array of words
	 */
	public String[] getWords()
	{
		setWords();
		return words;
	}
	
	//Returns the game word
	public char[] getGameWord(){
		setWords();
		Random rand = new Random();
		return words[rand.nextInt(10)].toCharArray();
	}
	
	//Returns the number of incorrect guesses
	public int getIncorrectGuesses(){
		return iGuesses;
	}
	
	//Returns the current word
	public char[] getCurrentWord(char guess,char[] cword, char[] word){
		for (int i = 0;i < word.length;i++){
			
			if (word[i] == guess)
				cword[i] = guess;
		}
		return cword;
	}
	
	//Checks if letter user guessed is in word
	public int guessL(char guess,char[] cword, char[] word){
		
			String wordz = new String(word); //Converts word array into string wordz
			
			if (wordz.contains(Character.toString(guess))){
				//Replace letter in cword
				getCurrentWord(guess,cword,word);
				return 1;
			}
			else{
				iGuesses += 1; 					
				iNguesses.add(Character.toString(guess));
				return 0;
			}
	}
	
	//Checks if phrase guessed by user is equal to random word
	public int guessP(String pGuess,char[] word,char[] cword){
		String cWord = new String(word);
		
		if (pGuess.equals(cWord.toUpperCase())){
			iGuesses = 250;
			return 0;
		}
		else{
			iNguesses.add(pGuess); //adds incorrect phrase to incorrect guesses
			iGuesses += 1;
			return 1;
		}
			
	}
	
	//Resets variables if user chooses to playagain
	public void reset(char[] cword){
		iGuesses = 0;
		iNguesses = new ArrayList<String>(10);
		//Resets guessed word to "_______"
		for (int i = 0; i < 7;i++)
			cword[i] = '_';
	}
	
	//Returns string of incorrect guesses
	public String sIguesses(){
		String iguesses = "";
		for (String p : iNguesses){
			iguesses = iguesses + p.toUpperCase() + " ";
		}
		return iguesses;
	}
}