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
	private static final int MAXIMUM_LENGTH = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.
	 * DocumentFilter.FilterBypass, int, java.lang.String,
	 * javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException
	{
		text = text.replaceAll("[^0-9]", "");
		if (fb.getDocument().getLength() + text.length() > MAXIMUM_LENGTH)
		{
			// Inserted string will extend the document string past the maximum.
			// Do not insert it.

			// Beep
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		else
		{
			// Insert it.
			fb.insertString(offset, text, attr);
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
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException
	{

		text = text.replaceAll("[^0-9]", "");
		if (fb.getDocument().getLength() + text.length() > MAXIMUM_LENGTH)
		{
			// Modification will put the total length past the maximum.
			// Do nothing.

			// Beep
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		else
		{
			// Replace it
			fb.replace(offset, length, text, attrs);
		}
	}

}
