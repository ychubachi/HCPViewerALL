/*
 * CTimeTest.java
 * Created on 2011/05/30
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clib.common.utils.ICChecker;

/**
 * @author macchan
 * 
 */
public class CTimeOrderedListTest {

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

	;

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

	@Test
	public void testSearchBefore() {
		CTimeOrderedList<CTime> list = new CTimeOrderedList<CTime>();
		list.add(new CTime(100));
		list.add(new CTime(200));
		list.add(new CTime(300));
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(null, list.searchElementBefore(new CTime(99)));
		Assert.assertEquals(new CTime(100),
				list.searchElementBefore(new CTime(100)));
		Assert.assertEquals(new CTime(100),
				list.searchElementBefore(new CTime(150)));
		Assert.assertEquals(new CTime(300),
				list.searchElementBefore(new CTime(300)));
		Assert.assertEquals(new CTime(300),
				list.searchElementBefore(new CTime(301)));
	}

	@Test
	public void testSearchAfter() {
		CTimeOrderedList<CTime> list = new CTimeOrderedList<CTime>();
		list.add(new CTime(100));
		list.add(new CTime(200));
		list.add(new CTime(300));
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(new CTime(100),
				list.searchElementAfter(new CTime(0)));
		Assert.assertEquals(new CTime(100),
				list.searchElementAfter(new CTime(99)));
		Assert.assertEquals(new CTime(100),
				list.searchElementAfter(new CTime(100)));
		Assert.assertEquals(new CTime(200),
				list.searchElementAfter(new CTime(101)));
		Assert.assertEquals(null, list.searchElementAfter(new CTime(301)));
	}

	@Test
	public void testSelect() {
		CTimeOrderedList<CTime> list = new CTimeOrderedList<CTime>();
		list.add(new A(100));
		list.add(new B(200));
		list.add(new A(300));
		CTimeOrderedList<CTime> aSelected = list.select(new ICChecker<CTime>() {
			public boolean check(CTime t) {
				return t instanceof A;
			}
		});
		Assert.assertEquals(2, aSelected.size());
		CTimeOrderedList<CTime> bSelected = list.select(new ICChecker<CTime>() {
			public boolean check(CTime t) {
				return t instanceof B;
			}
		});
		Assert.assertEquals(1, bSelected.size());
		CTimeOrderedList<CTime> noSelected = list
				.select(new ICChecker<CTime>() {
					public boolean check(CTime t) {
						return false;
					}
				});
		Assert.assertEquals(0, noSelected.size());
	}

	class A extends CTime {
		A(long time) {
			super(time);
		}
	}

	class B extends CTime {
		B(long time) {
			super(time);
		}
	}
}
