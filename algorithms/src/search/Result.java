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
	LEFT, RIGHT, EQUAL, UNDEF, NOTFOUND;

	@Override
	public String toString()
	{
		switch (this)
		{
			case EQUAL:
				return "Found it";
			case LEFT:
				return "Look left";
			case RIGHT:
				return "Look right";
			case NOTFOUND:
				return "Not found";
			case UNDEF:
			default:
				return "Undefined";
		}
	}
}
