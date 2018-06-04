package soap.exception;

/**
 * @author Mike Sheliga 5.29.18
 * Implements an extended Checked Exception for SOAP fault testing.
 */
public class BasicCheckedException extends Exception {
	
	public BasicCheckedException() {
		super("Test Checked Exception for SOAP Faults.");
	}
	
	public BasicCheckedException(String msg) {
		super(msg);
	}

} // end BasicCheckedException
