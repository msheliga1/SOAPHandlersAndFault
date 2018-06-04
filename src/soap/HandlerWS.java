package soap;

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import models.Family;
import models.Person;
import soap.exception.BasicCheckedException;
import soap.exception.FaultCheckedException;

/** 
 * 
 * @author Mike Sheliga - 5.25.18
 * Interface for web service methods demonstrating handlers and faults.
 *
 */
@WebService
public interface HandlerWS {
	
	@WebMethod public String sayHi(String name);
	
	@WebMethod public int getPersonAge(Person person) 
			throws NullPointerException, IOException, BasicCheckedException, FaultCheckedException;

	@WebMethod String personToString(Person person);

	// all are webMethods unless explicitly noted otherwise.
	String addIntList(List<Integer> ints);

	String getOldestFamily(List<Family> families);

	int addChildAges(Family family);

	List<Family> createFamilies();

} // end HandlerWS interface
