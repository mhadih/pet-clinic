package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class PetServiceTest {
	private PetService petService;
	private Integer petId;

	private final Logger loggerMock = Mockito.mock(Logger.class);

	public PetServiceTest(Integer petId) {
		this.petId = petId;
	}

	@Before
	public void setup() {
		petService = new PetService(new FakePetTimedCache(), null, loggerMock);
	}

	@Parameters
	public static Collection petIds() {
		return Arrays.asList(1, 5, 8, 9, 20);
	}

	@Test
	public void findPetTest() {
		assumeTrue(petId >= 0);
		assumeTrue(petId < 10);

		Pet pet = petService.findPet(petId);
		assertNotNull(pet);
		assertEquals(pet.getId(), petId);
	}

	static class FakePetTimedCache extends PetTimedCache {
		private final Map<Integer, Pet> pets;

		public FakePetTimedCache() {
			super(null);
			pets = new HashMap<>();
			for (int i = 0; i < 10; i++) {
				Pet pet = new Pet();
				pet.setId(i);
				pet.setName("pet" + i);
				pets.put(i, pet);
			}
		}

		@Override
		public Pet get(Integer petId) {
			return pets.get(petId);
		}
	}
}
