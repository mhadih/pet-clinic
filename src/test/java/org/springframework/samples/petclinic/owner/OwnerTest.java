package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OwnerTest {
	private Owner owner;
	private Pet dog;
	private Pet cat;

	private final static String DOG_NAME = "Hadi";
	private final static String CAT_NAME = "Ehsan";

	@BeforeEach
	public void setUp() {
		owner = new Owner();

		dog = new Pet();
		dog.setName(DOG_NAME);

		cat = new Pet();
		cat.setName(CAT_NAME);
	}

	@After
	public void tearDown()
	{
		dog = null;
		cat = null;
	}

	// getters & setters
	@Test
	public void addressTest()
	{
		String address = "shariati, pol sadr, pellak 5";
		owner.setAddress(address);
		assertEquals(owner.getAddress(), address);
		assertTrue((owner.getAddress().equals(address)));
	}

	@Test
	public void telephoneTest()
	{
		String telephone = "0936777598";
		owner.setTelephone(telephone);
		assertEquals(owner.getTelephone(), telephone);
	}

	@Test
	public void cityTest()
	{
		String city = "tehran";
		owner.setCity(city);
		assertEquals(owner.getCity(), city);
	}

	@Test
	public void petsInternalTest()
	{
		Set<Pet> pets = new HashSet<>();
		assertEquals(owner.getPetsInternal(), pets);
		pets.add(cat);
		pets.add(dog);
		owner.setPetsInternal(pets);
		assertEquals(owner.getPetsInternal(), pets);
		assertEquals(owner.getPetsInternal().size(), 2);
	}

//	@Test
//	public void testGetPetsIsSorted()
//	{
//		Set<Pet> pets = new HashSet<>();
//		pets.add(cat);
//		pets.add(dog);
//		owner.setPetsInternal(pets);
//		List<Pet> pets_list = owner.getPets();
//		for (int ind=0; ind<pets_list.size()-1; ind++)
//			if (pets_list.get(ind) < pets_list.get(ind-1))
//				fail("getPets method returned unsorted list");
//	}

	@Test
	public void addPetTest()
	{
		owner.addPet(dog);
		assertEquals(owner.getPet(dog.getName()), dog);
	}

	@Test
	public void testAddPetForSize()
	{
		assertTrue(owner.getPets().isEmpty());
		owner.addPet(dog);
		assertEquals(owner.getPets().size(), 1);
		owner.addPet(cat);
		assertEquals(owner.getPets().size(), 2);
	}

	@Test
	public void testAddedPetOwner()
	{
		owner.addPet(dog);
		assertEquals(dog.getOwner(), owner);
		String city = "tehran";
		owner.setCity(city);
		assertEquals(dog.getOwner().getCity(), city);
	}

	@Test
	public void testDuplicateAddPet()
	{
		owner.addPet(dog);
		owner.addPet(dog);
		assertEquals(owner.getPetsInternal().size(), 1);
		owner.addPet(cat);
		assertEquals(owner.getPetsInternal().size(), 2);
	}

	@Test
	public void removePetTest()
	{
		owner.addPet(dog);
		assertTrue(owner.getPetsInternal().contains(dog));
		owner.removePet(dog);
		assertFalse(owner.getPetsInternal().contains(dog));
	}

	@Test
	public void testRemovePetForSize()
	{
		owner.addPet(dog);
		owner.addPet(cat);
		assertEquals(owner.getPetsInternal().size(), 2);
		assertEquals(owner.getPets().size(), 2);
		owner.removePet(dog);
		assertEquals(owner.getPetsInternal().size(), 1);
		assertEquals(owner.getPets().size(), 1);
	}

	@Test
	public void testRemovePetNotExist()
	{
		assertEquals(owner.getPetsInternal().size(), 0);
		owner.removePet(dog);
		assertEquals(owner.getPetsInternal().size(), 0);
	}

	@Test
	public void testMultiRemovePet()
	{
		owner.addPet(dog);
		owner.addPet(cat);
		owner.removePet(dog);
		owner.removePet(dog);
		assertEquals(owner.getPetsInternal().size(), 1);
	}

	@Test
	public void testGetRemovedPet()
	{
		owner.addPet(dog);
		owner.removePet(dog);
		assertNull(owner.getPet(dog.getName()));
	}

	@Test
	public void getPetTest()
	{
		owner.addPet(cat);
		assertEquals(owner.getPet(cat.getName()), cat);
		assertNotEquals(owner.getPet(cat.getName()), dog);
	}

	@Test
	public void getPetIgnoreNewTest()
	{
		cat.setId(null);
		owner.addPet(cat);
		assertEquals(owner.getPet(cat.getName(), false), cat);
		assertNotEquals(owner.getPet(cat.getName(), false), dog);
		assertEquals(owner.getPet(cat.getName().toUpperCase(), false), cat);
		assertNull(owner.getPet(cat.getName(), true));
	}
}
