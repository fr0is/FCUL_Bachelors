package facade.services;

import business.AddCustomerHandler;
import facade.exceptions.ApplicationException;

/**
 * A service the offers the addition of customer. The purpose of this class is to provide access 
 * to the business layer, hiding its implementation from the clients. Clients should never interact 
 * directly with the business layer. 
 * 
 * @author fmartins
 * @version 1.1 (16/2/2017)
 */
public class CustomerService {

	/**
	 * The business object façade that handles this use case request
	 */
	private AddCustomerHandler customerHandler;

    /**
     * Constructs a customer service given the add customer handler.
     * 
     * @param customerHandler The add customer handler 
     */
	public CustomerService(AddCustomerHandler customerHandler) {
		this.customerHandler = customerHandler;
	}
	
	/**
	 * Adds a customer given its VAT number, denomination, phone number,
	 * and discount type.
	 * 
	 * @param vat The VAT number of the customer to add to the system
	 * @param denomination The customer's denomination 
	 * @param phoneNumber The customer's phone number
	 * @param discountCode The customer's discount code
	 * @throws ApplicationException In case the customer could not be added.
	 */
	public void addCustomer(int vat, String denomination, int phoneNumber, 
			int discountType) throws ApplicationException {
		customerHandler.addCustomer(vat, denomination, phoneNumber, discountType);
	}
}