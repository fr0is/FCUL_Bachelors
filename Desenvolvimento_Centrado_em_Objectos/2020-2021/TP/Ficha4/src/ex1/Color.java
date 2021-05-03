package ex1;

public class Color {

	private final static int CTE = 2;
	// @invariant 0 <= r, g, b <= 255
	private int red;
	private int green;
	private int blue;
	// @requires 0 <= r, g, b <= 255
	public Color(int r, int g,int b) {
		red = r;
		green = g;
		blue = b;
	}
	public void darken() {
		red = red - CTE >= 0 ? red - CTE : 0;
		blue = blue - CTE >= 0 ? blue - CTE : 0;
		green = green - CTE >= 0 ? green - CTE : 0;
	}
	
	@Override
	public boolean equals(Object obj){
	    if (this == obj)
	        return true;
	    if(obj != null && this.getClass().equals(obj.getClass()))   {
	    	Color c = (Color) obj;
	        if(this.red == c.red && this.green == c.green && this.blue == c.blue){
	            return true;

	        }
	    } 
	    return false;
	}

	
	public int getRed() {
		return red;
	}
	public int getGreen() {
		return green;
	}
	public int getBlue() {
		return blue;
	}
}