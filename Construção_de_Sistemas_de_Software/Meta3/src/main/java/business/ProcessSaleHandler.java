package business;

import facade.exceptions.ApplicationException;

/**
 * Handles the process sale use case.
 * 
 * Remarks:
 * 1. In this version, the process sale use case starts with the
 * creation of the sale, after which it is possible to repeatedly add products to
 * this sale until it is closed (ending the use case). At any time after the creation 
 * of the sale, it is possible to get its current discount and total.
 *	
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 */
public class ProcessSaleHandler {
	
	/**
	 * The sale's catalog
	 */
	private SaleCatalog saleCatalog;

	/**
	 * The customer's catalog
	 */
	private CustomerCatalog customerCatalog;

	/**
	 * The product's catalog
	 */
	private ProductCatalog productCatalog;

	/**
	 * The current sale
	 */
	private Sale currentSale;
	
	/**
	 * Creates a handler for the process sale use case given 
	 * the sale, customer, and product catalogs.
	 * 
	 * @param saleCatalog A sale's catalog
	 * @param customerCatalog A customer's catalog
	 * @param productCatalog A product's catalog
	 */
	public ProcessSaleHandler (SaleCatalog saleCatalog, CustomerCatalog customerCatalog, 
							ProductCatalog productCatalog) {
		this.saleCatalog = saleCatalog;
		this.customerCatalog = customerCatalog;
		this.productCatalog = productCatalog;
	}

	/**
	 * Creates a new sale
	 * 
	 * @param vatNumber The customer's VAT number for the sale
	 * @throws ApplicationException In case the customer is not in the repository
	 */
	public void newSale (int vatNumber) throws ApplicationException {
		Customer customer = customerCatalog.getCustomer(vatNumber);
		currentSale = saleCatalog.newSale(customer);
	}

	/**
	 * Adds a product to the current sale
	 * 
	 * @param prodCod The product code to be added to the sale 
	 * @param qty The quantity of the product sold
	 * @throws ApplicationException When the sale is closed, the product code
	 * is not part of the product's catalog, or when there is not enough stock
	 * to proceed with the sale
	 */
	public void addProductToSale (int prodCod, double qty) 
					throws ApplicationException {
		Product product = productCatalog.getProduct(prodCod);			
		saleCatalog.addProductToSale(currentSale, product, qty);
	}

	/**
	 * @return The sale's discount, according to the customer discount type
	 */
	public double getSaleDiscount ()  {
		return currentSale.discount();
	}
	
	/**
	 * @return The sale's total
	 */
	public double getTotal ()  {
		return currentSale.total();
	}

	
	/**
	 * Closes the current sale
	 * 
	 * @throws ApplicationException When the sale is already closed
	 */
	public void closeSale () throws ApplicationException  {
		currentSale.close();
	}
}
