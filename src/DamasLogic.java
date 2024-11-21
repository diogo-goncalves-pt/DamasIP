public class DamasLogic {
	
	Position[] pos;
	int[][] matrix;
	private boolean isWhiteTurn = true;
	private int numberOfWhite;
	private int numberOfBlack;
	int length;
	
	//CONSTRUTORES
	
	DamasLogic(){
		pos = new Position[64];
		matrix = new int[8][8];
		numberOfWhite = 12;
		length = 8;
	}
	DamasLogic(int length, int numberOfStones){
		pos = new Position[length*length];
		matrix = new int[length][length];
		numberOfWhite = numberOfStones;
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
	int[][] getMatrix(){
		return matrix;
	}
	int getNumberOfStones() {
		return numberOfWhite;
	}
	int setnumberOfStones(int num) {
		return num;
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
				int[][] mat = matrix;
				int index = i * length + c;
				int lastBlack = (numPieces+numPieces%length+(numPieces/length)*length); 
				int firstWhite = (length*length-1)-(numPieces+numPieces%length+(numPieces/length)*length);
				
				if(BlackSquare) {
					if(index < lastBlack) {
						pos[index].setPiece("black");
						mat[i][c] = 1;
					}
					if(index > firstWhite) {
						pos[index].setPiece("white");
						mat[i][c] = 2;
					}
				}
				
			}
		}
		
	}
	
	boolean isDraw() {
		return (numberOfWhite==0 && numberOfBlack == 0)? true:false;
	}
	
		
		
	
	
	void moveTo(Position p,int line, int col) {
		if(pos[line*length + length].validPlay(line,col))
			pos[line*length + length] = p;
	}
	
	void randomPlay(){
		if(isWhiteTurn) {
			
			
			
		}
		else {
			
		}
			
			
	}
	
	
	
}
