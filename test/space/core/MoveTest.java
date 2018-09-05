/**
 * @Author Leonhard Applis
 * @Created 04.09.2018
 * @Package space.core
 */
package space.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

	@Test
	void InitSpaceObjects() {
		Star anker1=new Star("Anker1",null,250,250,50);
		//Stars should not move!
		anker1.update();
		assertEquals(250,anker1.getX());assertEquals(250,anker1.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet a = new Planet("A",anker1,null,50,250,Math.PI/2);
		Satellite b= new Satellite("B",a,50,250,-Math.PI/2);
		Planet c= new Planet("C",a,null,50,250,0);
		//Check X-Y Koords
		assertEquals(250,a.getX());
		assertEquals(500,a.getY());
		assertEquals(250,b.getX());
		assertEquals(750,b.getY());
		assertEquals(250,c.getX());
		assertEquals(750,c.getY());
		//Check DegreeTo EachOther
		assertEquals(3*Math.PI/2, a.degreeTo(anker1));
		assertEquals(3*Math.PI/2, b.degreeTo(anker1));
		assertEquals(3*Math.PI/2, c.degreeTo(anker1));
		//assertEquals(3*Math.PI/2, a.degreeTo(b));
		
	}
	
	@Test
	void SimpleMove() {
		Star anker1=new Star("Anker1",null,250,250,50);
		//Stars should not move!
		anker1.update();
		assertEquals(250,anker1.getX());assertEquals(250,anker1.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet a = new Planet("A",anker1,null,50,250,Math.PI/2);
		Satellite b= new Satellite("B",a,50,250,-Math.PI/2);
		
		//Move all Items, if they are movable
		anker1.update();
		//Star not moved, Planet right to Star, Satellite Left to Planet in Star
		assertEquals(250,anker1.getX());
		assertEquals(250,anker1.getY());
		assertEquals(500,a.getX());
		assertEquals(250,a.getY());
		assertEquals(250,b.getX());
		assertEquals(250,b.getY());
		
		anker1.update();
		//Star not moved, Planet beyond Star, Satellite beyond Planet
		assertEquals(250,anker1.getX());
		assertEquals(250,anker1.getY());
		assertEquals(250,a.getX());
		assertEquals(0,a.getY());
		assertEquals(250,b.getX());
		assertEquals(-250,b.getY());
	}
	@Test
	void NoSpeed() {
		Star anker1=new Star("Anker1",null,250,250,50);
		//Stars should not move!
		anker1.update();
		assertEquals(250,anker1.getX());
		assertEquals(250,anker1.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet a = new Planet("A",anker1,null,50,250,0);
		Satellite b= new Satellite("B",a,50,250,0);
		
		for(int i =0;i<10;i++) {
			anker1.update();
			assertEquals(250,anker1.getX());
			assertEquals(250,anker1.getY());
			assertEquals(250,a.getX());
			assertEquals(500,a.getY());
			assertEquals(250,b.getX());
			assertEquals(750,b.getY());
		}
	}
	
	@Test
	public void RelativePosition() {
		Star anker1=new Star("Anker1",null,250,250,50);
		//Stars should not move!
		anker1.update();
		assertEquals(250,anker1.getX());assertEquals(250,anker1.getY());
		//Add a Planet to move with 90° per update
		//Add a Satellite to Planet to move with -90° per update
		Planet a = new Planet("A",anker1,null,50,250,Math.PI/2);
		Satellite b= new Satellite("B",a,50,50,-Math.PI/2);
		
		//Move all Items, if they are movable
		anker1.update();
		
		assertEquals(Math.PI*2,a.degreeTo(anker1));
		assertEquals(0,a.relativePos);
		assertEquals(Math.PI*2,b.degreeTo(anker1));
		assertEquals(Math.PI,b.degreeTo(a));
		assertEquals(Math.PI,b.relativePos);
		
		anker1.update();
		assertEquals(Math.PI/2,a.degreeTo(anker1));
		assertEquals(Math.PI/2,a.relativePos);
		assertEquals(Math.PI/2,b.degreeTo(anker1));
		
		assertEquals(Math.PI/2,b.degreeTo(a));
		assertEquals(Math.PI/2,b.relativePos);
	}
}