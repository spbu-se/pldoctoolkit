package org.spbu.pldoctoolkit.clones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

final class StringInfo {

	private String text = "";
	private final List<String> lines = new ArrayList<String>();
	private final List<Integer> absoluteIndexes = new ArrayList<Integer>();

	void setTextIfNeed(String text) {
		if (!text.equals(this.text)){
			long startInMillis = System.currentTimeMillis();
			this.text = text;
			lines.clear();
			absoluteIndexes.clear();
			BufferedReader r = new BufferedReader(new StringReader(text));
			try {
				int absoluteIndex = 0;
				for (String line = r.readLine(); line != null ; line = r.readLine()) {
					lines.add(line);
					absoluteIndexes.add(absoluteIndex);
					absoluteIndex+=line.length()+1;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("setTextIfNeed() method OK. time: "+ (System.currentTimeMillis()-startInMillis)/1000 + " sec");
		}
	}

	/**
	 * 
	 * @param index - minimum value is 1
	 * @return
	 */
	String getLineByNumber(int index) {
		return lines.get(index-1);
	}

	/**
	 * 
	 * @param index - minimum value is 1
	 * @return
	 */
	int getAbsoluteIndexOfFirstSymbolOfLineNumber(int line) {
		return absoluteIndexes.get(line-1);
	}

}
