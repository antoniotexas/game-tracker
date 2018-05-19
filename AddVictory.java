
public class AddVictory{

	private int gameId;
	private int victoryId;
	private String victoryName;
	private int victoryPoints;

	AddVictory(){}

	AddVictory(int gameId , int id, String name, int points){
		setGameId(gameId);
		setId(id);
		setName(name);
		setPoints(points);
	}

	public void setGameId(int id){this.gameId = id;}
	public void setId(int id){this.victoryId = id;}
	public void setName(String name){this.victoryName = name;}
	public void setPoints(int points){this.victoryPoints = points;};

	public int getGameId(){return gameId;}
	public int getVictoryId(){return victoryId;}
	public String getVictoryName(){return victoryName;}
	public int getVictoryPoints(){return victoryPoints;};
}