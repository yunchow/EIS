/**
 * copyright: EIS All rights reserved
 * author: nick.chow
 * date: Sep 18, 2013
 */
package com.eis.core.activiti;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;

/**
 * <p>
 * Please comment here
 * 
 * @author nick.chow
 * @date: Sep 18, 2013
 */
public class ProcessDiagramCanvasExt extends ProcessDiagramCanvas {
	protected static Color GREEN_COLOR = Color.GREEN;
	
	/**
	 * @param width
	 * @param height
	 */
	public ProcessDiagramCanvasExt(int width, int height) {
		super(width, height);
	}

	public ProcessDiagramCanvasExt(int width, int height, int minX, int minY) {
		super(width, height, minX, minY);
	}
	
	public void drawGreenHighLight(int x, int y, int width, int height) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();

		g.setPaint(GREEN_COLOR);
		g.setStroke(THICK_TASK_BORDER_STROKE);

		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
		g.draw(rect);

		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}

	public void drawRedHighLight(int x, int y, int width, int height) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();

		g.setPaint(HIGHLIGHT_COLOR);
		g.setStroke(THICK_TASK_BORDER_STROKE);

		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
		g.draw(rect);

		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}

}
