package client;

import facade.exceptions.ApplicationException;
import facade.services.CustomerService;
import facade.services.SaleService;
import facade.startup.SaleSys;

/**
 * A simple application client that uses both application handlers.
 *	
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 */
public class SimpleClient {

	private CustomerService customerService;
	private SaleService saleService;
	
	public SimpleClient(CustomerService customerService, SaleService saleService) {
		this.customerService = customerService;
		this.saleService = saleService;
	}
	
	/**
	 * A simple interaction with the application services
	 */
	public void createACustomerAndThenASale() {
		
		// the interaction
		try {
			// adds a customer.
			customerService.addCustomer(168027852, "Customer 1", 217500255, 2);

			// starts a new sale
			saleService.newSale(168027852);

			// adds two products to the sale
			saleService.addProductToSale(123, 10);
			saleService.addProductToSale(124, 5);
			
			// gets the discount amount
			System.out.println(saleService.getSaleDiscount());
		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			// for debugging purposes only. Typically, in the application
			// this information can be associated with a "details" button when
			// the error message is displayed.
			if (e.getCause() != null) 
				System.out.println("Cause: ");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Creates the application main object
		SaleSys app = new SaleSys();
		
		// Creates the services used for interaction with the application
		CustomerService customerService = new CustomerService(app.getAddCustomerHandler());
		SaleService saleService = new SaleService(app.getProcessSaleHandler());
		
		// Creates the simple interaction client and passes it the application services
		SimpleClient simpleClient = new SimpleClient(customerService, saleService);
		simpleClient.createACustomerAndThenASale();
	}
}
