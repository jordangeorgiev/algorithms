/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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

	private static final Font	button_font				= new Font(Font.MONOSPACED, Font.PLAIN, 42);
	private static final Font	input_font				= new Font(Font.MONOSPACED, Font.PLAIN, 42);

	private static final int	WIDTH							= 800;
	private static final int	HEIGHT						= 800;

	protected static enum Mode
	{
		PLAY, PAUSE, INCREMENT, FAST_FORWARD, FINISHED
	}

	private static final int	IMMEDIATE		= 0;
	private static final int	NORMAL			= 1000;

	// Main panel
	private JPanel						panel;
	private BorderLayout			layout;

	// Contains input-bar and toolbar
	private JPanel						north_split;
	private GridLayout				north_layout;

	// Input bar
	private JPanel						input_bar;
	private JTextField				input_value;
	private JTextField				input_list;
	private JButton						button_random;

	// Toolbar
	private JPanel						toolbar;
	private JButton						button_toggle;
	private JButton						button_increment;
	private JButton						button_fastforward;

	// private GridBagLayout center_layout;
	// private GridBagConstraints center_constraints;

	// Contains both split panels
	private JSplitPane				center_split;

	private JScrollPane[]			table_panes	= new JScrollPane[2];
	private JTable[]					tables			= new JTable[2];

	private SearchTable[]			table_data	= new SearchTable[2];

	private Search[]					searches		= new Search[2];

	private Mode							mode;

	private Timer							timer;

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
		input_value.setText("123");
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
				enableButtons();

				int[] sorted_value = new int[1024];
				int value = 125;
				for (int i = 0; i < 1023; i++)
				{
					sorted_value[i] = i;
				}
				createSearch(sorted_value, value);

			}
		});

		input_bar.add(input_value, BorderLayout.WEST);

		////////////////
		// Input List //
		////////////////

		input_list = new JTextField();
		input_list.setFont(input_font);
		input_list.setText("1, 2, 3, 4, 5");
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
				enableButtons();

				int[] sorted_list = new int[1024];
				int value = 125;
				for (int i = 0; i < 1023; i++)
				{
					sorted_list[i] = i;
				}
				createSearch(sorted_list, value);

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
					int random_max = r.nextInt(10000);
					int[] array = new int[random_max];
					for (int i = 0; i < random_max; i++)
					{
						array[i] = r.nextInt();
					}
					Arrays.sort(array);
					int value = r.nextInt();
					createSearch(array, value);
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
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "Increment");
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
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "Fast Forward");
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
		table_data[0] = new FibonacciTable();
		table_data[1] = new ExponentialTable();
		for (int i = 0; i < 2; i++)
		{
			tables[i] = new JTable(table_data[i]);
			tables[i].setFillsViewportHeight(true);
			table_panes[i] = new JScrollPane(tables[i]);
		}
		center_split.setLeftComponent(table_panes[0]);
		center_split.setRightComponent(table_panes[1]);
		center_split.setDividerLocation(WIDTH / 2);
		panel.add(center_split, BorderLayout.CENTER);

		add(panel);

		disableButtons();

		setSize(WIDTH, HEIGHT);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);

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
		table_data[0].clearTable();
		table_data[1].clearTable();
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
				tables[i].repaint();
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
		button_toggle.setText("Pause");
		timer.stop();
		mode = Mode.INCREMENT;
		if (!iterate())
		{
			mode = Mode.FINISHED;
			disableButtons();
		}
		mode = Mode.PAUSE;
	}

	private void fastforward()
	{
		button_toggle.setText("Play ");
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