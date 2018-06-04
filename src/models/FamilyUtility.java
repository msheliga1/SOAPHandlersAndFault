package models;

import java.util.Arrays;
import java.util.List;

public class FamilyUtility {
	
	/** 
	 * Builds and returns two families including errors.
	 * There are negative ages, too old people and children older than parents
	 * @return List<Family> - two sample families
	 */
	public static final List<Family> createFamilies() {
		List<Family> clans = null;
		
		Person grandpa = new Person(1, "Mike", "Marchevka", 202);
		Person grandma = new Person(2, "Anna", "Marchevka", 10);
		List<Person> parents = Arrays.asList(grandpa, grandma);
		Person mom = new Person(3, "Loretta", "Marchevka", 82);
		Person tom = new Person(4, "Tom", "Marchevka", -76);
		Person dolores = new Person(5, "Dolores", "Marchevka", 80);
		List<Person> kids = Arrays.asList(mom, tom, dolores);		
		Family marchevkas = new Family("Marchevka", parents, kids);	
		
		grandpa = new Person(11, "George", "Wargo", -22);
		grandma = new Person(12, "Helen", "Wargo", 10);
		parents = Arrays.asList(grandpa, grandma);
		Person cyril = new Person(13, "Cryil", "Wargo", -62);
		Person margaret = new Person(14, "Margaret", "Wargo", 61);
		Person martha = new Person(15, "Martha", "Wargo", 59);
		Person steve = new Person(16, "Steve", "Wargo", 48);
		kids = Arrays.asList(cyril, margaret, martha, steve);		
		Family wargos = new Family("Wargo", parents, kids);
		
		clans = Arrays.asList(marchevkas, wargos);
		return clans;
	} // end createFamilies

} // end FamilyUtility

