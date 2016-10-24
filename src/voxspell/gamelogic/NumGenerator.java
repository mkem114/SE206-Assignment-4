package voxspell.gamelogic;

import java.io.Serializable;
import java.util.*;

/**
 * <h1>NumGenerator</h1> Generates numbers (random)
 * <p>
 * Nothing more to add ATM but I have plans for being able to select different
 * properties and have one generate method to do any possible combination of
 * numbers to generate
 * 
 * @version 1.0
 * @author mkem114
 * @since 2016-09-18
 */
public class NumGenerator implements Serializable {

	/**
	 * Serialisation ID re:SpellingGame
	 */
	private static final long serialVersionUID = 6184422241947500744L;

	/**
	 * Generates a list (of a given length) of random numbers between low and
	 * high that are unique
	 * 
	 * @param low
	 *            Lowest random number
	 * @param high
	 *            Highest random number
	 * @param amount
	 *            Amount of random numbers
	 * @return List of unique numbers
	 */
	public List<Integer> UniqueRandomIntegers(int low, int high, int amount) {
		Set<Integer> nums = new HashSet<Integer>();

		if ((high - low) > amount) {
			// TODO Throw an exception that a unique set cannot be given
		}

		for (int i = 0; i < amount && i < (high - low); i++) {
			Random rand = new Random();
			int randNum = rand.nextInt(high - low) + low;
			if (nums.contains(randNum)) {
				i--;
			} else {
				nums.add(randNum);
			}
		}

		ArrayList<Integer> numList = new ArrayList<Integer>();
		numList.addAll(nums);
		return numList;
	}

	public int randomInt(int low, int high) {
		if ((high < low)) {
			// TODO Throw an exception that a unique set cannot be given
		}
		Random rand = new Random();
		return rand.nextInt(high - low) + low;
	}
}