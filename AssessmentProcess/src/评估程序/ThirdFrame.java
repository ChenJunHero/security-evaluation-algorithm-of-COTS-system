package 评估程序;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.acl.Permission;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ThirdFrame extends JFrame{
	

	
	private String[] Rate = { "OK", "Moderate", "Risk Prone", "Risk", "Worst Case", };
	private int flag;
	List<DriverInfo> driverList = new ArrayList<>();
	
	List<JComboBox> comboxList = new ArrayList<>();

	private Vector cells;
	List<JTextField> textfieldList = new ArrayList<>();

	private Vector columnNames;
	
	private int index;
	
	public ThirdFrame() throws Exception{
		JButton bt1, bt2;
		bt1 = new JButton("保存");
		bt2 = new JButton("加载");
	
		//保存和加载按钮
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout());
		p1.add(bt1);
		p1.add(bt2);
		add(p1, BorderLayout.SOUTH);
		
		
		/*BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(new File(Tools.currentDriverFileName)), Charset.defaultCharset()));*/
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(Tools.currentDriverFile), Charset.defaultCharset()));
		String buffer;
		StringBuilder sb = new StringBuilder();
		
		//将三个标签放在北区
		JPanel p4 = new JPanel();
		p4.setLayout(new BorderLayout());
		p4.add((new JLabel("代价动因",JLabel.CENTER)),BorderLayout.WEST);
		p4.add(new JLabel("潜在风险等级",JLabel.CENTER),BorderLayout.CENTER);
		p4.add(new JLabel("COTS赋值（CE）",JLabel.CENTER),BorderLayout.EAST);
		add(p4,BorderLayout.NORTH);
		

		//读文件
		while((buffer = br.readLine()) != null){
			DriverInfo driverInfo = new DriverInfo();
			driverInfo.setDriver(buffer);
			
			driverList.add(driverInfo);
		}
		
		//展示信息和界面
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(driverList.size(), 1));
		
        JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(driverList.size(), 1));
		
		JPanel p5= new JPanel();
		p5.setLayout(new GridLayout(driverList.size(), 1));
		
//
		for (int i=0; i<driverList.size(); i++){
			JLabel panel = new JLabel((i + 1) + ". " + driverList.get(i).getDriver() + ": ");//i从零开始
			p2.add(panel);
			
			JComboBox combox = new JComboBox<>(Rate);
			p3.add(combox);
			comboxList.add(combox);
			
			JTextField textField = new JTextField(5);
			p5.add(textField);
			textfieldList.add(textField);
		}
		
		

		//设置滚动条
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());		//Jpanel默认没有设置布局样式 必须手动设置
		pane.add(p2,BorderLayout.WEST);
		pane.add(p3,BorderLayout.CENTER);
		pane.add(p5,BorderLayout.EAST);
		pane.add(p4,BorderLayout.NORTH);
		pane.add(p1,BorderLayout.SOUTH);
	    JScrollPane jslp = new JScrollPane(pane);
	    jslp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//总是垂直显示滚动条
	    jslp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
	    add(jslp);
	    
	    for (int i=0; i<comboxList.size(); i++){
			JComboBox comboBox = comboxList.get(i);//拿到第i个对象。
			DriverInfo driverInfo = driverList.get(i);
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("current02 risk index = " + driverInfo.getRiskIndex());
					
					System.out.println(comboBox.getSelectedItem());
					System.out.println(comboBox.getSelectedIndex());
					
					driverInfo.setRiskIndex(comboBox.getSelectedIndex());//给对象设值
				}
			});
		}
		
		//保存事件
		bt1.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e){
	        	   JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("保存代价动因参数");
					chooser.setMultiSelectionEnabled(false);
					chooser.showSaveDialog(null);
					chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );
					File file = chooser.getSelectedFile();
					Tools.currentDriverInfoFile = file;
					
					if (flag == JFileChooser.APPROVE_OPTION) {
						// 获得该文件

						System.out.println("open file" + file.getName());
					}

					String filename;
					String filepath;
					java.io.File file2 = null;
					// 如果按下确定按钮，则获得该文件。
					if (flag == JFileChooser.APPROVE_OPTION) {

						filename = chooser.getName(file);
						System.out.println("filename = " + filename);

					}

					if (chooser.getSelectedFile() == null) {
						System.out.println("请保存文件");
					} else {
//						 Tools.currentDriverFileName = file.getAbsolutePath();

					}
	        	   //1. 读取所有的cos值
	        	   for (int i=0; i<textfieldList.size(); i++){
		       			JTextField textField = textfieldList.get(i);
		       			DriverInfo driverInfo = driverList.get(i);
		       			
		       			String str = textField.getText().toString();
		       			if (str == null || str.equals("")){
//		       				break;
		       			}else{
		       				
		       				driverInfo.setCosValue(Float.valueOf(str));
		       			}
		       			
		       		    driverInfo.setRiskIndex(comboxList.get(i).getSelectedIndex());
	       			}	
	        	   
//	        	   java.io.File file2= new java.io.File("driverInfo.txt");
	        	   
	        	   try {
					BufferedWriter bw = new BufferedWriter(
							   new OutputStreamWriter(new FileOutputStream(file)));
					
					for (int i=0; i<driverList.size(); i++){
		        		   System.out.println(driverList.get(i).getDriver());
		        		   System.out.println(driverList.get(i).getRiskIndex() + " " + driverList.get(i).getRisk());//有问题未显示
		        		   System.out.println(driverList.get(i).getCosValue());
		        		   
		        		   String str = driverList.get(i).getDriver() + " "
		        				   + driverList.get(i).getRiskIndex() + " " 
		        				   + driverList.get(i).getCosValue() + "\n";
		        		   System.out.println(str);
		        		   bw.write(str);
		        		   
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
		
		  bt2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					  JFileChooser chooser = new JFileChooser(); 
		       	   chooser.setDialogTitle("加载代价动因参数");//
		       	   chooser.setMultiSelectionEnabled(true);
		       	chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );
		       	   chooser.showOpenDialog(null);
		       	   File file = chooser.getSelectedFile(); 
		       	   Tools.currentDriverInfoFile = file;
		       	   System.out.println("file = " + file.getAbsolutePath());
		       	   
//		       	   Tools.currentDriverFileName = file.getAbsolutePath();
		       	   
		       	   /*driverList = new ArrayList<>();
		       	   comboxList = new ArrayList<>();
		       	   textfieldList.clear();*/
		       	   
		       	driverList = new ArrayList<>();
		       	   
		       	try{
					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									new FileInputStream(file)));//加载所需要的txt文件
					String buffer;//
					StringBuilder sb = new StringBuilder();
					while((buffer = br.readLine()) != null){
//						sb.append(buffer + "\n");// TODO Auto-generated method
						String[] str = buffer.split(" ");
						
						DriverInfo driverInfo = new DriverInfo();
						driverInfo.setDriver(str[0]);
						driverInfo.setRiskIndex(Integer.valueOf(str[1]));
						driverInfo.setCosValue(Float.valueOf(str[2]));
//						comboxList.get(index)
						driverList.add(driverInfo);
					}
					
					System.out.println("driverList.size() = " + driverList.size());
					System.out.println("comboxList.size() = " + comboxList.size());
					
					for (int i=0; i<driverList.size(); i++){
						comboxList.get(i).setSelectedIndex(driverList.get(i).getRiskIndex());
						textfieldList.get(i).setText(String.valueOf(driverList.get(i).getCosValue()));
					}
//					System.out.println(sb.toString());
//					textArea.setText(sb.toString());
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				}
			});
	        setTitle("代价动因参数设置");
//			setSize(500, 60*driverList.size());
	    	setSize(550, 635);
			setLocationRelativeTo(null);
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
	
	}
	
}
