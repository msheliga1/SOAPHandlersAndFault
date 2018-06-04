package soap.client.handlers;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import soap.saaj.soapmessage.SOAPMessageUtility;
import utility.PrintHelper;

/** 
 * 
 * @author Mike Sheliga 5.29.18
 * Prints the soap message (normal or fault) including line breaks. 
 * This is a client side SOAP handler.
 * Normally this handler should be last in the handler chain so that any
 * modifications made by other handlers can be seen.
 *
 */
public class PrintSoapHandler implements SOAPHandler<SOAPMessageContext> {
	public static final String NEW_LINE = System.lineSeparator();
	
	@Override public boolean handleMessage(SOAPMessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("CSH2: PrintSoapHandler: handleMessage" + ((outbound)?" => ":" <= "));
		SOAPMessage msg = context.getMessage();
		System.out.println(NEW_LINE + "The " + (outbound?"outbound ":"inbound ") + "SOAP Message: ");
		System.out.println(SOAPMessageUtility.soapMessageToString(msg) + NEW_LINE);
		return true;
	}
	
	@Override public boolean handleFault(SOAPMessageContext context) {
		printSOAPMessage(context, "handleFault");
		return true;
	}
	
	private void printSOAPMessage(SOAPMessageContext context, String methodName) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("CSH2: PrintSoapHandler: " + 
				methodName + ((outbound)?" => ":" <= "));
		SOAPMessage msg = context.getMessage();
		System.out.println(NEW_LINE + "The " + (outbound?"outbound ":"inbound ") + "SOAP Message: ");
		System.out.println(SOAPMessageUtility.soapMessageToString(msg) + NEW_LINE);
	}
	
	// Used for mustUnderstandHeaders
	@Override public Set<QName> getHeaders() {
		if (PrintHelper.showHandling) System.out.println("CSH2: PrintSoapHandler: getHeaders");
		return null;
	}

	@Override  // Note this is MessageContext, not SOAPMessageContext
	public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("CSH2: PrintSoapHandler: close" + ((outbound)?" => ":" <= "));	
	}

}
