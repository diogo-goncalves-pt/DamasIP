import pt.iscte.guitoo.Color;
import pt.iscte.guitoo.StandardColor;
import pt.iscte.guitoo.board.Board;

public class DamasGUI {
	Board board;
	DamasLogic logic;
	int count = 0;
	int initialLine = -1;
	int initialCol = -1;
	

	DamasGUI() {
		logic = new DamasLogic();
		logic.posFill();
		logic.firstPlace();
		board = new Board("As Brancas jogam   " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P", logic.getLength(), logic.getLength(), 80);
		board.addMouseListener(this::click);
		board.setBackgroundProvider(this::background);
		board.setIconProvider(this::icon);
		board.addAction("Random", this::random);
		board.addAction("New", this::newGame);
		board.addAction("Save",this::save);
		board.addAction("Load", this::load);
	}
	Color background(int line, int col) {
		if(logic.validPlay(initialLine, initialCol, line, col))
			return StandardColor.NAVY;
		if((line == initialLine && col == initialCol) && (logic.getPos()[line*logic.getLength() + col].piece() != null)) 
			return StandardColor.MAROON;
		return (line % 2 == 0) ? (col % 2 != 0 ? StandardColor.GRAY : StandardColor.WHITE)
                : (col % 2 == 0 ? StandardColor.GRAY : StandardColor.WHITE);
		
	}
	
	String icon(int line, int col) {
		
		if(logic.getPos()[line * logic.getLength() + col].piece() == "black")
			return("black.png");
		else if(logic.getPos()[line * logic.getLength() + col].piece() == "white")
			return "white.png";
		return null;
	}
	
	void click(int line, int col) {
		int index = line * logic.getLength() + col;
		if(logic.win()) {
			if(logic.getWhiteWin())
				board.showMessage("As brancas ganharam!");
			else
				board.showMessage("As pretas ganharam!");
		}
		else if(logic.isDraw())
			board.showMessage("O jogo Empatou");
		else {
			if(count == 1) {
				if(logic.validPlay(initialLine,initialCol,line,col)) {
					if(logic.isPossibleTocapture(initialLine, initialCol, line, col)) {
						logic.capture(initialLine, initialCol, line, col);
					}
					else
						logic.moveTo(initialLine, initialCol, line, col);
					count = 0;
					initialLine = -1;
					initialCol = -1;
					if(logic.getWturn())
						board.setTitle("As Brancas Jogam" + "    " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P");
					else
						board.setTitle("As Pretas Jogam!" + "    " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P");
				}
				if(initialLine == line && initialCol == col) {
					count = 0;
					initialLine = -1;
					initialCol = -1;
				}
			}
			else if(count == 0 && ((logic.getWturn() && logic.getPos()[index].piece() == "white" && line != 0)||(!logic.getWturn() && logic.getPos()[index].piece() == "black" && line != logic.getLength()-1))) {
					initialLine = line;
					initialCol = col;
					count = 1;
			}
		}
		
	}
	
	void random(){
		if(logic.win()) {
			if(logic.getWhiteWin())
				board.showMessage("As brancas ganharam!");
			else
				board.showMessage("As pretas ganharam!");
		}
		if(logic.isDraw())
			board.showMessage("O jogo Empatou");
		else {
			logic.randomPlay();
			if(logic.getError1()) 
				board.showMessage("(Error1)nenhuma jogada encontrada :(");
			if(logic.getWturn())
				board.setTitle("As Brancas Jogam" + "    " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P");
			else
				board.setTitle("As Pretas Jogam!" + "    " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P");
		}
	}
	void newGame() {
		DamasGUI gui = new DamasGUI();
		gui.start();
	}
	void save() {
		String saveGame = board.promptText("Nome do ficheiro: ");
		logic.saveGame(saveGame);
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