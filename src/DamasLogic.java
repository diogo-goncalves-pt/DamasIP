public class DamasLogic {
	
	private int[][] playMatrix;
	private boolean isWhiteTurn = true;
	private int numberOfWhite;
	int length;
	
	//CONSTRUTORES
	
	DamasLogic(){
		playMatrix = new int [8][8];
		numberOfWhite = 12;
		length = 8;
	}
	DamasLogic(int length, int numberOfStones){
		playMatrix = new int [length][length];
		numberOfWhite = numberOfStones;
		this.length = length;
	}
	DamasLogic(int SaveNumber){
		playMatrix = new int [8][8];
		numberOfWhite = 12;
		//nao esquecer length
	}
	
	//FUNCOES PARA PEGAR VALORES
	
	int[][] getMatrix(){
		return playMatrix;
	}
	boolean getWturn() {
		return isWhiteTurn;
	}
	int getLength() {
		return length;
	}
	int getNumberOfStones() {
		return numberOfWhite;
	}
	
	//FUNCOES DE LOGICA
	
	boolean isDraw() {
		return true;
	}
	
	
	void randomPlay(){
		
	}
	
	
	
}
