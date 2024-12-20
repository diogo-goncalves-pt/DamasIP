import pt.iscte.guitoo.Color;
import pt.iscte.guitoo.StandardColor;
import pt.iscte.guitoo.board.Board;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class DamasGUI {
	Board board;
	DamasLogic logic;
	int count = 0;
	int initialLine = -1;
	int initialCol = -1;
	

	DamasGUI() {
		this.logic = new DamasLogic();
		this.logic.posFill();
		this.logic.firstPlace();
		board = new Board("As Brancas jogam   " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P", logic.getLength(), logic.getLength(), 80);
		board.addMouseListener(this::click);
		board.setBackgroundProvider(this::background);
		board.setIconProvider(this::icon);
		board.addAction("Random", this::random);
		board.addAction("New", this::newGame);
		board.addAction("Save",this::save);
		board.addAction("Load", this::load);
	}
	DamasGUI(DamasLogic newLogic, int length) {
		this.logic = newLogic;
		board = new Board("As Brancas jogam   " + logic.getNumberOfWStones() + "B | " + logic.getNumberOfBStones() + "P", length, length, 80);
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
		if((line == initialLine && col == initialCol) && (!"null".equals(logic.getPos()[line*logic.getLength() + col].piece()))) 
			return StandardColor.MAROON;
		return (line % 2 == 0) ? (col % 2 != 0 ? StandardColor.GRAY : StandardColor.WHITE)
                : (col % 2 == 0 ? StandardColor.GRAY : StandardColor.WHITE);
		
	}
	
	String icon(int line, int col) {
		String piece = logic.getPos()[line * logic.getLength() + col].piece();
	    if ("black".equals(piece)) 
	        return "black.png";
	    else if ("white".equals(piece))
	        return "white.png";
	    else
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
			else if(count == 0 && ((logic.getWturn() && ("white").equals(logic.getPos()[index].piece()) && line != 0)||(!logic.getWturn() && ("black").equals(logic.getPos()[index].piece()) && line != logic.getLength()-1))) {
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
		if(!("").equals(saveGame) && saveGame != null) {
			logic.saveGame(saveGame);
			board.showMessage("Jogo gravado com sucesso!");
		}
		else
			board.showMessage("Nome Inválido");
	}
	void load() {
		int length = 0;
		boolean whiteturn = false;
		boolean findGame = false;
		Position[] newPos = null;
		String saveGame = board.promptText("Nome do ficheiro: ");
		try {
			Scanner scanner = new Scanner(new File("SaveGame.txt"));
			while(scanner.hasNext()) {
				if(scanner.next().equals(saveGame)) {
					findGame = true;
					length = scanner.nextInt();
					newPos = new Position[length * length];
					for(int i = 0; i<length*length; i ++) {
						newPos[i] = (new Position(scanner.nextInt(), scanner.nextInt()));
						String piece = scanner.next();
						if(piece.equals("null"))
							newPos[i].setPiece(null);
						else 
							newPos[i].setPiece(piece);
					}
					whiteturn = scanner.nextBoolean();
					break;
				}
			}
			
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Erro a carregar o jogo");
		}
		if(findGame) {
			DamasLogic newLogic = new DamasLogic(newPos, whiteturn);
			DamasGUI newgui = new DamasGUI(newLogic, length);
			newgui.start();	
		}
		else
			board.showMessage("jogo não encontrado!");
		
	}

	void start() {
		board.open();
	}

	public static void main(String[] args) {
		DamasGUI gui = new DamasGUI();
		gui.start();
	}
}