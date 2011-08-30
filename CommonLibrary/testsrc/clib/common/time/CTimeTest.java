/*
 * CTimeTest.java
 * Created on 2011/05/30
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author macchan
 * 
 */
public class CTimeTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link clib.common.time.CTime#before(clib.common.time.CTime)}.
	 */
	@Test
	public void testBefore() {
		assertEquals(true, new CTime((100)).before(new CTime(200)));
		assertEquals(false, new CTime((200)).before(new CTime(100)));
	}

	/**
	 * Test method for
	 * {@link clib.common.time.CTime#after(clib.common.time.CTime)}.
	 */
	@Test
	public void testAfter() {
		assertEquals(false, new CTime((100)).after(new CTime(200)));
		assertEquals(true, new CTime((200)).after(new CTime(100)));
	}

	@Test
	public void testEquals() {
		assertEquals(true, new CTime(100).equals(new CTime(100)));
		assertEquals(false, new CTime(100).equals(new CTime(101)));
		assertEquals(false, new CTime(100).equals(null));
		assertEquals(false, new CTime(100).equals(new Object()));
	}
}
