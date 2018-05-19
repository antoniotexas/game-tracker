import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class DataBase{

    ArrayList<String> saveLine;
	Map<Integer,String>mapGame;
	ArrayList<AddGame> arrGame;
	ArrayList<AddPlayer> arrPlayer;
	Map<Integer,String>mapPlayer;
	ArrayList<AddVictory> arrVictory;
	ArrayList<Plays> arrPlays;
	ArrayList<AddFriends> arrFriends;
	ArrayList<WinVictory> arrWinVictory;
	Map<Integer,String> mapVictory;

	//---------------------------------------------------------------------
	//Constructor 
	//---------------------------------------------------------------------
	DataBase(ArrayList<String> c ){
		
		saveLine = new ArrayList(c);
		mapGame = new HashMap<Integer,String>();
		arrGame = new ArrayList();
		arrPlayer = new ArrayList();
		mapPlayer = new HashMap<Integer,String>();
		arrVictory = new ArrayList();
		arrPlays = new ArrayList();
		arrFriends = new ArrayList();
		arrWinVictory = new ArrayList();
		mapVictory = new HashMap<Integer,String>();

		addToDataBase();
	}

	//------------------------------------------------------------------------
	//AddPlayer <Player ID> <Player Name> , and on the AddPlayer also takes the 
	//victories
	//------------------------------------------------------------------------
	public void parsePlayer(int i){

		String[] pId = saveLine.get(i).split(" ");
		int playerId = Integer.parseInt(pId[1]); 

		String[] pName = saveLine.get(i).split("\"");
		String playerName = pName[1];

		AddPlayer player1 = new AddPlayer(playerId, playerName,arrWinVictory);

		mapPlayer.put(playerId, playerName);
		arrPlayer.add(player1);
	}

	//------------------------------------------------------------------------
	//AddGame <Game ID> <Game Name>
	//------------------------------------------------------------------------
	public void parseGame(int i){

		String[] gId = saveLine.get(i).split(" ");
		int gameId = Integer.parseInt(gId[1]); 

		String[] gName = saveLine.get(i).split("\"");
		String gameName = gName[1];

		AddGame game = new AddGame(gameId, gameName);

		mapGame.put(gameId, gameName);
		arrGame.add(game);
	}

	//------------------------------------------------------------------------
	//AddVictory <Game ID> <Victory ID> <Victory Name> <Victory Points>
	//------------------------------------------------------------------------
	public void parseVictory(int i){

		String[] gId = saveLine.get(i).split(" ");
		int gameId = Integer.parseInt(gId[1]); 

		String[] vId = saveLine.get(i).split(" ");
		int victoryId = Integer.parseInt(vId[2]); 

		String[] vName = saveLine.get(i).split("\"");
		String victoryName = vName[1];

		String[] vPoints = saveLine.get(i).split("\"");
		int victoryPoints = Integer.parseInt(vPoints[2].trim()); 					

		AddVictory victory = new AddVictory(gameId,victoryId,victoryName,victoryPoints);

		arrVictory.add(victory);	
		mapVictory.put(victoryId,victoryName);
	}

	//------------------------------------------------------------------------
	//Plays <Player ID> <Game ID> <Player IGN>
	//------------------------------------------------------------------------
	public void parsePlays(int i){

		String[] pId = saveLine.get(i).split(" ");
		int playerId = Integer.parseInt(pId[1]); 

		String[] gId = saveLine.get(i).split(" ");
		int gameId = Integer.parseInt(gId[2]); 

		String[] pIgn = saveLine.get(i).split("\"");
		String  playerIgn= pIgn[1];

		Plays play = new Plays(playerId,gameId,playerIgn);

		arrPlays.add(play);
	}

	//------------------------------------------------------------------------
	//AddFriends <Player ID1> <Player ID2>
	//------------------------------------------------------------------------
	public void parseFriends(int i){

		String[] f1 = saveLine.get(i).split(" ");
		int friend1 = Integer.parseInt(f1[1]); 

		String[] f2 = saveLine.get(i).split(" ");
		int friend2 = Integer.parseInt(f2[2]); 

		AddFriends friends = new AddFriends(friend1,friend2);
			
		arrFriends.add(friends);
	}

	//------------------------------------------------------------------------
	//WinVictory <Player ID> <Game ID> <Victory ID>
	//------------------------------------------------------------------------
	public void parseWinVictory(int i){

		String[] pId = saveLine.get(i).split(" ");
		int playerId = Integer.parseInt(pId[1]);

		int gameId = Integer.parseInt(pId[2]); 

		int victoryId = Integer.parseInt(pId[3]); 

		WinVictory win = new WinVictory(playerId,gameId,victoryId);

		arrWinVictory.add(win);	


	}	

	//------------------------------------------------------------------------
	//Reads the elements from saveLine(file was save in this arrayList)
	// and stores the data into different data structures (Maps and Arraylist)
	//-------------------------------------------------------------------------
	public void addToDataBase(){

		String[] firstCommand;

		for(int i = 0 ; i < saveLine.size() ; i++){
			firstCommand = saveLine.get(i).split(" ");
			if(firstCommand[0].equals("AddPlayer")){
				parsePlayer(i);
			}
			else if(firstCommand[0].equals("AddGame")){
				parseGame(i);
			}
			else if(firstCommand[0].equals("AddVictory")){
				parseVictory(i);
			}
			else if(firstCommand[0].equals("Plays")){
				parsePlays(i);
			}
			else if(firstCommand[0].equals("AddFriends")){
				parseFriends(i);
			}
			else if(firstCommand[0].equals("WinVictory")){
				parseWinVictory(i);
			}
			///If there is more commands, it will ignore them. 
			///Thats why I did not put any else 
		}
	}

}





