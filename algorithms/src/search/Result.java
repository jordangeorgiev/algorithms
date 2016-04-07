/**
	 * Result - an enum representing the result of the current step
	 * of the search algorithm.
	 * 
	 * 
	 */
	public enum Result
	{
		LEFT,
		RIGHT,
		EQUAL,
		UNDEF,
		NOTFOUND;

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
					return "Item is not an element of array";
				case UNDEF:
				default:
					return "Undefined";
			}
		}
	}

