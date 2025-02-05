package Game2048;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game2048 frame=new Game2048();
		GamePanel panel = new GamePanel(frame);
		frame.add(panel);		
		frame.setVisible(true);
	}

}
