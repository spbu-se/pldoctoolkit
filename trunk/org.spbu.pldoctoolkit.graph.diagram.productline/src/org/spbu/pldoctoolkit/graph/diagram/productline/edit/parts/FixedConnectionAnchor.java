package org.spbu.pldoctoolkit.graph.diagram.productline.edit.parts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public class FixedConnectionAnchor extends AbstractConnectionAnchor {

	
	/**
	 * If true the connection will begin from the middle of the right side of
	 * the figure rectangle, from the left side otherwise. 
	 */
	private boolean rightConnection = true;

	public FixedConnectionAnchor(IFigure owner) {
		super(owner);
	}

	public FixedConnectionAnchor(IFigure owner, boolean rightConnection) {
		super(owner);
		this.rightConnection = rightConnection;
	}
	
	/**
	 * @see org.eclipse.draw2d.AbstractConnectionAnchor#ancestorMoved(IFigure)
	 */
	public void ancestorMoved(IFigure figure) {
		if (figure instanceof ScalableFigure)
			return;
		super.ancestorMoved(figure);
	}

	public Point getLocation(Point reference) {
		Rectangle r = getOwner().getBounds();
		int x, y;
		
		if(rightConnection) {
			x = r.right();
		} else {
			x = r.x;
		}
		
		y = r.y + r.height / 2;

		Point p = new PrecisionPoint(x, y);
		getOwner().translateToAbsolute(p);
		return p;
	}

	public Point getReferencePoint() {
		return getLocation(null);
	}

}
