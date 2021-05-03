package ex1;

public class ColoredRectangle extends Rectangle implements Cloneable {
	// @invariant 0 <= red, green, blue <= 255
	private Color color;
	// @requires h, w > 0
	// @requires 0 <= red, green, blue <= 255
	public ColoredRectangle (int h, int w, int red, int green, int blue) {
		super(h,w);
		color = new Color(red, green, blue);
	}
	public void darken() {
		color.darken();
	}
	
	@Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(obj != null && this.getClass().equals(obj.getClass()))  {
        	ColoredRectangle cr = (ColoredRectangle) obj;
            if(super.equals(cr) && (this.color.equals(cr.color) || (this.color == null && cr.color == null))){
                return true;
            }
        } 
        return false;
    }

	
	public Object clone() {
		return new ColoredRectangle(this.getHeight(),this.getWidth(),this.color.getRed(),this.color.getGreen(),this.color.getBlue());	
	}
}