package org.spbu.pldoctoolkit.clones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public final class CloneFinder {

//	private static final String OUTPUT_DIR = "Output\\";
//	private static final String INPUT_DIR = "Input\\";
//	private static final String INPUT_FILE_CONTENT_DIR = INPUT_DIR;//CLONE_TOOL_DIR + "inputFiles\\";
	private static final String DEFAULT_CLONE_TOOL_DIR = "D:\\clone_tool\\";

	private static final String INPUT_FILE_POSTFIX= "Input\\InputFiles.txt";

	private static final String RESULT_FILE_POSTFIX= "Output\\Clones.txt";
	
	private static final String tmpFile4TextName = "tmpFile4CloneFinding.txt";
	private LangElem infEl;

	public List<IClonesGroup> findClones(LangElem infElementToFindOfClones, IProgressMonitor monitor){
		return findClones(DEFAULT_CLONE_TOOL_DIR, infElementToFindOfClones, monitor);
	}
	
	public List<IClonesGroup> findClones(LangElem infElementToFindOfClones){
		return findClones(infElementToFindOfClones, null);
	}
	
	public List<IClonesGroup> findClones(String cloneToolDirectory, LangElem infElementToFindOfClones, IProgressMonitor monitor) {
		List<IClonesGroup> rez = null;
		try{
			this.infEl = infElementToFindOfClones;
			if (monitor != null)
				monitor.subTask("CloneMiner working ...");
			long startInMillis = System.currentTimeMillis();
			File inputFile = new File(cloneToolDirectory + INPUT_FILE_POSTFIX);
			createFileWithTextInUtf8(inputFile, tmpFile4TextName);
			File fileWithText = new File(cloneToolDirectory + tmpFile4TextName);
			//TODO for CloneMiner utf16 version need use next line
			// createFileWithTextInUtf8(fileWithText,
			// infEl.getTextRepresentation());
			createFileWithText(fileWithText, infEl.getTextRepresentation(),
					"US-ASCII");

			CloneToolRunner cloneToolRunner = new CloneToolRunner();
			cloneToolRunner.runTool(cloneToolDirectory);
			while (!cloneToolRunner.completed())
				;
			if (monitor != null){
				monitor.worked(2);
				monitor.subTask("Parsing of CloneMiner results ...");
			}
			CloneToolResultsParser parser = new CloneToolResultsParser();
			List<ClonesGroupData> toolRez = parser.parse(cloneToolDirectory
					+ RESULT_FILE_POSTFIX);

			fileWithText.deleteOnExit();
			// inputFile.deleteOnExit();

			System.out.println("Time of CloneMiner run and parsing: "
					+ (System.currentTimeMillis() - startInMillis) / 1000
					+ " sec");
			if (monitor != null){
				monitor.worked(2);				
				monitor.subTask("Conversion of results ...");
			}
			rez = convertToolResultsToListOfClones(toolRez);
			System.out.println("Ok conversion");
			if (monitor != null){
				monitor.worked(10);				
				monitor.subTask("Filtration of results ...");
			}
			ClonesGroupsFilter filter = new ClonesGroupsFilter();
			rez = filter.specifyClonesGroups4DRL(rez);
			if (monitor != null){
				monitor.worked(10);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rez;
	}

	private List<IClonesGroup> convertToolResultsToListOfClones(
			List<ClonesGroupData> toolRez) {
		List<IClonesGroup> rez = new ArrayList<IClonesGroup>(toolRez.size());
		for (ClonesGroupData clonesGroupData : toolRez) {
			long startInMillis = System.currentTimeMillis();
			IClonesGroup cloneGroupImpl = new ClonesGroupImpl(clonesGroupData.clonesGroupId, clonesGroupData.termCount, clonesGroupData.clones.size());
			rez.add(cloneGroupImpl);
			for (CloneInstData cloneInstData : clonesGroupData.clones) {
				PositionInText trueEndPos = CloneInstImpl.findTrueLocalEndPosition(infEl, cloneInstData.endPos);
				ICloneInst cloneInst = CloneInstImpl.createCloneInstByLocalPositions(infEl, cloneInstData.startPos, trueEndPos);
				cloneGroupImpl.addCloneInst(cloneInst );
			}
			System.out.println("Processing clonesGroup : "+clonesGroupData.clonesGroupId
					+" finished. time: "+ (System.currentTimeMillis()-startInMillis) + " millis.");
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
					new FileOutputStream(file), Charset.forName(charsetName)));
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
