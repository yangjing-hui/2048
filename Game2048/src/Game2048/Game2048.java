package Game2048;

import java.awt.*;
import javax.swing.*;

public class Game2048 extends JFrame{
	public Game2048() {	          //  g.setColor(startColor);
	 
		setTitle("2048");//标题
		setSize(590,650);//设置窗体大小
		getContentPane().setBackground(new Color(0xBBADA0));//设置背景颜色0xBBADA0
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭后进程退出
		setLocationRelativeTo(null);//居中
		setResizable(false);//设置窗体不允许变大
	}
}
