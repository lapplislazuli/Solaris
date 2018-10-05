package geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelativePointTest {
	
	static AbsolutePoint absAnker;
	static RelativePoint relAnker;

	RelativePoint absAnkered, relAnkered;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		absAnker= new AbsolutePoint(100,100);
		relAnker= new RelativePoint(absAnker,100,100); //[200,200]
	}

	@BeforeEach
	void setUp() throws Exception {
		absAnkered= new RelativePoint(absAnker, 50,50); //[150,150]
		relAnkered= new RelativePoint(relAnker, 50,50); //[250,250]
	}

	
	@Test
	void testAnkerMoved(){
		absAnker.move(100, 100); //[200,200]
		//RelAnker will move, if everything is correct!
		
		assertEquals(300,relAnker.getX());
		assertEquals(300,relAnker.getY());
		
		assertEquals(350,relAnkered.getX());
		assertEquals(350,relAnkered.getY());
		
		absAnker.move(-100, -100); //CleanUp
	}
	
	@Test 
	void testAnkerChanged(){
		relAnkered.anker=absAnkered.anker;
		
		assertEquals(150, relAnkered.getX());

		assertEquals(150, relAnkered.getY());
	}
	
	@Test
	void testGetX() {
		assertEquals(150,absAnkered.getX());
		assertEquals(250,relAnkered.getX());
	}

	@Test
	void testGetY() {
		assertEquals(150,absAnkered.getY());
		assertEquals(250,relAnkered.getY());
	}

	@Test
	void testGetZ() {
		assertEquals(0,absAnkered.getZ());
		assertEquals(0,relAnkered.getZ());
	}

	@Test
	void testSetX() {
		absAnkered.setX(100); //[100,100]
		relAnkered.setX(100); //[100,200]
		
		assertEquals(100,absAnkered.getX());
		assertEquals(100,relAnkered.getX());
	}

	@Test
	void testSetY() {
		absAnkered.setY(100); //[200,100]
		relAnkered.setY(100); //[300,100]
		
		assertEquals(100,absAnkered.getY());
		assertEquals(100,relAnkered.getY());
	}

	@Test
	void testSetZ() {
		absAnkered.setZ(100);
		relAnkered.setZ(100);
		
		assertEquals(100,absAnkered.getZ());
		assertEquals(100,relAnkered.getZ());
	}

	@Test
	void testSetXDif() {
		absAnkered.setXDif(100); //[200,100]
		relAnkered.setXDif(100); //[300,200]
		
		assertEquals(200,absAnkered.getX());
		assertEquals(300,relAnkered.getX());
	}

	@Test
	void testSetYDif() {
		absAnkered.setYDif(100); //[100,200]
		relAnkered.setYDif(100); //[200,300]
		
		assertEquals(200,absAnkered.getY());
		assertEquals(300,relAnkered.getY());
	}

	@Test
	void testSetZDif() {
		absAnkered.setZDif(100); //[200,100]
		relAnkered.setZDif(100); //[300,100]
		
		assertEquals(100,absAnkered.getZ());
		assertEquals(100,relAnkered.getZ());
	}
	
	
	@Test
	void testSamePosition() {
		RelativePoint onParent = new RelativePoint(absAnkered,0,0);
		
		assertTrue(onParent.samePosition(absAnkered));
	}
	
	@Test
	void testNegativeSamePosition() {
		assertFalse(absAnkered.samePosition(relAnkered));
	}
}
