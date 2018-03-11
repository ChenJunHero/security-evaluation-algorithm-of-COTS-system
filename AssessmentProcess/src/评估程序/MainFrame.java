package 评估程序;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainFrame extends JFrame{
	JButton bt1,bt2,bt3,bt4;
	public MainFrame()
	{
		//Jpannel
		
		/*GridLayout g1=new GridLayout(4,1,50,100);
		setLayout(g1);*/
		bt1=new JButton("代价动因设置");
		bt2=new JButton("代价动因参数设置");
		bt3=new JButton("安全规则设置");
	    bt4=new JButton("信息安全风险值计算");
	    JPanel p=new JPanel();
		JLabel l=new JLabel();
		Icon icon=new ImageIcon("images/security.jpg"); //在此直接创建对象
	l.setIcon(icon);
		l.setBounds(100, 100, icon.getIconWidth(),icon.getIconHeight());
		p.add(l,new Integer(Integer.MIN_VALUE));
		 p.add(bt1);
		 p.add(bt2);
		 p.add(bt3);
		 p.add(bt4);
		getContentPane().add(p,BorderLayout.CENTER);
	    pack(); //窗口适应组件大小
	    
	    bt2.setEnabled(false);
	    bt3.setEnabled(false);
	    bt4.setEnabled(false);
//	    JLabel jp = new JLabel(new ImageIcon("images/security.jpg"));
//	    jp.setSize(400,300);
//	    add(jp,BorderLayout.CENTER);
//	    JPanel pnlMain = new JPanel();
//	    getContentPane().add(pnlMain);
//	    JPanel p1 = new JPanel();
//	    p1.setSize(100, 250);
//	    p1.setLayout(new BorderLayout(30,60));
//	    p1.add(bt1, BorderLayout.NORTH);
//	    p1.add(bt2, BorderLayout.SOUTH);
////	    
//	    JPanel p2 = new JPanel();
//	    p2.setSize(100, 250);
//	    p2.setLayout(new BorderLayout(10,60));
//	    p2.add(bt3, BorderLayout.NORTH);
//	    p2.add(bt4, BorderLayout.SOUTH);
//	   
//	    add(p1, BorderLayout.NORTH);
//	    add(p2, BorderLayout.SOUTH);
//	    
	    
	    /*Font font = new Font("Consolas", 16, 200);
	    bt1.setFont(font);*/
	    /*add(bt1);
	    add(bt2);
	    add(bt3);
	    add(bt4);*/
	    setTitle("COTS组件化系统信息安全风险评估工具");
		setSize(550,635);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	    
	    bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("bt1");
				new SecondFrame();
				bt2.setEnabled(true);
			}
		});
         bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("bt2");
				try {
					new ThirdFrame();
					bt3.setEnabled(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 
			}
		});
         
         
         bt3.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
// 				 TODO Auto-generated method stub
 				System.out.println("bt4");
 				try {
					new FourthFrame();
					bt4.setEnabled(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 	 
 			}
 		});
         
         
         
         
         bt4.addActionListener(new ActionListener() {
 			
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				// TODO Auto-generated method stub
 				System.out.println("bt4");
 				new FifthFrame();
 	 
 			}
 		});
 	      
	}
	
	
	public static void main(String[] args){
		MainFrame myGridLayout = new MainFrame();
		
	}
}
	

