package 评估程序;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.synth.SynthOptionPaneUI;

public class SecondFrame extends JFrame {
	protected static final boolean False = false;

	private JButton bt1, bt2, bt3,bt4;

	// private String[] Drivers = { "MS", "CV", "IA", "SDC", "SIZE", "CPM",
	// "AUT", "SA", "CE", "KS", "FR", "PM", "MP",
	// "RP", "AC", "VDD", "others"};
	// //JComboBox jcb=new JComboBox(Drivers);

	private JTextField textField = new JTextField(10);
//	private JTextArea textArea = new JTextArea(27, 33);
	JList jls = new JList();
	private int flag;

	List<String> list = new ArrayList<>();

	public SecondFrame() {
		bt1 = new JButton("保存");
		bt2 = new JButton("加载");
		bt3 = new JButton("添加");
		bt4 = new JButton("删除");
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout());
		p1.add(bt1);
		p1.add(bt2);
		add(p1, BorderLayout.SOUTH);
		// bt1.setBounds(100,100,100,100);
		// add(jlst, BorderLayout.NORTH);
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout());
		p2.add(textField);
		p2.add(bt3);
		p2.add(bt4);
		// p2.setLayout(new BorderLayout());
		// p2.add(bt3,BorderLayout.EAST);
		// p2.add(jcb,BorderLayout.WEST);
		add(p2, BorderLayout.NORTH);
		// add(new JScrollPane(jcb),BorderLayout.NORTH);
		/*JPanel p3 = new JPanel();

		p3.add(jls);*/

		add(jls, BorderLayout.CENTER);

		bt3.addActionListener(new ActionListener() {
			// 添加事件
			@Override
			public void actionPerformed(ActionEvent e) {
				// 首先获得用户输入// TODO Auto-generated method stub
				String str = textField.getText();

				if (str == null || str.equals("")) {
					System.out.println("内容为空，无法添加");
					return;
				}
				// 将内容添加至list列表中

				System.out.println("str = " + str);
				list.add(str);

				// 字符处理函数
				StringBuilder sb = new StringBuilder();

				// 将list的内容输出到文本区域
				for (int i = 0; i < list.size(); i++) {
					sb.append(list.get(i) + "\n");

				}
//				Collections.as
//				JList jls = new JList(list.toArray());
				jls.setListData(list.toArray());
//				jls.setText(sb.toString());
				// 清空原有内容
				textField.setText(null);
			}

		});
		
		//删除按钮点击事件
		bt4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int indexs[] = jls.getSelectedIndices();//getSelectedIndex();
				
				for (int index: indexs){
					System.out.println("index = " + index);
//					list.remove(index);
				}
				list.removeAll(Arrays.asList(jls.getSelectedValues()));
				jls.setListData(list.toArray());
			}
		});
		// 点击的保存事件到文件中
		bt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				// java.io.File file2= new java.io.File("filename.txt");
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("保存代价动因文件");
				chooser.setMultiSelectionEnabled(False);
				chooser.showSaveDialog(null);
				chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );
				File file = chooser.getSelectedFile();
				Tools.currentDriverFile = file;
				
				if (flag == JFileChooser.APPROVE_OPTION) {
//					// 获得该文件
//
					System.out.println("open file" + file.getName());
				}
//
				String filename;
				String filepath;
				java.io.File file2 = null;
//				// 如果按下确定按钮，则获得该文件。
				if (flag == JFileChooser.APPROVE_OPTION) {

					filename = chooser.getName(file);
					System.out.println("filename = " + filename);

				}

				if (chooser.getSelectedFile() == null) {
					System.out.println("请保存文件");
				} else {
//					 Tools.currentDriverFileName = file.getAbsolutePath();

				}

				try {
					java.io.PrintWriter output = new java.io.PrintWriter(file);
					StringBuilder sb = new StringBuilder();
					// 输出到下面的文本区域
					for (int i = 0; i < list.size(); i++) {
						sb.append(list.get(i) + "\n");
					}
					System.out.println(sb.toString());
					output.print(sb.toString());
					output.flush();
					output.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});
		// 加载显示
		bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("选择代价动因");//
				chooser.setMultiSelectionEnabled(true);
//				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
//				chooser.setFileFilter(filter);//
				chooser.showOpenDialog(null);
				chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES );

				File file = chooser.getSelectedFile();
				System.out.println("file = " + file.getAbsolutePath());

				Tools.currentDriverFile = file;
//				Tools.currentDriverFileName = file.getAbsolutePath();
				// 将加载的文件显示到文本域中
				list = new ArrayList<>();
				jls.setListData(list.toArray());
				
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));// 加载所需要的txt文件
					String buffer;//
//					StringBuilder sb = new StringBuilder();
					while ((buffer = br.readLine()) != null) {
//						sb.append(buffer + "\n");// TODO Auto-generated method
						list.add(buffer);
					}
					
					jls.setListData(list.toArray());
					
//					System.out.println(sb.toString());
//					jls.setToolTipText(sb.toString());
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});
		setTitle("代价动因设置");
		setSize(550, 630);
		setLocationRelativeTo(null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
