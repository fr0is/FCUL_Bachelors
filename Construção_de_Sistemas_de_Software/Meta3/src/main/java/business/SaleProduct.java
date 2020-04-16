package business;


/**
 * A sale product.
 *	
 * @author fmartins
 * @version 1.1 (10/03/2015)
 *
 */
public class SaleProduct {

	// Sale product attributes 

	/**
	 * The product of the sale
	 */
	private Product product;

	/**
	 * The number of items purchased
	 */
	private double qty;
	

	// 1. constructors

	/**
	 * Creates a product that is part of a sale. The qty is the quantity of items in the sale. 
	 * 
	 * @param product The product to be associated with the sale.
	 * @param qty The number of products sold.
	 */
	public SaleProduct (Product product, double qty) {
		this.product = product;
		this.qty = qty;
	}
	

	// 2. getters and setters

	/**
	 * @return The product of the product sale
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return The quantity of the product sale
	 */
	public double getQty() {
		return qty;
	}

	/**
	 * @return The sub total of the product sale
	 */
	public double getSubTotal() {
		return qty * product.getFaceValue();
	}

	/**
	 * @return The eligible sub total of the product sale
	 */
	public double getEligibleSubtotal() {
		return product.isEligibleForDiscount() ? getSubTotal() : 0;
	}
}
