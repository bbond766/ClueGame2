package clueGame;

public class Solution {
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String room, String person, String weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}
	
	public String toString(){
		return person+", "+room+", "+weapon;
	}
}
