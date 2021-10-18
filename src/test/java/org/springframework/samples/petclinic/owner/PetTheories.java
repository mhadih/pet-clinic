package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import org.junit.experimental.theories.DataPoints;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

@RunWith(Theories.class)
public class PetTheories {
	private static Pet pet;

	@DataPoints
	public static List<List<Visit>> visitList = new ArrayList<>();

	@BeforeClass
	public static void setupVisits() {
		Visit visit1 = new Visit();
		visit1.setDate(LocalDate.now());
		Visit visit2 = new Visit();
		visit2.setDate(LocalDate.now().minusMonths(3));
		Visit visit3 = new Visit();
		visit3.setDate(LocalDate.now().plusDays(2));
		Visit visit4 = new Visit();
		visit4.setDate(LocalDate.now().plusYears(1));
		Visit visit5 = new Visit();
		visit5.setDate(LocalDate.now().minusMonths(2).minusYears(3));

		visitList.add(Collections.emptyList());
		visitList.add(Arrays.asList(visit1, visit2, visit5));
		visitList.add(Arrays.asList(visit2, visit4));
		visitList.add(Arrays.asList(visit1, visit2, visit3, visit4, visit5));
	}

	@Before
	public void setup() {
		pet = new Pet();
		pet.setId(1);
	}

	@After
	public void tearDown() {
		pet = null;
		visitList = null;
	}

	@Theory
	public void getVisitsTest(List<Visit> visits) {
		// assume
		assumeFalse(visits.isEmpty());
//		System.out.println(visits.size());
		// act
		for (Visit visit: visits)
			pet.addVisit(visit);
		List<Visit> res_visits = pet.getVisits();
//		for (int i = 0; i < visits.size(); i++)
//			System.out.println(res_visits.get(i).getDate());
		// assert
		for (int i = 0; i < visits.size()-1; i++) {
			assertTrue(res_visits.get(i).getDate().isAfter(res_visits.get(i+1).getDate()));
		}
	}
}
