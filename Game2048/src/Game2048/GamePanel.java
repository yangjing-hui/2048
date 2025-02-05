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
	private String gameFlag = "start";//��Ϸ״̬
	private static char m[][]=new char[100000][16];
	private static final int COLS=4;//��
	private static final int ROWS=4;//��
	
	private static Card cards[][] = new Card[ROWS][COLS];
	//���췽��
	public GamePanel(JFrame mainFrame){
		this.setLayout(null);
		this.setOpaque(false);
		this.mainFrame=mainFrame;
		//�����˵�
		createMenu();
		
		//��ʼ��
		init();
		
		//�������һ������
		createRandomNumber();try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��Ӽ����¼�
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
        // �����ļ�
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        // ���ļ�д������
        writer.write(strSavContent);
        writer.flush();
        writer.close();
        read(file);
	}//�����ļ�
	
	public static final void read(File file) throws IOException{
		// ���� FileReader ����
        FileReader fr = new FileReader(file);
        char[] a = new char[50];
        fr.read(a); // �������ж�ȡ����
        int r=0;
        for (char c : a) {
            System.out.print(c); // һ������ӡ�ַ�
            r++;
            for(int i=0;i<16;i++) {
                m[r%16][i]=c;
            }
        }
        fr.close();
	}

	private void createKeyListener() {
		
		KeyAdapter l = new KeyAdapter() {
			//����
			@Override
			public void keyPressed(KeyEvent e) {
				if(!"start".equals(gameFlag)) return ;
				int key = e.getKeyCode();
				switch (key) {
					//����
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						moveCard(1);//����
						break;
						
					//����	
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						moveCard(2);//����
						break;
						
					//����
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						moveCard(3);//������
						break;
						
					//����
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						moveCard(4);//����
						break;
				}
				
			}
			
		};
		//����frame��Ӽ��̼���
		mainFrame.addKeyListener(l);
	}

	
	//�������ƶ���Ƭ
	protected void moveCard(int dir) {
		//����Ƭ����һ�飬��Ϊÿ���ƶ����趨�ϲ���ǣ�������
		clearCard();
		
		if(dir==1){//�����ƶ�
			moveCardUp(true);
		}else if(dir==2){//�����ƶ�
			moveCardRight(true);
		}else if(dir==3){//�����ƶ�
			moveCardDown(true);
		}else if(dir==4){//�����ƶ�
			moveCardLeft(true);
		}
		//�ƶ���Ҫ�����µĿ�Ƭ
		createRandomNumber();try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//�ػ�
		repaint();
		//�ж���Ϸ�Ƿ����
		gameOverOrNot();
	}

	private void gameOverOrNot() {
		/* ����������
		 * 1.λ������
		 * 2.4������û�п��Ժϲ��Ŀ�Ƭ
		 */
		if(isWin()){//ʤ��
			gameWin();
		}else if(cardFull()){//λ������
			if(moveCardUp(false)||
				moveCardRight(false)||
				moveCardDown(false)||
				moveCardLeft(false)){//ֻҪ��һ����������ƶ����ߺϲ����ͱ�û����
				return ;
			}else{//��Ϸʧ��
				gameOver();
			}
		}
	}
	
	//��Ϸʤ��
	public void gameWin() {
		gameFlag = "end";
		//����������ʾ
	    JOptionPane.showMessageDialog(mainFrame, "��ɹ���,̫����!");
	}
	
	//��Ϸ����
	public void gameOver() {
		gameFlag = "end";
		//����������ʾ
	    JOptionPane.showMessageDialog(mainFrame, "��ʧ����,���ٽ�����!");
	}
	private boolean isWin(){
		Card card;
		for (int i = 0; i < ROWS; i++) {//i��1��ʼ����Ϊi=0����Ҫ�ƶ�
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()==2048){//ʤ����
					return true;
				}
			}
		}
		return false;
	}

	private void clearCard() {
		Card card;
		for (int i = 0; i < ROWS; i++) {//i��1��ʼ����Ϊi=0����Ҫ�ƶ�
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
			for (int j = 1; j < COLS ; j++) {//j��1��ʼ��������߿�ʼ�ƶ�
				card = cards[i][j];
				if(card.getNum()!=0){//ֻҪ��Ƭ��Ϊ�գ�Ҫ�ƶ�
					if(card.moveLeft(cards,bool)){//�����ƶ�
						res = true;//��һ��Ϊ�ƶ����ߺϲ��ˣ���resΪtrue
					}
				}
			}
		}
		return res;
	}

	private boolean moveCardDown(boolean bool) {
		boolean res = false;
		Card card;
		for (int i = ROWS-1; i >=0; i--) {//i��ROWS-1��ʼ�����µݼ��ƶ�
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()!=0){//ֻҪ��Ƭ��Ϊ�գ�Ҫ�ƶ�
					if(card.moveDown(cards,bool)){//���ƶ�
						res = true;//��һ��Ϊ�ƶ����ߺϲ��ˣ���resΪtrue
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
			for (int j = COLS-1; j >=0 ; j--) {//j��COLS-1��ʼ�������ұ߿�ʼ�ƶ��ݼ�
				card = cards[i][j];
				if(card.getNum()!=0){//ֻҪ��Ƭ��Ϊ�գ�Ҫ�ƶ�
					if(card.moveRight(cards,bool)){//�����ƶ�
						res = true;//��һ��Ϊ�ƶ����ߺϲ��ˣ���resΪtrue
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
		for (int i = 1; i < ROWS; i++) {//i��1��ʼ����Ϊi=0����Ҫ�ƶ�
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()!=0){//ֻҪ��Ƭ��Ϊ�գ�Ҫ�ƶ�
					if(card.moveUp(cards,bool)){//�����ƶ�
						res = true;//��һ��Ϊ�ƶ����ߺϲ��ˣ���resΪtrue
					}
				}
			}
		}
		return res;
	}

	//������Ŀտ�Ƭ��������2����4
	private void createRandomNumber() {
		int num = 0;
		Random random = new Random();
		int index = random.nextInt(5)+1;//����ȡ�����ľ���1-5 ֮��������
		//��Ϊ2��4���ֵĸ�����1��4���������index��1���򴴽�����4�����򴴽�����2(1����������ĸ��ʾ���1/5������������4/5 ����1��4�Ĺ�ϵ)
		
		if(index==1){
			num = 4;
		}else {
			num = 2;
		}
		//�ж���������Ѿ����ˣ����ٻ�ȡ���˳�
		if(cardFull()){
			return ;
		}
		//��ȡ�����Ƭ����Ϊ�յ�
		Card card = getRandomCard(random);
		//��card������������
		if(card!=null){
			card.setNum(num);
		}
	}
	
	private boolean cardFull() {
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				if(card.getNum()==0){//��һ��Ϊ�գ���û��
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
		if(card.getNum()==0){//����ǿհ׵Ŀ�Ƭ�����ҵ��ˣ�ֱ�ӷ���
			return card;
		}
		//û�ҵ��հ׵ģ��͵ݹ飬����Ѱ��
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
		//���ƿ�Ƭ
		drawCard(g);
	}
	//���ƿ�Ƭ
	private void drawCard(Graphics g) {
		Card card;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				card = cards[i][j];
				card.draw(g);
			}
		}		
	}
	//�����˵�
	private void createMenu() {
		JMenuBar jmb=new JMenuBar();
		
		JMenu jMenu1=new JMenu("��Ϸ");
		JMenuItem jmi1=new JMenuItem("����Ϸ");
		JMenuItem jmi2=new JMenuItem("�˳�");
		
		JMenu jMenu2=new JMenu("����");
		JMenuItem jmi3=new JMenuItem("��ʾ");
		JMenu jMenu3=new JMenu("�ط�");
		JMenuItem jmi4=new JMenuItem("�浵���ط�");
		
		jmb.add(jMenu1);
		jmb.add(jMenu2);
		jmb.add(jMenu3);
		jMenu1.add(jmi1);
		jMenu1.add(jmi2);
		jMenu2.add(jmi3);
		jMenu3.add(jmi4);
		mainFrame.setJMenuBar(jmb);
		
		//����¼�����
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
			Object[] options= {"ȷ��","ȡ��"};
			int res=JOptionPane.showOptionDialog(this,"��ȷ��Ҫ�˳���Ϸ��","",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
			if(res==0) {//ȷ���˳�
				System.exit(0);
			}
		}else if("help".equals(command)) {
			JOptionPane.showMessageDialog(this,"ͨ�����̵����������ƶ�����ͬ���ֻ�ϲ���\n�õ�2048����Ϸʤ����û�пտ�Ƭ��ʧ�ܣ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
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
				//���»���
				repaint();
				
				try {
		 			Thread.sleep(400);//ÿ��40��ִ��һ��
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
		//�������һ������
		createRandomNumber();
		//���»���
		repaint();
	}
}
