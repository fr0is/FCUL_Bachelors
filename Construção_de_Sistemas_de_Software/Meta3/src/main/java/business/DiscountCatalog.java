package business;

import facade.exceptions.ApplicationException;
import java.util.HashMap;
import java.util.Map;

/**
 * A catalog of allowed discounts
 * 
 * @author fmartins
 * @version 1.1 (25/02/2015)
 *
 */
public class DiscountCatalog {

	/**
	 * The discount's repository
	 */
	private Map<Integer, Discount> discounts;
	
	/**
	 * Constructs a discount's catalog with the allowed discount types
	 */
	public DiscountCatalog() {
		discounts = new HashMap<> ();
		loadDiscounts();
	}
	
	/**
	 * Load the allowed discount types
	 */
	private void loadDiscounts() {
		discounts.put(1, new NoDiscount("Sem desconto"));
		discounts.put(2, new ThresholdPercentageDiscount("Percentagem do Total (acima de limiar)", 50, 0.1));
		discounts.put(3, new EligibleProductsDiscount("Percentagem do Total Elegivel", 0.15));		
	}

	/**
	 * Finds a Discount given its type
	 * 
	 * @param discountType The discount id to find
	 * @return The discount associated with the id
	 * @throws ApplicationException When the discount type is not found
	 */
	public Discount getDiscount (int discountType) throws ApplicationException {
		Discount d = discounts.get(discountType);
		if (d == null)
			throw new ApplicationException ("Discount type " + discountType + " not found.");
		return d;
	}
}
