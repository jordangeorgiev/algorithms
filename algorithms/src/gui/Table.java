/**
 * 
 */
package gui;

import javax.swing.table.AbstractTableModel;

/**
 * @author AlexKerzner
 *
 */
public class Table extends AbstractTableModel
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8042477085776407449L;

	// See
	// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data
	private String[]					column_names;
	private Object[][]				data;

	/**
	 * 
	 */
	public Table()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount()
	{
		return column_names.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount()
	{
		return data.length;
	}

	public String getColumnName(int col)
	{
		return column_names[col];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col)
	{
		return data[row][col];
	}

}
