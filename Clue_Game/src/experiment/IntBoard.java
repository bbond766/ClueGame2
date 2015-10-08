package experiment;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	public IntBoard() {
		
	}
	
	public void calcAdjacencies(){

	}
	
	public void calcTargets(){

	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
		return null;
	}
}
