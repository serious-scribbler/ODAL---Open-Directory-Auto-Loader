package de.pniehus.odal.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

/**
 * Scales up all components based on a grid of squares
 * 
 * 
 * 
 * The alignment determines where the component is placed, if the dimensions are
 * smaller than a full grid. For example a width of 1.3 combined with a left
 * alignment and a gapRatio of 0.1 would result in the component being drawn 0.07*gridSize from the 
 * left boarder of the 1st of 2 grids.
 * Positions will be set in grid coordinates, the position in the grid is determined by
 * the alignment (like in the last example). A component smaller than a full grid cell
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
	private final int cellSize; // sell size in px
	private final int cellsX; // number of horizontal cells
	private final int cellsY; // number of vertical cells
	private final Dimension minimalSize;
	private final GapAlignment horizontalAlignment;
	private final GapAlignment verticalAlignment;
	
	/**
	 * Creates a RescaleGridLayout that fits into the given dimensions in pixles.
	 * The GapAlignments determine how the drawingArea aligns in the insets. More Info: {@link GapAlignment}
	 * @param dimensions The minimum dimensions of the final component (x = index 0, y = index 1)
	 * @param cellSize The size of the grid cells in pixels
	 * @param horizontalAlignment The horizontal alignment of the drawing area in the excess space
	 * @param verticalAlignment The horizontal alignment of the drawing area in the excess space
	 */
	public RescaleGridLayout(Dimension dimension, int cellSize, GapAlignment horizontalAlignment, GapAlignment verticalAlignment) {
		if(cellSize < 1) throw new IllegalArgumentException("Invalid cellSize! Size needs to be higher than 0!");
		if(dimension == null || dimension.getHeight() < (double) cellSize || dimension.getWidth() < (double) cellSize) throw new IllegalArgumentException("invalid dimensions!");
		if(horizontalAlignment == null || horizontalAlignment.getAlignmentType() != GapAlignment.HORIZONTAL_ALIGNMENT) throw new IllegalArgumentException("Invalid horizontal alignment!");
		if(verticalAlignment == null || verticalAlignment.getAlignmentType() != GapAlignment.VERTICAL_ALIGNMENT) throw new IllegalArgumentException("Invalid horizontal alignment!");
		
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		
		this.cellSize = cellSize;
		this.minimalSize = dimension;
		
		this.cellsX = ((int) dimension.getWidth()) / cellSize;
		this.cellsY = ((int) dimension.getHeight()) / cellSize;
			
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
	public RescaleGridLayout(int cellsX, int cellsY, int cellSize, GapAlignment horizontalAlignment, GapAlignment verticalAlignment) {
		if((cellsX <= 0) || (cellsY <= 0)) throw new IllegalArgumentException("Invalid cell count!");
		if(cellSize <= 0) throw new IllegalArgumentException("Invalid cellSize! Size must be bigger than 0!");
		if(horizontalAlignment == null || horizontalAlignment.getAlignmentType() != GapAlignment.HORIZONTAL_ALIGNMENT) throw new IllegalArgumentException("Invalid horizontal alignment!");
		if(verticalAlignment == null || verticalAlignment.getAlignmentType() != GapAlignment.VERTICAL_ALIGNMENT) throw new IllegalArgumentException("Invalid horizontal alignment!");
		this.cellsX = cellsX;
		this.cellsY = cellsY;
		this.cellSize = cellSize;
		
		int x = (cellsX + 1) * cellSize;
		int y = (cellsY + 1) * cellSize;
		
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
		
		this.minimalSize = new Dimension(x, y);
		
	}
	
	// TODO write a constructor that fits a grid with a set number of cells in an area of given size;
	
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * This layout has no alignment
	 */
	@Override
	public float getLayoutAlignmentY(Container arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Is ignored by this layout
	 */
	@Override
	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This object is used to store alignment and inset information for the {@link RescaleGridLayout}
	 * @author Phil Niehus
	 *
	 */
	public class GapAlignment{
		
		/**
		 * Describes the type of the {@see GapAlignment} as vertical
		 */
		public static final boolean VERTICAL_ALIGNMENT = false;
		
		/**
		 * Describes the type of the {@see GapAlignment} as horizontal
		 */
		public static final boolean HORIZONTAL_ALIGNMENT = true;
		
		/**
		 * Center alignment, can be used for vertical and horizontal alignment
		 */
		public static final int ALIGN_TO_CENTER = 0;
		
		/**
		 * Top alignment, can only be used for vertical alignment
		 */
		public static final int ALIGN_TO_TOP = 1;
		
		/**
		 * Bottom alignment, can only be used for vertical alignment
		 */
		public static final int ALIGN_TO_BOTTOM = 2;
		
		/**
		 * Left alignment, can only be used for horizontal alignment
		 */
		public static final int ALIGN_LEFT = -1;
		
		/**
		 * Right alignment, can only be used for horizontal alignment
		 */
		public static final int ALIGN_RIGHT = -2;
		
		/**
		 * Determines how big in percent of the access space the gap to the selected side should be
		 */
		private double gapRatio;
		
		/**
		 * The alignment type
		 */
		private boolean alignmentType;
		
		/**
		 * Holds the orientation
		 */
		private int alignTo;
		
		/**
		 * Creates an alignment which is used to align components in a container that uses the {@link RescaleGridLayout}
		 * This is also used to align elements in grid cells
		 * @param alignmentType The type of alignment either vertical or horizontal
		 * @param alignTo The direction the component is aligned to (left/center/right) or (top/center/left)
		 * @param gapRatio The gapRatio determines how much of the excess space is used as an inset on the side
		 * specified in alignTo for example: Grid size is 100, the components height 90, the vertical alignment top and the gapRatio 0.2
		 * this will lead position the component (10*0.2) = 2 pixels away from the grids top side
		 */
		public GapAlignment(boolean alignmentType, int alignTo, double gapRatio){
			this.alignmentType = alignmentType;
			if(alignmentType && alignTo > 0) throw new IllegalArgumentException("Invalid alignment (not horizontal): " + alignTo);
			if((!alignmentType) && alignTo < 0) throw new IllegalArgumentException("Invalid alignment (not vertical): " + alignTo);
			if(gapRatio >= 0.5 || gapRatio < 0) throw new IllegalArgumentException("Inavlid gapRatio required '0 <= gapRatio < 0.5' found: " + gapRatio);;
			this.alignTo = alignTo;
			this.gapRatio = gapRatio;
		}
		
		/**
		 * Returns the alignment
		 * @return
		 */
		public int getAlignMent(){
			return alignTo;
		}
		
		/**
		 * Returns the alignment type
		 * @return
		 */
		public boolean getAlignmentType(){
			return alignmentType;
		}
		
		/**
		 * Returns the gapRatio
		 * The gapRatio determines how much of the left over space is allocated to the inset (similar to a padding in css)
		 * in the alignment side (left over space * gapRatio = inset)
		 * @return
		 */
		public double getGapRatio(){
			return gapRatio;
		}
	}
}
