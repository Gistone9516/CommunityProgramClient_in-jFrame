package CClient;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class JoinFrame extends JFrame{
	JPanel mainPanel = new JPanel(); //프레임 판넬 FlowLayout
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JButton enter = new JButton("Join");  //Join버튼
	JButton cancel = new JButton("Cancel");  //Cancel 버튼
	JButton back = new JButton("Back");  //Back 버튼
	JTextField typeId = new JTextField();  //ID 입력 파츠
	JPasswordField typePassword = new JPasswordField();  //PW 입력 파츠
	JPasswordField typePassword_check = new JPasswordField(); //PW 재입력 파츠
//	JTextField typeHint = new JTextField();
	JTextField typeNickname = new JTextField();
	JTextField typename = new JTextField();
	JTextField typeArea = new JTextField();
	
	JLabel id = new JLabel("I   D"); //ID 표시 라벨
	JLabel password = new JLabel("Password"); //PW 표시 라벨
	JLabel password_check = new JLabel("PW_Check"); //PW 재입력 표시 라벨
//	JLabel Hint = new JLabel("Hint");
	JLabel Nickname = new JLabel("Nickname");
	JLabel name = new JLabel("이름");
	JLabel sex = new JLabel("성별");
	JLabel age = new JLabel("나이");
	JLabel Area = new JLabel("지역");
	ServerConnector sc;
	Operator o;
	WaringFrame wf;
	
	String[] sex_s = {"남", "여"};
	JComboBox<String> sex_combo = new JComboBox<String>(sex_s);
	String[] age_s = {"14","15","16","17","18","19","20","21","22","23","24","25","26"};
	JComboBox<String> age_combo = new JComboBox<String>(age_s);
	
	public JoinFrame(Operator _o) {
		o = _o; //Operator 객체 공유
		sc = _o.sc; //MyConnector 객체 공유
		
		MyActionListener ml = new MyActionListener(); //액션리스너 객체 생성
//		MyActionListener_combo mc = new MyActionListener_combo(); //액션리스너 콤보 객체 생성
		setTitle("도란도란 커뮤니티 회원가입");
		//GUI 객체 크기 설정
		id.setPreferredSize(new Dimension(60, 30)); 
		typeId.setPreferredSize(new Dimension(270, 30));
		
		password.setPreferredSize(new Dimension(60, 30));
		typePassword.setPreferredSize(new Dimension(270, 30));
		
		password_check.setPreferredSize(new Dimension(60, 30));
		typePassword_check.setPreferredSize(new Dimension(270, 30));
		
//		Hint.setPreferredSize(new Dimension(70, 30));
//		typeHint.setPreferredSize(new Dimension(300, 30));
		
		Nickname.setPreferredSize(new Dimension(60, 30));
		typeNickname.setPreferredSize(new Dimension(270, 30));
		
		name.setPreferredSize(new Dimension(60, 30));
		typename.setPreferredSize(new Dimension(270, 30));
		
		sex.setPreferredSize(new Dimension(60, 30));
		sex_combo.setPreferredSize(new Dimension(270, 30));
		
		age.setPreferredSize(new Dimension(60, 30));
		age_combo.setPreferredSize(new Dimension(270, 30));
		
		Area.setPreferredSize(new Dimension(60, 30));
		typeArea.setPreferredSize(new Dimension(270, 30));
		
		enter.setPreferredSize(new Dimension(123, 30));
		cancel.setPreferredSize(new Dimension(123, 30)); 
		back.setPreferredSize(new Dimension(123,30));
		
		mainPanel.setPreferredSize(new Dimension(780,290));
		leftPanel.setPreferredSize(new Dimension(370,220));
		rightPanel.setPreferredSize(new Dimension(370,220));
		
		
		
		
		setContentPane(mainPanel);
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		//순서대로 프레임 판넬 삽입
		leftPanel.add(id); 
		leftPanel.add(typeId);
		leftPanel.add(password); 
		leftPanel.add(typePassword);
		leftPanel.add(password_check);
		leftPanel.add(typePassword_check);
//		leftPanel.add(Hint);
//		leftPanel.add(typeHint);
		rightPanel.add(Nickname);
		rightPanel.add(typeNickname);
		rightPanel.add(name);
		rightPanel.add(typename);
		rightPanel.add(sex);
		rightPanel.add(sex_combo);
		rightPanel.add(age);
		rightPanel.add(age_combo);
		rightPanel.add(Area);
		rightPanel.add(typeArea);
		
		mainPanel.add(enter);
		mainPanel.add(cancel);
		mainPanel.add(back);
		
		//액션리스너 지정
		enter.addActionListener(ml); 
		cancel.addActionListener(ml); 
		back.addActionListener(ml);
		
		setResizable(false); //창 크기 변경 불가
		setSize(780, 320); //창 크기 설정
		
		//로그인 창을 화면 중앙에 배치
  	    Dimension frameSize = this.getSize();   //현재 사용 중인 화면 프레임 사이즈 수집
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        //GUI 위치 설정

		
	}
	class MyActionListener implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource(); //버튼 문자열 수집
			if (b.getText().equals("Join")) { //버튼 문자열이 Join일 경우
				String pw = "";
				String pw_c = "";
				String id = typeId.getText();
				String nickname = typeNickname.getText();
				String name = typename.getText();
				String sex = sex_combo.getSelectedItem().toString();
				String age = age_combo.getSelectedItem().toString();
				String area = typeArea.getText();
				for(int i=0; i<typePassword.getPassword().length; i++) { //PW 문자열화
					pw =  pw + typePassword.getPassword()[i];
				}
				for(int i=0; i<typePassword_check.getPassword().length; i++) { //PW_check 문자열화
					pw_c =  pw_c + typePassword_check.getPassword()[i];
				}
				if(pw.equals(pw_c)) { //PW 및 PW_check 비교
					sc.JoinUser(id, pw, nickname, name, sex, age, area); //회원가입 정보 파일 저장 메소드
					o.lf.setVisible(true); //로그인 프레임 가시화
					typeId.setText(""); //ID 파츠 초기화
					typePassword.setText(""); //PW 파츠 초기화
					typePassword_check.setText(""); //PW_c 파츠 초기화
					typeNickname.setText("");
					typename.setText("");
					sex_combo.setSelectedIndex(0);
					age_combo.setSelectedIndex(0);
					typeArea.setText("");
					setVisible(false); //회원가입 프레임 숨김
				}
				else {
					wf = new WaringFrame("PWnonEqual");
				}
			}else if (b.getText().equals("Cancel")) { //버튼 문자열이 Cancel일 경우
				typeId.setText(""); //ID 파츠 초기화
				typePassword.setText(""); //PW 파츠 초기화
				typePassword_check.setText(""); //PW_c 파츠 초기화
				typeNickname.setText("");
				typename.setText("");
				sex_combo.setSelectedIndex(0);
				age_combo.setSelectedIndex(0);
				typeArea.setText("");
			}
			else if(b.getText().equals("Back")) { //버튼 문자열이 Back일 경우
				o.lf.setVisible(true); //로그인 프레임 가시화
				typeId.setText(""); //ID 파츠 초기화
				typePassword.setText(""); //PW 파츠 초기화
				typePassword_check.setText(""); //PW_c 파츠 초기화
				typeNickname.setText("");
				typename.setText("");
				sex_combo.setSelectedIndex(0);
				age_combo.setSelectedIndex(0);
				typeArea.setText("");
				setVisible(false); //회원가입 프레임 숨김
			}
		}
	}
}
