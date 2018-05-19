import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class Report{

	DataBase db;

	Report(ArrayList<String> list){
		db = new  DataBase(list);
	}

	//--------------------------------------------------------------------------
	//Iterates through saveLine(ArrayList) commands which is stored in DataBase
	// and calls functions to print the report 
	//---------------------------------------------------------------------------
	public void report(){

		String[] firstCommand;

		for(int i = 0 ; i < db.saveLine.size() ; i++){
			firstCommand = db.saveLine.get(i).split(" ");
			
			if(firstCommand[0].equals("SummarizePlayer")){
				String[] nameId = db.saveLine.get(i).split(" ");
				//summarizePlayer(playerID)
				summarizePlayer(Integer.parseInt(nameId[1]));
			}
			else if(firstCommand[0].equals("SummarizeGame")){
				String[] gameId = db.saveLine.get(i).split(" ");
				//summarizeGame(gameID)
				summarizeGame(Integer.parseInt(gameId[1]));
			}
			else if(firstCommand[0].equals("VictoryRanking")){
				victoryRanking();
			}
			else if(firstCommand[0].equals("SummarizeVictory")){
				String[] command = db.saveLine.get(i).split(" ");
				//SummarizeVictory(gameID, VictoryID)
				summarizeVictory(Integer.parseInt(command[1]) , Integer.parseInt(command[2]));
			}
			else if(firstCommand[0].equals("ComparePlayers")){
				String[] command = db.saveLine.get(i).split(" ");
				//ComparePlayers(Player 1, Player 2, gameId)
				comparePlayers(Integer.parseInt(command[1]),Integer.parseInt(command[2]),Integer.parseInt(command[3]));
			}
			else if(firstCommand[0].equals("FriendsWhoPlay")){
				String[] command = db.saveLine.get(i).split(" ");
				//FriendsWhoPlay(playerId, gameId)
				friendsWhoPlay(Integer.parseInt(command[1]) , Integer.parseInt(command[2]));
			}
			///If there is more commands, it will ignore them. 
			///Thats why I did not put any else 
		}
	}

	//---------------------------------------------------------------------------
	//Report which of player's friends play the specified game
	//---------------------------------------------------------------------------
	public void friendsWhoPlay(int playerId, int gameId){

		System.out.println();
		System.out.println("-------------------------Friends Who Play-------------------------------");
		System.out.println("Player: "+ db.mapPlayer.get(playerId));
		System.out.println("Game: " + db.mapGame.get(gameId));
		System.out.println();
		System.out.println("Friends:");

		ArrayList<Integer> arr = new ArrayList();

	    arr = determineFriends(playerId);

	    for(int i = 0; i < arr.size(); i++){
	    	for(int j = 0; j < db.arrPlays.size(); j++){
	    		if(arr.get(i) == db.arrPlays.get(j).getPlayerId() && gameId == db.arrPlays.get(j).getGameId() ){
	    			System.out.println( (i+1)+ "." +db.mapPlayer.get(arr.get(i)));
	    		}
	    	}
	    }
	    System.out.println();
	    System.out.println();
	}

	//---------------------------------------------------------------------------
	//ComparePlayer helper function. Returns the number victories 
	//---------------------------------------------------------------------------
	public int cpVictoryHelper(int playerId , int gameId){

		int p1Victories = 0;

		for(int i = 0; i< db.arrWinVictory.size(); i++){
			if( (playerId == db.arrWinVictory.get(i).getPlayerId())
			 && (gameId == db.arrWinVictory.get(i).getGameId())){
				p1Victories = p1Victories + 1;
			}
		}
		return p1Victories;
	}

	//---------------------------------------------------------------------------
	//ComparePlayer helper function. Returns the player score
	//---------------------------------------------------------------------------
	public int cpScoreHelper(int playerId, int gameId){

		int score = 0;

		for(int j =0; j< db.arrWinVictory.size(); j++){
			if((playerId == db.arrWinVictory.get(j).getPlayerId()) 
				&& gameId == db.arrWinVictory.get(j).getGameId() ){

				for(int k = 0; k < db.arrVictory.size(); k++){
					if( db.arrWinVictory.get(j).getVictoryId() == db.arrVictory.get(k).getVictoryId()){
							score = score + db.arrVictory.get(k).getVictoryPoints();
					}
				}
			}
		}
		return score;
	}

	//---------------------------------------------------------------------------
	//Print report comparing player 1 and player 2's Victory records and total
	// Victory scores for the given game.
	//The given game is guaranteed to have been played by both players
	//---------------------------------------------------------------------------
	public void comparePlayers(int id1, int id2, int gameId){

		int player1Vic = cpVictoryHelper(id1, gameId);
		int player2Vic = cpVictoryHelper(id2, gameId);

		System.out.println("--------------------------Compare Players:----------------------------");
		System.out.println("Game: " + db.mapGame.get(gameId));
		System.out.println();
		System.out.println("Player    " + "              Victories" + "        Points");
		System.out.printf("%d%c%-22s %-16d %d \n",1,'.',db.mapPlayer.get(id1),player1Vic,cpScoreHelper(id1,gameId));
		System.out.printf("%d%c%-22s %-16d %d \n",2,'.',db.mapPlayer.get(id2),player2Vic,cpScoreHelper(id2,gameId));
		System.out.println();
		System.out.println();

		
	}

	//------------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns which games were played by the player
	//------------------------------------------------------------------------------
	public ArrayList<String> gamesPlayed(int playerId){

		ArrayList<String> rtn = new ArrayList();
		String str = "";

		for(int i = 0; i < db.arrPlays.size(); i++){
			if(playerId == db.arrPlays.get(i).getPlayerId()){
				str = db.mapGame.get( db.arrPlays.get(i).getGameId());
				rtn.add(str);
			}	
		}
		return rtn;	
	}

	//----------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns an ArrayList of the number of 
	//player victories  
	//----------------------------------------------------------------------------
	public ArrayList<Integer> numOfPlayerVictories(int playerId){

		ArrayList<Integer> rtn = new ArrayList();
		int count = 0;

		for(int i = 0; i < db.arrGame.size();i++){
			for(int j =0; j< db.arrWinVictory.size(); j++){
				if(playerId == db.arrWinVictory.get(j).getPlayerId()){
					if (db.arrGame.get(i).getGameId() == db.arrWinVictory.get(j).getGameId() ){
						count = count + 1;
					}
				}
			}
			rtn.add(count);
			count = 0;
		}
		return rtn;
	}

	//---------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns an ArrayList of the number of possible victories for each game
	//---------------------------------------------------------------------------
	public ArrayList<Integer> numOfPossibleVictories(){

		ArrayList<Integer> rtn = new ArrayList();
 		int count = 0;

		for(int i = 0; i< db.arrGame.size(); i++){
				for(int j =0; j< db.arrVictory.size(); j++){
					if (db.arrGame.get(i).getGameId() == db.arrVictory.get(j).getGameId() ){
						count = count + 1;
					}
				}
				rtn.add(count);
					count = 0;
		}
		return rtn;
	}

	//---------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns an ArrayList of player scores 
	//---------------------------------------------------------------------------
	public ArrayList<Integer> gameScore(int playerId){

		ArrayList<Integer> rtn = new ArrayList();
		int score = 0;

		for(int i = 0; i < db.arrGame.size();i++){

			for(int j =0; j< db.arrWinVictory.size(); j++){
				if((playerId == db.arrWinVictory.get(j).getPlayerId()) && 
					(db.arrGame.get(i).getGameId() == db.arrWinVictory.get(j).getGameId()) ){

					for(int k = 0; k < db.arrVictory.size(); k++){
						if( db.arrWinVictory.get(j).getVictoryId() == db.arrVictory.get(k).getVictoryId()){
							score = score + db.arrVictory.get(k).getVictoryPoints();
						}
					}
				}
			}
			rtn.add(score);
			score = 0;
		}
		return rtn;
	}

	//-----------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns the player IGN 
	//-----------------------------------------------------------------------------
	public ArrayList<String> iGN(int playerId){

		ArrayList<String> rtn = new ArrayList();
		String compare = "";

		for(int i = 0; i < db.arrPlays.size(); i++){
			if(playerId == db.arrPlays.get(i).getPlayerId()){
				compare =  db.arrPlays.get(i).getPlayerIgn();
				rtn.add(compare);
			}	
		}
		return rtn;	
	}

	//-----------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns the player friends
	//-----------------------------------------------------------------------------
	public ArrayList<Integer> determineFriends(int playerId){

		ArrayList<Integer> friendList = new ArrayList();
		int friend;

		for(int  i = 0; i <db.arrFriends.size(); i++ ){
			if(playerId == db.arrFriends.get(i).getPlayer()){
				friend = db.arrFriends.get(i).getFriend();
				friendList.add(friend);
			}
		}
		return friendList;
	}

	//-----------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns the player total game score 
	//-----------------------------------------------------------------------------
	public int playerTotalScore(int playerId){

		int gameScore = 0;

		for(int i = 0 ; i <db.arrWinVictory.size(); i++){
			if(playerId == (db.arrWinVictory.get(i).getPlayerId())){
				for(int j = 0; j < db.arrVictory.size(); j++){
					if((db.arrWinVictory.get(i).getVictoryId()) == db.arrVictory.get(j).getVictoryId()){
						gameScore += db.arrVictory.get(j).getVictoryPoints();
					}
				}
			}
		}
		return gameScore;
	}

	//-----------------------------------------------------------------------------
	//SummarizePlayer helper function. Returns the friend total game score 
	//-----------------------------------------------------------------------------
	public ArrayList<Integer> friendsScore(int playerId){
		ArrayList<Integer> score = new ArrayList();
		score.add(playerTotalScore(playerId));
		return score;
	}

	//-----------------------------------------------------------------------------
	//Print record of all of player's friends, games the player plays,
	// and gamer point totals.
	//-----------------------------------------------------------------------------
	public void  summarizePlayer(int playerId){

		String playerName = db.mapPlayer.get(playerId);

		int score = playerTotalScore(playerId);

		ArrayList<String>  gPlayed   = new ArrayList();
		ArrayList<String>  playerIgn = new ArrayList();
		ArrayList<Integer> friends   = new ArrayList();
		ArrayList<Integer> victories = new ArrayList();
		ArrayList<Integer> possibleVictories = new ArrayList();
		ArrayList<Integer> gScore    = new ArrayList();

		gPlayed   = gamesPlayed(playerId);
		playerIgn = iGN(playerId);
		victories = numOfPlayerVictories(playerId);
		possibleVictories = numOfPossibleVictories();
		gScore    =  gameScore(playerId);
		friends   = determineFriends(playerId);
    
		System.out.println();
		System.out.println("----------------------------Summarize Player--------------------------");
		System.out.println("Player: " + playerName); 
		System.out.println("Total Game Score: " + score + " pts");
		System.out.println();
		System.out.println("  Game               Victories      GameScore       IGN             ");
		System.out.println("-----------------------------------------------------------------------");

		for(int i = 0; i< db.arrGame.size(); i++){

			System.out.printf("%d%c%-18s %d%c%-12d %d %-11s %s\n",(i+1),'.',gPlayed.get(i),victories.get(i),'/',
				possibleVictories.get(i),gScore.get(i),"pts",playerIgn.get(i) );
		}
		System.out.println();
		System.out.println("Friend" + "                       " + "     GameScore");
		System.out.println("---------------------------------------------");

		Map<Integer, String> mapRanking = new HashMap<Integer,String>();
		ArrayList<String> friendName = new ArrayList();
		ArrayList<Integer> friendScore = new ArrayList();
	

		for(int i = 0; i<friends.size(); i++){
			mapRanking.put( playerTotalScore(friends.get(i)) , db.mapPlayer.get(friends.get(i)));
		}

		Map<Integer, String> treeMap = new TreeMap<Integer, String>(mapRanking);

		for(Map.Entry<Integer, String> entry : treeMap.entrySet()){
           	friendName.add(entry.getValue());
           	friendScore.add(entry.getKey());
		}
     
        for(int i = friendName.size()-1; i >= 0 ; i--){
        	System.out.printf( "%d%c%-32s %d \n" , (friendName.size() - i),'.' ,friendName.get(i), friendScore.get(i));
        }	
		System.out.println();
		System.out.println();
	}

	//-----------------------------------------------------------------------------
	//Print a record of all players who play the specified game
	// and the number of times each of its victories have been accomplished.
	//-----------------------------------------------------------------------------
	public void summarizeGame(int gameId){///4001

		ArrayList<Integer> victories = new ArrayList();
		ArrayList<String> playerList= new ArrayList();
		ArrayList<Integer> score = new ArrayList();
		int count = 0;
		int count2 = 0;


		for(int i = 0; i<db.arrPlays.size(); i++ ){
			if(gameId == db.arrPlays.get(i).getGameId()){
				playerList.add(db.mapPlayer.get(db.arrPlays.get(i).getPlayerId())   );
			}
		}

		System.out.println();
		System.out.println("---------------------------Summarize Game---------------------------");
		System.out.println("Game: " + db.mapGame.get(gameId));
		System.out.println();
		System.out.println("Players Name" + "         Victories");
		System.out.println("--------------------------------------");

		for(int i = 0; i<db.arrPlays.size(); i++ ){
			if(gameId == db.arrPlays.get(i).getGameId()){
				count2++;
				System.out.println(count2 + "." + db.mapPlayer.get(db.arrPlays.get(i).getPlayerId()));
				for(int j =0; j< db.arrWinVictory.size(); j++){

					if( (db.arrPlays.get(i).getPlayerId() == db.arrWinVictory.get(j).getPlayerId()) && (gameId == db.arrWinVictory.get(j).getGameId())) {
						count = count + 1;
						System.out.printf("%-20s%s\n"," ",db.mapVictory.get(db.arrWinVictory.get(j).getVictoryId()));	
					}
				}
				System.out.printf("%-20s%s%s%d"," ","TOTAL:"," ",count);
				count = 0;
				System.out.println();
				System.out.println();
			}
		};
	
		System.out.println();
		System.out.println();
	}

	//--------------------------------------------------------------------------------
	//Print a list of all players who have achieved a Victory, and the percentage of 
	//players who play that game who have the Victory
	//---------------------------------------------------------------------------------
	public void summarizeVictory(int gameId, int victoryId){///NOT FINISHED---------------------------------------------------

		String str = " ";
		for(int i = 0; i<db.arrVictory.size();i++){
			if(victoryId == db.arrVictory.get(i).getVictoryId()){
				str = db.arrVictory.get(i).getVictoryName();
			}
		}

		int count = 0;
		System.out.println();
		System.out.println("-----------------------------Summarize Victory---------------------------");
		System.out.println("Game: "+ db.mapGame.get(gameId)  + " " + "'" +str+"'" );
		System.out.println();
		System.out.println("Players who have achieved a victory");
		System.out.println("-----------------------------------");
		for(int i = 0; i< db.arrWinVictory.size(); i++){

			if((gameId == db.arrWinVictory.get(i).getGameId()) && (victoryId == db.arrWinVictory.get(i).getVictoryId())){
					count++;
					System.out.println( count + "."+db.mapPlayer.get(db.arrWinVictory.get(i).getPlayerId()));
			}
		}
		System.out.println("Percentage of victories: " + 85 +"%");
		System.out.println();

	} 

	//-----------------------------------------------------------------------------
	//Print a summary ranking all players by their total number of gamer points
	//-----------------------------------------------------------------------------
	public void victoryRanking(){

		Map<Integer, String> mapRanking = new HashMap<Integer,String>();
		ArrayList<String> player  = new ArrayList();
		ArrayList<Integer> score = new ArrayList();
		String list = " ";

		for(int i = 0; i<db.arrPlayer.size(); i++){
			mapRanking.put(playerTotalScore(db.arrPlayer.get(i).getPlayerId()),db.mapPlayer.get(db.arrPlayer.get(i).getPlayerId()));
		}

		Map<Integer, String> treeMap = new TreeMap<Integer, String>(mapRanking);

		for(Map.Entry<Integer, String> entry : treeMap.entrySet()){
           	player.add(entry.getValue());
           	score.add(entry.getKey());
		}
		
		System.out.println();
		System.out.println("-----------------------Victory Ranking--------------------------");
        System.out.println("Player Name" + "      		  Score");
        System.out.println("------------------------------------------");

        for(int i = player.size()-1; i >= 0 ; i--){
        	System.out.printf( "%d%c%-32s %d \n" , (player.size() - i),'.' ,player.get(i), score.get(i));
        }	

        System.out.println();
        System.out.println();
	}	
}