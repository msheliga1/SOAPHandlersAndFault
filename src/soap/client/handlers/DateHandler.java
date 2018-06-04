package soap.client.handlers;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import utility.PrintHelper;

/**
 * 
 * @author Mike Sheliga 5.29.18
 * Inserts the current datetime into the soap message header. 
 * The datetime is represented using epochSeconds (as a long).
 * This is a client side soap handler intended for all web methods.
 *
 */
public class DateHandler implements SOAPHandler<SOAPMessageContext> {
	
	public static final String handlerID = "CSH1"; // Client Soap Handler #1
	
	/** 
	 * Inserts the current datetime into the SOAP header for outbound msgs.
	 * @param - SOAPMessageContext - includes outbound property.
	 * @return - a boolean where true indicates proceed with next handler.
	 */
	@Override public boolean handleMessage(SOAPMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": DateHandler: handleMessage" + ((outbound)?" => ":" <= "));
		if (!outbound) return true;
		try {
			SOAPMessage msg = context.getMessage();
			if (msg == null) throw new SOAPException("SOAP Message is null.");
			SOAPHeader header = msg.getSOAPHeader();
			if (header == null) {
				// possible to have msg without a header.  If so, insert one.
				SOAPPart sp = msg.getSOAPPart();
				if (sp == null) throw new SOAPException("SOAP Part is null.");
				SOAPEnvelope env = sp.getEnvelope();
				if (env == null) throw new SOAPException("SOAP Envelope is null.");
				header = env.addHeader(); 
			}
			if (header == null) throw new SOAPException("SOAP Header is null.");
			QName qName = new QName("http://soap.handle/", "RequestTime");
			SOAPHeaderElement she = header.addHeaderElement(qName);
			long epochSeconds = ZonedDateTime.now().toEpochSecond();
			// Could add a named subnode, but this is okay for test purposes.
			she.addTextNode("" + epochSeconds);
		} catch (SOAPException e) {
			System.out.println("Error adding time in DateHandler. Reason: " + e.getMessage());
			return false;  // stop processing
		}
		return true;
	} // end handleMesssage
	
	/**
	 * Prints handler info to standard output if showHandling is set.
	 * @param - SOAPMessageContext - includes outbound property.
	 * @return - a boolean where true indicates proceed with next handler.
	 */
	@Override public boolean handleFault(SOAPMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": DateHandler: handleFault" + ((outbound)?" => ":" <= "));
		return true; // proceed to next handler in chain
	}
	
	/**
	 * Prints handler info to standard output if showHandling is set.
	 * @return - a Set of QNames indicating understood MustUnderstand headers.
	 */
	@Override public Set<QName> getHeaders() {
		if (PrintHelper.showHandling) System.out.println(handlerID + ": DateHandler: getHeaders");
		return null; // new HashSet<QName>();
	}

	/**
	 * Prints handler info to standard output if showHandling is set.
	 * @param - MessageContext - various metadata about the message.
	 */
	@Override public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println(handlerID + 
			": DateHandler: close" + ((outbound)?" => ":" <= "));
	}

} // end class DateHandler implements SOAPHandler
