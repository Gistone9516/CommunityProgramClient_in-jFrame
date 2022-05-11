package CClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;


public class ServerConnector extends Thread{
	ArrayList <boardTable> ntArray = new ArrayList<boardTable>();
	boardTable nt;
	ArrayList <boardTable> ftArray = new ArrayList<boardTable>();
	boardTable ft;
	ArrayList <boardTable> ptArray = new ArrayList<boardTable>();
	boardTable pt;
	ArrayList <commentTable> ctArray = new ArrayList<commentTable>();
	commentTable ct;
	
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream=null;
	
	
	final String LOGIN_TAG = "LOGIN";
	final String JOIN_TAG = "JOIN";
	final String UPDATE_TAG = "UPDATE";
	final String INFO_TAG = "INFO";
	final String pop_DB_SELECT = "pop_SELECT";
	final String free_DB_SELECT = "free_SELECT";
	final String notice_DB_SELECT = "notice_SELECT";
	final String comment_DB_SELECT = "comment_SELECT";
	final String create_DB_INSERT = "create_INSERT";
	final String DB_ADD = "ADD";
	final String DB_DEL = "DEL";
	final String DB_IFSELECT = "IFSELECT";
	final String view_DB_COUNT = "view_COUNT";
	final String commnet_DB_INSERT = "comment_INSERT";
	
	String loginInfo = "";
	
	Operator o;
	user Nowuser;
	
	ServerConnector(Operator _o){
		o = _o;
		try {
			mySocket = new Socket("localhost", 20000);
			System.out.println("CLIENT LOG> 서버로 연결되었습니다.");
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream =  new DataInputStream(inStream);
			Thread.sleep(100);
		}catch(Exception e) {}
	}
	
	public void run() {
		while(true) {
			try {
				String msg = dataInStream.readUTF();
				
				StringTokenizer stk = new StringTokenizer(msg, "//");
				
				String tag = stk.nextToken();
				if(tag.equals(notice_DB_SELECT)) {
					String notice = stk.nextToken();
					noticeView(notice);
				}
				else if(tag.equals(free_DB_SELECT)) {
					String free = stk.nextToken();
					freeView(free);
				}
				else if(tag.equals(pop_DB_SELECT)) {
					String pop = stk.nextToken();
					popView(pop);
				}
				else if(tag.equals(comment_DB_SELECT)) {
					String comments = stk.nextToken();
					commentView(comments);
				}
				else if(tag.equals(INFO_TAG)) {
					String bc = stk.nextToken();
					String cc = stk.nextToken();
					InfoView(bc,cc);
				}
			}catch(Exception e) {
				System.out.println("Error" + e.getMessage());
			}
		}
	}
	
	int LoginCheck(String ID, String password) {
		int check = 1;
		String msg = LOGIN_TAG + "//" + ID + "//" + password;
		try {
		dataOutStream.writeUTF(msg);
		String reception = dataInStream.readUTF(); //Client thread state off
		if(reception.equals("PWerror")) {check = 2;}
		else if(reception.equals("IDnonExist")) {check = 0;}
		else {
			loginInfo = reception;
			StringTokenizer stk = new StringTokenizer(loginInfo, ".");
							//ID			//password		  //Nickname	   //name			//sex			 //age								//Area			//userNumber	//permission	
			Nowuser = new user(stk.nextToken(), stk.nextToken(), stk.nextToken(), stk.nextToken(), stk.nextToken(), Integer.parseInt(stk.nextToken()) ,stk.nextToken(),stk.nextToken(),stk.nextToken());
			} //loginInfo 현재 클라이언트 로그인 총 정보
		}catch(Exception e) {
			System.out.println("LoginCheck_Error" + e.getMessage());
		}
		return check; 
	}
	
	void JoinUser(String ID, String password, String Nickname, String name, String sex, String age, String Area) {
		String msg = JOIN_TAG + "//" + ID + "//" + password + "//" + Nickname + "//" + name + "//" + sex + "//" + age + "//" + Area;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("JoinUser_Error" + e.getMessage());
		}
	}
	
	void UpdateUser(String ID, String password, String Nickname, String name, String sex, String age, String Area, String userNumber) {
		String msg = UPDATE_TAG + "//" + ID + "//" + password + "//" + Nickname + "//" + name + "//" + sex + "//" + age + "//" + Area + "//" + userNumber;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("UpdateUser_Error" + e.getMessage());
		}
	}
	
	void InfoUser(String userNumber) {
		String msg = INFO_TAG + "//" + userNumber;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("InfoUser_Error" + e.getMessage());
		}
	}
	
	void popSelect() {
		try {
			dataOutStream.writeUTF(pop_DB_SELECT + "//");
			}catch(Exception e) {
				System.out.println("popSelcet_Error" + e.getMessage());
			}
	}
	
	void freeSelect() {
		try {
			dataOutStream.writeUTF(free_DB_SELECT + "//");
			}catch(Exception e) {
				System.out.println("freeSelcet_Error" + e.getMessage());
			}
	}
	
	void noticeSelect() {
		try {
			dataOutStream.writeUTF(notice_DB_SELECT + "//");
			}catch(Exception e) {
				System.out.println("noticeSelcet_Error" + e.getMessage());
			}
	}
	
	void commentSelect(String bn) {
		try {
			dataOutStream.writeUTF(comment_DB_SELECT + "//" + bn);
			}catch(Exception e) {
				System.out.println("noticeSelcet_Error" + e.getMessage());
			}
	}
	
	void popView(String msg) {
		ptArray.clear();
		StringTokenizer infos = new StringTokenizer(msg, "-");
		while(infos.hasMoreTokens()) {
			String next = infos.nextToken();
			StringTokenizer cell = new StringTokenizer(next, ".");
			
			String title = cell.nextToken();
			String contents = cell.nextToken();
			String tag = cell.nextToken();
			String boradNumber = cell.nextToken();
			String userNumber = cell.nextToken();
			String views =  cell.nextToken();
			//title contents tag boradNumber userNumber views
			ptArray.add(new boardTable(title, contents, tag, boradNumber, userNumber, views));
			 
		}
		o.mf.pop_model.setNumRows(0);
		for(boardTable data : ptArray) {
			Vector<String> v = new Vector<String>();
			v.addElement(data.title);
			v.addElement(data.contents);
			v.addElement(data.tag);
			v.addElement(data.boardNumber);
			v.addElement(data.userNumber);
			v.addElement(data.views);
			o.mf.pop_model.addRow(v);
		}
	}
	
	void freeView(String msg) {
		ftArray.clear();
		StringTokenizer infos = new StringTokenizer(msg, "-");
		while(infos.hasMoreTokens()) {
			String next = infos.nextToken();
			StringTokenizer cell = new StringTokenizer(next, ".");
			
			String title = cell.nextToken();
			String contents = cell.nextToken();
			String tag = cell.nextToken();
			String boradNumber = cell.nextToken();
			String userNumber = cell.nextToken();
			String views =  cell.nextToken();
			//title contents tag boradNumber userNumber views
			ftArray.add(new boardTable(title, contents, tag, boradNumber, userNumber, views));
			 
		}
		o.mf.free_model.setNumRows(0);
		for(boardTable data : ftArray) {
			Vector<String> v = new Vector<String>();
			v.addElement(data.title);
			v.addElement(data.contents);
			v.addElement(data.tag);
			v.addElement(data.boardNumber);
			v.addElement(data.userNumber);
			v.addElement(data.views);
			o.mf.free_model.addRow(v);
		}
	}
	
	void noticeView(String msg) {
		ntArray.clear();
		StringTokenizer infos = new StringTokenizer(msg, "-");
		while(infos.hasMoreTokens()) {
			String next = infos.nextToken();
			StringTokenizer cell = new StringTokenizer(next, ".");
			
			String title = cell.nextToken();
			String contents = cell.nextToken();
			String tag = cell.nextToken();
			String boradNumber = cell.nextToken();
			String userNumber = cell.nextToken();
			String views =  cell.nextToken();
			//title contents tag boradNumber userNumber views
			ntArray.add(new boardTable(title, contents, tag, boradNumber, userNumber, views));
			 
		}
		o.mf.notice_model.setNumRows(0);
		for(boardTable data : ntArray) {
			Vector<String> v = new Vector<String>();
			v.addElement(data.title);
			v.addElement(data.contents);
			v.addElement(data.tag);
			v.addElement(data.boardNumber);
			v.addElement(data.userNumber);
			v.addElement(data.views);
			o.mf.notice_model.addRow(v);
		}
	}
	
	void commentView (String msg) {
		ctArray.clear();
		StringTokenizer infos = new StringTokenizer(msg, "-");
		while(infos.hasMoreTokens()) {
			String next = infos.nextToken();
			StringTokenizer cell = new StringTokenizer(next, ".");
			
			String comment = cell.nextToken();
			String commentNumber = cell.nextToken();
			String userNumber = cell.nextToken();
			String boardNumber = cell.nextToken();
			
			//title contents tag boradNumber userNumber views
			ctArray.add(new commentTable(comment, commentNumber, userNumber, boardNumber));
			 
		}
		o.mf.comment_model.setNumRows(0);
		for(commentTable data : ctArray) {
			Vector<String> v = new Vector<String>();
			v.addElement(data.comment);
			v.addElement(data.commentNumber);
			v.addElement(data.userNumber);
			v.addElement(data.boardNumber);
			o.mf.comment_model.addRow(v);
		}
	}
	
	void InfoView(String bc, String cc) {
		o.mf.info_creCount.setText(bc);
		o.mf.info_commCount.setText(cc);
	}
	
	void createInsert(String title, String kinds, String content, String tag) {
		String msg = create_DB_INSERT + "//" + title + "//" + kinds + "//" + content + "//" + tag + "//" + Nowuser.userNumber;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("createInsert _Error" + e.getMessage());
		}
	}
	
	void viewCount(String boardNumber, String view, int check) {
		String msg = view_DB_COUNT + "//" + boardNumber + "//" + view + "//" + check;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("viewCount _Error" + e.getMessage());
		}
	}
	
	void commentInsert(String comment, String un, String bn) {
		String msg = commnet_DB_INSERT + "//" + comment + "//" + un + "//" + bn;
		try {
			dataOutStream.writeUTF(msg);
		}catch(Exception e) {
			System.out.println("viewCount _Error" + e.getMessage());
		}
	}
	
}
