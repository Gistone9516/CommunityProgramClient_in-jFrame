package CClient;

public class user {
	String ID;
	String password;
	String Nickname;
	String name;
	String sex;
	int age;
	String Area;
	String userNumber;
	String permissions;
	
	user(String _ID, String _password, String _Nickname, String _name, String _sex, int _age, String _Area, String _userNumber, String _permissions) {
		this.ID = _ID;
		this.password = _password;
		this.Nickname = _Nickname;
		this.name = _name;
		this.sex = _sex;
		this.age = _age;
		this.Area = _Area;
		this.userNumber = _userNumber;
		this.permissions = _permissions;
	}
}
