package business;

import facade.exceptions.ApplicationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A sale
 *	
 * @author fmartins
 * @version 1.1 (25/02/2015)
 * 
 */
public class Sale {

	/**
	 * The date the sale was made 
	 * I'm suppressing the warning because the data attribute is not yet
	 * being used (in this version of the application)
	 */
	@SuppressWarnings("unused") private Date date;
		
	/**
	 * Whether the sale is open or closed. 
	 */
	private SaleStatus status;
	 
	/**
	 * The customer the sales refers to
	 */
	private Customer customer;
	
	/**
	 * The products of the sale
	 */
	private List<SaleProduct> saleProducts;
		
	
	// 1. constructor
	
	/**
	 * Creates a new sale given the date it occurred and the customer that
	 * made the purchase.
	 * 
	 * @param date The date that the sale occurred
	 * @param customer The customer that made the purchase
	 */
	public Sale(Date date, Customer customer) {
		this.date = date;
		this.customer = customer;
		this.status = SaleStatus.OPEN;
		this.saleProducts = new LinkedList<>();
	}

	
	// 2. getters and setters

	/**
	 * @return The sale's total 
	 */
	public double total() {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getSubTotal();
		return total;
	}

	/**
	 * @return The sale's amount eligible for discount
	 */
	public double eligibleDiscountTotal () {
		double total = 0;
		for (SaleProduct sp : saleProducts)
			total += sp.getEligibleSubtotal();
		return total;
	}
		
	/**
	 * @return Computes the sale's discount amount based on the discount type of the customer.
	 */
	public double discount () {
		Discount discount = customer.getDiscountType();
		return discount.computeDiscount(this);
	}

	/**
	 * @return Whether the sale is open
	 */
	public boolean isOpen() {
		return status == SaleStatus.OPEN;
	}

	/**
	 * Adds a product to the sale
	 * 
	 * @param product The product to sale
	 * @param qty The amount of the product being sold
	 * @throws ApplicationException if sale is closed or there is insuficient product
	 * in stock 
	 */
	public void addProductToSale(Product product, double qty) throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Cannot add products to a closed sale.");

		// if there is enough stock
		if (product.getQty() >= qty) {
			// adds product to sale
			product.setQty(product.getQty() - qty);
			saleProducts.add(new SaleProduct(product, qty));
		} else
			throw new ApplicationException("Product " + product.getProdCod() + " has stock ("  + 
							product.getQty() + ") which is insuficient for the current sale");
	}
	
	/**
	 * Closes the sale
	 * 
	 * @throws ApplicationException  if the sale already closed
	 */
	public void close() throws ApplicationException {
		if (!isOpen())
			throw new ApplicationException("Sale already closed.");
		status = SaleStatus.CLOSED;

	}
}
