package soap.server.handlers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import utility.PrintHelper;

/**
 * @author Mike Sheliga 5.30.18
 * Service side handler that prints out the method name or fault in handleMessage and handleFault.
 * If used, this should generally be the first handler in the chain.
 * Methods aside from handleMessage and handleFault do not do anything.
 */
public class MethodNameHandler implements SOAPHandler<SOAPMessageContext> {
	
	public static final String handlerID = "SSH1"; // server soap handler #1

	/** 
	 * Prints the handlerClass, handlerType and direction.
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		printMethodName(context, "handleMessage");
		return true;
	} // end handleMessage
	
	/** 
	 * Prints the handlerClass, handlerType, soap method name or fault, and direction.
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		printMethodName(context, "handleFault");
		return true;  // proceed to further handlers.
	}
	
	/**
	 * Prints the handler class, handler method, direction and SOAP Method Name (or Fault).
	 * @param context - SOAP Message context including outbound property.
	 * @param handlerType - either handleMessage or handleFault
	 * @return - boolean with true indicating to continue processing handlers.
	 */
	private void printMethodName(SOAPMessageContext context, String handlerType) {
		Boolean outbound = (Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) {
			SOAPMessage sm = context.getMessage();
			if (sm==null) throw new NullPointerException("Soap Message in MethodNameHandler is null.");
			try {
				SOAPBody sb = sm.getSOAPBody();
				Iterator<SOAPBodyElement> iter = sb.getChildElements();
				if (iter==null || !(iter.hasNext())) {
					throw new SOAPException("Soap Body has no children in MethodNameHandler.");
				}
				SOAPBodyElement methodElement = iter.next();
				// methodElement.getNodeName(); - returns QName xxx:yyy format
				String methodName = methodElement.getLocalName();
				String msg = handlerID + ": MethodNameHandler: " + handlerType + 
						((outbound) ? " => ":" <= ") + methodName;
				System.out.println(msg);
			} catch (SOAPException e) {
				System.out.println("SOAPException in MethodNameHandler.");
				e.printStackTrace();
			}
		}				
	} // end printMethodName
	
	/** 
	 * Prints handler header info if showHandling is set.
	 */
	@Override
	public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) {
			System.out.println(handlerID + ": MethodNameHandler: close" + ((outbound)?" => ":" <= "));
			// This should be the first inbound handler and hence the last handler in 
			// the outbound chain => print a blank line for readability
			if (outbound) System.out.println();
		}
	} // end close
	
	@Override
	public Set<QName> getHeaders( ) {
		if (PrintHelper.showHandling) System.out.println(handlerID + ": MethodNameHandler: getHeaders");
		return null;
	}

} // end class MethodNameHandler (Server Harndler)
