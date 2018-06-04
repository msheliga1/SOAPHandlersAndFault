package soap.server.handlers;

import java.io.IOException;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

import utility.PrintHelper;


/** 
 * @author Mike Sheliga - 6.1.18
 * Implements a server side logical handler that prints out it is running.
 * This is used to confirm the handler running order. It has no business logic.
 *
 */
public class MarriageHandler implements LogicalHandler<LogicalMessageContext> {
	
	@Override
	public boolean handleMessage(LogicalMessageContext context) {
		Boolean outbound = (Boolean) context.get(LogicalMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("SLH1: MarriageHandler: handleMessage" + ((outbound)?" => ":" <= "));	
		// throw new IOException("test IO Exception");
		return true;
	}
	
	@Override
	public boolean handleFault(LogicalMessageContext context) {
		Boolean outbound = (Boolean) context.get(LogicalMessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("SLH1: MarriageHandler: handleFault" + ((outbound)?" => ":" <= "));
		return true;
	}
	
	@Override
	public void close(MessageContext context) {
		Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (PrintHelper.showHandling) System.out.println("SLH1: MarriageHandler: close" + ((outbound)?" => ":" <= "));
	}
 
}
