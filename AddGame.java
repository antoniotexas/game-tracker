
public class AddGame{

	private int gameId;
	private String gameName;

	AddGame(){}

	AddGame(int id, String name){
		setGameId(id);
		setGameName(name);
	}

	public void setGameName(String name){this.gameName = name;}
	public void setGameId(int id){this.gameId = id;}

	public String getGameName(){return gameName;}
	public int getGameId(){return gameId;}
}