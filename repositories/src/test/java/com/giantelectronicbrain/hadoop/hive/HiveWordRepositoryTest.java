/**
 * 
 */
package com.giantelectronicbrain.hadoop.hive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.giantelectronicbrain.hadoop.IWordRepository;
import com.giantelectronicbrain.hadoop.RepositoryException;
import com.giantelectronicbrain.hadoop.Word;

/**
 * @author tharter
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class HiveWordRepositoryTest {

// Note: none of this is really working yet, as I can't seem to get hive to run an insert correctly.
	
	IWordRepository uut;

	@BeforeClass
	public static void before() throws RepositoryException {
	}
	
	@Before
	public void setup() throws RepositoryException {
		uut = new HiveWordRepository();
		uut.initTable(); // make sure table exists
		uut.clearTable(); // insure known table state
	}
	
	/*
	 * Test method for {@link com.giantelectronicbrain.hadoop.hbase.WordRepository#clearTable()}.
	 */
//	@Test
//	public void testClearTable() throws RepositoryException {
//		uut.save("666", "waldo");
//		uut.clearTable();
//		List<Word> words = uut.findAll();
//		assertNotNull(words);
//		assertEquals("should be zero words",0,words.size());
//	}
	
	/**
	 * Test method for {@link com.giantelectronicbrain.hadoop.hbase.WordRepository#findAll()}.
	 * @throws RepositoryException 
	 */
//	@Test
//	public void testFindAll() throws RepositoryException {
//		uut.save("666", "waldo");
//		List<Word> words = uut.findAll();
//		assertNotNull(words);
//		assertEquals("should be 1 words",1,words.size());
//		Word word = words.get(0);
//		assertEquals("word should be 'waldo'","waldo",word.getWord());
//		assertEquals("Count should be '666'","666",word.getCount());
//	}

	/**
	 * Test method for {@link com.giantelectronicbrain.hadoop.hbase.HbaseWordRepository#initTable()}.
	 */
	@Test
	public void testDeleteTable() {
		try {
			uut.deleteTable();
			boolean tExists = uut.tableExists();
			assertTrue("table should not exist",!tExists);
		} catch (RepositoryException e) {
			fail("Delete table threw exception");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link com.giantelectronicbrain.hadoop.hbase.HbaseWordRepository#initTable()}.
	 * @throws IOException 
	 */
	@Test
	public void testInitTable() throws RepositoryException {
		uut.deleteTable();
		uut.initTable();
		assertTrue("table should exist",uut.tableExists());
	}

}
