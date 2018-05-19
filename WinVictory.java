
public class WinVictory{

	private int playerId;
	private int gameId;
	private int victoryId;

	WinVictory(int pId, int gId, int vId){
		setPlayerId(pId);
		setGameId(gId);
		setVictoryId(vId);
	}
	public void setPlayerId(int pId){this.playerId = pId;}
	public void setGameId(int gId){this.gameId = gId;}
	public void setVictoryId(int vId){this.victoryId = vId;}
	
	public int getPlayerId(){return playerId;}
	public int getGameId(){return gameId;}
	public int getVictoryId(){return victoryId;}
}