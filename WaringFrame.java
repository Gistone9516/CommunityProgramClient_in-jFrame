package CClient;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class WaringFrame extends JFrame {
	JPanel mainPanel = new JPanel();
	JLabel waring_lb = new JLabel("");
	JButton close_bt = new JButton("확인");
	MyActionListener ml = new MyActionListener();
	
	WaringFrame(String tag){
		setTitle("경고창");
		if(tag.equals("PWerror")) {
			waring_lb.setText("비밀번호가 틀립니다");
		}else if(tag.equals("IDnonExist")) {
			waring_lb.setText("아이디가 존재하지 않습니다");
		}else if(tag.equals("PWnonEqual")) {
			waring_lb.setText("비밀번호가 일치하지 않습니다");
		}
		
		waring_lb.setPreferredSize(new Dimension(200, 70));
		close_bt.setPreferredSize(new Dimension(120, 30));
		setContentPane(mainPanel);
		mainPanel.add(waring_lb);
		mainPanel.add(close_bt);
		
		close_bt.addActionListener(ml);
		
		setResizable(false);
		setSize(300, 150);
		
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener  {
		  //이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource();
			if (b.getText().equals("확인")) {
				dispose();
			}	
		}

	}
}
