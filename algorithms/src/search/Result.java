package search;

/**
 * Result - an enum representing the result of the current step of the search
 * algorithm.
 * 
 * @author ajkerzner@smcm.edu
 * @author sfrosenblatt@smcm.edu
 * 
 */
public enum Result
{
	LEFT, RIGHT, FOUND, NOT_FOUND, UNDEF;

	@Override
	public String toString()
	{
		switch (this)
		{
			case FOUND:
				return "Found";
			case LEFT:
				return "<--";
			case RIGHT:
				return "-->";
			case NOT_FOUND:
				return "Not found";
			case UNDEF:
			default:
				return "Undefined";
		}
	}
}
