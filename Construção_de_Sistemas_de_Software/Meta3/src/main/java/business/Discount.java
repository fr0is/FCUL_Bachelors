package business;

/**
 * This abstract class represents the discounts available for customers
 * 
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 * Remarks:
 * 1. Note that there is NO attribute discoutType to register the type of a discount. 
 * This is discriminated by the subtypes of this class.
 * 
 * 2. The strategy GoF pattern was used here.
 * 
 */
public abstract class Discount {
	
	/**
	 * Discount description
	 */
	private String description;
	
	/**
	 * Creates a discount type given its description
	 * 
	 * @param description The discount description
	 */
	public Discount(String description) {
		this.description = description;
	}

	/**
	 * @return The discount description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param sale The sale to compute the discount on
	 * @return The discount amount using the current discount type
	 */
	public abstract double computeDiscount(Sale sale);
}
