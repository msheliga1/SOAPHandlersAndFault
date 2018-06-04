package soap.client.handlers;

import java.util.Arrays;
import java.util.List;
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
import handleClient.Person;
import utility.PrintHelper;

/** 
 * 
 * @author Mike Sheliga 5.29.18
 * Corrects negative ages by changing them to positives.
 * This is a client side Logical Handler for the GetOldestFamily web method.
 */
public class AgeHandler implements LogicalHandler<LogicalMessageContext> {
	
	public static final String handlerID = "CLH1";  // Clientside Logical Handler #1 

	/**
	 * Changes negative ages to positive ages for GetOldestFamily routine.
	 * Routine first prints handler info to standard output if showHandling is set. 
	 * This is an outbound client handler so ages are corrected before being sent 
	 * to the web method.
	 */
	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": AgeHandler: handleMessage" + ((outbound)?" => ":" <= "));
		if (!outbound) return true;
		LogicalMessage msg = context.getMessage();
		// Using objectFactory here will "recognize" many classes 
		// such as method1, method1Response, method2, method2Response, etc.
		JAXBContext jbc = null;
		try {
			jbc = JAXBContext.newInstance(ObjectFactory.class);
		} catch (JAXBException e) {
			System.out.println("JAXBException in AgeHandler ... continuing");
			e.printStackTrace();
			return true;
		}
		// msg.getPayload() returns a Source ... harder to work with
		Object payload = msg.getPayload(jbc);
		if (!(payload instanceof JAXBElement)) return true;
		JAXBElement jbe = (JAXBElement) payload;
		Object obj = jbe.getValue();
		if (obj == null) return true;
		// This handler is only for the GetOldestFamilyMethod
		if (!(obj instanceof GetOldestFamily)) return true;

		GetOldestFamily gof = (GetOldestFamily) obj;
		// There is no setParents since getParents returns a list that 
		// can be modified.  .collect(Collectors.toList());
		// Note how poor the names are here .. there may be multiple arg0s...
		List<Family> familyList2 = gof.getArg0();
		List<Person> parentList = familyList2.stream()
			.map(fam->fam.getParents())
			.flatMap(p->p.stream())
			.collect(Collectors.toList());
		List<Family> familyList = gof.getArg0();
		List<Person> kidList = familyList.stream()
			.map(fam->fam.getChildren())
			.flatMap(p->p.stream())
			.collect(Collectors.toList());
		// Combine parent, children lists, flatMap, filter, update
		Arrays.asList(parentList, kidList).stream()
			.flatMap(p->p.stream())
			.filter(p->p.getAge() < 0)          // main filter
			.forEach(p->p.setAge(-p.getAge())); // convert negatives to positives
		// no gof.setArg0 since its a list (change list members instead).
		((JAXBElement) payload).setValue(gof);
		msg.setPayload(payload, jbc);
		return true;  // continue with next handler
	} // end handleMessage

	/** 
	 * Prints info about the handler to standard output if showHandling is set.
	 */
	@Override
	public boolean handleFault(LogicalMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": AgeHandler: handleFault" + ((outbound)?" => ":" <= "));
		return true;
	}

	/** 
	 * Prints info about the handler to standard output if showHandling is set.
	 */
	@Override
	public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) {
			System.out.println(handlerID + 
				": AgeHandler: close" + ((outbound)?" => ":" <= "));
			System.out.println(""); // normally last client handler in chain, print blank line
		}
	}

} // end class AgeHandler implements LogicalHandler
