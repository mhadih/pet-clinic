package org.springframework.samples.petclinic.owner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class OwnerTheories {
	private static Owner owner;

	@DataPoints("newPets")
	public static String[] newPetNames = {"pet0", "pet1", "pet2", "pet3", "pet4", "pet5"};

	@DataPoints
	public static Boolean[] ignoreNew = {false, true};

	@BeforeClass
	public static void setup() {
		owner = new Owner();
		for (int i = 0; i < 6; i++) {
			Pet pet = new Pet();
			pet.setName("pet" + i);
			owner.addPet(pet);
			if (i % 2 == 0)
				pet.setId(i);
		}
	}

	@AfterClass
	public static void tearDown() {
		for (int i = 0; i < 6; i++) {
			owner.removePet(owner.getPet("pet" + i));
		}
		owner = null;
	}


	@Theory
	public void testGetPetNotException(String petName, Boolean ignoreNew) {
		//System.out.println(petName + " " + ignoreNew.toString());
		owner.getPet(petName);
		owner.getPet(petName, ignoreNew);
	}

	@Theory
	public void getPetTest(@FromDataPoints("newPets") String petName) {
		assertEquals(owner.getPet(petName).getName(), petName);
	}

	@Theory
	public void testGetPetIgnoreNewWithId(@FromDataPoints("newPets") String petName) {
		char lastChar = petName.charAt(petName.length()-1);
		int petId = Integer.parseInt(String.valueOf(lastChar));
		assumeTrue(petId % 2 == 0);  // filter pets with id
		assertEquals(owner.getPet(petName, true).getName(), petName);
	}

	@Theory
	public void testGetPetIgnoreNewWithoutId(@FromDataPoints("newPets") String petName) {
		char lastChar = petName.charAt(petName.length()-1);
		int petId = Integer.parseInt(String.valueOf(lastChar));
		assumeTrue(petId % 2 == 1);  // filter pets without id
		//System.out.println(petName);
		assertNull(owner.getPet(petName, true));
	}
}
