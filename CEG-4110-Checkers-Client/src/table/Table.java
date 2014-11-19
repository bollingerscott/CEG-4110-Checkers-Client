package table;

public class Table {

	private Integer tid;
	private String redseat, blackseat;
	private boolean player1;
	private boolean changed = false;
	private byte[][] boardState  = new byte[][]{
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},
			{0,1,0,1,0,1,0,1},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{2,0,2,0,2,0,2,0},
			{0,2,0,2,0,2,0,2},
			{2,0,2,0,2,0,2,0}};


	public Table(Integer tid, String redseat, String blackseat) {
		this.tid = tid;
		this.redseat = redseat;
		this.blackseat = blackseat;
	}

	public String getBlackseat() {
		return blackseat;
	}

	public byte[][] getBoardState() {
		return boardState;
	}

	public String getRedseat() {
		return redseat;
	}

	public Integer getTid() {
		return tid;
	}

	public boolean isChanged() {
		return changed;
	}

	public boolean isPlayer1() {
		return player1;
	}

	public void setBlackseat(String blackseat) {
		this.blackseat = blackseat;
	}

	public void setBoardState(byte[][] boardState) {
		this.boardState = boardState;
		changed = true;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void setPlayer1(boolean player1) {
		this.player1 = player1;
	}

	public void setRedseat(String redseat) {
		this.redseat = redseat;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}
}
