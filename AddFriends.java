
public class AddFriends{

	private int playerName;
	private int friendName;

	AddFriends(){}

	AddFriends(int player, int friend){
		setPlayer(player);
		setFriend(friend);
	}

	public void setPlayer(int player){this.playerName = player;}
	public void setFriend(int friend){this.friendName = friend;}

	public int getPlayer(){return playerName;}
	public int getFriend(){return friendName;}

}