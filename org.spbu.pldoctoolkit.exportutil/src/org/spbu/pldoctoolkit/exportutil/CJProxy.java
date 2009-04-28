package org.spbu.pldoctoolkit.exportutil;

/**
 * CJProxy is the Java side of the C-to-Java JNI interface.
 * Classes that implement CJProxy implement the command pattern
 */
public interface CJProxy
{
   public Object execute(Object args) throws Exception;
}

