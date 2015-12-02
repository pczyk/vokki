package de.unidue.s3.bcrypt;

/**
 * Utility class that wraps the jBCrypt library in order to provide an easy to
 * use interface. This class is thread safe and can be used without any
 * additional synchronization.
 * @author Björn Zurmaar
 */
public final class BCrypt
{
	/** An object for synchronizing access to the classes' options. */
	private static final Object LOCK = new Object();

	/** The pepper to be used. */
	private static String pepper = null;

	/**
	 * Sets the pepper to be used to {@code pepper}. The pepper will be
	 * concatenated to all passwords before hashing and will hence improve
	 * the security of weak passwords and complicate the generation of rainbow
	 * tables for a possible attacker.
	 * @param newPepper The new pepper to be used.
	 * @throws IllegalStateException If setting the pepper would overwrite a
	 *   previously set pepper. A pepper value can only be set once.
	 */
	public static final void setPepper(final String newPepper)
		throws IllegalStateException
	{
		synchronized (LOCK)
		{
			// We do not allow overwriting a previously set pepper
			final String oldPepper = BCrypt.pepper;
			if (oldPepper != null && !oldPepper.equals(newPepper))
				throw new IllegalStateException(
					"Pepper already set to different value.");

			// If the pepper is null we set it to an empty string instead.
			BCrypt.pepper = newPepper != null ? newPepper : "";
		}
	}

	/**
	 * Peppers the given plaintext password {@code password} by simply
	 * appending the pepper to it.
	 * @param password The plaintext password to be peppered.
	 * @return The peppered password.
	 */
	private static final String pepper(final String password)
	{
		synchronized (LOCK)
		{
			// If the pepper has not yet been set we need to freeze it.
			if (pepper == null)
			{
				setPepper("");
			}

			// Now we can safely use the pepper and concatenate it.
			return password.concat(pepper);
		}
	}

	/** The default number of rounds for the bcrypt algorithm. */
	private static final int DEFAULT_ROUNDS = 12;

	/** The number of rounds to be used when no round number is given. */
	private static int default_rounds = DEFAULT_ROUNDS;

	/**
	 * Checks the argument {@code rounds} for validity. If {@code rounds} is
	 * lower than 4 or greater than 31 this methods throws an {@link
	 * IllegalArgumentException}. Otherwise the methods silently returns.
	 * @param rounds The rounds parameter to be checked.
	 */
	private static final void checkRoundParameter(final int rounds)
	{
		if (4 > rounds || rounds > 31)
			throw new IllegalArgumentException(String.format(
				"Illegal round number:  %d. Valid range is [4..31].",rounds));
	}

	/**
	 * Sets the number of rounds to be used when calling {@link #hash(String)}.
	 * The argument {@code rounds} must be in the range 4..31, otherwise this
	 * method throws an {@link IllegalArgumentException}. Please note that
	 * that the number of iterations and hence the required time for the
	 * algorithm to complete quadratically rises with the number of rounds.
	 * @param rounds The number of rounds to be used when hashing a string by
	 *   calling {@link #hash(String)}.
	 * @see #hash(String)
	 */
	public static final void setDefaultRounds(final int rounds)
	{
		// It's safe to check the argument without synchronization.
		checkRoundParameter(rounds);

		// But the assignment itself requires synchronization.
		synchronized (LOCK)
		{
			default_rounds = rounds;
		}
	}

	/**
	 * Returns the number of rounds used when hashing a string with the
	 * {@link #hash(String)} method.
	 * @return The default number of bcrypt rounds to be used.
	 */
	public static final int getDefaultRounds()
	{
		synchronized (LOCK)
		{
			return default_rounds;
		}
	}

	/**
	 * Calculates a hash with a random salt for the given plaintext password
	 * with a default round number of {@link #DEFAULT_ROUNDS}.
	 * @param password The plaintext password to be hashed.
	 * @return The bcrypt hash for the given password.
	 */
	public static final String hash(final String password)
	{
		return hash(password,getDefaultRounds());
	}

	/**
	 * Calculates a hash with a random salt for the given plaintext password
	 * with {@code rounds} rounds.
	 * @param password The plaintext password to be hashed.
	 * @param rounds The number of rounds. Please note that the amount of work
	 *   increases exponentially, so each increment is twice as much work.
	 * @return The bcrypt hash for the given password.
	 * @throws IllegalArgumentException If {@code rounds} is not in the range
	 *   of 4 to 31.
	 */
	public static final String hash(final String password,final int rounds)
	{
		checkRoundParameter(rounds);

		return BCryptImpl.hashpw(pepper(password),BCryptImpl.gensalt(rounds));
	}

	/**
	 * Checks if the plaintext password {@code password} matches the given hash
	 * {@code hash}. If password and hash match this method returns {@code true}
	 * , {@code false} otherwise.
	 * @param password The password to be checked.
	 * @param hash The hash the password should match.
	 * @return {@code true} in case the password and the hash match,
	 *   {@code false} otherwise.
	 */
	public static final boolean check(final String password, final String hash)
	{
		return BCryptImpl.checkpw(pepper(password),hash);
	}

	/**
	 * Private default constructor to avoid instantiation of this class.
	 */
	private BCrypt() {}
}
