package org.spbu.pldoctoolkit.exportutil.utils;

import java.io.File;
import java.util.List;

public interface ProjectRegistry {
	public RegisteredLocation getRegisteredLocation(String uri);
	public List<RegisteredLocation> findForFile(File file);
	public void registerDirectory(File dir) throws ExportExeption;
}
