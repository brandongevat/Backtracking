
/* COP 3503C Assignment 2
 * This program is written by: Brandon Gevat */
import java.io.*;
import java.util.*;

public class Main {

	// Outputs grid to console.
	public static void displayGrid(char[][] grid, int nRows, int nCols) {
		for (int i = 0; i < nRows; i++) {
			System.out.print("[");
			for (int j = 0; j < nCols; j++) {
				if (grid[i][j] == '\u0000') {
					System.out.print(" ");
					if (j < nCols - 1)
						System.out.print(", ");
				} else {
					System.out.print(grid[i][j]);
					if (j < nCols - 1)
						System.out.print(", ");
				}
			}
			System.out.print("]\n");
		}
		System.out.println();
	}

	// Reads in given grid.
	public static char[][] readGrid(Scanner sc, int nRows, int nCols) {
		char[][] grid = new char[nRows][nCols];
		for (int m = 0; m < nRows; m++) {
			for (int n = 0; n < nCols; n++) {
				grid[m][n] = sc.next().charAt(0);
			}
		}
		sc.nextLine();
		return grid;
	}

	// Reads in given words.
	public static String[] readWords(Scanner sc, int nWords) {
		String[] words = new String[nWords];
		for (int i = 0; i < nWords; i++) {
			words[i] = sc.nextLine();
			// DEBUG
			// System.out.println("\n" + words[i]);
		}
		return words;
	}

	// Checks if the current attempt is out of bounds, DNE, or already apart of the
	// solution set.
	public static boolean isSafe(char grid[][], char[][] sol, String word, int rows, int cols, int nRows, int nCols,
			int curr) {
		if (cols < 0 || cols >= nCols || rows < 0 || rows >= nRows || grid[rows][cols] != word.charAt(curr)
				|| grid[rows][cols] == sol[rows][cols]) {
			return false;
		}
		return true;
	}

	// Attempts to find current char in the grid and adds it to the solution set
	public static boolean findChar(char[][] grid, char[][] sol, String word, int row, int col, int nRows, int nCols,
			int curr) {
		boolean ret = false;
		// DEBUG
		// System.out.println(curr);

		// If we reach the end of the word we found our solution.
		if (curr == word.length())
			return true;

		if (isSafe(grid, sol, word, row, col, nRows, nCols, curr)) {

			// Adds current char to solution set, then checks.
			sol[row][col] = word.charAt(curr);

			// Attempts to go in every possible direction, right, down, left, up,
			// right-down, left-down, right-up, left-up.
			if (findChar(grid, sol, word, row, col + 1, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row + 1, col, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row - 1, col, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row, col - 1, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row + 1, col + 1, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row + 1, col - 1, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row - 1, col + 1, nRows, nCols, curr + 1))
				return true;
			if (findChar(grid, sol, word, row - 1, col - 1, nRows, nCols, curr + 1))
				return true;

			// If current char isn't apart of the solution set, remove it.
			if (!ret)
				sol[row][col] = '\u0000';
		}
		return false;
	}

	// Driver code
	public static void main(String[] args) throws FileNotFoundException {
		boolean found;
		int i = 0;
		char[][] grid, original, sol;
		String[] words;

		File file = new File("in.txt");
		Scanner sc = new Scanner(file);

		if (sc.hasNextLine()) {

			// "k" # of test cases
			int testCases = sc.nextInt();
			// DEBUG
			// System.out.println("Test Cases: " + testCases);

			// For each test case: M = # of rows, N = # of cols, S = # of words
			int nRows, nCols, nWords;

			// Reads in each test case and attempts to find the target words for each case
			while (testCases > 0) {
				System.out.println("Test#" + (i + 1));
				nRows = sc.nextInt();
				// DEBUG
				// System.out.println("nRows: " + nRows);

				nCols = sc.nextInt();
				// DEBUG
				// System.out.println("nCols: " + nCols);

				nWords = sc.nextInt();
				// DEBUG
				// System.out.println("nWords: " + nWords);

				original = readGrid(sc, nRows, nCols);

				words = readWords(sc, nWords);

				// Iterates through each word
				for (String word : words) {
					System.out.println("Looking for " + word);
					found = false;
					sol = new char[nRows][nCols];
					grid = original;

					// Iterates through 2D array for each word
					// O(m * n * nWords * word.length()) runtime
					for (int j = 0; j < nRows; j++) {
						for (int k = 0; k < nCols; k++) {
							found = findChar(grid, sol, word, j, k, nRows, nCols, 0);
							// If we find the first solution, output it and break out of the loop (column
							// loop).
							if (found) {
								displayGrid(sol, nRows, nCols);
								break;
							}
						}
						// If found, break out the loop (row loop).
						if (found)
							break;

					}
					// If not found, output the word is not found.
					if (!found)
						System.out.println(word + " not found!\n");

				}
				i++;
				testCases--;
			}
		}
	}
}
