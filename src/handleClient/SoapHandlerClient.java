package handleClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.ws.soap.SOAPFaultException;

import soap.client.handlers.ClientHandlersResolver;
import soap.client.handlers.GOFHandlersResolver;

public class SoapHandlerClient {
	public static final String NEW_LINE = System.lineSeparator();
	
	/** 
	 * Author - Mike Sheliga 5.24.18 
	 * Demonstrates several client side SOAP handlers and SOAP faults.
	 */
	public static void main(String[] args) {
		System.out.println("Starting SoapHandlerClient main . . . ");
		System.out.println("to demonstrate client side soap handlers and faults.");
		System.out.println("-----------------------------------------------------");
		System.out.println("PrintSoapHandler (Soap) - pretty prints soap msg.");
		System.out.println("DateHandler (Soap) - adds epoch seconds to soap header.");
		System.out.println("BornHandler (Logical) - makes sure parents are older than kids.");
		System.out.println("AgeHandler(Logical) - corrects negative ages.");
		// setup the web service port - use 2 soap handlers
		HandlerWSImplService service = new HandlerWSImplService();
		service.setHandlerResolver(new ClientHandlersResolver());
		HandlerWS port = service.getHandlerWSImplPort();
		
		// use service side handler to generate faults
		generateHandlerFaultsRandomly(port);
		// use web method exceptions to generate faults
		generateWebMethodFaultsRepeatedly(port);
		
		// Use the web service to get create data about mulitple families 
		// Much easier calling a web method than building these using artifact methods.
		List<Family> clans = port.createFamilies();
		if (clans.size() > 0) {
			Family aFamily = clans.get(0);
			System.out.println("Name of first family: " + aFamily.getSurName());
			System.out.println("Sum of child ages: " + port.addChildAges(aFamily));
			if (aFamily.getChildren().size() > 0) { 
				Person aChild = aFamily.getChildren().get(0);
				System.out.println("The first child: " + port.personToString(aChild));
			}
		} // end if clans.size > 0
		
		// update the client side handlers and use them to correct various data
		updateHandlersToCorrectAges(service, clans);
	} // end main
	
	/** generates handler faults (via the TimeoutHandler) randomly.
	 * The TimeoutHandler is written to only generate faults for
	 * the SayHi method.
	 * Precondition: TimeoutHandler is in the port's handler-chain.
	 * @param port - the web service port 
	 */
	public static void generateHandlerFaultsRandomly(HandlerWS port) {
		// The sayHi web method generates a random fault that simulates a 
		// timeout about 80% of the time. 
		System.out.println(NEW_LINE + 
			"=============================================================" + NEW_LINE +  
			" Randomly Generating Handler Timeout Faults " + NEW_LINE);
		System.out.println("Calling SayHi(Bob) ... => ");
		int count = 0;
		String sayHiResult = null;
		while (sayHiResult == null && count < 5) {
			count++;
			try {
				sayHiResult = port.sayHi("Bob");
			} catch (SOAPFaultException e) {
				if (e.getMessage().equals("Timeout Fault")) {
					System.out.println("Could not sayHi on try: " + count);
				} else {
					System.out.println("Unexpected exception in sayHi: " + e.getMessage());					
				}
			}
		}
		if (sayHiResult == null) {
			System.out.println("Web method sayHi failed " + count + " consecutive times. Giving up." + NEW_LINE);
		} else {
			System.out.println("SayHi(Bob) returned " + sayHiResult + 
				" after " + count + " tries." + NEW_LINE);	
		}		
	} // end generateHandlerFaultsRandomly
	
	/**
	 * Generates various SOAPFaults in a web method. 
	 * The getPersonAge web method generates various exceptions mapped to faults.
	 * These include a standard checked exception, an exception that extends the 
	 * Exception class, and another that extends Exception and includes a getFaultInfo method. 
	 * The age parameter is reduced to modify and finally eliminate the faults.
	 * @param port - the web service port to include getPersonAge
	 */
	public static void generateWebMethodFaultsRepeatedly(HandlerWS port) {
		System.out.println(NEW_LINE + 
			"=============================================================" + NEW_LINE +
			" Repeatedly Generating Web Method Faults of Varying Types" + NEW_LINE);
		Person Bob = new Person();
		Bob.setId(1L);
		Bob.setFirstName("Bob");
		Bob.setLastName("Marafka");
		Bob.setAge(142);
		boolean done = false;
		Integer age = null;
		while (!done) {
			try {
				age = port.getPersonAge(Bob);
				done = true;
			} catch (SOAPFaultException ex) {
				System.out.println("Age of " + Bob.getAge() + " generated a soap fault of: " + ex.getMessage());
				Bob.setAge(Bob.getAge() - 5);
			}
		} // end while !done
		System.out.println("The modified age of Bob is " + age);
	}	
	
	/**
	 * Generates various SOAPFaults in a web method. 
	 * The getPersonAge web method generates various exceptions mapped to faults.
	 * These include a standard checked exception, an exception that extends the 
	 * Exception class, and another that extends Exception and includes a getFaultInfo method. 
	 * The age parameter is reduced to modify and finally eliminate the faults.
	 * @param clans - a list of families
	 * @param service - the web service (needed to get new port after handlers changed).
	 */
	public static void updateHandlersToCorrectAges(HandlerWSImplService service, List<Family> clans) {
		// Change the default handler chains (include age and born handlers)
		System.out.println(NEW_LINE + 
			"=============================================================" + NEW_LINE + 
			"Dynamically updating handler chain to correct ages." + NEW_LINE);
		service.setHandlerResolver(new GOFHandlersResolver());
		HandlerWS port = service.getHandlerWSImplPort();
		System.out.println(NEW_LINE + "Info about family ages: ");
		System.out.println(port.getOldestFamily(clans));
		System.out.println("   .... DONE SoapHandlerClient main.");
	} // end updateHandlersToCorrectAges
	
	/*
	 * Removed - Easier to use multiple Eclipse console windows 
	 * Sets up a client side logger and file.
	 *  
	public static void setupLogging() {
		logger = Logger.getLogger("ClientSideLogger");
		System.out.println("Got logger ... user.home is: " + System.getProperty("user.home"));
		try {  // FileHandler does not implement auto-closeable
			Handler fh = new FileHandler("ClientSideFile");
			fh.setFormatter(new SimpleFormatter()); // use text instead of xml
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);
			logger.info("Completed client logging setup in main.");
			fh.flush();
			fh.close();
		} catch (SecurityException e) {
			System.out.println("SecurityException opening FileHandler in client main.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException opening FileHandler in client main.");
			e.printStackTrace();
		}		
	} 
	*/ 

} // end class SoapHandlerClient
