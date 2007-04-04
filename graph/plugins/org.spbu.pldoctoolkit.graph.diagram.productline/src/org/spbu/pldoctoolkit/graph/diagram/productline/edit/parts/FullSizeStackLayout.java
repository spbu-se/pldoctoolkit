/**
 * 
 */
package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * @author Alexey
 *
 */
public class FullSizeStackLayout extends StackLayout {

	private boolean needVertical;
	private boolean needHorizontal;

	public FullSizeStackLayout(boolean needVertical, boolean needHorizontal) {
		super();
		
		this.needVertical = needVertical;
		this.needHorizontal = needHorizontal;
	}

	/**
	 * @see org.eclipse.draw2d.StackLayout#layout(org.eclipse.draw2d.IFigure)
	 */
	@Override
	public void layout(IFigure figure) {
		Rectangle parentRectangle = figure.getParent().getClientArea();
		Rectangle myBoundsRectangle = figure.getBounds();
		
		if(needVertical) {
			myBoundsRectangle.height = parentRectangle.height;
		}
		
		if(needHorizontal) {
			myBoundsRectangle.width = parentRectangle.width;
		}

		figure.setBounds(myBoundsRectangle);

		super.layout(figure);
	}
}
