/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

	private static final int	WIDTH							= 800;
	private static final int	HEIGHT						= 800;
	private BorderLayout			layout;
	private JPanel						panel;
	private JButton						button_toggle;
	private JButton						button_increment;
	private JButton						button_fastforward;

	private JPanel						north_split;
	private JPanel						toolbar;

	private JPanel						center_split;

	private JPanel[]					split;

	public Driver()
	{
		// Set title
		super("Fibonacci vs. Exponential Search");

		// Set default close operation
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set look-and-feel
		setDefaultLookAndFeelDecorated(true);

		// Define panel
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Set layout
		layout = new BorderLayout(20, 20);
		panel.setLayout(layout);

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
					if (button_toggle.getText().equals("Play"))
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
		button_toggle.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
				"Pause/Play");
		button_toggle.getActionMap().put("Pause/Play", button_toggle.getAction());
		button_toggle.setText("Pause");
		button_toggle.setFocusable(!true);
		button_toggle.setFont(button_font);
		button_toggle.setEnabled(!false);

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
					pause();
					increment();
				}
			}
		});
		button_increment.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0),
				"Increment");
		button_increment.getActionMap().put("Increment", button_increment.getAction());
		button_increment.setText("Increment");
		button_increment.setFocusable(!true);
		button_increment.setFont(button_font);
		button_increment.setEnabled(!false);

		toolbar.add(button_increment);

		//////////////////////
		// Button Fast Forward //
		//////////////////////

		button_fastforward = new JButton();
		button_fastforward.setName("Fast Forward");
		button_fastforward.setAction(new AbstractAction()
		{

			/**
			 * Randomly typed serial version UID
			 */
			private static final long serialVersionUID = 24721035756348904L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (button_fastforward.isEnabled())
				{
					// Fast Forward
					play();
					fastforward();
				}
			}
		});
		button_fastforward.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0),
				"Fast Forward");
		button_fastforward.getActionMap().put("Fast Forward", button_fastforward.getAction());
		button_fastforward.setText("Fast Forward");
		button_fastforward.setFocusable(!true);
		button_fastforward.setFont(button_font);
		button_fastforward.setEnabled(!false);

		toolbar.add(button_fastforward);

		panel.add(toolbar, BorderLayout.NORTH);
		add(panel);

		setSize(WIDTH, HEIGHT);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void pause()
	{
		button_toggle.setText("Play");
		// TODO
	}

	private void play()
	{
		button_toggle.setText("Pause");
		// TODO
	}

	private void increment()
	{
		// TODO
	}

	private void fastforward()
	{
		// TODO
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
