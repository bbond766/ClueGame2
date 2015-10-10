package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private int gridSize;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	public IntBoard(int initialSize) {
		gridSize = initialSize;
		grid = new BoardCell[gridSize][gridSize];
		for(int i=0; i < gridSize; i++)
			for(int j=0; j < gridSize; j++)
				grid[i][j] = new BoardCell(i, j);
		
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		calcAdjacencies();
	}
	
	public void calcAdjacencies(){
		for(int i=0; i < gridSize; i++)
			for(int j=0; j < gridSize; j++)
				adjMtx.put(grid[i][j], getAdjList(grid[i][j]));
	}
	
	public void calcTargets(BoardCell boardCell, int distance){
		visited = new HashSet<BoardCell>();
		targets = findAllTargets(boardCell, visited, distance);
	}
	private Set<BoardCell> findAllTargets(BoardCell boardCell, Set<BoardCell> oldVisited, int distance) {
		HashSet<BoardCell> visited = new HashSet<BoardCell>(oldVisited);
		visited.add(boardCell);

		HashSet<BoardCell> targets = new HashSet<BoardCell>();
		HashSet<BoardCell> adjacent = new HashSet<BoardCell>();
		LinkedList<BoardCell> cells = adjMtx.get(boardCell);
		
		for(BoardCell target : cells)
			if(!visited.contains(target))
				adjacent.add(target);

		if(distance == 1) {
			return adjacent;
		}
		
		for(BoardCell target : adjacent)
			targets.addAll(findAllTargets(target, visited, distance-1));
		
		return targets;
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell boardCell){
		LinkedList<BoardCell> list = new LinkedList<BoardCell>();
		if(boardCell.row - 1 >= 0)
			list.add(grid[boardCell.row - 1][boardCell.column]);
		if(boardCell.row + 1 < gridSize)
			list.add(grid[boardCell.row + 1][boardCell.column]);
		if(boardCell.column - 1 >= 0)
			list.add(grid[boardCell.row][boardCell.column - 1]);
		if(boardCell.column + 1 < gridSize)
			list.add(grid[boardCell.row][boardCell.column + 1]);
		
		return list;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
}
