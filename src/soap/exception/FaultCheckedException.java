package soap.exception;

/** @author - 5.30.18
 * A sample checked exception with a details and getFaultInfo method.
 * Used for testing SOAP faults.
 */
public class FaultCheckedException extends Exception {
	
	private String details;
	
	public FaultCheckedException(String reason, String details) {
		super(reason);
		this.details = details;
	}
	
	public String getFaultInfo() {
		return details;
	}

} // end FaultCheckedException
