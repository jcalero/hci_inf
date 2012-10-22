package hci;

import hci.ImageLabeller;
import hci.ImagePanel;
import hci.AnnotatedImages;
import java.util.ArrayList;
import hci.utils.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class CustomDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private String userInput = null;
	
	public String getUserInput() {
		return userInput;
	}

	public CustomDialog(ImageLabeller imageLabeller, ImagePanel imagePanel, String message, AnnotatedImages savedImage) {
		super(imageLabeller, message, true);
		
		String userInput = JOptionPane.showInputDialog("Enter object Label", JOptionPane.OK_CANCEL_OPTION);
		
		if (userInput != null){
			
			ArrayList<Point> polygon = imagePanel.getPolygon();
			System.out.println(polygon.size());
			savedImage.addImage(userInput, polygon);
			System.out.println(savedImage.getName(0));
		}
	}
}