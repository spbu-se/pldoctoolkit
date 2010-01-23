package org.spbu.pldoctoolkit.refactor;

public class PositionInText {
	public int line, column;
	
	public PositionInText(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public PositionInText(PositionInText pos) {
		this.line = pos.line;
		this.column = pos.column;
	}
	
	// возвращает 1, если pos меньше	
	public int compare(PositionInText pos){
		int t = compare(this.line, pos.line);
		return (t != 0 ? t : compare(this.column, pos.column)); 
	}
	
	private int compare(int a, int b) {		
		if (a == b)
			return 0;
		if (a > b)
			return 1;
		else
			return -1;
	}
}
