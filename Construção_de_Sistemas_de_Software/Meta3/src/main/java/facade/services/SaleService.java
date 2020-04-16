package facade.services;

import business.ProcessSaleHandler;
import facade.exceptions.ApplicationException;

/**
 * A service that offers operations to process a sale. 
 * The purpose of this class is to provide access to the business layer, hiding its 
 * implementation from the clients. 
 * 
 * @author fmartins
 * @version 1.1 (16/2/2017)
 */
public class SaleService {

	/**
	 * The business object façade that handles this use case request
	 */
	private ProcessSaleHandler saleHandler;

    /**
     * Constructs a process sale service given the process sale handler.
     * 
     * @param saleHandler The business process sale handler 
     */
	public SaleService(ProcessSaleHandler saleHandler) {
		this.saleHandler = saleHandler;
	}

	/**
	 * Adds a new sale given the customer's vat number that will purchase the goods
	 * 
	 * @param vat The customer's vat number
	 * @throws ApplicationException When the customer does not exists
	 */
	public void newSale(int vat) throws ApplicationException {
		saleHandler.newSale(vat);
	}

	/**
	 * Adds a product to the current sale
	 * 
	 * @param productCode The code of the product to add to the sale
	 * @param qty The quantity to purchase
	 * @throws ApplicationException When the product does not exists or there is 
	 * not enough product in stock to fulfill the request
	 */
	public void addProductToSale(int productCode, int qty) 
			throws ApplicationException {
		saleHandler.addProductToSale(productCode, qty);
	}

	/**
	 * @return The discount the customer has on the current sale
	 * @throws ApplicationException When there is not possible to obtain the information
	 * to compute the sale discount
	 */
	public double getSaleDiscount() throws ApplicationException {
		return saleHandler.getSaleDiscount();
	}
}
