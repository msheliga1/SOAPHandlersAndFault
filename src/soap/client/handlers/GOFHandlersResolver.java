package soap.client.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * @author Mike Sheliga 6.3.18
 * Implements a client side handler chain for the getOldestFamily method.
 *
 */
public class GOFHandlersResolver implements HandlerResolver {

	/** 
	 * Implements a dynamic client side handler chain for the getOldestFamily method.
	 * This handlers in this chain include special handling for ages and the relationships 
	 * between family members, as well as "common" SOAP handlers for adding dates to the 
	 * SOAP header and pretty printing the SOAP message.
	 */
	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlers = new ArrayList<Handler>();
		handlers.add(new AgeHandler());  // should be before born handler
		handlers.add(new DateHandler());
		handlers.add(new PrintSoapHandler());  // must be last SOAP handler
		handlers.add(new BornHandler());
		return handlers;
	}

}
