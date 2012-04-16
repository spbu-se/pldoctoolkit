package org.spbu.pldoctoolkit.refactor;

public class PositionInText {
	private final static int MINIMUM_LINE = 1;
	private final static int MINIMUM_COLUMN = 1;

	public final int line, column;

	public PositionInText(int line, int column) {
		if (line < MINIMUM_LINE || column < MINIMUM_COLUMN)
			throw new IllegalArgumentException("Position line:" + line
					+ ",column:" + column);
		this.line = line;
		this.column = column;
	}

	public PositionInText(PositionInText pos) {
		this(pos.line, pos.column);
	}

	// возвращает 1, если pos меньше
	public int compare(PositionInText pos) {
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

	public String toString() {
		return line + "." + column;
	}
}
