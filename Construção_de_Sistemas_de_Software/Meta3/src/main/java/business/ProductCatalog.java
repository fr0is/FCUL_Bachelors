package business;

import facade.exceptions.ApplicationException;
import java.util.HashMap;
import java.util.Map;

/**
 * A catalog of products
 * 
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 * Remarks: The products are stored in memory in a map. 
 *
 */
public class ProductCatalog {

	/**
	 * The product's repository
	 */
	private Map<Integer, Product> products;
	
	/**
	 * Constructs a product's catalog with the predefined products and stocks
	 */
	public ProductCatalog() {
		products = new HashMap<> ();
		loadProducts();
	}
	
	/**
	 * Load predefined products
	 */
	private void loadProducts() {
		products.put(123, new Product(123, "Prod 1", 100, 500, false, new Unit()));
		products.put(124, new Product(124, "Prod 2", 35, 1000, true, new Unit()));
	}

	/**
	 * Finds a product given its code
	 * 
	 * @param prodCod The code of the product to find
	 * @return The product associated with the product code
	 * @throws ApplicationException When the product with a given prodCod is not found
	 */
	public Product getProduct (int prodCod) throws ApplicationException {
		Product p = products.get(prodCod);
		if (p == null)
			throw new ApplicationException ("Product with code " + prodCod + " does not exist");
		return p;
	}

	
}
