
public class Plays{

	private int playerId;
	private int gameId;
	private String playerIgn;

	Plays(){}

	Plays(int pId, int gId, String pIgn ){
		setPlayerId(pId);
		setGameId(gId);
		setPlayerIgn(pIgn);
	}

	public void setPlayerId(int id){this.playerId = id;}
	public void setGameId(int id){this.gameId = id;}
	public void setPlayerIgn(String ign){this.playerIgn = ign;}

	public int getPlayerId(){return playerId;}
	public int getGameId(){return gameId;}
	public String getPlayerIgn(){return playerIgn;}
}