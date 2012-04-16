package org.spbu.pldoctoolkit.clones;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

final class CloneToolResultsParser {

	public List<ClonesGroupData> parse(String resultFileName) {
		BufferedReader in = null;
		List<ClonesGroupData> rez = new ArrayList<ClonesGroupData>();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					resultFileName)));
			String st = in.readLine();
			while (st != null) {
				if (!st.isEmpty()) {
					StringTokenizer mainTok = new StringTokenizer(st, ";");
					int clonesGroupId = Integer.valueOf(mainTok.nextToken());
					int termCount = Integer.valueOf(mainTok.nextToken());
					int countOfClones = Integer.valueOf(mainTok.nextToken());
					ClonesGroupData clonesGroup = new ClonesGroupData(clonesGroupId,
							termCount, countOfClones);
					for (int i = 0; i < countOfClones; i++) {
						st = in.readLine();
						StringTokenizer tokenizer = new StringTokenizer(st,
								":.-");
						int fileNumber = Integer.valueOf(tokenizer.nextToken());
						int startLine = Integer.valueOf(tokenizer.nextToken());
						int startColumn = Integer
								.valueOf(tokenizer.nextToken());
						int endLine = Integer.valueOf(tokenizer.nextToken());
						int endColumn = Integer.valueOf(tokenizer.nextToken());

						clonesGroup.addCloneInst(fileNumber, startLine,
								startColumn, endLine, endColumn);
					}
					rez.add(clonesGroup);
				}
				st = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rez;
	}

}
