package com.safetynet.apirest.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateUtilTests {

	@Test
	final void testGetAge() {
		long age;

		age = DateUtils.getAge("03/29/1963");
		assertEquals(age, 58);
	}

	@Test
	final void testBadFormatGetAge() {
		long age;

		age = DateUtils.getAge("29/03/1963");
		assertEquals(0, age);
	}

	@Test
	final void testIsChild() {
		boolean isChild;

		isChild = DateUtils.isChild("03/29/1963");
		assertFalse(isChild);
	}

	@Test
	final void testIsNoChild() {
		boolean isChild;

		isChild = DateUtils.isChild("03/29/2018");
		assertTrue(isChild);
	}

	@Test
	final void testDateUtilTest() {
		final DateUtils date = new DateUtils();
		assertNotNull(date);
	}


}
