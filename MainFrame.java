package CClient;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.table.*;

public class MainFrame extends JFrame implements MouseListener {

	JPanel mainPanel = new JPanel(new BorderLayout()); //전체 포괄
	JLabel CommunityTitle = new JLabel("도란도란 커뮤니티");
		JPanel localSetPanel= new JPanel(new BorderLayout()); //하단 포괄
		//---------
			JPanel leftPanel = new JPanel(new BorderLayout()); //하단 왼쪽 포괄
				JPanel module_bp = new JPanel(); //메뉴버튼 판넬
					JButton module_popular_jb = new JButton("인기");
					JButton module_free_jb = new JButton("자유");
					JButton module_notice_jb = new JButton("공지");
					//
				CardLayout moduleCard = new CardLayout(); //하단 왼쪽 모듈 판넬
				JPanel modulePanel = new JPanel();
					JPanel default_np = new JPanel(); //기본 게시판 판넬(인기 및 공지)
						//기본 게시판은 인기 및 공지 테이블 운용
					JPanel popular_np = new JPanel(new FlowLayout(FlowLayout.CENTER)); //인기 게시판 판넬
						JLabel pop_lb = new JLabel("인기");
						DefaultTableModel pop_model;  
						JTable pop_tb;
						String[] columPop = {"Title","contents","tag","BN","UN","views"};
						Vector<String> vectorPop = new Vector<String>();
						JScrollPane pop_sp = new JScrollPane();
						//
					JPanel free_np = new JPanel(); //자유 게시판 판넬
						JLabel free_lb = new JLabel("자유");
						DefaultTableModel free_model = new DefaultTableModel();
						JTable free_tb = new JTable(free_model);
						String[] columFree = {"Title","contents","tag","BN","UN","views"};
						Vector<String> vectorFree = new Vector<String>();
						JScrollPane free_sp = new JScrollPane();
						//
					JPanel notice_np = new JPanel(); //공지 게시판 판넬
						JLabel notice_lb = new JLabel("공지");
						DefaultTableModel notice_model = new DefaultTableModel();
						JTable notice_tb = new JTable(notice_model);
						String[] columNotice = {"Title","contents","tag","BN","UN","views"};
						Vector<String> vectorNotice = new Vector<String>();
						JScrollPane notice_sp = new JScrollPane();
						//
					JPanel create_np = new JPanel(); //게시글 작성 판넬
						JTextField create_title_tf = new JTextField();
						JTextField create_content_tf = new JTextField();
						JTextField create_teg_tf = new JTextField();
						String[] create_kinds = {"자유","공지"};
						JComboBox<String> create_combo = new JComboBox<String>(create_kinds);
						JButton create_ok_jb = new JButton("확인_c");
						JButton create_cancel_jb = new JButton("취소_c");
						//
					JPanel view_np = new JPanel(); //게시글 보기 판넬
						JLabel view_title_lb = new JLabel();
						JLabel view_content_lb = new JLabel();
						JLabel view_teg_lb = new JLabel();
						JLabel view_comment_lb = new JLabel();
						DefaultTableModel comment_model = new DefaultTableModel();
						JTable comment_tb = new JTable(notice_model);
						String[] columComment = {"comment","commentNumber","userNumber","boardNumber"};
						Vector<String> vectorComment = new Vector<String>();
						JScrollPane comment_sp = new JScrollPane();
						JTextField comment_tf = new JTextField();
						JButton comment_bt = new JButton("전송");
						
						//
		//---------
			JPanel rightPanel = new JPanel(); //하단 오른쪽 포괄
				JPanel inforPanel = new JPanel(); //사용자 정보 판넬
					JLabel info_userNick_lb = new JLabel("Nickname");
					JLabel info_permission_lb = new JLabel("권한");
					JLabel info_permission = new JLabel();
					JLabel info_creCount_lb = new JLabel("글쓴 횟수");
					JLabel info_creCount = new JLabel();
					JLabel info_commCount_lb = new JLabel("댓글 횟수");
					JLabel info_commCount = new JLabel();
					JButton info_mypage_jb = new JButton("마이페이지");
					JButton info_create_jb = new JButton("글쓰기"); 
					//
				JPanel groupPanel = new JPanel(); //소그룹 판넬
					JLabel group_lb = new JLabel("소그룹");
					DefaultTableModel group_model = new DefaultTableModel();
					JTable group_tb = new JTable(group_model);
					//
				JPanel userPanel = new JPanel(); //접속 유저 판넬
					JLabel user_lb = new JLabel("접속된 유저");
					DefaultTableModel user_model = new DefaultTableModel();
					JTable user_tb = new JTable(user_model);
					//
					
	int check; //pageChecker
	String glo_bn;
	mypageFrame my;
    Operator o;
	MainFrame(Operator _o){
		o = _o;
		MyActionListener ml = new MyActionListener();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("도란도란 커뮤니티");
		//CommunityTitle
		//mainPanel, localSetPanel, leftPanel, rightPanel
		CommunityTitle.setPreferredSize(new Dimension(800, 80));
		CommunityTitle.setOpaque(true);
		CommunityTitle.setBackground(new Color(201, 235, 242));
		CommunityTitle.setFont(new Font("Serif", Font.BOLD, 30));
		CommunityTitle.setForeground(Color.black);
		CommunityTitle.setHorizontalAlignment(JLabel.CENTER);
		leftPanel.setPreferredSize(new Dimension(600, mainPanel.getHeight()-80));
		rightPanel.setPreferredSize(new Dimension(200, mainPanel.getHeight()-80));
		setContentPane(mainPanel);
		// 동 East, 서 West, 남 South, 북 North
		mainPanel.add(CommunityTitle, BorderLayout.NORTH);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		module_bp.setPreferredSize(new Dimension(590,33));
		module_bp.setOpaque(true);
		module_bp.setBackground(new Color(128, 164, 196));
		module_bp.add(module_popular_jb);
		module_bp.add(module_free_jb);
		module_bp.add(module_notice_jb);
		module_popular_jb.setPreferredSize(new Dimension(160,30));
		module_free_jb.setPreferredSize(new Dimension(160,30));
		module_notice_jb.setPreferredSize(new Dimension(160,30));
		leftPanel.add(module_bp, BorderLayout.NORTH);
		
		modulePanel.setLayout(moduleCard);
		modulePanel.add("default", default_np);
		modulePanel.add("popular", popular_np);
		modulePanel.add("free", free_np);
		modulePanel.add("notice", notice_np);
		modulePanel.add("create", create_np);
		modulePanel.add("view", view_np);
		leftPanel.add(modulePanel, BorderLayout.CENTER);
		moduleCard.show(modulePanel, "popular");
		
		//popular_np set
		pop_lb.setPreferredSize(new Dimension(600, 25));
		pop_lb.setHorizontalAlignment(JLabel.CENTER);
		pop_lb.setOpaque(true);
		pop_lb.setBackground(new Color(128, 164, 196));
		pop_lb.setFont(new Font("Serif", Font.BOLD, 13));
		pop_lb.setForeground(Color.white);
		for(int i = 0; i<columPop.length; i++) {
			vectorPop.addElement(columPop[i]);
		}
		pop_model = new DefaultTableModel(vectorPop, 0);
		pop_tb = new JTable(pop_model);
		pop_tb.getColumnModel().getColumn(0).setPreferredWidth(120);
		pop_tb.getColumnModel().getColumn(1).setPreferredWidth(200);
		pop_tb.getColumnModel().getColumn(2).setPreferredWidth(50);
		pop_tb.getColumnModel().getColumn(3).setPreferredWidth(1);
		pop_tb.getColumnModel().getColumn(4).setPreferredWidth(1);
		pop_tb.getColumnModel().getColumn(5).setPreferredWidth(30);
		pop_tb.setRowHeight(30);
		pop_sp.setViewportView(pop_tb);
		pop_sp.setPreferredSize(new Dimension(500, 600));
		popular_np.add(pop_lb);
		popular_np.add(pop_sp);
	
		
		//free_np set
		free_lb.setPreferredSize(new Dimension(600, 25));
		free_lb.setHorizontalAlignment(JLabel.CENTER);
		free_lb.setOpaque(true);
		free_lb.setBackground(new Color(128, 164, 196));
		free_lb.setFont(new Font("Serif", Font.BOLD, 13));
		free_lb.setForeground(Color.white);
		free_lb.setPreferredSize(new Dimension(600, 25));
		for(int i = 0; i<columFree.length; i++) {
			vectorFree.addElement(columFree[i]);
		}
		free_model = new DefaultTableModel(vectorFree, 0);
		free_tb = new JTable(free_model);
		free_tb.getColumnModel().getColumn(0).setPreferredWidth(120);
		free_tb.getColumnModel().getColumn(1).setPreferredWidth(200);
		free_tb.getColumnModel().getColumn(2).setPreferredWidth(50);
		free_tb.getColumnModel().getColumn(3).setPreferredWidth(1);
		free_tb.getColumnModel().getColumn(4).setPreferredWidth(1);
		free_tb.getColumnModel().getColumn(5).setPreferredWidth(30);
		free_tb.setRowHeight(30);
		free_sp.setViewportView(free_tb);
		free_sp.setPreferredSize(new Dimension(500, 600));
		free_np.add(free_lb);
		free_np.add(free_sp);
		
		//notice_np set
		notice_lb.setPreferredSize(new Dimension(600, 25));
		notice_lb.setHorizontalAlignment(JLabel.CENTER);
		notice_lb.setOpaque(true);
		notice_lb.setBackground(new Color(128, 164, 196));
		notice_lb.setFont(new Font("Serif", Font.BOLD, 13));
		notice_lb.setForeground(Color.white);
		notice_lb.setPreferredSize(new Dimension(600, 25));
		for(int i = 0; i<columNotice.length; i++) {
			vectorNotice.addElement(columNotice[i]);
		}
		notice_model = new DefaultTableModel(vectorNotice, 0);
		notice_tb = new JTable(notice_model);
		notice_tb.getColumnModel().getColumn(0).setPreferredWidth(120);
		notice_tb.getColumnModel().getColumn(1).setPreferredWidth(200);
		notice_tb.getColumnModel().getColumn(2).setPreferredWidth(50);
		notice_tb.getColumnModel().getColumn(3).setPreferredWidth(1);
		notice_tb.getColumnModel().getColumn(4).setPreferredWidth(1);
		notice_tb.getColumnModel().getColumn(5).setPreferredWidth(30);
		notice_tb.setRowHeight(30);
		notice_sp.setViewportView(notice_tb);
		notice_sp.setPreferredSize(new Dimension(500, 600));
		notice_np.add(notice_lb);
		notice_np.add(notice_sp);
		
		//create_np set
		create_title_tf.setPreferredSize(new Dimension(350, 30));
		create_combo.setPreferredSize(new Dimension(150, 25));
		create_content_tf.setPreferredSize(new Dimension(550, 300));
		create_teg_tf.setPreferredSize(new Dimension(550, 30));
		create_ok_jb.setPreferredSize(new Dimension(200, 30));
		create_cancel_jb.setPreferredSize(new Dimension(200, 30));
		create_np.add(create_title_tf);
		create_np.add(create_combo);
		create_np.add(create_content_tf);
		create_np.add(create_teg_tf);
		create_np.add(create_ok_jb);
		create_np.add(create_cancel_jb);
		
		//view_np set
		//view_lb.setPreferredSize(new Dimension(600, 25));
		for(int i = 0; i<columComment.length; i++) {
			vectorComment.addElement(columComment[i]);
		}
		comment_model = new DefaultTableModel(vectorComment, 0);
		comment_tb = new JTable(comment_model);
		comment_tb.getColumnModel().getColumn(0).setPreferredWidth(400);
		comment_tb.getColumnModel().getColumn(1).setPreferredWidth(10);
		comment_tb.getColumnModel().getColumn(2).setPreferredWidth(10);
		comment_tb.getColumnModel().getColumn(3).setPreferredWidth(10);
		
		view_title_lb.setPreferredSize(new Dimension(550, 30));
		view_content_lb.setPreferredSize(new Dimension(550, 300));
		view_teg_lb.setPreferredSize(new Dimension(550, 30));
		comment_tf.setPreferredSize(new Dimension(400, 30));
		comment_bt.setPreferredSize(new Dimension(100, 30));
		
		view_title_lb.setOpaque(true);
		view_title_lb.setBackground(Color.white);
		view_title_lb.setFont(new Font("Serif", Font.BOLD, 13));
		
		view_content_lb.setOpaque(true);
		view_content_lb.setBackground(Color.white);
		view_content_lb.setFont(new Font("Serif", Font.BOLD, 13));
		
		view_teg_lb.setOpaque(true);
		view_teg_lb.setBackground(Color.white);
		view_teg_lb.setFont(new Font("Serif", Font.BOLD, 13));
		
		comment_sp.setViewportView(comment_tb);
		comment_sp.setPreferredSize(new Dimension(500, 220));
		view_np.add(view_title_lb);
		view_np.add(view_content_lb);
		view_np.add(view_teg_lb);
		view_np.add(comment_sp);
		view_np.add(comment_tf);
		view_np.add(comment_bt);
		
		
		
		
		module_popular_jb.addActionListener(ml);
		module_free_jb.addActionListener(ml);
		module_notice_jb.addActionListener(ml);
		
		info_mypage_jb.addActionListener(ml);
		info_create_jb.addActionListener(ml);
		create_ok_jb.addActionListener(ml);
		create_cancel_jb.addActionListener(ml);
		
		
		comment_bt.addActionListener(ml);
		
		pop_tb.addMouseListener(this);
		free_tb.addMouseListener(this);
		notice_tb.addMouseListener(this);
		
		inforPanel.setPreferredSize(new Dimension(200, 256));
		groupPanel.setPreferredSize(new Dimension(180, 256));
		userPanel.setPreferredSize(new Dimension(180, 256));
		
		
		//inforPanel set  //height 256~7
		info_userNick_lb.setPreferredSize(new Dimension(160,50));
		info_permission_lb.setPreferredSize(new Dimension(80,20));
		info_permission.setPreferredSize(new Dimension(80,20));
		info_creCount_lb.setPreferredSize(new Dimension(80,20));
		info_creCount.setPreferredSize(new Dimension(80,20));
		info_commCount_lb.setPreferredSize(new Dimension(80,20));
		info_commCount.setPreferredSize(new Dimension(80,20));
		info_mypage_jb.setPreferredSize(new Dimension(160,30));
		info_create_jb.setPreferredSize(new Dimension(160,30));
		inforPanel.add(info_userNick_lb);
		inforPanel.add(info_permission_lb);
		inforPanel.add(info_permission);
		inforPanel.add(info_creCount_lb);
		inforPanel.add(info_creCount);
		inforPanel.add(info_commCount_lb);
		inforPanel.add(info_commCount);
		inforPanel.add(info_mypage_jb);
		inforPanel.add(info_create_jb);
		
		inforPanel.setOpaque(true);
		inforPanel.setBackground(new Color(128, 164, 196));

		
		//groupPanel set
		
		//userPanel set 

		
		rightPanel.setOpaque(true);
		rightPanel.setBackground(new Color(128, 164, 196));
		
		
		rightPanel.add(inforPanel);
		//rightPanel.add(groupPanel);
		//rightPanel.add(userPanel); 
		
		
		
		
		setResizable(false);
		setSize(800,830);
		setVisible(false);
		
	}
	
	class MyActionListener implements ActionListener  {
		  //이벤트를 발생시킨 컴포넌트(소스)
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource();
			if (b.getText().equals("인기")) {	
				moduleCard.show(modulePanel, "popular");
				pageChecker("인기");
				comment_model.setNumRows(0);
				o.sc.popSelect();
			}
			else if(b.getText().equals("자유")) {
				moduleCard.show(modulePanel, "free");
				pageChecker("자유");
				comment_model.setNumRows(0);
				o.sc.freeSelect();
			}
			else if(b.getText().equals("공지")) {
				moduleCard.show(modulePanel, "notice");
				pageChecker("공지");
				comment_model.setNumRows(0);
				o.sc.noticeSelect();
			}
			else if(b.getText().equals("마이페이지")) {
				my = new mypageFrame(o);
			}
			else if(b.getText().equals("글쓰기")) {
				comment_model.setNumRows(0);
				moduleCard.show(modulePanel, "create");
			}
			else if(b.getText().equals("확인_c")) {
				String _title = create_title_tf.getText();
				String _content = create_content_tf.getText();
				String _teg = create_teg_tf.getText();
				String _kinds = create_combo.getSelectedItem().toString();
				o.sc.createInsert(_title, _kinds, _content, _teg);
				create_title_tf.setText("");
				create_content_tf.setText("");
				create_teg_tf.setText("");
				create_combo.setSelectedIndex(0);
				moduleCard.show(modulePanel, "popular");
				o.sc.popSelect();
			}
			else if(b.getText().equals("취소_c")) {
				create_title_tf.setText("");
				create_content_tf.setText("");
				create_teg_tf.setText("");
				create_combo.setSelectedIndex(0);
				moduleCard.show(modulePanel, "popular");
				o.sc.popSelect();
			}
			else if(b.getText().equals("전송")) { //comments 전송
				// "comment","commentNumber","userNumber","boardNumber"
				String _comment = comment_tf.getText();
				String _un = o.sc.Nowuser.userNumber;
				String _bn = glo_bn;
				o.sc.commentInsert(_comment, _un, _bn);
				comment_tf.setText("");
				o.sc.commentSelect(glo_bn);
			}
		}
	}
	
	void pageChecker(String page) {
		if(page.equals("인기")) {check = 1;}
		else if(page.equals("자유")) {check = 2;}
		else if(page.equals("공지")) {check = 3;}
		  //1 pop, 2 free, 3 notice
	}
	
	 @Override
	 public void mouseClicked(MouseEvent e) {
		 if(check == 1) {
			 int row = pop_tb.getSelectedRow();
			 TableModel data = pop_tb.getModel();
			 String title = (String)data.getValueAt(row, 0);
			 String content = (String)data.getValueAt(row, 1);
			 String tag = (String)data.getValueAt(row, 2);
			 glo_bn = (String)data.getValueAt(row, 3);
			 String un = (String)data.getValueAt(row, 4);
			 String views = (String)data.getValueAt(row, 5);
			 
			 view_title_lb.setText(title);
			 view_content_lb.setText(content);
			 view_teg_lb.setText(tag);
			 moduleCard.show(modulePanel, "view");
			 o.sc.viewCount(glo_bn, views, check);
			 o.sc.commentSelect(glo_bn);
			 
			 //boardTable bt = new boardTable(title, content, tag, bn, un, views); 
		 }
		 else if(check == 2) {
			 int row = free_tb.getSelectedRow();
			 TableModel data = free_tb.getModel();
			 String title = (String)data.getValueAt(row, 0);
			 String content = (String)data.getValueAt(row, 1);
			 String tag = (String)data.getValueAt(row, 2);
			 glo_bn = (String)data.getValueAt(row, 3);
			 String un = (String)data.getValueAt(row, 4);
			 String views = (String)data.getValueAt(row, 5);
			 
			 view_title_lb.setText(title);
			 view_content_lb.setText(content);
			 view_teg_lb.setText(tag);
			 moduleCard.show(modulePanel, "view");
			 o.sc.viewCount(glo_bn, views, check);
			 o.sc.commentSelect(glo_bn);
		 }
		 else if(check == 3) {
			 int row = notice_tb.getSelectedRow();
			 TableModel data = notice_tb.getModel();
			 String title = (String)data.getValueAt(row, 0);
			 String content = (String)data.getValueAt(row, 1);
			 String tag = (String)data.getValueAt(row, 2);
			 glo_bn = (String)data.getValueAt(row, 3);
			 String un = (String)data.getValueAt(row, 4);
			 String views = (String)data.getValueAt(row, 5);
			 
			 view_title_lb.setText(title);
			 view_content_lb.setText(content);
			 view_teg_lb.setText(tag);
			 moduleCard.show(modulePanel, "view");
			 o.sc.viewCount(glo_bn, views, check);
			 o.sc.commentSelect(glo_bn);
		 }
	  }
	 @Override
	 public void mousePressed(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
	 }

	 @Override
	 public void mouseReleased(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
	 }

	 @Override
	 public void mouseEntered(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
	 }

	 @Override
	 public void mouseExited(MouseEvent e) {
	  // TODO Auto-generated method stub
	  
	 }
}
