package de.pniehus.odal.GUI;

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
	
	/**
	 * Throws an exception if one of the given Alignments is of the wrong type
	 * (for example: horizontal alignment expected, vertical alignment found)
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 */
	public static void evaluateAlignments(GapAlignment horizontalAlignment, GapAlignment verticalAlignment){
		if(horizontalAlignment == null || horizontalAlignment.getAlignmentType() != GapAlignment.HORIZONTAL_ALIGNMENT) throw new IllegalArgumentException("Invalid horizontal alignment!");
		if(verticalAlignment == null || verticalAlignment.getAlignmentType() != GapAlignment.VERTICAL_ALIGNMENT) throw new IllegalArgumentException("Invalid vertical alignment!");
	}
}