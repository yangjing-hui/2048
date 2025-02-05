package Game2048;

import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.awt.*;
import javax.swing.*;




public class GamePanel extends JPanel implements ActionListener{
	private JFrame mainFrame = null;
	private String gameFlag = "start";//游戏状态
	private static char m[][]=new char[100000][16];
	private static final int COLS=4;//列
	private static final int ROWS=4;//行
	
	private static Card cards[][] = new Card[ROWS][COLS];
	//构造方法
	public GamePanel(JFrame mainFrame){
		this.setLayout(null);
		this.setOpaque(false);
		this.mainFrame=mainFrame;
		//创建菜单
		createMenu();
		
		//初始化
		init();
		
		//随机创建一个数字
		createRandomNumber();try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//添加键盘事件
		createKeyListener();
		
	}	
	public static final void save() throws IOException{
		String strSavContent="";
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				strSavContent+=cards[i][j].getNum();

			}
		}
		File file = new File("Hello1.txt");
        // 创建文件
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        // 向文件写入内容
        writer.write(strSavContent);
        writer.flush();
        writer.close();
        read(file);
	}//保存文件
	
	public static final void read(File file) throws IOException{
		// 创建 FileReader 对象
        FileReader fr = new FileReader(file);
        char[] a = new char[50];
        fr.read(a); // 从数组中读取内容
        int r=0;
        for (char c : a) {
            System.out.print(c); // 一个个打印字符
            r++;
            for(int i=0;i<16;i++) {
                m[r%16][i]=c;
            }
        }
        fr.close();
	}

	private void createKeyListener() {
		
		KeyAdapter l = new KeyAdapter() {
			//按下
			@Override
			public void keyPressed(KeyEvent e) {
				if(!"start".equals(gameFlag)) return ;
				int key = e.getKeyCode();
				switch (key) {
					//向上
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						moveCard(1);//向上
						break;
						
					//向右	
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						moveCard(2);//向右
						break;
						
					//向下
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						moveCard(3);//向上下
						break;
						
					//向左
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						moveCard(4);//向左
						break;
				}
				
			}
			
		};
		//给主frame添加键盘监听
		mainFrame.addKeyListener(l);
	}

	
	//按方向移动卡片
	protected void moveCard(int dir) {
		//将卡片清理一遍，因为每轮移动会设定合并标记，需重置
		clearCard();
		
		if(dir==1){//向上移动
			moveCardUp(true);
		}else if(dir==2){//向右移动
			moveCardRight(true);
		}else if(dir==3){//向下移动
			moveCardDown(true);
		}else if(dir==4){//向左移动
			moveCardLeft(true);
		}
		//移动后要创建新的卡片
		createRandomNumber();try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//重绘
		repaint();
		//判断游戏是否结束
		gameOverOrNot();
	}

	private void gameOverOrNot() {
		/* 结束条件：
		 * 1.位置已满
		 * 2.4个方向都没有可以合并的卡片
		 */
		if(isWin()){//胜利
			gameWin();
		}else if(cardFull()){//位置已满
			if(moveCardUp(false)||
				moveCardRight(false)||
				moveCardDown(false)||
				moveCardLeft(false)){//只要有一个方向可以移动或者合并，就表没结束
				return ;
			}else{//游戏失败
				gameOver();
			}
		}
	}
	
	//游戏胜利
	public void gameWin() {
		gameFlag = "end";
		//弹出结束提示
	    JOptionPane.showMessageDialog(mainFrame, "你成功了,太棒了!");
	}
	
	//游戏结束
	public void gameOver() {
		gameFlag = "end";
		//弹出结束提示
	    JOptionPane.showMessageDialog(mainFrame, "你失败了,请再接再厉!");
	}
	private boolean isWin(){
		Card card;
		for (int i = 0; i < ROWS; i++) {//i从1开始，因为i=0不需要移动
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()==2048){//胜利了
					return true;
				}
			}
		}
		return false;
	}

	private void clearCard() {
		Card card;
		for (int i = 0; i < ROWS; i++) {//i从1开始，因为i=0不需要移动
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				card.setMerge(false);
			}
		}
	}

	private boolean moveCardLeft(boolean bool) {
		boolean res = false;
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 1; j < COLS ; j++) {//j从1开始，从最左边开始移动
				card = cards[i][j];
				if(card.getNum()!=0){//只要卡片不为空，要移动
					if(card.moveLeft(cards,bool)){//向左移动
						res = true;//有一个为移动或者合并了，则res为true
					}
				}
			}
		}
		return res;
	}

	private boolean moveCardDown(boolean bool) {
		boolean res = false;
		Card card;
		for (int i = ROWS-1; i >=0; i--) {//i从ROWS-1开始，往下递减移动
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()!=0){//只要卡片不为空，要移动
					if(card.moveDown(cards,bool)){//下移动
						res = true;//有一个为移动或者合并了，则res为true
					}
				}
			}
		}
		return res;
	}

	private boolean moveCardRight(boolean bool) {
		boolean res = false;
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = COLS-1; j >=0 ; j--) {//j从COLS-1开始，从最右边开始移动递减
				card = cards[i][j];
				if(card.getNum()!=0){//只要卡片不为空，要移动
					if(card.moveRight(cards,bool)){//向右移动
						res = true;//有一个为移动或者合并了，则res为true
					}
				}
			}
		}
		return res;
	}

	private boolean moveCardUp(boolean bool) {
		// TODO Auto-generated method stub
		boolean res = false;
		Card card;
		for (int i = 1; i < ROWS; i++) {//i从1开始，因为i=0不需要移动
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()!=0){//只要卡片不为空，要移动
					if(card.moveUp(cards,bool)){//向上移动
						res = true;//有一个为移动或者合并了，则res为true
					}
				}
			}
		}
		return res;
	}

	//在随机的空卡片创建数字2或者4
	private void createRandomNumber() {
		int num = 0;
		Random random = new Random();
		int index = random.nextInt(5)+1;//这样取出来的就是1-5 之间的随机数
		//因为2和4出现的概率是1比4，所以如果index是1，则创建数字4，否则创建数字2(1被随机出来的概率就是1/5，而其他就是4/5 就是1：4的关系)
		
		if(index==1){
			num = 4;
		}else {
			num = 2;
		}
		//判断如果格子已经满了，则不再获取，退出
		if(cardFull()){
			return ;
		}
		//获取随机卡片，不为空的
		Card card = getRandomCard(random);
		//给card对象设置数字
		if(card!=null){
			card.setNum(num);
		}
	}
	
	private boolean cardFull() {
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()==0){//有一个为空，则没满
					return false;
				}
			}
		}		
		return true;
	}

	private Card getRandomCard(Random random) {
		int i = random.nextInt(ROWS);
		int j = random.nextInt(COLS);
		Card card = cards[i][j];
		if(card.getNum()==0){//如果是空白的卡片，则找到了，直接返回
			return card;
		}
		//没找到空白的，就递归，继续寻找
		return getRandomCard(random);
	}

	private void init() {
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = new Card(i,j);
				cards[i][j]=card;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//绘制卡片
		drawCard(g);
	}
	//绘制卡片
	private void drawCard(Graphics g) {
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				card.draw(g);
			}
		}		
	}
	//创建菜单
	private void createMenu() {
		JMenuBar jmb=new JMenuBar();
		
		JMenu jMenu1=new JMenu("游戏");
		JMenuItem jmi1=new JMenuItem("新游戏");
		JMenuItem jmi2=new JMenuItem("退出");
		
		JMenu jMenu2=new JMenu("帮助");
		JMenuItem jmi3=new JMenuItem("提示");
		JMenu jMenu3=new JMenu("回放");
		JMenuItem jmi4=new JMenuItem("存档并回放");
		
		jmb.add(jMenu1);
		jmb.add(jMenu2);
		jmb.add(jMenu3);
		jMenu1.add(jmi1);
		jMenu1.add(jmi2);
		jMenu2.add(jmi3);
		jMenu3.add(jmi4);
		mainFrame.setJMenuBar(jmb);
		
		//添加事件监听
		jmi1.addActionListener(this);
		jmi2.addActionListener(this);
		jmi3.addActionListener(this);
		jmi4.addActionListener(this);
		jmi1.setActionCommand("restart");
		jmi2.setActionCommand("exit");
		jmi3.setActionCommand("help");
		jmi4.setActionCommand("review");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		if("restart".equals(command)) {
			restart();
		}else if("exit".equals(command)) {
			Object[] options= {"确定","取消"};
			int res=JOptionPane.showOptionDialog(this,"你确定要退出游戏吗","",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
			if(res==0) {//确认退出
				System.exit(0);
			}
		}else if("help".equals(command)) {
			JOptionPane.showMessageDialog(this,"通过键盘的上下左右移动，相同数字会合并！\n得到2048则游戏胜利，没有空卡片则失败！","提示",JOptionPane.INFORMATION_MESSAGE);
		}else if("review".equals(command)) {
			review();
		}
	}	
	


	private void review() {
		// TODO Auto-generated method stub
		gameFlag = "start";
		Card card;
		init();
		int g=0;
		while(m[g][0]!='\0') {
			for(int k=0;k<16;k++) {
				clearCard();
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COLS; j++) {
						card = cards[i][j];
						card.setNum(m[g][k]);System.out.println(cards[i][j].getNum());
					}
				}
				//重新绘制
				repaint();
				
				try {
		 			Thread.sleep(400);//每隔40秒执行一次
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			g++;
		}
	}

	private void restart() {
		gameFlag = "start";
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				card.setNum(0);
				card.setMerge(false);
			}
		}
		//随机创建一个数字
		createRandomNumber();
		//重新绘制
		repaint();
	}
}
