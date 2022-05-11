package CClient;


public class Operator {
	ServerConnector sc = null;
	LoginFrame lf = null;
	JoinFrame jf = null;
	MainFrame mf = null;

	
	public Operator() {

		sc = new ServerConnector(this);
		jf = new JoinFrame(this);
		lf = new LoginFrame(this);
		mf = new MainFrame(this);
	}
	
	public static void main(String[] args) {
		new Operator();
		
	}
}
