package business;

/**
 * Objects of this class represent customers of SaleSys
 *	
 * @author fmartins
 * @author malopes
 * @version 1.1 (03/03/2018)
 *
 * 
 * Remarks:
 * 1. In this version of the application, some of the attributes are not
 * yet used. Notice that only the relevant getters and setters were included. 
 * That is how it should be!
 * 
 * 2. The isValidVAT method is included in this class, but notice it is a static method.
 * It is a "service" method and could be included in a class that provides these
 * kind of services. To keep it simple, it is included in this class.
 * 
 * 3. Notice the precondition in the constructor: only valid vat numbers are
 * accepted. Because it is required such a validation, we opted for making available
 * the isValidVAT as public static. 
 *
 */
public class Customer {

	// Customer attributes 

	/**
	 * Customer's VAT number
	 */
	@SuppressWarnings("unused") private int vatNumber;

	/**
	 * Customer's name. In case of a company, the represents its commercial denomination 
	 */
	@SuppressWarnings("unused") private String designation;

	/**
	 * Customer's contact number
	 */
	@SuppressWarnings("unused") private int phoneNumber;

	/**
	 * Customer's discount. This discount is applied to eligible products.
	 */
	private Discount discount;
	

	// 1. constructor 

	/**
	 * Creates a new customer given its VAT number, its designation, phone contact, and discount type.
	 * 
	 * @param vatNumber The customer's VAT number
	 * @param designation The customer's designation
	 * @param phoneNumber The customer's phone number
	 * @param discountType The customer's discount type
	 * @pre isValidVAT(vat) 
	 */
	public Customer(int vatNumber, String designation, int phoneNumber, Discount discountType) {
		this.vatNumber = vatNumber;
		this.designation = designation;
		this.phoneNumber = phoneNumber;
		this.discount = discountType;
	}

	
	// 2. getters and setters
	
	/**
	 * @return The discount type of the customer
	 */
	public Discount getDiscountType() {
		return discount;
	}

	/**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat The number to be checked.
	 * @return Whether the VAT number is valid. 
	 */
	static boolean isValidVAT(int vat) {
		// If the number of digits is not 9, error!
		if (vat < 100000000 || vat > 999999999)
			return false;
		
		// If the first number is not 1, 2, 5, 6, 8, 9, error!
		int firstDigit = vat / 100000000;
		if (firstDigit > 2 && firstDigit < 8 &&
			firstDigit != 5 && firstDigit != 6)
			return false;
		
		return vat % 10 == mod11(vat);
	}


	/**
	 * @param num The number to compute the modulus 11.
	 * @return The modulus 11 of num.
	 */
	private static int mod11(int num) {
		// Checks the congruence modules 11.
		int sum = 0;
		int quocient = num / 10;
		
		for (int i = 2; i < 10 && quocient != 0; i++) {
			sum += quocient % 10 * i;
			quocient /= 10;
		}
		
		int checkDigitCalc = 11 - sum % 11;
		return checkDigitCalc == 10 ? 0 : checkDigitCalc;
	}
	
}
