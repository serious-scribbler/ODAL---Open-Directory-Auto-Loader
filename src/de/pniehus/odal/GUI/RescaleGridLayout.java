package de.pniehus.odal.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;

/**
 * Scales up all containers based on a grid of squares
 * 
 * This layout is not recommended for positioning actual containers this layout
 * was designed to be used to lay out containers The original size of the
 * container will be used as minimum size The container will then be divided
 * into squares of the given size. Any excess space is used as inset
 * 
 * The ratio between the the horizopntal and vertical side of the
 * area will the content is drawn will be kept, unused space will be used as inset
 * 
 * The alignment determines where the component is placed, if the dimensions are
 * smaller than a full grid. For example a width of 1.3 combined with a left
 * alignment would result in the component being drawn to the left of the 1st of
 * 2 full grid cells While it is possible to use decimals for the dimension of
 * components, this layout will only allow integers to set the position. Gaps
 * can be created by making components Smaller than a full grid. Minimum and
 * preferred size are ignored, maximum size will increase the gap size if it
 * would be exceeded
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
	private final RescaleGridInset insets; // Stores the insets in grid units
											// (double)

	/**
	 * Will divide the area with the given size into a grid, excess space will
	 * be used as inset Minimal insets will be subtracted from the size for
	 * gridsize calculation. vertical and horizontal gap will be as close to
	 * equal (at corresponding sites) as possible
	 * 
	 * @param width
	 *            The initial container width in pixels
	 * @param height
	 *            The initial container height in pixels
	 * @param cellSize
	 *            the cellSize in pixels
	 * @param minVerticalInset
	 *            The minimal inset size in pixels for the top and bottom insets
	 * @param minHorizontalInset
	 *            The minimal inset size in pixels for the left and right side
	 */
	public RescaleGridLayout(int width, int height, int cellSize, int minVerticalInset, int minHorizontalInset) {
		this.cellSize = cellSize;
		cellsX = (width - (2 * minHorizontalInset))/cellSize;
		cellsY = (height - (2 * minVerticalInset))/cellSize;
		int excessY = (height - (2 * minVerticalInset)) - (cellSize * cellsY);
		int excessX = (width - (2 * minHorizontalInset)) - (cellSize * cellsX);
		// TODO change to maximum insets
		
	}

	/**
	 * Devides the container of cellsX*cellsY squared cells with a size of
	 * cellSize*cellSize Adds insets of the given size the container
	 * 
	 * @param cellsX
	 * @param cellsY
	 * @param cellSize
	 * @param insets
	 *            Specifies the in
	 */
	public RescaleGridLayout(int cellsX, int cellsY, int cellSize, RescaleGridInset insets) {
		// TODO implement
	}

	/**
	 * Unused by this LayoutManager
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	/**
	 * Lays out the container according to the base layout
	 */
	@Override
	public void layoutContainer(Container parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
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

	public class RescaleGridInset {
		private int pixelsTop;
		private int pixelsBottom;
		private int pixelsLeft;
		private int pixelsRight;
		
		private double gridUnitsTop;
		private double gridUnitsBottom;
		private double gridUnitsLeft;
		private double gridUnitsRight;
		
		public int getPixelsTop() {
			return pixelsTop;
		}

		public int getPixelsBottom() {
			return pixelsBottom;
		}

		public int getPixelsLeft() {
			return pixelsLeft;
		}

		public int getPixelsRight() {
			return pixelsRight;
		}

		public double getGridUnitsTop() {
			return gridUnitsTop;
		}

		public double getGridUnitsBottom() {
			return gridUnitsBottom;
		}

		public double getGridUnitsLeft() {
			return gridUnitsLeft;
		}

		public double getGridUnitsRight() {
			return gridUnitsRight;
		}

		/**
		 * Creates insets with pixels as base size, those will be scaled
		 * Insets can be larger to keep the ratio between sides, the ratio between insets will stay the same
		 * @param top
		 * @param left
		 * @param bottom
		 * @param right
		 */
		public RescaleGridInset(int top, int left, int bottom, int right){
			this.pixelsTop = top;
			this.pixelsLeft = left;
			this.pixelsBottom = bottom;
			this.pixelsRight = right;
		}
		
		/**
		 * Insets will be kept in the given ratio. minimal insets will be the given values*gridSize
		 * @param top
		 * @param left
		 * @param right
		 * @param bottom
		 */
		public RescaleGridInset(double top, double left, double right, double bottom){
			this.gridUnitsBottom = bottom;
			this.gridUnitsLeft = left;
			this.gridUnitsRight = right;
			this.gridUnitsTop = top;
		}
	}
}
