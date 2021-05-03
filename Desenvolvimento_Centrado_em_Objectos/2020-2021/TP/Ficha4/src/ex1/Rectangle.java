package ex1;

public class Rectangle implements Cloneable{
	// The width and height of the rectangle
	// @invariant width > 0 && height > 0
	private int width;
	private int height;
	/**
	 * Constructor of a rectangle with dimensions w x h
	 * @param w the width
	 * @param h the height
	 * @requires w > 0 && h > 0
	 * @ensures getWidth() == w && getHeight() == h;
	 */
	public Rectangle(int w, int h){
		width = w;
		height = h;
	}
	public double perimeter(){
		return 2 * (width + height);
	}
	/**
	 * Resizes the rectangle, maintaining the ratio
	 * @param perc the percentage
	 * @requires perc > 0;
	 * @ensures getWidth() == \old(getWidth()) * perc / 100 &&
	 * getHeight() == \old(getHeight()) * perc / 100;
	 */
	public void resize(int perc) {
		width = (width * perc) / 100;
		height = (height * perc) / 100;
	}
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	@Override
    public boolean equals(Object obj){
        if (this == obj)return true;
        if(obj != null && this.getClass().equals(obj.getClass()))   {
        	Rectangle r = (Rectangle) obj;
            if(this.width == r.width && this.height == r.height){
                return true;

            }
        } 
        return false;
    }

	
	public Object clone() {
		return new Rectangle(this.width,this.height);	
	}
}
