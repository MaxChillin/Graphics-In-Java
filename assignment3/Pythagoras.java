package assignment3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Pythagoras extends Frame implements ActionListener {
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu();
	JMenuItem menuItem = new JMenuItem();

	String[] options = {"Make A Selection", "Pythagoras", "Quit"};
	JComboBox<String> box = new JComboBox<>(options);
	JLabel label = new JLabel();
	int HEIGHT = 1080, WIDTH = HEIGHT *16/9;
	float SCALER = 1.0f;
	
	public static void main(String[] args) {
		
		new Pythagoras();

	}

	Pythagoras() {
		super("Tree of Pythagoras");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		SCALER = 0.25f;
		menuItem = new JMenuItem("Pythagoras");
		menu.add(menuItem);
		menuBar.add(menu);
		setSize((int) (WIDTH * SCALER), (int) (HEIGHT * SCALER));
		setBackground(Color.BLACK);
		box.setSelectedIndex(0);
		box.addActionListener(this);
		add(box);
		add(label);
		setVisible(true);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
	}

	
	Pythagoras(String title) {
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		SCALER = 1.0f;
		menuItem = new JMenuItem("Pythagoras");
		menu.add(menuItem);
		menuBar.add(menu);
		setSize((int) (WIDTH * SCALER), (int) (HEIGHT * SCALER));
		setBackground(Color.BLACK);
		box.setSelectedIndex(1);
		box.addActionListener(this);
		add(box);
		add(label);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		add("Center", new MyCanvas());
		setVisible(true);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == box) {
			switch((String) box.getSelectedItem()) {
			case "Pythagoras":
				this.dispose();
				new Pythagoras("Tree of Pythagoras");
				break;
			case "Quit":
				System.exit(0);
			default:
				//do nothing
			}// end of switch statement
		}// end of if statement
	}// end of actionPerformed method
}// end of Pythagoras class

