package CClient;

public class boardTable {
	String title;
	String contents;
	String tag;
	String boardNumber;
	String userNumber;
	String views;
	//title contents tag boradNumber userNumber views
	boardTable(String _title, String _contents, String _tag, String _boradNumber, String _userNumber, String _views) {
		this.title = _title;
		this.contents = _contents;
		this.tag = _tag;
		this.boardNumber = _boradNumber;
		this.userNumber = _userNumber;
		this.views = _views;
	}
}
