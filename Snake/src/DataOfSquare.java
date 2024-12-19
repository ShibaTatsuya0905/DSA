import java.util.ArrayList;
import java.awt.Color;

public class DataOfSquare {


	//ArrayList that'll contain the colors
	ArrayList<Color> C =new ArrayList<Color>();
	int color;
	SquarePanel square;
	public DataOfSquare(int col){
		
		//Lets add the color to the arrayList
		C.add(Color.GREEN);//0
		C.add(Color.RED);    //1
		C.add(Color.white);   //2
		color=col;
		square = new SquarePanel(C.get(color));
	}
	public void lightMeUp(int c){
		square.ChangeColor(C.get(c));
	}
}
