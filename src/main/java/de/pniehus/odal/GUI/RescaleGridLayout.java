package de.pniehus.odal.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Iterator;

import com.sun.javafx.collections.MappingChange.Map;

/**
 * Scales up all components based on a grid of squares
 * 
 * TODO change -> no GapAlignment, alignment as double
 * 
 * The alignment determines where the component is placed, if the dimensions are
 * smaller than a full grid. Values smaller than 0.5f align the component left, 0.5f aligns the component
 * in the center and everything larger than 0.5f aligns the component right
 * Positions are  set in grid coordinates, the position in the grid is determined by
 * the alignment. A component smaller than a full grid cell
 * will be placed in the cell according to its alignment. 
 * 
 * The container which is layed out using this LayoutManager will keep its size calculated or set at
 * the initialization of this LayoutManager as its minimum size.
 * 
 *  The prefferedSize and minimum size of child components will be ignored, the components can't
 *  get any smaller than in the initial layout. The preferred size is ignored, because the ratio of
 *  the components height and width will stay unchanged with this layout.
 *  
 *  The maximum size will be ignored, if it has a different width/height ratio than the calculated size.
 *  If it is not ignored, the maximum size will increase the components insets in the grid, but the relative position
 *  of its center will stay unchanged.
 *   
 * @author Phil Niehus
 *
 */
public class RescaleGridLayout implements LayoutManager2 {

	/*
	 * minimum size = ((cellsX*cellSize) +
	 * (cellSize*leftInset)+(cellSize*rightInset)) * ((cellsY*cellSize) + (sellSize*TopInset) + (sellSize*bottomInset))
	 * 
	 */
	private final int initialCellSize; // sell size in px -> TODO not necessary
	private final int cellsX; // number of horizontal cells
	private final int cellsY; // number of vertical cells
	private final Dimension minimalSize;
	private final float horizontalAlignment;
	private final float verticalAlignment;
	private java.util.Map<Component, RescaleGridConstraints> components;
	
	/**
	 * Creates a RescaleGridLayout that fits into the given dimensions in pixles.
	 * The GapAlignments determine how the drawingArea aligns in the insets. More Info: {@link GapAlignment}
	 * @param dimensions The minimum dimensions of the final component (x = index 0, y = index 1)
	 * @param cellSize The size of the grid cells in pixels
	 * @param horizontalAlignment The horizontal alignment of the drawing area in the excess space
	 * @param verticalAlignment The horizontal alignment of the drawing area in the excess space
	 */
	public RescaleGridLayout(Dimension dimension, int cellSize, float horizontalAlignment, float verticalAlignment) {
		if(cellSize < 1) throw new IllegalArgumentException("Invalid cellSize! Size needs to be higher than 0!");
		if(dimension == null || dimension.getHeight() < (double) cellSize || dimension.getWidth() < (double) cellSize) throw new IllegalArgumentException("invalid dimensions!");
		RescaleGridLayout.evaluateAlignment(verticalAlignment);
		RescaleGridLayout.evaluateAlignment(horizontalAlignment);
		
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		
		this.initialCellSize = cellSize;
		this.minimalSize = dimension;
		
		this.cellsX = ((int) dimension.getWidth()) / cellSize;
		this.cellsY = ((int) dimension.getHeight()) / cellSize;
		
		components = new HashMap<Component, RescaleGridConstraints>();
	}

	/**
	 * Creates a new RescaleGrid with the given vertical and horizontal alignment of the drawing area,
	 * number of cells and cellSize. Take a look at {@link GapAlignment} for more information.
	 * @param cellsX The number of horizontal cells
	 * @param cellsY The number of vertical cells
	 * @param cellSize The size of a cell in pixels (squared cells)
	 * @param horizontalAlignment The horizontal alignment of the drawing Area in the excess space (uses cellSize as excess)
	 * @param verticalAlignment the vertical alignment of the drawing Area in the excess space (uses cellSize as excess)
	 */
	public RescaleGridLayout(int cellsX, int cellsY, int cellSize, float horizontalAlignment, float verticalAlignment) {
		if((cellsX <= 0) || (cellsY <= 0)) throw new IllegalArgumentException("Invalid cell count!");
		if(cellSize <= 0) throw new IllegalArgumentException("Invalid cellSize! Size must be bigger than 0!");
		RescaleGridLayout.evaluateAlignment(verticalAlignment);
		RescaleGridLayout.evaluateAlignment(horizontalAlignment);
		
		this.cellsX = cellsX;
		this.cellsY = cellsY;
		this.initialCellSize = cellSize;
		
		int x = (cellsX + 1) * cellSize;
		int y = (cellsY + 1) * cellSize;
		
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
		
		this.minimalSize = new Dimension(x, y);
		components = new HashMap<Component, RescaleGridConstraints>();
	}
	
	/**
	 * Constructs a grid of squares, which fits in the given dimensions, with the given number of cells and alignment
	 * @param dimension The dimensions the grid will fit in
	 * @param cellsX The number of horizontal cells
	 * @param cellsY The number of vertical cells
	 * @param horizontalAlignment The horizontal alignment of the drawing Area in the excess space
	 * @param verticalAlignment the vertical alignment of the drawing Area in the excess space
	 */
	public RescaleGridLayout(Dimension dimension, int cellsX, int cellsY, float horizontalAlignment, float verticalAlignment){
		if((cellsX < 1) || (cellsY < 1)) throw new IllegalArgumentException("Invalid cell size! At least 1 vertical and one horizontal cell required!");
		if(dimension == null) throw new IllegalArgumentException("Invalid dimension: null");
		if((cellsX > dimension.width) || (cellsY > dimension.height)) throw new IllegalArgumentException("This number of cells does not fit in the given dimensions!");
		RescaleGridLayout.evaluateAlignment(verticalAlignment);
		RescaleGridLayout.evaluateAlignment(horizontalAlignment);
		
		this.minimalSize = dimension;
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		int xSize = dimension.width/cellsX;
		int ySize = dimension.height/cellsY;
		
		this.cellsX = cellsX;
		this.cellsY = cellsY;
		this.initialCellSize = (xSize < ySize) ? xSize : ySize; // TODO re use formula
		components = new HashMap<Component, RescaleGridConstraints>();
	}
	
	/**
	 * Unused by this LayoutManager
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		
	}

	/**
	 * Lays out the container according to the grid position, size in grid units and alignment
	 */
	@Override
	public void layoutContainer(Container parent) {
		Dimension realParentDimension = getRealParentDimensions(parent);
		
		int cellSize = getCellSize(realParentDimension);
		
		int drawingAreaWidth = cellSize * cellsX;
		int drawingAreaHeight = cellSize * cellsY;
		
		Insets parentInsets = parent.getInsets();
		
		int verticalInsets = realParentDimension.height - drawingAreaHeight;
		int horizontalInsets = realParentDimension.width - drawingAreaWidth;
		
		int insetTop = parentInsets.top;
		int insetLeft = parentInsets.left;
				
		insetTop += Math.round(insetTop * verticalInsets);
		insetLeft += Math.round(insetLeft * horizontalInsets);
		
		
		Iterator iterate = components.entrySet().iterator();
		while(iterate.hasNext()){
			java.util.Map.Entry<Component, RescaleGridConstraints> entry = (java.util.Map.Entry<Component, RescaleGridConstraints>) iterate.next();
			Component c = entry.getKey();
			RescaleGridConstraints constraint = entry.getValue();
			
			int xMargin = insetLeft + (constraint.xPos * cellSize);
			int yMargin = insetTop + (constraint.yPos * cellSize);
			int height = Math.round(constraint.height * cellSize);
			int width = Math.round(constraint.width * cellSize);
			
			int componentVertInset = ((int) Math.ceil(constraint.height) * cellSize) - height;
			int componentHorInset = ((int) Math.ceil(constraint.width) * cellSize) - width;
			
			int xPos = xMargin + Math.round(componentHorInset * constraint.horizontalAlignment);
			int yPos = yMargin + Math.round(componentVertInset * constraint.verticalAlignment);
			
			c.setBounds(xPos, yPos, width, height);
			
		}
	}
	
	/**
	 * Returns the real dimensions of the parent container (dimensions when insets are applied)
	 * @param parent
	 * @return
	 */
	private Dimension getRealParentDimensions(Container parent){
		Insets insets = parent.getInsets();
		Dimension parentDimensions = parent.getSize();
		int parentWidth = parentDimensions.width - (insets.right + insets.left);
		int parentHeight = parentDimensions.height - (insets.bottom + insets.top);
		
		return new Dimension(parentWidth, parentHeight);
	}
	
	/**
	 * Calculates the cellSize according to the real parent dimensions
	 * @param parentDimension
	 * @return
	 */
	private int getCellSize(Dimension parentDimension){
		int xSize = parentDimension.width/cellsX;
		int ySize = parentDimension.height/cellsY;
		return (xSize < ySize) ? xSize : ySize;
	}
	
	/**
	 * Returns the minimal size the container which is layed out by this layout manager requires
	 * @param parent
	 * @return
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return minimalSize;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Figure out how this works
		return null;
	}
	
	/**
	 * Removes the given component from the layout
	 * @param comp
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		if(comp != null && components.containsKey(comp)){
			components.remove(comp);
		}
	}
	
	/**
	 * Adds a component with the given constraints to the layout
	 * @param comp
	 * @param constraints Constrains has to be an instance of {@link RescaleGridConstraints}
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints == null || constraints.getClass() != RescaleGridConstraints.class) throw new IllegalArgumentException("Invalid constraint object");
		if(comp == null) throw new IllegalArgumentException("Components my not be null!");
		components.put(comp, (RescaleGridConstraints) constraints);
	}

	/**
	 * Always returns center
	 * @param arg0
	 * @return
	 */
	@Override
	public float getLayoutAlignmentX(Container arg0) {
		// TODO EXPERIMENT
		return 0.5f;
	}

	/**
	 * Always returns center
	 * @param arg0
	 * @return
	 */
	@Override
	public float getLayoutAlignmentY(Container arg0) {
		// TODO EXPERIMENT
		return 0.5f;
	}

	/**
	 * Is ignored by this layout
	 */
	@Override
	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * This layout has no maximum size
	 * @param arg0
	 * @return
	 */
	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return null;
	}
	
	/**
	 * This method evaluates the alignment values, throws an IllegalArgumentException if the allignment is wrong
	 * @param alignment
	 */
	public static void evaluateAlignment(float alignment){
		if((alignment > 1f) || (alignment < 0f)) throw new IllegalArgumentException("Invalid alignment!");
	}
	
}
