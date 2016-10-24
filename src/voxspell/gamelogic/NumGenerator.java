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
 * @author mkem114
 * @version 1.0
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
     * @param low    Lowest random number
     * @param high   Highest random number
     * @param amount Amount of random numbers
     * @return List of unique numbers
     */
    public List<Integer> UniqueRandomIntegers(int low, int high, int amount) {
        // Creates unique number set
        Set<Integer> nums = new HashSet();

        // Invalid user input
        if ((high - low) > amount) {
            // TODO Throw an exception that a unique set cannot be given
        }

        // Creates the amount of random numbers asked for and keeps them unique
        for (int i = 0; i < amount && i < (high - low); i++) {
            Random rand = new Random();
            int randNum = rand.nextInt(high - low) + low;
            if (nums.contains(randNum)) {
                i--;
            } else {
                nums.add(randNum);
            }
        }

        // Returns them in an ArrayList
        ArrayList<Integer> numList = new ArrayList();
        numList.addAll(nums);
        return numList;
    }

    /**
     * Generates a random int between the low and high
     *
     * @param low  The lowest number inclusive
     * @param high The highest number exclusive
     * @return Random number between low and high
     */
    public int randomInt(int low, int high) {
        // Invalid input
        if ((high < low)) {
            // TODO Throw an exception that a unique set cannot be given
        }

        // Generate number
        Random rand = new Random();
        // Stops logic errors
        if (high == low) {
            return low;
        }
        return rand.nextInt(high - low) + low;
    }
}