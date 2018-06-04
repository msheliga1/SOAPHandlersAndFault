package soap;

import java.io.IOException;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import models.Family;
import models.FamilyUtility;
import models.Person;
import soap.exception.BasicCheckedException;
import soap.exception.FaultCheckedException;

/** 
 * @author Mike Sheliga - May 2018
 * This web service implements several routines for testing SOAP handlers and faults.
 * A server side handler-chain is referenced using the @HandlerChain annotation.
 * The getPersonAge method throws several exceptions that will be converted to 
 * soap faults. The other routines are used by various handlers to demonstrates 
 * both handlers, exceptions and soap faults. Since handlers are meant to "not interfere" 
 * with normal programming logic, the handler details are not part of this class.
 */
@WebService(endpointInterface="soap.HandlerWS")
@HandlerChain(file="./server/handlers/handler-chain.xml")
public class HandlerWSImpl implements HandlerWS {
	private static final String NEW_LINE = System.lineSeparator();
	
	/** Returns a constant string with a name appended \
	 */
	@Override
	public String sayHi(String name) {
		return "HandlerWSImpl->sayHi " + name;
	}
	
	/** Converts a person object to a string.
	 */
	@Override
	public String personToString(Person p1) {
		return p1.getFirstName() + " " + p1.getLastName() + " Age: " + p1.getAge();
	}
	
	/** Sums a list of integers 
	 */
	@Override
	public String addIntList(List<Integer> ints) {
		int sum = 0;
		for (Integer intValue: ints) {
			sum += intValue;
		}
		return "Sum is " + sum;
	} // end addIntList

	/**
	 * Generates various types of soap faults based upon a person's age. 
	 * This routine demonstrates that even if a "normal" exception such as IOException 
	 * or NPE (as opposed to a user defined exception with a detail field) is thrown, 
	 * it becomes a SOAPFaultException and handleFault will be called. 
	 * This is true for both server side handlers (outbound ones in this case) as well as 
	 * client side handlers.
	 */
	@Override
	public int getPersonAge(Person p1) 
			throws IOException, BasicCheckedException, FaultCheckedException {
		int age = p1.getAge();
		// throw exceptions of various types so that their soap fault outputs 
		// can be seen.
		if (age > 140) throw new NullPointerException("Too old NPE.");
		if (age > 135) throw new IOException("Too old IOException.");
		if (age > 130) throw new BasicCheckedException("Too old BasicChecked");
		if (age > 125) throw new FaultCheckedException("Too old reason", "Fault Detail");
		return p1.getAge();
	} // end getPersonAge
	
	@Override
	/* Sum the ages of all children in a family. 
	 */
	public int addChildAges(Family family) {
		return family.getChildren()
					 .stream()
					 .mapToInt(person->person.getAge())
					 .sum();
	} // end addChildAges
		
	@Override
	/* Sums the ages of all family members and returns the maximum total.
	 * Returns a string showing the names and ages of all family members, 
	 * along with a sum of the ages for each family, and the name 
	 * and total years for the family with the greatest sum of ages.
	 * The string is returned for readability and debugging. A typical web 
	 * service would normally just return the oldest family object.
	 */
	public String getOldestFamily(List<Family> families) {
		String result = "Finding oldest family among: " + NEW_LINE;
		int oldestSum = 0; // initial value
		Family oldestFamily = null;
		for (Family family: families) {
			result += " " + family.getSurName();
			int parentSum = 0;
			result += ": (Parents: ";
			for (Person parent: family.getParents()) {
				parentSum += parent.getAge();
				result += " " + parent.getFirstName() + " (" + parent.getAge() + ") ";
			}
			result += " ==> " + parentSum + ", " + NEW_LINE;
			int childSum = 0;
			result += "          + Children: ";
			for (Person child: family.getChildren()) {
				childSum += child.getAge();
				result += " " + child.getFirstName() + " (" + child.getAge() + ") ";
			}
			result += " ==> " + childSum + ", " + NEW_LINE;
			int totalSum = parentSum + childSum;
			result += "        ==> " + totalSum + NEW_LINE;
			if (oldestFamily == null || totalSum > oldestSum) {
				oldestFamily = family;
				oldestSum = totalSum;
			} // end if new oldest family found
		} // end for all families
		return result += " The family with the greatest sum of ages is the " + oldestFamily.getSurName() + 
				" family (" + oldestSum + " years).";
	} // end getOldestFamily

	/** Creates a list of families.
	 * This is much easier than creating the same list on the client side using only
	 * client side artifacts.
	 */
	@Override
	public List<Family> createFamilies() {
		return FamilyUtility.createFamilies();
	}
	
} // end class HandlerWSImpl
