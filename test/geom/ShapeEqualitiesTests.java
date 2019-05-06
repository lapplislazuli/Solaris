/**
 * @Author Leonhard Applis
 * @Created 03.10.2018
 * @Package geom
 */
package geom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import interfaces.geom.Shape;

class ShapeEqualitiesTests {

	static Shape aOne,aTwo,bOne,bTwo;
	static Shape c,d;
	
	@BeforeAll
	static void initShapes() {
		aOne = new Circle(new AbsolutePoint(100,100),25);
		aTwo = new Circle(new AbsolutePoint(100,100),25);
		
		bOne = new HShape(50,50,50);
		bOne.setCenter(new AbsolutePoint(500,500));
	
		bTwo = new HShape(50,50,50);
		bTwo.setCenter(new AbsolutePoint(500,500));
		
		c= new Rectangle(50,100);
		
		d= new TShape(150,150,29);
	}
	
	@Test
	void testPositiveSameShape() {
		assertTrue(aOne.sameShape(aTwo));
		assertTrue(bOne.sameShape(bTwo));
	}

	@Test
	void testNegativeSameShape() {
		assertFalse(aOne.sameShape(c));
		assertFalse(aOne.sameShape(bTwo));
	}
	
	@Test
	void testSameShapeSymmetry() {
		assertTrue(aOne.sameShape(aTwo)==aTwo.sameShape(aOne));
		assertTrue(bOne.sameShape(bTwo)==bTwo.sameShape(bOne));
	}
}
