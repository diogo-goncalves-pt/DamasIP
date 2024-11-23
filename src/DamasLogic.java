public class DamasLogic {
	
	Position[] pos;
	Position[] oneColorPiece;
	Position[] validPlays;
	private boolean isWhiteTurn = true;
	private int numberOfWhite;
	private int numberOfBlack;
	int length;
	boolean Error1 = true;
	
	//CONSTRUTORES
	
	DamasLogic(){
		pos = new Position[64];
		numberOfWhite = 12;
		length = 8;
	}
	DamasLogic(int length, int numberOfStones){
		pos = new Position[length*length];
		numberOfWhite = numberOfStones;
		numberOfBlack = numberOfWhite;
		this.length = length;
	}
	DamasLogic(int SaveNumber){
		pos = new Position[64];
		numberOfWhite = 12;
		//nao esquecer length
	}
	
	void posFill() {
		for(int j = 0; j< length; j++)
			for(int i = 0; i < length; i ++) {
				pos[i*length + j] = new Position(i,j);
			}
	}
	
	//FUNCOES PARA PEGAR VALORES
	
	
	boolean getWturn() {
		return isWhiteTurn;
	}
	int getLength() {
		return length;
	}
	int getNumberOfStones() {
		return numberOfWhite;
	}
	int setnumberOfStones(int num) {
		return num;
	}
	boolean getError1(){
		return Error1;
	}
	void setError1(boolean a){
		Error1 = a;
	}
	Position[] getPos() {
		return pos;
	}
	
	//FUNCOES DE LOGICA
	
	void firstPlace() {
		int numPieces = numberOfWhite;
		
		for(int i = 0; i<length; i++) {
			for(int c = 0; c<length;c++) {
				boolean BlackSquare = (i % 2 == 0 && c % 2 != 0) || (i % 2 != 0 && c % 2 == 0);
				int index = i * length + c;
				int lastBlack = (numPieces+numPieces%length+(numPieces/length)*length); 
				int firstWhite = (length*length-1)-(numPieces+numPieces%length+(numPieces/length)*length);
				
				if(BlackSquare) {
					if(index < lastBlack) {
						pos[index].setPiece("black");
					}
					if(index > firstWhite) {
						pos[index].setPiece("white");
					}
				}
				
			}
		}
		
	}
	
	boolean isDraw() {
		return (numberOfWhite==0 && numberOfBlack == 0)? true:false;
	}
	
	boolean validPlay(int initialLine,int initialCol, int finalLine, int finalCol){
		if((isWhiteTurn && initialLine > finalLine && initialLine != 0)||(!isWhiteTurn && initialLine< finalLine && initialLine != length-1)) {
			if((pos[initialLine * length + initialCol].piece() !=  null) && (pos[finalLine * length + finalCol].piece() ==  null)) {
				if(Math.abs(finalLine - initialLine)== 1 && Math.abs(finalCol - initialCol)== 1)
					return true;
			}
		}
		return false;
	}
	
	void moveTo(int initialLine, int initialCol, int finalLine, int finalCol) {
		if(isWhiteTurn) {
			pos[initialLine *length + initialCol].setPiece(null);
			pos[finalLine *length + finalCol].setPiece("white");
		}
		else {
			pos[initialLine *length + initialCol].setPiece(null);
			pos[finalLine *length + finalCol].setPiece("black");
		}
		isWhiteTurn =! isWhiteTurn;
	}	
	
	void oneColorPiece() {
		int count = 0;
		if(isWhiteTurn) {
			oneColorPiece = new Position[numberOfWhite];
			for(int i = 0; i<pos.length;i++) {
				if(pos[i].piece() == "white") {
					oneColorPiece[count] = pos[i];
					count++;
				}
			}
		}
		else {
			oneColorPiece = new Position[numberOfBlack];
			for(int i = 0; i<pos.length;i++) {
				if(pos[i].piece() == "black") {
					oneColorPiece[count] = pos[i];
					count++;
				}
			}
		}  
	}
	
	void randomPlay(){
		oneColorPiece();
		Error1 = false;
		int count = 0;
		int numberOfTrys = 0;
		Position randomPiece = oneColorPiece[(int)(Math.random()*oneColorPiece.length)];
		while (numberOfTrys <= 100) {
			for(int l = 0; l < length; l++ ) {
				for(int c = 0; c<length; c++) {
					if(validPlay(randomPiece.getLine(), randomPiece.getCol(), l, c)) {
						count ++;
					}
				}
			}
			if(count > 0) {
				validPlays = new Position[count];
				count = 0;
				for(int l = 0; l < length; l++ ) {
					for(int c = 0; c <length; c++) {
						if((isWhiteTurn && randomPiece.getLine() != 0) || (!isWhiteTurn && randomPiece.getLine() != length -1))
							if(validPlay(randomPiece.getLine(), randomPiece.getCol(), l, c)) {
								validPlays[count] = pos[l*length + c];
								count ++;
							}
					}
				}
				Position randomPlay = validPlays[(int)(Math.random()*validPlays.length)];
				moveTo(randomPiece.getLine(), randomPiece.getCol(), randomPlay.getLine(), randomPlay.getCol());
				break;
			}
			randomPiece = oneColorPiece[(int)(Math.random()*oneColorPiece.length)];
			if(numberOfTrys == 100) {
				Error1 = true;
			}
			numberOfTrys ++;
		}
	}
}
		
	
