import java.util.Arrays;

import pt.iscte.guitoo.Color;
import pt.iscte.guitoo.StandardColor;
import pt.iscte.guitoo.board.Board;

public class DamasGUI {
	Board board;
	DamasLogic logic;

	DamasGUI() {
		logic = new DamasLogic(6,3);
		logic.posFill();
		logic.firstPlace();
		board = new Board("As Brancas jogam", logic.getLength(), logic.getLength(), 80);
		board.addMouseListener(this::click);
		board.setBackgroundProvider(this::background);
		board.setIconProvider(this::icon);
		board.addAction("Random", this::random);
		board.addAction("New", this::newGame);
		board.addAction("Save",this::save);
		board.addAction("Load", this::load);
	}
	Color background(int line, int col) {
		
		if(line % 2 == 0){
			return col%2 != 0 ? StandardColor.GRAY: StandardColor.WHITE;
		}
		else
			return col%2 == 0 ? StandardColor.GRAY : StandardColor.WHITE;
		
	}
	String icon(int line, int col) {
		int[][] mat = logic.getMatrix();
		
		if(mat[line][col] == 1)
			return("black.png");
		else if(mat[line][col] == 2)
			return "white.png";
		return null;
	}
	
	void click(int line, int col) {
		int index = line * logic.getLength() + col;
		System.out.println(logic.getPos()[line*logic.getLength()+col].piece());
		System.out.println(logic.getPos()[line * logic.getLength() + col].validPlay(4, 3));
	}
	void random(){
		logic.randomPlay();
	}
	void newGame() {
		DamasGUI gui = new DamasGUI();
		gui.start();
	}
	void save() {
		String saveGame = board.promptText("Nome do ficheiro: ");
		System.out.println(saveGame);;
	}
	void load() {
		String saveGame = board.promptText("Ficheiro a carregar: ");
	}

	void start() {
		board.open();
	}

	public static void main(String[] args) {
		DamasGUI gui = new DamasGUI();
		gui.start();
	}
}