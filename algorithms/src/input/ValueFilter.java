/**
 * 
 */
package input;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * 
 * 
 * @author ajkerzner@smcm.edu
 * 
 * 
 *
 *
 */
public class ValueFilter extends DocumentFilter
{
	/**
	 * The maximum length
	 */
	private static final int MAXIMUM_LENGTH = 15;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.
	 * DocumentFilter.FilterBypass, int, java.lang.String,
	 * javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException
	{
		string = string.replaceAll("[^0-9\\-]", "");
		int length_to_cut_off = MAXIMUM_LENGTH - (fb.getDocument().getLength() + string.length());
		if (length_to_cut_off < 0)
		{
			// Inserted string will extend the document string past the maximum.
			// Do not insert all of it.

			// Beep
			Toolkit.getDefaultToolkit().beep();

			string = string.substring(0, string.length() - Math.abs(length_to_cut_off));

			length_to_cut_off = MAXIMUM_LENGTH - (fb.getDocument().getLength() + string.length());

			if (length_to_cut_off >= 0)
			{
				// Insert some of it
				fb.insertString(offset, string, attr);
			}

			return;
		}
		else
		{
			// Insert all of it.
			fb.insertString(offset, string, attr);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.text.DocumentFilter#remove(javax.swing.text.DocumentFilter.
	 * FilterBypass, int, int)
	 */
	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
	{
		// Remove string
		fb.remove(offset, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.
	 * FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
			throws BadLocationException
	{

		string = string.replaceAll("[^0-9\\-]", "");
		// fb.getDocument().getLength() - (offset - length)) + string.length() >
		// MAXIMUM_LENGTH)
		int length_to_cut_off = MAXIMUM_LENGTH - (fb.getDocument().getLength() - length + string.length());
		if (length_to_cut_off < 0)
		{
			// Modification will put the total length past the maximum.
			// Replace some of it.

			// Beep
			Toolkit.getDefaultToolkit().beep();

			string = string.substring(0, string.length() - Math.abs(length_to_cut_off));
			// length = length + length_to_cut_off;
			length_to_cut_off = MAXIMUM_LENGTH - (fb.getDocument().getLength() - length + string.length());

			if (length_to_cut_off >= 0)
			{
				fb.replace(offset, length, string, attrs);
			}
			return;
		}
		else
		{
			// Replace all of it
			fb.replace(offset, length, string, attrs);
		}
	}

}
