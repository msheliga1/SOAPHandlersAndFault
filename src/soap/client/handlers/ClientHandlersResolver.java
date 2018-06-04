package soap.client.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/** 
 * Implements a handler-chain that includes handlers for all HandlerWSImpl methods.
 * The DateHandler and PrintSoapHandler are applicable to all web methods. 
 * @author Mike Sheliga 6.2.18
 */
public class ClientHandlersResolver implements HandlerResolver {

	/** Implements "common" handlers for the HandlerWSImpl class 
	 *  Instead of checking the method type inside each handler, this 
	 *  routine can change the client side handlers dynamically.
	 */
	@Override
	public List<Handler> getHandlerChain(PortInfo portInfo) {
		List<Handler> handlers = new ArrayList<Handler>();
		handlers.add(new DateHandler());
		handlers.add(new PrintSoapHandler());  // must be last SOAP handler
		return handlers;
	}

}
