package hci;

import java.util.ArrayList;
import hci.utils.*;

public class AnnotatedImages {
	
	private ArrayList<ArrayList<Point>> polygonList = new ArrayList<ArrayList<Point>>();
	
	private ArrayList<String> nameList = new ArrayList<String>();
	
	public String getName(int index){
		return nameList.get(index);
	}
	
	public ArrayList<Point> getPolygon(int index){
		return polygonList.get(index);
	}
	
	public void addImage(String name, ArrayList<Point> polygon){
		nameList.add(name);
		polygonList.add(polygon);
	}
	
	
	
}