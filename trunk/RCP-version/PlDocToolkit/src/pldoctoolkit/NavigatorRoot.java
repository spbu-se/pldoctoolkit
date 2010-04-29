package pldoctoolkit;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.*;
import org.eclipse.ui.model.*;

public class NavigatorRoot implements IAdaptable, IPersistableElement, IElementFactory
{
	public NavigatorRoot()
	{
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter)
	{
		if (adapter == IPersistableElement.class) return this;
		if (adapter == IWorkbenchAdapter.class)
			return ResourcesPlugin.getWorkspace().getRoot().getAdapter(adapter);
		return null;
	}

	public String getFactoryId()
	{
		return this.getClass().getCanonicalName();
	}

	public void saveState(IMemento memento)
	{
		// TODO Auto-generated method stub
		return;
	}

	public IAdaptable createElement(IMemento memento)
	{
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}

