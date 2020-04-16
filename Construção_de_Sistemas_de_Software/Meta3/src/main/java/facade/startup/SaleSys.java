package facade.startup;

import business.AddCustomerHandler;
import business.CustomerCatalog;
import business.DiscountCatalog;
import business.ProcessSaleHandler;
import business.ProductCatalog;
import business.SaleCatalog;

/**
 * The main application class. It implements the  start up use case. 
 * 
 * @author fmartins
 * @author malopes
 * @version 1.1 (3/03/2018)
 *
 */
public class SaleSys {

	//the catalogs
	private CustomerCatalog customerCatalog;
	private SaleCatalog saleCatalog;
	private ProductCatalog productCatalog;

	/**
	 * The add customer use case handler
	 */
	private AddCustomerHandler addCustomerHandler;


	/**
	 * Performs the start up use case
	 */
	public SaleSys() {
		this.customerCatalog = new CustomerCatalog();
		this.productCatalog = new ProductCatalog();
		this.saleCatalog = new SaleCatalog();		
		addCustomerHandler = new AddCustomerHandler(customerCatalog, new DiscountCatalog());		
	}
	
	/**
	 * @return The add customer use case handler
	 */
	public AddCustomerHandler getAddCustomerHandler () {
		return addCustomerHandler;
	}

	/**
	 * @return The process sale use case handler
	 */
	public ProcessSaleHandler getProcessSaleHandler() {
		// always provides a new ProcessSaleHandler since it knows about the current sale (it is stateful)
		return new ProcessSaleHandler(saleCatalog, customerCatalog, productCatalog);	
	}
}
