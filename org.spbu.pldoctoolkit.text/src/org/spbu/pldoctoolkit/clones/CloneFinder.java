package org.spbu.pldoctoolkit.clones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public final class CloneFinder {

//	private static final String OUTPUT_DIR = "Output\\";
//	private static final String INPUT_DIR = "Input\\";
//	private static final String INPUT_FILE_CONTENT_DIR = INPUT_DIR;//CLONE_TOOL_DIR + "inputFiles\\";
	private static final String DEFAULT_CLONE_TOOL_DIR = "D:\\clone_tool\\";

	private static final String INPUT_FILE_POSTFIX= "Input\\InputFiles.txt";

	private static final String RESULT_FILE_POSTFIX= "Output\\Clones.txt";
	
	private static final String tmpFile4TextName = "tmpFile4CloneFinding.txt";
	private LangElem infEl;


//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		String text = "str 1\n"
//			+"str 2 str\n"
//			+"str 3 str str";
//		CloneToolRunner.runTool(DEFAULT_CLONE_TOOL_DIR);
//		System.out.println("OK");
//	}

	public List<IClonesGroup> findClones(LangElem infElementToFindOfClones){
		return findClones(DEFAULT_CLONE_TOOL_DIR, infElementToFindOfClones);
	}
	
	public List<IClonesGroup> findClones(String cloneToolDirectory, LangElem infElementToFindOfClones) {
		this.infEl = infElementToFindOfClones;

		File inputFile = new File(cloneToolDirectory + INPUT_FILE_POSTFIX);
		createFileWithTextInUtf8(inputFile, tmpFile4TextName);
		File fileWithText = new File(cloneToolDirectory + tmpFile4TextName);
		createFileWithTextInUtf16(fileWithText, infEl.getTextRepresentation());

		CloneToolRunner.runTool(cloneToolDirectory);
		CloneToolResultsParser parser = new CloneToolResultsParser();
		List<ClonesGroupData> toolRez = parser.parse(cloneToolDirectory + RESULT_FILE_POSTFIX);

		fileWithText.deleteOnExit();
//		inputFile.deleteOnExit();

		return convertToolResultsToListOfClones(toolRez);
	}

	private List<IClonesGroup> convertToolResultsToListOfClones(
			List<ClonesGroupData> toolRez) {
		List<IClonesGroup> rez = new ArrayList<IClonesGroup>(toolRez.size());
		for (ClonesGroupData clonesGroupData : toolRez) {
			IClonesGroup cloneGroupImpl = new ClonesGroupImpl();
			rez.add(cloneGroupImpl);
			for (CloneInstData cloneInstData : clonesGroupData.clones) {
				ICloneInst cloneInst = new CloneInstImpl(infEl, cloneInstData.startPos, cloneInstData.endPos);
				cloneGroupImpl.addCloneInst(cloneInst );
			}
		}
		return rez;
	}

	private static void createFileWithTextInUtf16(File file, String text) {
		createFileWithText(file, text, "UTF-16");
	}
	
	private static void createFileWithTextInUtf8(File file, String text) {
		createFileWithText(file, text, "UTF-8");
	}
	
	private static void createFileWithText(File file, String text, String charsetName) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), charsetName));
			out.write(text);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
