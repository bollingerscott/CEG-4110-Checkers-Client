package table;

public class Table {

	private Integer tid;
	private String redseat, blackseat;
	private boolean player1;
	private byte[][] boardState = new byte[][]{
			{0,1,0,1,0,1,0,1},
			{1,0,1,0,1,0,1,0},
			{0,1,0,1,0,1,0,1},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{2,0,2,0,2,0,2,0},
			{0,2,0,2,0,2,0,2},
			{2,0,2,0,2,0,2,0}};
	
	public Table(Integer tid, String redseat, String blackseat){
		this.tid = tid;
		this.redseat = redseat;
		this.blackseat = blackseat;
	}
	
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getRedseat() {
		return redseat;
	}
	public void setRedseat(String redseat) {
		this.redseat = redseat;
	}
	public String getBlackseat() {
		return blackseat;
	}
	public void setBlackseat(String blackseat) {
		this.blackseat = blackseat;
	}

	public byte[][] getBoardState() {
		return boardState;
	}

	public void setBoardState(byte[][] boardState) {
		this.boardState = boardState;
	}

	public boolean isPlayer1() {
		return player1;
	}

	public void setPlayer1(boolean player1) {
		this.player1 = player1;
	}
}
