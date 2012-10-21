package hci;

import java.util.ArrayList;
import hci.utils.*;

public class AnnotatedImages {
	
	private ArrayList<ArrayList<Point>> polygonList = new ArrayList<ArrayList<Point>>();
	
	private ArrayList<String> nameList = new ArrayList<String>();
	
	public String getName(int index){
		return nameList.get(index);
	}
	
	public ArrayList<String> getNameList(){
		return nameList;
	}
	
	public ArrayList<Point> getPolygon(int index){
		return polygonList.get(index);
	}
	
	public void addImage(String name, ArrayList<Point> polygon){
		nameList.add(name);
		polygonList.add(polygon);
	}
	
	public int getPolygonCount() {
		return nameList.size();
	}
	
	public String getCSVPointList(int index) {
		String pointList = "";
		ArrayList<Point> polygon = getPolygon(index);
		for (int i = 0; i < polygon.size(); i++) {
			pointList += ";" + polygon.get(i).getX() + "," + polygon.get(i).getY();
		}
		return pointList;
	}
	
}