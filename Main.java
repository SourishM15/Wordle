import java.util.Scanner;

public class Main {

	public static int ALLOWED_GUESSES = 6;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String unguessedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		//Setup a 12 x 5 array
		Character[][] displayArray = new Character[12][5];
		initializeDisplay(displayArray);
		//Accept input from another user
		Scanner scan = new Scanner(System.in);
		System.out.println("***Welcome to Wordle!***");
		System.out.println("Have someone enter in a five letter word for you to guess, all caps:");
		String chosenWord = scan.nextLine();
		//scroll down 25 lines so that the player cannot see the chosen word
		for (int i=1; i <= 25; i++) {
			System.out.println();
		}
		//initialize number of guesses, odd and even rows
		int numberOfGuesses = 1;
		int evenRow = 0;
		int oddRow = 1;
		while (numberOfGuesses <= ALLOWED_GUESSES) {
			System.out.print("Please enter in your guess: ");
			String guessedWord = scan.nextLine();
			//populate the guess
			for (int j = 0; j < guessedWord.length(); j++) {
				Character guessChar = guessedWord.charAt(j);
				displayArray[evenRow][j] = guessChar;
				//replace the guessed character
				unguessedCharacters = unguessedCharacters.replace(guessChar, '#');
			}
			Character[] hints = populateHints(guessedWord, chosenWord);
			//populate hints
			for (int k = 0; k < hints.length; k++) {
				displayArray[oddRow][k] = hints[k];
			}
			//Print guesses and clues
			printDisplay(displayArray);
			//Check for winner
			if (isEniterWordGuessed(hints)) {
				System.out.println("YOU GOT IT!");
				System.out.println("You used " + numberOfGuesses + " guesses");
				printUnguessedLetters(unguessedCharacters);
				break;
			}
			printUnguessedLetters(unguessedCharacters);
			//increment the counters here
			numberOfGuesses++;
			evenRow += 2;
			oddRow += 2;
		}
		//Exceeded number of guesses
		if (numberOfGuesses >= ALLOWED_GUESSES) {
			System.out.println("Sorry, you lost!");			
		}
		scan.close();
	}

	public static Character[] populateHints(String guess, String solved) {
		Character[] hints = new Character[5];
		for (int i = 0; i < guess.length(); i++) {
			Character guessedChar = guess.charAt(i);
			Character solvedChar = solved.charAt(i);
			if (guessedChar == solvedChar) {
				hints[i] = Character.valueOf('*');
			} else if (solved.indexOf(guessedChar) != -1) {
				hints[i] = Character.valueOf('@');
			} else {
				hints[i] = Character.valueOf('x');
			}
		}
		return hints;
	}
	
	public static void printDisplay(Character[][] display) {
		for (int i = 0; i < display.length; i++) { //this equals to the row in our matrix.
			System.out.print("| ");
			for (int j = 0; j < display[0].length; j++) { //this equals to the column in each row.
				System.out.print(display[i][j] + " ");
			}
			System.out.print(" |");
			System.out.println();
		}
	}
	
	public static void initializeDisplay(Character[][] display) {
		for (int i = 0; i < display.length; i++) { //this equals to the row in our matrix.
			for (int j = 0; j < display[0].length; j++) { //this equals to the column in each row.
				display[i][j] = Character.valueOf(' ');
			}
		}
	}
	
	public static boolean isEniterWordGuessed(Character[] hints) {
		boolean guessed = true;
		for (int i = 0; i < hints.length; i++) {
			if (hints[i] != Character.valueOf('*')) {
				guessed = false;
				break;
			}
		}
		return guessed;
	}
	
	public static void printUnguessedLetters(String unguessedCharacters) {
		System.out.print("Letters Left: ");
		char[] characters = unguessedCharacters.toCharArray();
		for (int i = 0; i < characters.length; i++) {
			System.out.print(characters[i] + " ");
		}
		System.out.println();
	}
}
