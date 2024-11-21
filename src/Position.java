public class Position {
	private int line;
	private int column;
	private String piece; //white, black or null
	
	Position(int l, int c){
		this.line = l;
		this.column = c;
	}
	
	public String toString() {
		String text = "";
		return(text + line +","+column);
	}
	
	void setPiece(String name) {
		piece = name;
	}
	
	String piece() {
		return piece;
	}
	
	
	boolean validPlay(int l,int c){
		if(l == column && c == line)
			return true;
		else if(l == line-1 && c == column -1)
			return true;
		return false;
	}
	
}
