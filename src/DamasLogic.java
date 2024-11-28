import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DamasLogic {
	
	private Position[] pos;
	private Position[] oneColorPiece;
	private Position[] validPlays;
	private boolean isWhiteTurn = true;
	private int initialOneColorPieces;
	private int numberOfWhite;
	private int numberOfBlack;
	private int length;
	private boolean whiteWin;
	private boolean Error1 = true;
	
	//CONSTRUTORES
	
	DamasLogic(){
		pos = new Position[64];
		initialOneColorPieces = 12;
		numberOfWhite = initialOneColorPieces;
		numberOfBlack = numberOfWhite;
		length = 8;
	}
	DamasLogic(int length, int numberOfStones){
		assert(numberOfStones <= (length*length)/4);
		pos = new Position[length*length];
		initialOneColorPieces = numberOfStones;
		numberOfWhite = initialOneColorPieces;
		numberOfBlack = numberOfWhite;
		this.length = length;
	}
	DamasLogic(Position[] savedPos, boolean Whiteturn){
		pos = savedPos;
		this.isWhiteTurn = Whiteturn;
		this.numberOfWhite = 0;
		this.numberOfBlack = 0;
		this.length = (int)Math.sqrt(savedPos.length);
		for(int i = 0; i < savedPos.length; i++) {
			if(("white").equals(savedPos[i].piece()))
				numberOfWhite++;
			if(("black").equals(savedPos[i].piece()))
				numberOfBlack++;
		}
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
	int getNumberOfWStones() {
		return numberOfWhite;
	}
	int getNumberOfBStones() {
		return numberOfBlack;
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
	void setPos(Position newPos,int index) {
		pos[index] = newPos;
	}
	boolean getWhiteWin() {
		return whiteWin;
	}
	
	//FUNCOES DE LOGICA
	
	void firstPlace() {
		int numPieces = initialOneColorPieces;
		
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
	boolean isWithinBounds(int line, int col) {
		return((line >= 0 && line < length) && (col >=0 && col< length));
	}
	
	boolean isDraw() {
		int countUnplayableWhite = 0;
		int countUnplayableBlack = 0;
		for(int i =0; i<pos.length; i++) {
			if(pos[i].getPlayable() == false && ("white").equals(pos[i].piece()))
				countUnplayableWhite++;
			if(pos[i].getPlayable() == false && ("black").equals(pos[i].piece()))
				countUnplayableBlack++;
		}
		return((countUnplayableBlack == countUnplayableWhite) && (countUnplayableBlack == numberOfBlack && countUnplayableWhite == numberOfWhite));
	}
	
	boolean validPlay(int initialLine,int initialCol, int finalLine, int finalCol){
		if(captureAvailable()){
			if(isPossibleTocapture(initialLine, initialCol, finalLine, finalCol)) {
				if(pos[finalLine * length + finalCol].piece() == null && Math.abs(finalLine - initialLine)== 2 && Math.abs(finalCol - initialCol)== 2) {
					if((isWhiteTurn && initialLine > finalLine && initialLine > 0 && initialLine < length)||(!isWhiteTurn && initialLine < finalLine && initialLine >= 0 && initialLine < length -1))
						return true;
				}
			}
		}
		else {
			if((isWhiteTurn && initialLine > finalLine && initialLine > 0 && initialLine < length)||(!isWhiteTurn && initialLine < finalLine && initialLine >= 0 && initialLine < length -1)) {
				if((pos[initialLine * length + initialCol].piece() !=  null) && (pos[finalLine * length + finalCol].piece() ==  null)) {
					if(Math.abs(finalLine - initialLine)== 1 && Math.abs(finalCol - initialCol)== 1)
						return true;
				}
			}
		}
		return false;
	}
	
	void moveTo(int initialLine, int initialCol, int finalLine, int finalCol) {
		if(isWhiteTurn) {
			pos[initialLine *length + initialCol].setPiece(null);
			pos[finalLine *length + finalCol].setPiece("white");
			if(finalLine == 0)
				pos[finalLine *length + finalCol].setPlayable(false);
			for(int l = 0; l<length ; l++) {
				for(int c = 0; c<length; c++) {
					if(!validPlay(finalLine, finalCol,l,c))
						pos[finalLine *length + finalCol].setPlayable(false);
				}
			}
		}
		else {
			pos[initialLine *length + initialCol].setPiece(null);
			pos[finalLine *length + finalCol].setPiece("black");
			if(finalLine == length -1) {
				pos[finalLine *length + finalCol].setPlayable(false);
			}
			for(int l = 0; l<length ; l++) {
				for(int c = 0; c<length; c++) {
					if(!validPlay(finalLine, finalCol,l,c))
						pos[finalLine *length + finalCol].setPlayable(false);
				}
			}
		}
		isWhiteTurn =! isWhiteTurn;
	}	
	
	void oneColorPiece() {
		int count = 0;
		if(isWhiteTurn) {
			oneColorPiece = new Position[numberOfWhite];
			for(int i = 0; i<pos.length;i++) {
				if(("white").equals(pos[i].piece())) {
					oneColorPiece[count] = pos[i];
					count++;
				}
			}
		}
		else {
			oneColorPiece = new Position[numberOfBlack];
			for(int i = 0; i<pos.length;i++) {
				if(("black").equals(pos[i].piece())) {
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
		if(!win()) {
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
					if(isPossibleTocapture(randomPiece.getLine(), randomPiece.getCol(), randomPlay.getLine(),  randomPlay.getCol())) 
						capture(randomPiece.getLine(),randomPiece.getCol(), randomPlay.getLine(),  randomPlay.getCol());
					
					else
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
	
	boolean isPossibleTocapture(int initialLine, int initialCol,int finalLine,int finalCol) {
		int middlePieceLine = (initialLine + finalLine)/2;
		int middlePieceCol = (initialCol + finalCol)/2;
		
		if(pos[finalLine * length + finalCol].piece() == null) {
			if(("black").equals(pos[middlePieceLine * length + middlePieceCol].piece()) && isWhiteTurn && finalLine < initialLine)
				return true;
			else if(("white").equals(pos[middlePieceLine * length + middlePieceCol].piece()) && !isWhiteTurn && finalLine > initialLine)
				return true;
		}
		return false;
	}
	boolean isPossibleToMove() {
		for(int l = 0; l < length ; l ++) {
			for(int c = 0; c<length; c++) {
				if(("white").equals(pos[l * length + c].piece()) && isWhiteTurn || ("black").equals(pos[l * length + c].piece()) && !isWhiteTurn) {
					int[][] playableDirections = {{1,1},{1,-1},{-1,1},{-1,-1}};
					for(int i = 0; i<playableDirections.length; i++) {
						int finalLine = l + playableDirections[i][0];
						int finalCol = c + playableDirections[i][1];
						if(isWithinBounds(finalLine, finalCol) && validPlay(l, c, finalLine, finalCol) || captureAvailable())
							return true;
					}
				}
			}
		}
		return false;
	}
	
	boolean captureAvailable() {
		for(int l = 0; l < length ; l ++) {
			for(int c = 0; c<length; c++) {
				if(("white").equals(pos[l * length + c].piece()) && isWhiteTurn || ("black").equals(pos[l * length + c].piece()) && !isWhiteTurn) {
					int[][] captureDirections = {{2,2},{2,-2},{-2,2},{-2,-2}};
					for(int i = 0; i<captureDirections.length; i++) {
						int finalLine = l + captureDirections[i][0];
						int finalCol = c + captureDirections[i][1];
						if(isWithinBounds(finalLine, finalCol) && isPossibleTocapture(l, c, finalLine, finalCol))
							return true;
					}
				}
			}
		}
		return false;
	}
	void capture(int initialLine, int initialCol,int finalLine,int finalCol) { //line e col sao locais onde a peca vai
		int middlePieceLine = (initialLine + finalLine)/2;
		int middlePieceCol = (initialCol + finalCol)/2;
		
		if(isPossibleTocapture(initialLine, initialCol, finalLine, finalCol)) {
			pos[middlePieceLine * length + middlePieceCol].setPiece(null);
			pos[initialLine *length + initialCol].setPiece(null);
		}
		if(isWhiteTurn) {
			pos[finalLine * length + finalCol].setPiece("white");		
			numberOfBlack --;
			}
		else{
			pos[finalLine * length + finalCol].setPiece("black");
			numberOfWhite--;
		}
		if(!captureAvailable())
			isWhiteTurn = !isWhiteTurn;
	}
	boolean win() {
		if(numberOfBlack == 0 && numberOfWhite != 0) {
			whiteWin = true;
			return true;
		}
		if(numberOfBlack != 0 && numberOfWhite == 0) {
			whiteWin = false;
			return true;
		}
		if(!isPossibleToMove() && !isDraw()) {
			if(numberOfWhite>numberOfBlack) {
				whiteWin = true;
				return true;
			}
			if(numberOfWhite<numberOfBlack) {
				whiteWin = false;
				return true;
			}
		}
		return false; 
	}
	void saveGame(String saveName) {
		try {
			PrintWriter writter = new PrintWriter(new FileWriter("SaveGame.txt", true));
			writter.println(saveName);
			writter.println(length);
			for(int i = 0; i<length;i++) {
				for(int c = 0; c< length; c++) {
					writter.print(i + " " + c + " " + pos[i*length + c].piece());
					writter.println();
				}
			}
			writter.println(isWhiteTurn);
			writter.close();
			
		}
		catch(FileNotFoundException e){
			System.err.println("Erro a escrever no ficheiro");
		}
		catch(IOException e) {
	        System.err.println("Erro ao abrir o ficheiro para append");
	    }
	}
}
		
	
