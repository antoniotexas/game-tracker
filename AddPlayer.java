import java.util.*;

public class AddPlayer{

	private int playerId;
	private String playerName;
	private ArrayList<WinVictory> victory;

	AddPlayer(){}

	AddPlayer( int playerId, String playerName, ArrayList<WinVictory> victories){
		setPlayerId(playerId);
		setPlayerName(playerName);
		setPlayerVictories(victories);
	}

	public void setPlayerId(int playerId){this.playerId = playerId;}
	public void setPlayerName(String playerName){this.playerName = playerName;}
	public void setPlayerVictories(ArrayList<WinVictory> v){this.victory = v;}

	public int getPlayerId(){return playerId;}
	public String getPlayerName(){return playerName;}
	public ArrayList<WinVictory> getVictory(){return victory;}
}