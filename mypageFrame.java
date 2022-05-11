package CClient;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class mypageFrame extends JFrame{
	JPanel mainPanel = new JPanel(); //프레임 판넬 FlowLayout
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JButton enter = new JButton("변경");  //Join버튼
	JButton back = new JButton("닫기");  //Back 버튼
	JLabel typeId = new JLabel();  //ID
	JPasswordField typePassword = new JPasswordField();  //PW 입력 파츠
	JPasswordField typePassword_check = new JPasswordField(); //PW 재입력 파츠
	JTextField typeNickname = new JTextField();
	JTextField typename = new JTextField();
	JLabel typesex = new JLabel();
	JLabel typeage = new JLabel();
	JTextField typeArea = new JTextField();
	JLabel userNumber = new JLabel();
	JLabel permissions = new JLabel();
	
	JLabel id = new JLabel("I   D"); //ID 표시 라벨
	JLabel password = new JLabel("Password"); //PW 표시 라벨
	JLabel password_check = new JLabel("PW_Check"); //PW 재입력 표시 라벨
	JLabel Nickname = new JLabel("Nickname");
	JLabel name = new JLabel("이름");
	JLabel sex = new JLabel("성별");
	JLabel age = new JLabel("나이");
	JLabel Area = new JLabel("지역");
	ServerConnector sc;
	Operator o;
	user u;
	WaringFrame wf;
	

	public mypageFrame(Operator _o) {
		o = _o; //Operator 객체 공유
		sc = _o.sc; //MyConnector 객체 공유
		u = sc.Nowuser;
		MyActionListener ml = new MyActionListener(); //액션리스너 객체 생성
		setTitle("도란도란 마이페이지");
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
		typesex.setPreferredSize(new Dimension(270, 30));
		
		age.setPreferredSize(new Dimension(60, 30));
		typeage.setPreferredSize(new Dimension(270, 30));
		
		Area.setPreferredSize(new Dimension(60, 30));
		typeArea.setPreferredSize(new Dimension(270, 30));
		
		enter.setPreferredSize(new Dimension(123, 30));
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
		typeId.setText(u.ID);
		leftPanel.add(password); 
		leftPanel.add(typePassword);
		leftPanel.add(password_check);
		leftPanel.add(typePassword_check);
		leftPanel.add(userNumber);
		userNumber.setText(u.userNumber);
		leftPanel.add(permissions);
		permissions.setText(u.permissions);
		rightPanel.add(Nickname);
		rightPanel.add(typeNickname);
		typeNickname.setText(u.Nickname);
		rightPanel.add(name);
		rightPanel.add(typename);
		typename.setText(u.name);
		rightPanel.add(sex);
		rightPanel.add(typesex);
		typesex.setText(u.sex);
		rightPanel.add(age);
		rightPanel.add(typeage);
		typeage.setText(Integer.toString(u.age)); 
		rightPanel.add(Area);
		rightPanel.add(typeArea);
		typeArea.setText(u.Area);
		
		
		mainPanel.add(enter);
		mainPanel.add(back);
		
		//액션리스너 지정
		enter.addActionListener(ml);  
		back.addActionListener(ml);
		
		setResizable(false); //창 크기 변경 불가
		setSize(780, 320); //창 크기 설정
		
		//로그인 창을 화면 중앙에 배치
  	    Dimension frameSize = this.getSize();   //현재 사용 중인 화면 프레임 사이즈 수집
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        //GUI 위치 설정
        
        setVisible(true);
		
	}
	class MyActionListener implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			JButton b =  (JButton)e.getSource(); //버튼 문자열 수집
			if (b.getText().equals("변경")) { //버튼 문자열이 Join일 경우
				String pw = "";
				String pw_c = "";
				String id = typeId.getText();
				String nickname = typeNickname.getText();
				String name = typename.getText();
				String sex = u.sex;
				String age = Integer.toString(u.age);
				String area = typeArea.getText();
				for(int i=0; i<typePassword.getPassword().length; i++) { //PW 문자열화
					pw =  pw + typePassword.getPassword()[i];
				}
				for(int i=0; i<typePassword_check.getPassword().length; i++) { //PW_check 문자열화
					pw_c =  pw_c + typePassword_check.getPassword()[i];
				}
				if(pw.equals(pw_c)) { //PW 및 PW_check 비교
					sc.UpdateUser(id, pw, nickname, name, sex, age, area, u.userNumber); 
					typeId.setText(""); //ID 파츠 초기화
					typePassword.setText(""); //PW 파츠 초기화
					typePassword_check.setText(""); //PW_c 파츠 초기화
					typeNickname.setText("");
					typename.setText("");
					typesex.setText("");
					typeage.setText("");
					typeArea.setText("");
					setVisible(false); //회원가입 프레임 숨김
				}
				else {
					//비밀번호가 일치하지 않음 알림창 띄우기
					wf = new WaringFrame("PWnonEqual");
				}
			}else if(b.getText().equals("닫기")) { //버튼 문자열이 Back일 경우
				typeId.setText(""); //ID 파츠 초기화
				typePassword.setText(""); //PW 파츠 초기화
				typePassword_check.setText(""); //PW_c 파츠 초기화
				typeNickname.setText("");
				typename.setText("");
				typesex.setText("");
				typeage.setText("");
				typeArea.setText("");
				setVisible(false); //회원가입 프레임 숨김
			}
		}
	}
}
