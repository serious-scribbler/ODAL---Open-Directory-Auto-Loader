package de.pniehus.odal.GUI;

/**
 * This object stores how a component wants to be positioned
 * @author Phil Niehus
 *
 */
public class RescaleGridConstraints{
	
	/**
	 * The vertical alignment of the associated component
	 */
	public final float verticalAlignment;
	
	/**
	 * The horizontal alignment of the associated component
	 */
	public final float horizontalAlignment;
	
	/**
	 * The horizontal position of the associated component in the grid
	 */
	public final int xPos;
	
	/**
	 * The vertical position of the associated component in the grid
	 */
	public final int yPos;
	
	/**
	 * The height of the associated component
	 */
	public final float height;
	
	/**
	 * The width of the associated component
	 */
	public final float width;
	
	
	/**
	 * Creates a RescaleGridConstraint with center alignment and the given position and size
	 * @param xPos The horizontal grid cells coordinate, coordinates need to be >= 0
	 * @param yPos The vertical grid cells coordinate, coordinates need to be >= 0
	 * @param width The width of the object in grids, the width needs to b > 0, more info {@link RescaleGridLayout}
	 * @param height The width of the object in grids, the width needs to b > 0, more info {@link RescaleGridLayout}
	 */
	public RescaleGridConstraints(int xPos, int yPos, float width, float height){
		if((xPos < 0) || (yPos < 0)) throw new IllegalArgumentException("Positions must be >= 1!");
		if((width <= 0) || (height <= 0)) throw new IllegalArgumentException("Width and height must be bigger than 0");
		this.verticalAlignment = 0.5f;
		this.horizontalAlignment = 0.5f;
		this.yPos = yPos;
		this.xPos = xPos;
		this.height = height;
		this.width = width;
	}
	
	/**
	 * Creates a RescaleGridConstraint with the given alignment, position and size
	 * How to place components: more info {@link RescaleGridLayout}
	 * @param xPos The horizontal grid cells coordinate, coordinates need to be >= 0
	 * @param yPos The vertical grid cells coordinate, coordinates need to be >= 0
	 * @param width The width of the object in grids, the width needs to b > 0 
	 * @param height The width of the object in grids, the width needs to b > 0
	 * @param horizontalAlignment The horizontal alignment of the component in its grid cells
	 * @param verticalAlignment The vertical alignment of the component in its grid cells
	 */
	public RescaleGridConstraints(int xPos, int yPos, float width, float height, float horizontalAlignment, float verticalAlignment){
		if((xPos < 0) || (yPos < 0)) throw new IllegalArgumentException("Positions must be >= 1!");
		if((width <= 0) || (height <= 0)) throw new IllegalArgumentException("Width and height must be bigger than 0");
		RescaleGridLayout.evaluateAlignment(horizontalAlignment);
		RescaleGridLayout.evaluateAlignment(verticalAlignment);
		
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
		this.yPos = yPos;
		this.xPos = xPos;
		this.height = height;
		this.width = width;
	}
			
}

