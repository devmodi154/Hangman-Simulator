
// WORKING
import java.util.Random;
import java.util.Scanner;

class ErrorException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	ErrorException(String arg) {
		System.out.println(arg);
	}
}

public class simulator3 implements Runnable {

	private static HangmanCanvas canvas;

	// FUNCTION TO CHECK LETTR IN THE WORD
	public static int word_check(final String word, final String correct_letters, String guessed_word,
			final int no_of_guesses) {
		int flag = 0;
		for (int i = 0; i < word.length(); i++) {
			if (correct_letters.indexOf(word.charAt(i)) >= 0) {
				guessed_word = guessed_word.substring(0, i) + word.charAt(i) + guessed_word.substring(i + 1);
				flag++;
			}
		}
		if (flag == word.length()) {
			System.out.println("You guessed the word: " + guessed_word);

		} else {
			System.out.println("The word now looks like: " + guessed_word);
			System.out.println("You have " + no_of_guesses + " guesses left.");
		}
		return flag;
	}

	public static void simulation() throws ErrorException {
		int no_of_correct_guesses = 0, x = 0, no_of_guesses = 8;
		String word, correct_letters = "", guessed_word = "";
		final char dash = '_';
		char guess = '@';
		final HangmanLexicon h = new HangmanLexicon();
		final Random r = new Random();
		final Scanner sc = new Scanner(System.in);

		// Random word
		x = r.nextInt(10);
		word = h.getWord(x);

		for (int i = 0; i < word.length(); i++) {
			guessed_word = guessed_word + dash;
		}

		System.out.println("Welcome to Hangman!");
		no_of_correct_guesses = word_check(word, correct_letters, guessed_word, no_of_guesses);

		while (no_of_guesses > 0 && no_of_correct_guesses != word.length()) {
			// Taking input
			guess = sc.next().charAt(0);

			// Checking for illegal attempts
			while (Character.isLetter(guess) == false) {
				System.out.println("Illegal input! Try again.");
				guess = sc.next().charAt(0);
			}
			System.out.println("Your guess:" + guess);
			guess = Character.toUpperCase(guess);

			if (word.indexOf(guess) >= 0) {
				System.out.println("That guess is correct.");
				correct_letters = correct_letters + guess;
			} else {
				System.out.println("There are no " + guess + "'s in the word");
				no_of_guesses--;
			}
			no_of_correct_guesses = word_check(word, correct_letters, guessed_word, no_of_guesses);
		}

		// RESULTS:
		if (no_of_correct_guesses == word.length()) {
			System.out.println("You win.");
		} else {
			System.out.println("You're completely hung.");
			System.out.println("The word was: " + word);
			System.out.println("You lose.");
		}

	}

	public void run() {
		// SIMULATION:
		try {
			simulation();
		} catch (final ErrorException e) {
			System.out.println("Error");
		}
	}

	public static void main(final String args[]) throws ErrorException {
		final simulator3 s = new simulator3();
		final Thread t = new Thread(s);
		t.start();
	}

}