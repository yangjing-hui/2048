package Game2048;

import java.awt.*;
import javax.swing.*;

public class Game2048 extends JFrame{
	public Game2048() {	          //  g.setColor(startColor);
	 
		setTitle("2048");//����
		setSize(590,650);//���ô����С
		getContentPane().setBackground(new Color(0xBBADA0));//���ñ�����ɫ0xBBADA0
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رպ�����˳�
		setLocationRelativeTo(null);//����
		setResizable(false);//���ô��岻������
	}
}
