package soap.client.handlers;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import handleClient.Family;
import handleClient.GetOldestFamily;
import handleClient.ObjectFactory;
import utility.PrintHelper;

/**
 * @author Mike Sheliga = 5.30.18
 * Corrects having children older than parents by changing parent age. 
 * This is a client side handler for the getOldestFamily web method.
 * If parent age < max(child ages), parent age is "corrected" to oldest child age plus 20 years.
 * Also print info about each method to standard output if PrintHelper.showHandling is set.
 */
public class BornHandler implements LogicalHandler<LogicalMessageContext> {
	// Years between parent age and oldest child age
	private static final Integer GENERATION_YEARS = 20;
	private static final String handlerID = "CLH2";  // Client Logical Handler #2
	
	/** 
	 * Corrects ages of parents who are younger than their children.
	 * Client side handler for outbound messages. First prints handler info if showHandling is set.
	 * Then uses the context to get the LogicalMessage, sets up a jaxB Context, uses it and the 
	 * logical message to get the payload, which is then used to the the value.  If the value 
	 * is of type GetOldestFamily a complex stream is used to correct the age of parents 
	 * who are younger than any of their children. The parents new ages are set to 20 years older 
	 * than the oldest child. The new values are then written back to the logicalMessage using 
	 * the reverse of the methods used to extract the values from the logicalMessage.
	 */
	@Override 
	public boolean handleMessage(LogicalMessageContext context) {
		Boolean defaultReturn = true;  // allow msg to proceed even if ages not checked
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": BornHandler: handleMessage" + ((outbound)?" => ":" <= "));
		if (!outbound) return true;
		LogicalMessage msg = context.getMessage();
		if (msg==null) return defaultReturn; 
		JAXBContext jbc;
		try {
			jbc = JAXBContext.newInstance(ObjectFactory.class);
		} catch (JAXBException e) {
			System.out.println("Error in BornHandler. " + e.getMessage());
			e.printStackTrace();
			return defaultReturn;  
		}
		Object payload = msg.getPayload(jbc);
		if (payload == null) return defaultReturn;
		if (!(payload instanceof JAXBElement)) return defaultReturn;
		Object value = ((JAXBElement) payload).getValue(); 
		if (!(value instanceof GetOldestFamily)) return true; 
		GetOldestFamily gof = ((GetOldestFamily) value);
		
		List<Family> familyList = gof.getArg0();
		familyList.stream()
			.forEach(     // operate on each family separately
				f->f.getParents().stream()   
					.filter(parent-> parent.getAge() < getOldestChild(f))
					// .peek(p->System.out.print(p.getFirstName()+p.getAge()+", "))
					.forEach(p->p.setAge(getOldestChild(f) + GENERATION_YEARS))
			);
		((JAXBElement) payload).setValue(value);
		msg.setPayload(payload, jbc);
		return true; // proceed with next handler in the chain
	} // end handleMessage of BornHandler
	
	/** 
	 * Returns the age of the oldest child or zero if there are no children
	 * @param family - a family with zero or more children
	 * @return - the age of the oldest child in the family, or 0 if no children exist
	 */
	private static Integer getOldestChild(Family family) {
		return family.getChildren().stream()
			  .mapToInt(c->c.getAge())
			  .max()
			  .orElse(0);
		} // end getOldestChild
	
	/** 
	 * If showHandling is set print info about this handler to standard output. 
	 */
	@Override 
	public boolean handleFault(LogicalMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": BornHandler: handleFault" + ((outbound)?" => ":" <= "));
		return true;
	}
	
	/** 
	 * If showHandling is set print info about this handler to standard output. 
	 */
	@Override 
	public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": BornHandler: close" + ((outbound)?" => ":" <= "));
		return;
	}

}
