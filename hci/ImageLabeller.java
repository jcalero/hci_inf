package hci;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Main class of the program - handles display of the main window
 * @author Michal
 *
 */
public class ImageLabeller extends JFrame {
	/**
	 * some java stuff to get rid of warnings hello
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * main window panel
	 */
	JPanel appPanel = null;
	
	
	
	
	/**
	 * toolbox - put all buttons and stuff here!
	 */
	ToolBox toolboxPanel = null;
	
	/**
	 * image panel - displays image and editing area
	 */
	ImagePanel imagePanel = null;
	
	/**
	 * handles New Object button action
	 */
	public void addNewPolygon() {
		imagePanel.addNewPolygon();
	}
	
	/**
	 * handles File browser button action
	 */
	public void openFileBrowser() {
		File file = FileBrowser.open();
		try {
			//create a window and display the image
			imagePanel = new ImagePanel(file.getPath(), this);
			imagePanel.setOpaque(true);
			appPanel.add(imagePanel);
		} catch (Exception e) {
			System.err.println("Could not open image");
			//e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		imagePanel.paint(g); //update image panel
	}
	
	/**
	 * sets up application window
	 * @param imageFilename image to be loaded for editing
	 * @throws Exception
	 */
	public void setupGUI(String imageFilename) throws Exception {
		this.addWindowListener(new WindowAdapter() {
		  	public void windowClosing(WindowEvent event) {
		  		//here we exit the program (maybe we should ask if the user really wants to do it?)
		  		//maybe we also want to store the polygons somewhere? and read them next time
		  		System.out.println("Bye bye!");
		    	System.exit(0);
		  	}
		});

		//setup main window panel
		appPanel = new JPanel();
		
		
		this.setLayout(new BoxLayout(appPanel, BoxLayout.X_AXIS));
		this.setContentPane(appPanel);
		
        //Create and set up the image panel.
		imagePanel = new ImagePanel(imageFilename, this);
		imagePanel.setOpaque(true); //content panes must be opaque
		
        appPanel.add(imagePanel);

        //create toolbox panel
        toolboxPanel = new ToolBox(imagePanel, this);
        
        
		appPanel.add(toolboxPanel);
		
		
		//display all the stuff
		this.pack();
        this.setVisible(true);
	}
	
	/**
	 * Runs the program
	 * @param argv path to an image
	 */
	public static void main(String argv[]) {
//		File defImg = new File()
		String defaultImg = "." + File.separator + "images" + File.separator + "U1003_0000.jpg";
		File file = FileBrowser.open();
		try {
			//create a window and display the image
			ImageLabeller window = new ImageLabeller();
			window.setupGUI(file.getPath());
		} catch (Exception e) {
			System.err.println("Could not open image, opening default.");
			System.err.println("Image: " + defaultImg);
			ImageLabeller window = new ImageLabeller();
			try {
				window.setupGUI(defaultImg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
