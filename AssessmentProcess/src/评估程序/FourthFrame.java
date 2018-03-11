package 评估程序;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FourthFrame extends JFrame {
	private JButton btSave, btLoad;
	List<String> list = new ArrayList<>();
	private String[] Rate = {"None", "OK", "Mod", "RP", "Risk", "WC",  };

	String driverNameMax;
	int currentLength = 0;
	
	private int riskBorder[][][]; 
	private JComboBox comboxs[][][];

	public FourthFrame() throws Exception {
		btSave = new JButton("保存");
		btLoad = new JButton("加载");
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout());
		p1.add(btSave);
		p1.add(btLoad);
		add(p1, BorderLayout.SOUTH);

		// 读文件 拿出里面的数据
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Tools.currentDriverFile)));
		String buffer;
		while ((buffer = br.readLine()) != null) {
			System.out.println(buffer);
			list.add(buffer);
		}

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(list.size() + 1, list.size() + 1, 15, 10));

		riskBorder = new int[list.size()][list.size()][2];
		comboxs = new JComboBox[list.size()][list.size()][2];

		for (int i = 0; i < list.size() + 1; i++) {
			for (int j = 0; j < list.size() + 1; j++) {

				if (i == 0 && j==0){
					p2.add(new JLabel("代价动因", JLabel.CENTER)); // 将首行首列添加内容
				}else if (i == 0 && j != 0){
					p2.add(new JLabel(list.get(j - 1), JLabel.CENTER));//读取list显示在第一行
				}else if (i != 0 && j == 0){
					p2.add(new JLabel(list.get(i - 1), JLabel.CENTER));//读取list显示在第一行
				}else {
					JPanel boxPanel = new JPanel();
					boxPanel.setLayout(new GridLayout(1, 2, 5, 5));
					JComboBox combox01 = new JComboBox<>(Rate);
					JComboBox combox02 = new JComboBox<>(Rate);
					combox01.setSize(5, 5);
					combox02.setSize(5, 5);
					boxPanel.add(combox01);
					boxPanel.add(combox02);
					
					//加上点击事件
					combox01.addActionListener(new MyActionListener(i - 1, j - 1, 0, combox01));
					combox02.addActionListener(new MyActionListener(i - 1, j - 1, 1, combox02));
					comboxs[i-1][j-1][0] = combox01;
					comboxs[i-1][j-1][1] = combox02;
					
					p2.add(boxPanel);
					if (i >= j) {
						combox01.setEnabled(false);
						combox02.setEnabled(false);
					}
				}
			}
		}
		
		JScrollPane jslp = new JScrollPane(p2);
	    jslp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//总是垂直显示滚动条
	    jslp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(jslp, BorderLayout.CENTER);
		
		//保存事件
		btSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("保存代价动因安全规则");//;';
				chooser.setMultiSelectionEnabled(false);
				chooser.showSaveDialog(null);
				chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );
				File file = chooser.getSelectedFile();
				Tools.currentDriverrulesFile = file;
			
				try {
					BufferedWriter bw = new BufferedWriter(
							   new OutputStreamWriter(new FileOutputStream(file)));
					for (int i=0; i<list.size(); i++){
						for (int j=0; j < list.size(); j++){
							for (int k=0; k < 2; k++){
//								System.out.println("i = " + i + " j = " + j + " k = " + k + " riskBorder =" + riskBorder[i][j][k]);
								bw.write(i + " " + j + " " + k + " " + riskBorder[i][j][k] + "\n");
							}
						}
					}
					bw.flush();
					bw.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//加载事件
		btLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("加载代价动因安全规则");//
				chooser.setMultiSelectionEnabled(true);
				chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				Tools.currentDriverrulesFile = file;
				
				BufferedReader br;
				riskBorder = new int[list.size()][list.size()][2];
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String buffer;
					while ((buffer = br.readLine()) != null) {
//						list.add(buffer);
						String[] str = buffer.split(" ");
						
						riskBorder[Integer.valueOf(str[0])]
								[Integer.valueOf(str[1])]
										[Integer.valueOf(str[2])] 
												= Integer.valueOf(str[3]);
						comboxs[Integer.valueOf(str[0])]
								[Integer.valueOf(str[1])]
										[Integer.valueOf(str[2])]
												.setSelectedIndex(Integer.valueOf(str[3]));
					}
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		setTitle("安全规则设置");
		setSize(689, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String args[]) throws Exception {
		new FourthFrame();
	}
	
	class MyActionListener implements ActionListener{
		private int i;
		private int j;
		private int k;
		private JComboBox combox;
		
		public MyActionListener(int i, int j, int k, JComboBox combox) {
			// TODO Auto-generated constructor stub
			this.i = i;
			this.j = j;
			this.k = k;
			this.combox = combox;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println("i = " + i);
//			System.out.println("j = " + j);
//			System.out.println("index = " + k);
//			System.out.println(combox.getSelectedIndex() + " " + combox.getSelectedItem());
			
			riskBorder[i][j][k] = combox.getSelectedIndex();
		}
		
	}
}

