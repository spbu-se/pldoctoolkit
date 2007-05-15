/**
 * 
 */
package org.spbu.pldoctoolkit.graph.diagram.infproduct.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

/**
 * @author Alexey Semenov
 *
 */
public class DestroyDrlElementCommand extends DestroyElementCommand {

	/**
	 * @param request
	 */
	public DestroyDrlElementCommand(DestroyElementRequest request) {
		super(request);
	}

	
}
