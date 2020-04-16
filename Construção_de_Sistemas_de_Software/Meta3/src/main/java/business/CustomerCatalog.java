package business;

import facade.exceptions.ApplicationException;
import java.util.HashMap;
import java.util.Map;

/**
 * A catalog of customers
 * 
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 * Remarks: The customers are stored in memory in a map. 
 * 
 */
public class CustomerCatalog {

	/**
	 * The customer's repository
	 */
	private Map<Integer, Customer> customers;
	
	/**
	 * Constructs an empty customer's catalog
	 */
	public CustomerCatalog() {
		customers = new HashMap<> ();
	}
	
	/**
	 * Finds a customer given its VAT number.
	 * 
	 * @param vat The VAT number of the customer to fetch from the repository
	 * @return The Customer object corresponding to the customer with the vat number.
	 * @throws ApplicationException When the customer with the vat number is not found.
	 */
	public Customer getCustomer (int vat) throws ApplicationException {
		Customer c = customers.get(vat);
		if (c == null)
			throw new ApplicationException ("Customer not found.");
		return c;
	}
	
	/**
	 * Adds a new customer
	 * 
	 * @param vat The customer's VAT number
	 * @param designation The customer's designation
	 * @param phoneNumber The customer's phone number
	 * @param discountType The customer's discount type
	 * @throws ApplicationException When the customer is already in the repository or the 
	 * vat number is invalid.
	 */
	public void addCustomer (int vat, String designation, int phoneNumber, Discount discountType) 
			throws ApplicationException {
		if (!Customer.isValidVAT(vat))
			throw new ApplicationException ("Invalid VAT number");
		if (customers.containsKey(vat))
			throw new ApplicationException ("Customer already exists");
		customers.put(vat, new Customer(vat, designation, phoneNumber, discountType));
	}
}
