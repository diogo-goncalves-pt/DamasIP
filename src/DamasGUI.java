import pt.iscte.guitoo.Color;
import pt.iscte.guitoo.StandardColor;
import pt.iscte.guitoo.board.Board;

public class DamasGUI {
	Board board;
	DamasLogic logic;

	DamasGUI() {
		logic = new DamasLogic();
		board = new Board("Damas", logic.getLength(), logic.getLength(), 80);
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
			return col%2 != 0 ? StandardColor.GRAY : StandardColor.WHITE;
		}
		else
			return col%2 == 0 ? StandardColor.GRAY : StandardColor.WHITE;
		
		
	}
	String icon(int line, int col) {
		if(line < logic.getNumberOfStones()%logic.getLength()){
				return col%2 != 0 ? "black.png": null;
		}
		else {
			return col%2 != 0 ? "black.png": null;
		}
		/*if(logic.getLength()-line > logic.getNumberOfStones()%logic.getLength()){
			return col%2 != 0 ? "white.png" : null;
		}
		return null;*/
	}
	void click(int line, int col) {
		System.out.println(line);
		System.out.println(col);
	}
	void random(){
		
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