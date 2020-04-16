package business;

import facade.exceptions.ApplicationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A catalog of sales
 * 
  * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 * Remarks: The sales are stored in memory in a list. 
 *
 */
public class SaleCatalog {

	/**
	 * The sale's repository
	 */
	private List<Sale> sales;
		
	/**
	 * Constructs an empty sale's catalog
	 */
	public SaleCatalog() {
		this.sales = new LinkedList<>();
	}

	/**
	 * Creates a new sale and adds it to the repository
	 * 
	 * @param customer The customer the sales belongs to
	 * @return The newly created sale
	 */
	public Sale newSale (Customer customer) {
		Sale sale = new Sale(new Date(), customer);
		sales.add(sale);
		return sale;
	}

	/**
	 * Add the product to the given sale
	 * @param sale
	 * @param product
	 * @param qty
	 * @throws ApplicationException
	 */
	public void addProductToSale (Sale sale, Product product, double qty) throws ApplicationException {
		sale.addProductToSale(product, qty);
	}
}
