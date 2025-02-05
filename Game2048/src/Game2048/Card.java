package Game2048;

import java.awt.*;


public class Card {
	private int x=0;//x����
	private int y=0;//y����
	private int w=100;//��
	private int h=100;//��
	private int i=0;
	private int j=0;
	
	private int start=60;//ƫ����
	private int num=0;//����
	private boolean merge =false;//�ж��Ƿ�ϲ�

	public Card(int i,int j) {
		this.i=i;
		this.j=j;
		cal();
	}

	private void cal() {
		this.x=start+j*w+(j+1)*10;
		this.y=start+i*h+(i+1)*10;
	}
	
	//��Ƭ����
	public void draw(Graphics g) {
		Color color=getColor();
		Color ocolor=g.getColor();
		g.setColor(color);
		g.fillRoundRect(x,y,w,h,4,4);
		
		if(num!=0) {
			g.setColor(new Color(0x701710));
			Font font =new Font("˼Դ����",Font.BOLD,40);
			g.setFont(font);
			String text=num+"";
			int textLen=getWordWidth(font,text,g);
			int tx=x+(w-textLen)/2;
			int ty=y+65;
			g.drawString(text,tx,ty);
		}
		
		g.setColor(ocolor);
	}

	public static int getWordWidth(Font font,String content,Graphics g) {
		FontMetrics metrics=g.getFontMetrics(font);
		int width =0;
		for(int i=0;i<content.length();i++) {
			width+=metrics.charWidth(content.charAt(i));
		}
		return width;
	}
	private Color getColor() {
		Color color=null;
		switch (num) {
			case 2:
				color=new Color(255,255,255);//0x701710
				break;
			case 4:
				color=new Color(0xfff4d3); 
				break;
			case 8:	
				color=new Color(0xffdac3);
				break;
			case 16:
				color=new Color(0xe7b08e);
				break;
			case 32:
				color=new Color(0xe7bf8e);
				break;
			case 64:	
				color=new Color(0xffc4c3);
				break;
			case 128:
				color=new Color(0xE7948e);
				break;
			case 256:
				color=new Color(0xbe7e56);
				break;
			case 512:
				color=new Color(0xbe5e56);
				break;
			case 1024:	
				color=new Color(0x9c3931);
				break;
			case 2048:
				color=new Color(255,244,233); 
				break;
			default:
				color=new Color(0xCDC1B4);
				break;
		}
		return color;
	}

	public void setNum(int num) {
		this.num=num;

	}

	public int getNum() {
		// TODO Auto-generated method stub
		return this.num;
	}
	
	public boolean moveUp(Card[][] cards,boolean bool) {
		//�趨�˳�����
		if(i==0){//�Ѿ�����������
			return false;
		}
		//����һ����Ƭ
		Card prev = cards[i-1][j];
		if(prev.getNum()==0){//��һ����Ƭ�ǿ�
			//�ƶ������ʾ�����������
			if(bool){//boolΪtrue��ִ�У���Ϊflaseֻ�������ж��ܷ��ƶ�
				prev.num=this.num;
				this.num=0;
				//�ݹ������ע��������Ҫ prev �� move�ˣ�
				prev.moveUp(cards,bool);
			}
			return true;
		}else if(prev.getNum()==num && !prev.merge){//�ϲ�����������Ѿ��ϲ��ˣ��������ٴκϲ�����Ե�Ȼ�֣�
			if(bool){////boolΪtrue��ִ��
				prev.merge=true;
				prev.num=this.num*2;
				this.num=0;
			}
			return true;
		}else {//��һ����num�뵱ǰnum��ͬ���޷��ƶ������˳�
			return false;
		}
	}
	
	public boolean moveDown(Card[][] cards,boolean bool) {
		//�趨�˳�����
		if(i==3){//�Ѿ�����������
			return false;
		}
		//����һ����Ƭ
		Card prev = cards[i+1][j];
		if(prev.getNum()==0){//��һ����Ƭ�ǿ�
			//�ƶ������ʾ�����������
			if(bool){//boolΪtrue��ִ�У���Ϊflaseֻ�������ж��ܷ��ƶ�
				prev.num=this.num;
				this.num=0;
				//�ݹ������ע��������Ҫ prev �� move�ˣ�
				prev.moveDown(cards,bool);
			}
			return true;
		}else if(prev.getNum()==num && !prev.merge){//�ϲ�����������Ѿ��ϲ��ˣ��������ٴκϲ�����Ե�Ȼ�֣�
			if(bool){////boolΪtrue��ִ��
				prev.merge=true;
				prev.num=this.num*2;
				this.num=0;
			}
			return true;
		}else {//��һ����num�뵱ǰnum��ͬ���޷��ƶ������˳�
			return false;
		}
	
	}
	
	public boolean moveRight(Card[][] cards,boolean bool) {
		
		if(j==3){//�Ѿ������ұ���
			return false;
		}
		
		Card prev = cards[i][j+1];
		if(prev.getNum()==0){//��һ����Ƭ�ǿ�
			//�ƶ������ʾ�����������
			if(bool){//boolΪtrue��ִ�У���Ϊflaseֻ�������ж��ܷ��ƶ�
				prev.num=this.num;
				this.num=0;
				//�ݹ������ע��������Ҫ prev �� move�ˣ�
				prev.moveRight(cards,bool);
			}
			return true;
		}else if(prev.getNum()==num && !prev.merge){//�ϲ�����������Ѿ��ϲ��ˣ��������ٴκϲ�����Ե�Ȼ�֣�
			if(bool){////boolΪtrue��ִ��
				prev.merge=true;
				prev.num=this.num*2;
				this.num=0;
			}
			return true;
		}else {//��һ����num�뵱ǰnum��ͬ���޷��ƶ������˳�
			return false;
		}
	}
	//�����ƶ�
	public boolean moveLeft(Card[][] cards,boolean bool) {
		//�趨�˳�����
		if(j==0){//�Ѿ����������
			return false;
		}
		//����һ����Ƭ
		Card prev = cards[i][j-1];
		if(prev.getNum()==0){//��һ����Ƭ�ǿ�
			//�ƶ������ʾ�����������
			if(bool){//boolΪtrue��ִ�У���Ϊflaseֻ�������ж��ܷ��ƶ�
				prev.num=this.num;
				this.num=0;
				//�ݹ������ע��������Ҫ prev �� move�ˣ�
				prev.moveLeft(cards,bool);	
			}
			return true;
		}else if(prev.getNum()==num && !prev.merge){//�ϲ�����������Ѿ��ϲ��ˣ��������ٴκϲ�����Ե�Ȼ�֣�
			if(bool){////boolΪtrue��ִ��
				prev.merge=true;
				prev.num=this.num*2;
				this.num=0;
			}
			return true;
		}else {//��һ����num�뵱ǰnum��ͬ���޷��ƶ������˳�
			return false;
		}
	}
	public void setMerge(boolean b) {
		this.merge=b;
		
	}
	public boolean isMerge() {
		return merge;
	}
	
}