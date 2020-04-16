package business;

/**
 * No discount whatsoever
 * 
 * @author fmartins
 * @version 1.1 (25/02/2015)
 *
 */
public class NoDiscount extends Discount {

	/**
	 * Creates a discount that always computes to 0 irrespectively of 
	 * the sale.
	 * 
	 * @param description The discount description
	 */
	public NoDiscount (String description) {
		super(description);
	}
	
	@Override
	public double computeDiscount(Sale sale) {	
		return 0;	
	}
	
}
