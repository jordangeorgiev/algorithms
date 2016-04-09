/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;

import input.ListFilter;
import input.ValueFilter;
import search.FibonacciSearch;
import search.Result;
import search.Search;
import search.ExponentialSearch;
import table.ExponentialTable;
import table.FibonacciTable;
import table.SearchTable;

import javax.swing.Timer;

/**
 * @author ajkerzner@smcm.edu
 *
 */
public class Driver extends JFrame
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6847267042402021945L;

	private static final Font	button_font				= new Font(Font.MONOSPACED, Font.PLAIN, 21);
	private static final Font	input_font				= new Font(Font.MONOSPACED, Font.PLAIN, 21);

	private static final int	WIDTH							= 800;
	private static final int	HEIGHT						= 800;

	private static final int	MAX_VALUE					= Integer.MAX_VALUE - 147483647;
	private static final int	MIN_VALUE					= Integer.MIN_VALUE + 147483648;

	protected static enum Mode
	{
		PLAY, PAUSE, INCREMENT, FAST_FORWARD, FINISHED
	}

	private static final int			IMMEDIATE			= 0;
	private static final int			NORMAL				= 1000;

	private static final Pattern	DELIMITER			= Pattern.compile("[, ]+");

	// Main panel
	private JPanel								panel;
	private BorderLayout					layout;

	// Contains input-bar and toolbar
	private JPanel								north_split;
	private GridLayout						north_layout;

	// Input bar
	private JPanel								input_bar;
	private JTextField						input_value;
	private JTextField						input_list;
	private JButton								button_random;

	// Toolbar
	private JPanel								toolbar;
	private JButton								button_toggle;
	private JButton								button_increment;
	private JButton								button_fastforward;

	// private GridBagLayout center_layout;
	// private GridBagConstraints center_constraints;

	// Contains both split panels
	private JSplitPane						center_split;

	private JPanel[]							split_panels	= new JPanel[2];
	private SearchBar[]						search_bars		= new SearchBar[2];
	private BoxLayout[]						split_layouts	= new BoxLayout[2];

	private JScrollPane[]					table_panes		= new JScrollPane[2];
	private JTable[]							tables				= new JTable[2];

	private SearchTable[]					table_data		= new SearchTable[2];

	private Search[]							searches			= new Search[2];
	private int[]									list					= { 1, 2, 3, 4, 5 };
	private int										value					= 1;
	public Mode										mode;

	private Timer									timer;

	public Driver()
	{
		// Set title
		super("Fibonacci vs. Exponential Search");

		timer = new Timer(NORMAL, new AbstractAction()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 7381400032055686269L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (!iterate())
				{
					timer.stop();
					mode = Mode.FINISHED;
					button_toggle.setText("Play ");
					disableButtons();
				}
			}
		});

		timer.setInitialDelay(0);

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set look-and-feel
		setDefaultLookAndFeelDecorated(true);

		// Define panel
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Set layout
		layout = new BorderLayout(10, 10);

		panel.setLayout(layout);

		///////////////////////
		// North Split Panel //
		///////////////////////

		north_split = new JPanel();
		north_layout = new GridLayout(2, 1);
		north_split.setLayout(north_layout);

		///////////////
		// Input Bar //
		///////////////

		input_bar = new JPanel();
		input_bar.setLayout(new BorderLayout(0, 0));

		/////////////////
		// Input Value //
		/////////////////

		input_value = new JTextField();
		input_value.setFont(input_font);
		input_value.setText("012345678901234");
		input_value.repaint();
		input_value.setMinimumSize(input_value.getSize());
		input_value.setText(String.valueOf(value));

		((AbstractDocument) input_value.getDocument()).setDocumentFilter(new ValueFilter());
		input_value.setEnabled(true);
		input_value.setAction(new AbstractAction()
		{

			/**
			* 
			*/
			private static final long serialVersionUID = 7381400032055686269L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				update();

			}
		});

		input_bar.add(input_value, BorderLayout.WEST);

		////////////////
		// Input List //
		////////////////

		input_list = new JTextField();
		input_list.setFont(input_font);
		input_list.setText("-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5");
		((AbstractDocument) input_list.getDocument()).setDocumentFilter(new ListFilter());
		input_list.setEnabled(true);
		input_list.setAction(new AbstractAction()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 7381400032055686269L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				// Update list
				update();
			}
		});

		input_bar.add(input_list, BorderLayout.CENTER);

		///////////////////
		// Button Random //
		///////////////////

		button_random = new JButton();
		button_random.setName("Pause/Play");
		button_random.setAction(new AbstractAction()
		{

			/**
			 * Randomly typed serial version UID
			 */
			private static final long serialVersionUID = 13928131872557435L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (button_random.isEnabled())
				{
					Random r = new Random();
					int random_max = r.nextInt(1000);
					list = new int[random_max];
					for (int i = 0; i < random_max; i++)
					{
						list[i] = r.nextInt(MAX_VALUE);
					}
					value = list[random_max - 1];
					Arrays.sort(list);
					input_value.setText(String.valueOf(value));
					input_list.setText(Arrays.toString(list));
					createSearch(list, value);
				}
			}
		});
		button_random.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "Random");
		button_random.getActionMap().put("Random", button_random.getAction());
		button_random.setText("Random");
		button_random.setFocusable(false);
		button_random.setFont(button_font);
		button_random.setEnabled(true);

		input_bar.add(button_random, BorderLayout.EAST);

		north_split.add(input_bar, 0);

		/////////////
		// Toolbar //
		/////////////

		toolbar = new JPanel();

		///////////////////////
		// Button Pause/Play //
		///////////////////////

		button_toggle = new JButton();
		button_toggle.setName("Pause/Play");
		button_toggle.setAction(new AbstractAction()
		{

			/**
			 * Randomly typed serial version UID
			 */
			private static final long serialVersionUID = 13928131872557435L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (button_toggle.isEnabled())
				{
					if (button_toggle.getText().equals("Play "))
					{
						// Play -> Pause
						play();
					}
					else if (button_toggle.getText().equals("Pause"))
					{
						// Pause -> Play
						pause();
					}
				}
			}
		});
		button_toggle.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Pause/Play");
		button_toggle.getActionMap().put("Pause/Play", button_toggle.getAction());
		button_toggle.setText("Pause");
		button_toggle.setFocusable(false);
		button_toggle.setFont(button_font);

		toolbar.add(button_toggle);

		//////////////////////
		// Button Increment //
		//////////////////////

		button_increment = new JButton();
		button_increment.setName("Increment");
		button_increment.setAction(new AbstractAction()
		{

			/**
			 * Randomly typed serial version UID
			 */
			private static final long serialVersionUID = 24721035756348904L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (button_increment.isEnabled())
				{
					// Increment
					increment();
				}
			}
		});
		button_increment.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "Increment");
		button_increment.getActionMap().put("Increment", button_increment.getAction());
		button_increment.setText("Increment");
		button_increment.setFocusable(false);
		button_increment.setFont(button_font);

		toolbar.add(button_increment);

		/////////////////////////
		// Button Fast Forward //
		/////////////////////////

		button_fastforward = new JButton();
		button_fastforward.setName("Fast Forward");
		button_fastforward.setAction(new AbstractAction()
		{

			/**
			 * Randomly typed serial version UID
			 */
			private static final long serialVersionUID = 12497132571330571L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (button_fastforward.isEnabled())
				{
					// Fast Forward
					fastforward();
				}
			}
		});
		button_fastforward.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Fast Forward");
		button_fastforward.getActionMap().put("Fast Forward", button_fastforward.getAction());
		button_fastforward.setText("Fast Forward");
		button_fastforward.setFocusable(false);
		button_fastforward.setFont(button_font);

		toolbar.add(button_fastforward);

		north_split.add(toolbar, 1);

		panel.add(north_split, BorderLayout.NORTH);

		//////////////////
		// Center Split //
		//////////////////
		center_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		////////////////
		// Table Data //
		////////////////
		table_data[0] = new FibonacciTable();
		table_data[1] = new ExponentialTable();

		for (int i = 0; i < 2; i++)
		{
			////////////////
			// Split Pane //
			////////////////
			split_panels[i] = new JPanel();
			split_layouts[i] = new BoxLayout(split_panels[i], BoxLayout.Y_AXIS);
			split_panels[i].setLayout(split_layouts[i]);

			///////////////
			// SearchBar //
			///////////////
			search_bars[i] = new SearchBar(list.length);
			split_panels[i].add(search_bars[i]);

			////////////
			// Tables //
			////////////
			tables[i] = new JTable(table_data[i]);
			tables[i].setFillsViewportHeight(true);
			table_panes[i] = new JScrollPane(tables[i]);

			split_panels[i].add(table_panes[i]);

		}
		center_split.setLeftComponent(split_panels[0]);
		center_split.setRightComponent(split_panels[1]);
		center_split.setDividerLocation(WIDTH / 2);
		panel.add(center_split, BorderLayout.CENTER);

		add(panel);

		disableButtons();

		setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void update()
	{
		updateValue();
		updateList();
		createSearch(list, value);
	}

	private void updateValue()
	{
		String string = input_value.getText();

		// string, by default, contains only numbers, if any.

		if (string.length() == 0)
		{
			string = "0";
		}
		long item = Long.parseLong(string);

		if (item > MAX_VALUE)
		{
			// item is too big.
			value = MAX_VALUE;
		}
		else if (item < MIN_VALUE)
		{
			// item is too small.
			value = MIN_VALUE;
		}
		else
		{
			// item is easily an integer.
			value = (int) item;
		}
		input_value.setText(String.valueOf(value));
	}

	private void updateList()
	{
		String string = input_list.getText();
		string = string.replace("-", ",-");
		string = string.replaceAll("[\\-]+,", "");

		Scanner input = new Scanner(string);

		// Ensure that each possible item has only a minus sign if applicable, and
		// numbers - no spaces or commas
		input.useDelimiter(DELIMITER);

		ArrayList<Integer> array = new ArrayList<Integer>();

		String item;
		long possible_value;
		float overflow = 0;
		boolean end = false;
		while (input.hasNext())
		{
			// The easiest, simplest way.
			possible_value = 0;
			do
			{
				if (input.hasNext())
				{
					item = input.next();
				}
				else
				{
					item = "";
					end = true;
				}
			}
			while (((item.isEmpty() || item.equals("-"))) && !end);

			// Remove all but the first minus sign
			if (end)
			{
				break;
			}

			try
			{

				possible_value = Long.parseLong(item);
			}
			catch (NumberFormatException exception)
			{
				// Number is too large for an integer
				overflow = Float.parseFloat(item);
				possible_value = (overflow > MAX_VALUE) ? MAX_VALUE : MIN_VALUE;
			}

			// string, by default, contains only numbers, if any, and commas and
			// spaces and minus signs

			if (possible_value >= MAX_VALUE)
			{
				// item is too big.
				array.add(MAX_VALUE);
			}
			else if (possible_value <= MIN_VALUE)
			{
				// item is too small.
				array.add(MIN_VALUE);
			}
			else
			{
				// item is easily an integer.
				array.add((int) possible_value);
			}
		}
		input.close();
		if (array.size() == 0)
		{
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		list = new int[array.size()];
		Collections.sort(array);
		Iterator<Integer> iterator = array.listIterator();
		for (int i = 0; i < array.size(); i++)
		{
			list[i] = iterator.next();
		}
		input_list.setText(array.toString());
	}

	private void enableButtons()
	{
		setButtons(true);
	}

	private void disableButtons()
	{
		setButtons(false);
	}

	private void setButtons(boolean value)
	{
		button_toggle.setEnabled(value);
		button_increment.setEnabled(value);
		button_fastforward.setEnabled(value);
	}

	private void createSearch(int[] sorted_list, int value)
	{
		timer.stop();
		enableButtons();
		searches[0] = new FibonacciSearch(sorted_list, value);
		searches[1] = new ExponentialSearch(sorted_list, value);
		for (int i = 0; i < 2; i++)
		{
			table_data[i].clearTable();
			search_bars[i].reset(sorted_list.length);
		}
		increment();
	}

	private boolean iterate()
	{
		Result[] result = new Result[2];
		int finished_count = 0;
		for (int i = 0; i < 2; i++)
		{
			result[i] = searches[i].result;
			if (result[i] != Result.NOTFOUND && result[i] != Result.EQUAL)
			{
				searches[i].next();
				searches[i].getNextStep();
				table_data[i].addRow(searches[i].getRow());
				tables[i].revalidate();
				tables[i].repaint();
				search_bars[i].add(searches[i].index);
			}
			else
			{
				finished_count = finished_count + 1;
			}
		}
		if (finished_count == 2)
		{
			return false;
		}
		return true;
	}

	private void pause()
	{
		button_toggle.setText("Play ");
		mode = Mode.PAUSE;
		timer.stop();

	}

	private void play()
	{
		button_toggle.setText("Pause");
		mode = Mode.PLAY;
		timer.stop();
		timer.setDelay(NORMAL);
		timer.restart();
	}

	private void increment()
	{
		button_toggle.setText("Play ");
		timer.stop();
		mode = Mode.INCREMENT;
		if (!iterate())
		{
			mode = Mode.FINISHED;
			disableButtons();
			button_toggle.setText("Play ");
		}
		mode = Mode.PAUSE;
	}

	private void fastforward()
	{
		button_toggle.setText("Pause");
		mode = Mode.FAST_FORWARD;
		timer.stop();
		timer.setDelay(IMMEDIATE);
		timer.restart();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *          the command line arguments
	 */
	public static void main(String[] args)
	{
		try
		{
			// Set System Look and Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
		}
		catch (IllegalAccessException error)
		{
			error.printStackTrace();
		}
		catch (InstantiationException error)
		{
			error.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException error)
		{
			error.printStackTrace();
		}

		new Driver();
	}

}
