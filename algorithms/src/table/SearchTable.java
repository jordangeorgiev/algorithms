/**
 * 
 */
package table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * @author AlexKerzner
 *
 */
public abstract class SearchTable extends AbstractTableModel
{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 8042477085776407449L;

	// See
	// https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data
	private String[]						column_names;
	private ArrayList<Object[]>	data;

	/**
	 * 
	 */
	public SearchTable(String[] other_columns)
	{
		column_names = new String[3 + other_columns.length];
		column_names[0] = "Step";
		column_names[1] = "Index";
		column_names[2] = "Result";
		for (int i = 0; i < other_columns.length; i++)
		{
			column_names[3 + i] = other_columns[i];
		}
		data = new ArrayList<Object[]>();

	}

	/**
	 * Add column
	 * 
	 * @param array
	 *          contains a single row's data
	 * @return result
	 */
	public boolean addRow(Object[] row)
	{
		return data.add(row);
	}

	/**
	 * Clear table
	 * 
	 */

	public void clearTable()
	{
		data.clear();
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
		return data.size();
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
		return data.get(row)[col];
	}

}
