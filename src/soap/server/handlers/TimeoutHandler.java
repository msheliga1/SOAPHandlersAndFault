package soap.server.handlers;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;


import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.w3c.dom.Node;

import utility.PrintHelper;

/**
 * @author - Mike Sheliga 6.2.18
 * Randomly generates SOAPFaults on incoming handleMessage for sayHi routine.
 * Service side handler that mimics a web service request timing-out for the 
 * sayHi web method only.
 * Methods aside from the incoming handleMessage do not do anything except 
 * print that they have been called.
 */
public class TimeoutHandler implements SOAPHandler<SOAPMessageContext> {
	
	/** 
	 * Simulates a timeout error for the SayHi web method.
	 * A timeout fault will be thrown 80% of the time based upon a random number.
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		printHandlingInfo(context, "handleMessage");
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outbound) return true;
		SOAPMessage msg = context.getMessage();
		try {
			SOAPBody body = msg.getSOAPBody();
			Node methodNode = body.getFirstChild();
			String methodName = methodNode.getLocalName();
			Random rn = new Random();
			// hard coded to generate faults 80% of the time
			if (methodName.trim().equals("sayHi") && 
					rn.nextInt(100) < 80 ) {
				SOAPFault fault = body.addFault();
				fault.setFaultString("Timeout Fault");
				Detail detail = fault.addDetail();
				QName qName = new QName("tns", "TimeoutHandler");
				DetailEntry entry = detail.addDetailEntry(qName);
				entry.addTextNode("Timeout Detail Line1");
				DetailEntry entry2 = detail.addDetailEntry(qName);
				entry2.addTextNode("Randomly generated soap fault.");
				throw new SOAPFaultException(fault);
			}
		} catch (SOAPException e) {
			System.out.println("Could not add fault in TimeoutHandler: handleMessage");
			e.printStackTrace();
		}
		
		return true;  // timed-out, stop processing
	} // end handleMessage
	
	/** 
	 * Prints the handler class, name and direction.
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		printHandlingInfo(context, "handleFault");
		return true;  // proceed to further handlers.
	}
	
	/**
	 * Prints the handler class, name and direction.
	 * @param context - SOAP Message context including outbound property.
	 * @param handlerType - either handleMessage or handleFault
	 */
	private void printHandlingInfo (MessageContext context, String handlerType) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) {
			System.out.println("SSH2: TimeoutHandler: " + handlerType + ((outbound)?" => ":" <= "));
		}				
	} // end printHandlingInfo
	
	/** Prints the handler class, name and direction.
	 */
	@Override
	public void close(MessageContext context) {
		printHandlingInfo(context, "close");
	} // end close
	
	/** Prints the handler class, name and direction, then returns null.
	 */
	@Override
	public Set<QName> getHeaders( ) {
		if (PrintHelper.showHandling) System.out.println("SSH2: TimeoutHandler: getHeaders");
		return null;
	}

}
