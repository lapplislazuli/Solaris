package junit.spaceobjects.spacecrafts;


import static junit.testhelpers.FakeSpaceObjectFactory.fakeStar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import geom.LolliShape;
import interfaces.geom.Point;
import interfaces.logical.MovingUpdatingObject;
import interfaces.spacecraft.SpacecraftState;
import javafx.scene.paint.Color;
import junit.fakes.FakeSensor;
import junit.fakes.interfaces.FakeCollidingObject;
import junit.spaceobjects.RemovableTests;
import logic.manager.ManagerRegistry;
import space.advanced.Asteroid;
import space.core.MovingSpaceObject;
import space.core.Planet;
import space.core.SpaceObject;
import space.core.Star;
import space.spacecraft.ships.devices.DroneRack;
import space.spacecraft.ships.devices.LaserCannon;
import space.spacecraft.ships.devices.RocketLauncher;
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.ships.DroneFactory;
import space.spacecrafts.ships.ShipFactory;
import space.spacecrafts.ships.Spaceshuttle;
import space.spacecrafts.ships.missiles.Laserbeam;
import space.spacecrafts.ships.missiles.Missile;
import space.spacecrafts.ships.missiles.Rocket;

public class SpaceshuttleTests implements RemovableTests {

	@BeforeEach
	void initManagerRegistry() {
		ManagerRegistry.getInstance();
	}
	@AfterEach
	void resteManagerRegistry() {
		ManagerRegistry.reset();
	}

	@Tag("Basic")
	@Test
	void testConstructor_shouldBePlayer() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		
		assertFalse(testObject.isActivePlayer());
	}

	@Tag("Basic")
	@Test
	void testConstructor_ValuesShouldBeAsExpected() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
		assertEquals(50,shuttle.getOrbitingDistance());
		assertEquals(Math.PI,shuttle.getSpeed());
	}

	@Tag("Basic")
	@Test
	void testSetTarget_targetShouldBeSet() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		
		assertEquals(target,shuttle.getTarget());
	}

	@Tag("Basic")
	@Test
	void testSetTarget_doubleSet_targetIsTheLaterSetTarget() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		SpaceObject newertarget = fakeStar(250,250);
		
		shuttle.setTarget(target);
		shuttle.setTarget(newertarget);
		
		assertEquals(newertarget,shuttle.getTarget());
	}

	@Test
	void testLaunch_InspectParentAfterLaunch_ParentShouldBeOldTarget() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(target,shuttle.getParent());
	}
	
	@Test
	void testLaunch_InspectTargetAfterLaunch_TargetShouldBeNull() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertEquals(null, shuttle.getTarget());
	}
	
	@Test
	void testLaunch_shouldHaveNewState_shouldBeFlying() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		assertTrue(shuttle.getState()==SpacecraftState.FLYING);
	}

	@Tag("Basic")
	@Test
	void testLaunch_InspectDistance_shouldBeDistanceToTarget() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(shuttle.getDistance(),(int)shuttle.distanceTo(target));
	}

	@Tag("Complex")
	@Test
	void testLaunch_InspectRelativePos_shouldBeDegreeToTarget() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		SpaceObject target = fakeStar(100,100);
		
		shuttle.setTarget(target);
		shuttle.launch();

		assertEquals(target.degreeTo(shuttle),shuttle.getRelativePos());
	}

	@Tag("Basic")
	@Test
	void testLaunch_NoTarget_shouldNotChangeState() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		
		shuttle.setTarget(null);
		shuttle.launch();
		
		assertEquals(SpacecraftState.ORBITING,shuttle.getState());
	}

	@Tag("Basic")
	@Test
	void testLaunch_NoTarget_shouldHaveOldParent() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,Math.PI);
		
		shuttle.setTarget(null);
		shuttle.launch();

		assertEquals(root,shuttle.getParent());
	}

	@Tag("Complex")
	@Tag("Regression")
	@Test 
	void testLaunch_CheckForTeleport_shouldBeOnSamePosition() {
		/*
		 * I had a very special problem that the ships do teleport after
		 * launching and doing an update. This was probably related to relativePos
		 * But i want to check it separately 
		 * It didn't occur with 0 speed
		 */
		
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,speed);
		SpaceObject target = fakeStar(1000,1000);
		
		Point oldPos= shuttle.getCenter().clone();
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		target.update();

		assertTrue(oldPos.distanceTo(shuttle.getCenter())<5);
	}
	

	@Tag("Basic")
	@Test
	public void testConstructors_IsInParentsChildren(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		assertTrue(root.getTrabants().contains(testObject));
	}

	@Tag("Basic")
	@Test
	public void testConstructors_isNotOrphaned(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		assertFalse(testObject.isOrphan());
	}

	@Tag("Basic")
	@Test
	public void testRemove_isNotInParentsChildren() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertFalse(root.getTrabants().contains(testObject));
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testRemove_isOrphaned() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertTrue(testObject.isOrphan());
	}

	@Tag("Basic")
	@Test
	public void testRemove_inspectTarget_shouldBeNull() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		testObject.setTarget(target);
		
		testObject.remove();
		
		assertEquals(null,testObject.getTarget());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testRemove_doubleRemove_shouldThrowNullpoint() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		testObject.remove();
		
		assertThrows(NullPointerException.class,
				() -> testObject.remove());
	}
	
	@Tag("Complex")
	@Test
	void testLaunch_flipSpeedIfnecessary_shouldNotBeFlipped() {
		SpaceObject origin = new Star("star",null,new AbsolutePoint(250,250),250);
		MovingSpaceObject launchedTowards=new Planet.Builder("Forward", origin).speed(Math.PI/2000).distance(300).build();
		
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",origin,0,50,-Math.PI/400);
		double oldSpeed=shuttle.getSpeed();
		origin.update();//To move Planets
		
		shuttle.setTarget(launchedTowards);		
		shuttle.launch();
		
		assertEquals(oldSpeed,shuttle.getSpeed());
	}

	@Tag("Complex")
	@Test
	void testLaunch_flipSpeedIfnecessary_shouldBeFlipped() {
		SpaceObject origin = new Star("star",null,new AbsolutePoint(250,250),250);
		MovingSpaceObject launchedAway = new Planet.Builder("Flip", origin).speed(Math.PI/80).distance(250).build();
		
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",origin,0,50,-Math.PI/400);
		double oldSpeed=shuttle.getSpeed();
		origin.update();//To move Planets
		
		shuttle.setTarget(launchedAway);		
		shuttle.launch();
		
		assertEquals(oldSpeed,-shuttle.getSpeed());
	}
	

	@Tag("Approximation")
	@Test
	void testMove_isFlying_comesCloserToTarget_shouldBeCloser() {
		SpaceObject root = fakeStar(0,0);
		double speed= 1; //Minimal Speed
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,speed);
		SpaceObject target = fakeStar(1000,1000);
		
		double oldDistance= shuttle.getDistance();
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		shuttle.move(target.getCenter());
		
		assertTrue(shuttle.getDistance()>oldDistance);
	}
	
	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Tag("Regression")
	@Test
	void testCollide_doesNotCollideItself() {
		SpaceObject root = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle testObject = new Spaceshuttle.Builder("TestShuttle", root)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(root);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		assertFalse(testObject.collides(testObject));
	}
	
	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Tag("Regression")
	@Test
	void testCollide_twoShuttlesAtDifferentPositions_shouldNotCollide() {
		SpaceObject rootA = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleA = new Spaceshuttle.Builder("TestShuttle", rootA)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		SpaceObject rootB = new Star.Builder("SampleRoot").center(250,250).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleB = new Spaceshuttle.Builder("TestShuttle", rootB)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(rootA);
		ManagerRegistry.getUpdateManager().addSpaceObject(rootB);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		assertFalse(shuttleA.collides(shuttleB));
		assertFalse(shuttleB.collides(shuttleA));
	}
	
	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Tag("Regression")
	@Test
	void testCollide_twoShuttlesAtSamePosition_shouldCollide() {
		SpaceObject rootA = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleA = new Spaceshuttle.Builder("TestShuttle", rootA)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		SpaceObject rootB = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleB = new Spaceshuttle.Builder("TestShuttle", rootB)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(rootA);
		ManagerRegistry.getUpdateManager().addSpaceObject(rootB);
		//ManagerRegistry.getUpdateManager().update();
		//ManagerRegistry.getCollisionManager().update();
		
		assertTrue(shuttleA.collides(shuttleB));
		assertTrue(shuttleB.collides(shuttleA));
	}
	
	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Tag("Regression")
	@Test
	void testCollide_twoShuttlesAtSamePosition_theyAlreadyCollidedAndDied_shouldNotCollide() {
		SpaceObject rootA = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleA = new Spaceshuttle.Builder("TestShuttle", rootA)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		SpaceObject rootB = new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		Spaceshuttle shuttleB = new Spaceshuttle.Builder("TestShuttle", rootB)
				.size(2)
				.levelOfDetail(3)
				.build();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(rootA);
		ManagerRegistry.getUpdateManager().addSpaceObject(rootB);
		
		shuttleA.destruct();
		shuttleB.destruct();
		
		assertFalse(shuttleA.collides(shuttleB));
		assertFalse(shuttleB.collides(shuttleA));
	}
	
	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Tag("Regression")
	@Test
	void testCollide_shuttleIsNotDestroyed_butShuttleIsRebuild_doesCollideWithRebuild() {
		SpaceObject root =  new Star.Builder("SampleRoot").center(0,0).radious(2).levelOfDetail(2).build();
		
		Spaceshuttle testObject = new Spaceshuttle.Builder("TestShuttle", root)
				.size(2)
				.levelOfDetail(3)
				.build();

		ManagerRegistry.getUpdateManager().addSpaceObject(root);
		
		var rebuild = testObject.rebuildAt("TestShuttleRebuild", root);

		ManagerRegistry.getUpdateManager().addSpaceObject(root);
		
		assertTrue(rebuild.collides(testObject));
		assertTrue(testObject.collides(rebuild));
	}
	
	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testMove_isOrbiting_distanceStaysSame() {
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,speed);
		
		double oldDistance= shuttle.getDistance();
		
		
		shuttle.move(root.getCenter());
		
		assertEquals(oldDistance,shuttle.getDistance());
	}
	
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testMove_isFlying_CloseEnoughToStay_shouldGetOrbiting() {
		SpaceObject root = fakeStar(0,0);
		double speed= 0.001; //Minimal Speed
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,speed);
		SpaceObject target = fakeStar(0,0);
		
		shuttle.setTarget(target);
		shuttle.launch();
		
		shuttle.move(target.getCenter());
		
		assertEquals(SpacecraftState.ORBITING ,shuttle.getState());
	}

	@Tag("Basic")
	@Test
	void testRebuildAt_shouldHaveSameValues() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,1);
		
		Spaceshuttle copy = shuttle.rebuildAt("copy", root);
		
		assertEquals(150,copy.getOrbitingDistance());
		assertEquals(1, copy.getSpeed());
		assertEquals(shuttle.getParent(),copy.getParent());
		assertEquals(shuttle.getState(),copy.getState());
	}

	@Tag("Basic")
	@Test
	void testRebuildAt_loosesTarget() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		SpaceObject target = fakeStar(600,600);
		
		shuttle.setTarget(target);
		
		Spaceshuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(null ,copy.getTarget());
	}

	@Tag("Basic")
	@Test
	void testRebuildAt_spawnsAtParent() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		
		Spaceshuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(root,copy.getParent());
	}

	@Tag("Basic")
	@Test
	void testRebuildAt_spawnsOrbiting() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		SpaceObject target = fakeStar(0,0);
		
		shuttle.setTarget(target);
		shuttle.launch();
		Spaceshuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(SpacecraftState.ORBITING,copy.getState());
	}

	@Tag("Basic")
	@Test
	void testRebuildAt_OriginalShipWasDead_CopyShouldBeAlive() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		
		Spaceshuttle copy = shuttle.rebuildAt("new", root);
		
		assertEquals(SpacecraftState.ORBITING,copy.getState());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testDestruct_stateShouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		
		assertEquals(SpacecraftState.DEAD,shuttle.getState());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testDestruct_destructNonPlayerShip_checkPlayerManagerDeathCount_shouldBeZero() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		//The ship is not the player, therefore the PlayerManager should not Care
		assertEquals(0,ManagerRegistry.getPlayerManager().getPlayerDeaths());
	}

	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testDestruct_doubleDestruct_shouldNotDoAnything() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,150,0);
		
		shuttle.destruct();
		shuttle.destruct();
		
		assertTrue(shuttle.isDead());
	}

	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testDestruct_shouldSpawnTrash() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		shuttle.destruct();
		
		MovingUpdatingObject test = root.getTrabants().get(0);
		assertTrue(test instanceof Asteroid);
		Asteroid castedTest = (Asteroid) test;
		assertEquals(Asteroid.Type.TRASH,castedTest.getType());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testDestruct_shouldBeDead() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		shuttle.destruct();
		
		assertTrue(shuttle.isDead());
	}

	@Tag("Basic")
	@Test
	void testDetectedItems_SensorIsEmpty_ShouldBeEmpty() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		FakeSensor stub = new FakeSensor();
		shuttle.setSensor(stub);
		
		assertTrue(shuttle.getDetectedItems().isEmpty());
	}

	@Tag("Basic")
	@Test
	void testDetectedItems_SensorHasItems_shouldBeTheSameAsFakeSensor(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle shuttle= new Spaceshuttle("shuttleOne",root,0,50,0);
		
		FakeSensor stub = new FakeSensor();
		shuttle.setSensor(stub);
		stub.detectedItems.add(root);
		
		assertTrue(shuttle.getDetectedItems().contains(root));
		assertEquals(1,shuttle.getDetectedItems().size());		
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleHadNoWeapons_shouldNotHaveWeaponsEither() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		Spaceshuttle carrier = makeBattleCarrier();
		
		Spaceshuttle copy = carrier.rebuildAt("copy",carrierRoot);
		
		assertTrue(copy.isCarrier());
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleWasCarrier_shouldHaveDrones() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
				.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		assertTrue(copy.isCarrier());
		assertEquals(2,copy.getDrones().size());
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleWasCarrier_shouldHaveDroneRack() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
				.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
				.shallBeCarrier(true)
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		assertTrue(copy.getWeapons().stream().filter(w -> w instanceof DroneRack).findFirst().isPresent());
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleHadASingleLaser_shouldHaveSingleLaser() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		assertEquals(1,copy.getWeapons().size());
		assertTrue(copy.getWeapons().stream().findFirst().map(u -> u instanceof LaserCannon).get());
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleHad5Weapons_shouldHave5Weapons() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.addMountedWeapon(WeaponFactory::standardLaserCannon)
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		assertEquals(5,copy.getWeapons().size());
	}
	
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleWasPink_shouldBePinkToo() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.color(Color.DEEPPINK)
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		var dinfo = (JavaFXDrawingInformation) copy.getDrawingInformation();
		
		assertTrue(dinfo.primarycolor.equals(Color.DEEPPINK));
	}
	
	@Tag("Basic")
	@Test
	void rebuildAt_ShuttleHadLolliShape_shouldHaveLollyShapeToo() {
		SpaceObject root = fakeStar(0,0);
		var testObject = new Spaceshuttle.Builder("TestCarrier", root)
				.shape(new LolliShape(10,10,5))
				.build();
		
		Spaceshuttle copy = testObject.rebuildAt("copy",testObject);
		
		assertTrue(copy.getShape() instanceof LolliShape);
	}	
	
	/*
	 * ===============================================================================================
	 * Carrier Tests Start here
	 * ===============================================================================================
	 */
	

	@Tag("Basic")
	@Test
	void testBuilder_allValuesFine_shouldBuild() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		//Add 3 normal Drones
		for(int i = 0;i<4;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		
		var carrier = 
				b.color(Color.ALICEBLUE)
				 .size(4)
				 .orbitingDistance(40)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .levelOfDetail(3)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardLaserCannon)
				 .build();
		
		assertEquals(4,carrier.getTrabants().size());
	}

	@Tag("Basic")
	@Test
	void testBuilder_setStandardWeapons_shouldHaveLaserCannonAndRockets() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		
		var carrier = 
				new Spaceshuttle.Builder("TestCarrier", carrierRoot).color(Color.ALICEBLUE)
				 .size(4)
				 .orbitingDistance(40)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .sensorSize(150)
				 .levelOfDetail(3)
				 .setStandardWeaponry(true)
				 .build();
		
		assertEquals(2,carrier.getWeapons().size());
		
		carrier.getWeapons().stream()
			.forEach(t -> assertTrue(t instanceof LaserCannon || t instanceof RocketLauncher));
	}

	@Tag("Basic")
	@Test
	void testBuilder_minimalBuilder_EmptyDrones_shouldBuild() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var carrier = new Spaceshuttle.Builder("TestCarrier", carrierRoot).build();
		
		assertEquals(0,carrier.getTrabants().size());
	}
	

	@Tag("Basic")
	@Test
	void testBuilder_negativeSize_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.size(-1));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_negativeSensorSize_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.size(-1));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_negativeDistance_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.sensorSize(-1));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_nullShape_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.shape(null));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_nullDrawingInfo_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.drawingInformation(null));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_setColorAndDrawingInfo_DrawingInfoShouldOverwriteColor() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		// Should be overwritten by JavaFXDrawingInformation no matter the order, therefore two tests
		var testObject1 = 
				new Spaceshuttle.Builder("TestCarrier", carrierRoot)
				.color(Color.ALICEBLUE)
				.drawingInformation(new JavaFXDrawingInformation(Color.DARKGREEN))
				.build();
		
		var testObject2 = 
				new Spaceshuttle.Builder("TestCarrier", carrierRoot)
				.drawingInformation(new JavaFXDrawingInformation(Color.DARKGREEN))
				.color(Color.ALICEBLUE)
				.build();
		
		var dInfo1 = (JavaFXDrawingInformation) testObject1.getDrawingInformation();
		var dInfo2 = (JavaFXDrawingInformation) testObject2.getDrawingInformation();
		
		assertEquals(Color.DARKGREEN,dInfo1.primarycolor);
		assertEquals(Color.DARKGREEN,dInfo2.primarycolor);
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_negativeLevelOfDetail_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(-1));
	}

	@Tag("Basic")
	@Test
	void testBuilder_LevelOfDetailZero_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.levelOfDetail(0));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_OrbitingDistanceZero_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.orbitingDistance(0));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_negativeOrbitingDistance_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		assertThrows(IllegalArgumentException.class, () -> b.orbitingDistance(-50));
	}
	
	@Tag("Basic")
	@Test
	void testBuilder_emptyName_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder("", carrierRoot));
	}

	@Tag("Basic")
	@Test
	void testBuilder_nullName_shouldThrowError() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder(null, carrierRoot));
	}

	@Tag("Basic")
	@Test
	void testBuilder_nullRoot_shouldThrowError() {
		assertThrows(IllegalArgumentException.class, () ->new Spaceshuttle.Builder("TestCarrier", null));
	}

	@Tag("Basic")
	@Test
	void testRevoke_inspectTarget_shouldHaveNoTrabants() {
		//TODO
	}

	@Tag("Complex")
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,5,10,50})
	void testCollision_inspectCarrier_doesNotCollideDrones(int numberOfDrones) {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		for(Spaceshuttle drone : carrier.getDrones())
			assertFalse(carrier.collides(drone));
	}

	@Tag("Complex")
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,5,10,50})
	void testCollision_inspectDrones_DoNotCollideCarrier(int numberOfDrones) {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		for(Spaceshuttle drone : carrier.getDrones())
			assertFalse(drone.collides(carrier));
	}

	@Tag("Complex")
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,5,10,50})
	void testCollision_inspectDrones_DoNotCollideWithOtherDronesOfSameCarrier(int numberOfDrones) {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		
		
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		for(Spaceshuttle drone : carrier.getDrones())
			for(Spaceshuttle otherDrone : carrier.getDrones())
				if(drone != otherDrone)
					assertFalse(drone.collides(carrier));
	}
	
	@Tag("Complex")
	@ParameterizedTest
	@ValueSource(ints = {5,10,100})
	void testCollision_dronesShooting_shouldNotCollideWithDronesOfSameCarrier(int numberOfDrones) {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		SpaceObject fakeTarget = fakeStar(1000,1000);
		for(Spaceshuttle drone : carrier.getDrones())
			drone.attack(fakeTarget);
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		List<Missile> allShots = 
				carrier.getDrones().stream()
				.flatMap(d -> d.getTrabants().stream())
				.filter(m -> m instanceof Missile)
				.map(m -> (Missile) m)
				.collect(Collectors.toList());

		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		for(Spaceshuttle drone : carrier.getDrones())
			for(Missile m : allShots) {
				assertFalse(drone.collides(m));			
			}
	}
	
	@Tag("Complex")
	@Tag("Regression")
	@Test
	void testCollision_dronesOfACarrier_AreInAHugeSpaceObject_shouldCollide() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<5;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		SpaceObject forcefullCollider = new Star.Builder("EmptyRoot")
				.center(carrier.getCenter().absoluteClone())
				.radious(250)
				.levelOfDetail(20)
				.build();
		forcefullCollider.getShape().updateOrInitOutline();
		forcefullCollider.setCenter(carrier.getCenter().absoluteClone());
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().addSpaceObject(forcefullCollider);
		
		for(Spaceshuttle drone : carrier.getDrones())
			assertTrue(drone.collides(forcefullCollider));
	}
	
	
	@Tag("Complex")
	@Tag("Regression")
	@Test
	void testCollision_dronesOfACarrier_SpaceObjectSomeWhereElse_shouldNotCollide() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<5;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		SpaceObject noCollider = new Star.Builder("EmptyRoot")
				.center(10000,5000)
				.radious(250)
				.levelOfDetail(20)
				.build();
		noCollider.getShape().updateOrInitOutline();
		noCollider.getShape().updateOrInitOutline();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().addSpaceObject(noCollider);
		
		for(Spaceshuttle drone : carrier.getDrones()) {
			assertFalse(drone.collides(noCollider));
			assertFalse(noCollider.collides(drone));	
		}
		assertFalse(carrier.collides(noCollider));
		assertFalse(noCollider.collides(carrier));	
	}
	
	@Tag("Complex")
	@Tag("Regression")
	@Test
	void testCollision_dronesOfACarrier_AreInAHugeSpaceCraft_shouldCollide() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<5;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		Spaceshuttle forcefullCollider = new Spaceshuttle.Builder("Collider",carrierRoot).size(250).levelOfDetail(10).build();
		forcefullCollider.setCenter(carrierRoot.getCenter().absoluteClone());
		forcefullCollider.getShape().updateOrInitOutline();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		
		for(Spaceshuttle drone : carrier.getDrones())
			assertTrue(drone.collides(forcefullCollider));
	}	
	
	@Tag("Complex")
	@Tag("Regression")
	@Test
	void testCollision_dronesOfACarrier_AreInAHugeSpaceCraft_spaceCraftHasSameParentButIsNoDrone_shouldCollide() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<5;i++) {
			b.addDroneMount(SpaceshuttleTests::hugeLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		Spaceshuttle forcefullCollider = new Spaceshuttle.Builder("Collider",carrier).size(250).levelOfDetail(10).build();
		forcefullCollider.setCenter(carrierRoot.getCenter().absoluteClone());
		forcefullCollider.getShape().updateOrInitOutline();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		
		for(Spaceshuttle drone : carrier.getDrones())
			assertTrue(drone.collides(forcefullCollider));
	}
	
	@Tag("Complex")
	@ParameterizedTest
	@ValueSource(ints = {5,10,100})
	void testCollision_dronesShooting_shouldNotCollideWithCarrier(int numberOfDrones) {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		SpaceObject fakeTarget = fakeStar(1000,1000);
		for(Spaceshuttle drone : carrier.getDrones()) {
			drone.shootLaser(fakeTarget);	
		}

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();

		List<Missile> allShots = 
				carrier.getDrones().stream()
				.flatMap(d -> d.getTrabants().stream())
				.filter(m -> m instanceof Missile)
				.map(m -> (Missile) m)
				.collect(Collectors.toList());

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		ManagerRegistry.getCollisionManager().update();
		
		for(Missile m : allShots)
			assertFalse(carrier.collides(m));
	}
	
	@Tag("Complex")
	@Tag("Registry")
	@Tag("Sideeffect")
	@Test
	void testCollision_isCarrier_actuallyCollidesWithAStar() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		var b = new Spaceshuttle.Builder("TestCarrier", carrierRoot);
		for(int i = 0;i<3;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier =  b.size(150).build(); //Huge Carrier, so there is a definite collision
		
		SpaceObject collider = new Star.Builder("Collider").center(carrier.getCenter().absoluteClone()).levelOfDetail(5).radious(10).build();

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);

		assertTrue(carrier.collides(collider));
	}
	
	@Test
	void testAttackTargetSpaceObject_shouldEqualLaunchShipsForCarrier() {
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		Spaceshuttle carrier = makeBattleCarrier();

		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		
		SpaceObject droneTarget = fakeStar(1000,1000);
		carrier.attack(droneTarget);
		
		assertEquals(4, droneTarget.getTrabants().size());
	}

	@Tag("Basic")
	@Test
	void testAttackTargetPoint_ForCarrier_shouldNotLaunchShips() {
		//For more Input on Launching Ships check Spaceshuttle Tests
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		Spaceshuttle carrier = makeBattleCarrier();
		
		Point target = new AbsolutePoint(1000,100);
		carrier.attack(target);
		
		assertEquals(4,carrier.getTrabants().size());
	}

	@Tag("Basic")
	@Test
	void testGetNearestPossibleTarget_noItemsDetected_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
			
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}

	@Tag("Basic")
	@Test
	void testGetNearestPossibleTarget_notDestructible_shouldBeEmptyOptional(){
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(root);
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}

	@Tag("Basic")
	@Test
	void testGetNearestPossibleTarget_SensorNonEmpty_noSpaceObjectInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		stubSensor.detectedItems.add(new FakeCollidingObject());
		
		assertFalse(testObject.getNearestPossibleTarget().isPresent());
	}

	@Tag("Complex")
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}

	@Tag("Complex")
	@Test
	void testGetNearestPossibleTarget_DestructibleItemInSensor_shouldReturnTheDestructibleInOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		
		Optional<SpaceObject> expected = Optional.of(a);
		Optional<SpaceObject> result = testObject.getNearestPossibleTarget();
		
		assertEquals(expected,result);
	}

	@Tag("Complex")
	@Test
	void testGetNearestPossibleTarget_MultipleQualifiedItemsInSensor_shouldBeNonEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		Asteroid b = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(b);
		
		assertTrue(testObject.getNearestPossibleTarget().isPresent());
	}

	@Tag("Complex")
	@Test
	void testGetNearestPossibleTarget_MultipleQualifiedItemsInSensor_shouldReturnFirst() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		Asteroid a = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(a);
		Asteroid b = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(b);
		Asteroid c = new Asteroid("stubDestructible",root,0,0);
		stubSensor.detectedItems.add(c);
		
		Optional<SpaceObject> expected = Optional.of(a);
		Optional<SpaceObject> result = testObject.getNearestPossibleTarget();
		
		assertEquals(expected,result);
	}

	@Tag("Complex")
	@Test
	void testGetNearestPossibleTarget_OnlyDronesInSensor_shouldBeEmptyOptional() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject = makeBattleCarrier();
		
		FakeSensor stubSensor = new FakeSensor();
		testObject.setSensor(stubSensor);
		
		stubSensor.detectedItems.addAll(testObject.getDrones());
		
		var result = testObject.getNearestPossibleTarget(); 

		assertFalse(result.isPresent());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,5,10})
	void testConstructor_checkDrones_DronesShouldHaveDifferentPositions(int numberOfDrones) {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", root).orbitingDistance(100);
		
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier = b.build();
		var drones = carrier.getDrones();
		// We get the drones positions and filter them for distinctiveness, the resulting size should be equal to the number of drones
		var dronePositions = drones.stream().map(d -> d.getCenter()).distinct().collect(Collectors.toSet());
		
		boolean samePositionFound = false;
		for(var p1 : dronePositions)       // Compare Every Drones Position
			for(var p2: dronePositions)    // To every other Drones Position
				if(! p1.equals(p2))        // If they are not the same
					samePositionFound = samePositionFound || (p1.getX() == p2.getX() && p1.getY() == p2.getY());
		assertFalse(samePositionFound);
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@ParameterizedTest
	@ValueSource(ints = { 0,1,3,15})
	void testConstructor_checkDrones_DronesShouldHaveDifferentPositionFromCarrier(int numberOfDrones) {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestCarrier", root).orbitingDistance(100);
		for(int i = 0;i<numberOfDrones;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}
		var carrier = b.build();
		var drones = carrier.getDrones();
		var dronePositions = drones.stream().map(d -> d.getCenter()).distinct().collect(Collectors.toSet());
		
		boolean samePositionFound = false;
		for(var p1 : dronePositions)
					samePositionFound = samePositionFound || (p1.getX() == carrier.getCenter().getX() && p1.getY() == carrier.getCenter().getY());
		assertFalse(samePositionFound);
	}

	@Tag("Basic")
	@Test
	void testEquality_selfReflexivity_ShouldBeTrue() {
		Spaceshuttle testObject = makeBattleCarrier();
		
		assertEquals(testObject,testObject);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_isDroneOfThisCarrier_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertTrue(testCarrier.isMyDrone(drone));
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_testNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(null));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_isDroneOfOtherCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) otherCarrier.getDrones().get(0);
		
		assertFalse(testCarrier.isMyDrone(drone));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_otherIsCarrierButNotDrone_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(otherCarrier));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_otherIsSpaceshuttle_butNeitherCarrierNotDrone_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isMyDrone(shuttle));
	}	

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyDrone_IAmNotACarrier_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(shuttle.isMyDrone(testCarrier));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyCarrier_IAmNotADrone_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(shuttle.isMyCarrier(testCarrier));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyCarrier_IAmHisDrone_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertTrue(drone.isMyCarrier(testCarrier));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyCarrier_isCarrierButNotMyCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isMyCarrier(otherCarrier));
	}

	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsMyCarrier_isNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isMyCarrier(null));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsDroneOfSameCarrier_bothDronesFromSameCarrier_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone 		= testCarrier.getDrones().get(0);
		Spaceshuttle otherDrone = testCarrier.getDrones().get(1);
		
		assertTrue(drone.isDroneWithSameCarrier(otherDrone));
		assertTrue(otherDrone.isDroneWithSameCarrier(drone));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsDroneOfSameCarrier_dronesOfDifferentCarriers_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle otherCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		Spaceshuttle otherDrone = (Spaceshuttle) otherCarrier.getDrones().get(0);
		
		boolean result = drone.isDroneWithSameCarrier(otherDrone);
		
		assertFalse(result);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsDroneOfSameCarrier_IamNotACarrier_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100);
		var shuttle = b.build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(shuttle.isMyCarrier(drone));
	}

	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testIsDroneOfSameCarrier_isNull_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
		
		assertFalse(drone.isDroneWithSameCarrier(null));
	}
	
	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_checkDrone_shouldBeTrue() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = testCarrier.getDrones().get(0);
		
		assertTrue(drone.isDrone());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_checkAllDrones_shouldAllBeDrones() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		testCarrier.getDrones().stream().forEach(d -> assertTrue(d.isDrone()));
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_checkAllDrones_withUpdateManagerUpdate_shouldAllBeDrones() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		ManagerRegistry.getUpdateManager().update();
		
		testCarrier.getDrones().stream().forEach(d -> assertTrue(d.isDrone()));
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_checkingOnDrone_otherItemsAndOtherCarriersInManagers_shouldBeTrue() {
		Spaceshuttle noiseCarrier = makeBattleCarrier();
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle noiseShuttle = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100).build();
		
		Spaceshuttle testCarrier = makeBattleCarrier();
		Spaceshuttle drone = (Spaceshuttle) testCarrier.getDrones().get(0);
	
		assertTrue(drone.isDrone());
		
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_IsCarrier_shouldBeFalse() {
		Spaceshuttle testCarrier = makeBattleCarrier();
		
		assertFalse(testCarrier.isDrone());
	}

	@Tag("Basic")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	public void testIsDrone_NormalSpaceShip_shouldBeFalse() {
		SpaceObject root = fakeStar(0,0);
		var b = new Spaceshuttle.Builder("TestShuttle", root).orbitingDistance(100);
		var shuttle = b.build();
		
		assertFalse(shuttle.isDrone());
	}
	
	/*
	 * ===================================================================================
	 * Armed Spaceshuttle Tests
	 * ===================================================================================
	 */

	@Tag("Basic")
	@Test
	void testConstructor_shouldNotBePlayer() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		
		assertFalse(testObject.isActivePlayer());
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testShootLaserAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.shootLaser(target);
		
	    MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testShootLaserAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootLaser(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testShootRocketAtPoint_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testShootRocketAtTarget_shouldBeShot() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		
		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Rocket);
	}


	@Tag("Complex")
	@Tag("Regression")
	@Test
	void testCollision_doesNotCollideOwnLasers() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootLaser(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    Laserbeam castedLaser = (Laserbeam) missile;
	    assertFalse(testObject.collides(castedLaser));
	}

	@Tag("Regression")
	@Tag("Complex")
	@Test
	void testCollision_doesNotCollideOwnRockets() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);
		

		testObject.shootRocket(target);
		
		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    Rocket castedRocket = (Rocket) missile;
	    assertFalse(testObject.collides(castedRocket));
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testAttackPoint_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		Point target = new AbsolutePoint(1000,1000);
		
		testObject.attack(target);

		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);
	}

	@Tag("Complex")
	@Tag("SideEffect")
	@Tag("Registry")
	@Test
	void testAttackTarget_shouldSpawnLaser() {
		SpaceObject root = fakeStar(0,0);
		Spaceshuttle testObject= new Spaceshuttle("Army",root,0,50,0);
		SpaceObject target = fakeStar(1000,1000);

		testObject.attack(target);

		MovingUpdatingObject missile = testObject.getTrabants().get(0);
	    assertTrue(missile instanceof Missile);
		assertTrue(missile instanceof Laserbeam);	
	}
	
	/*
	 * ===============================================================================
	 * FACTORY METHODS AND HELPERS
	 * ===============================================================================
	 */
	
	
	public static Spaceshuttle makeBattleCarrier() {
		//SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		SpaceObject carrierRoot = new Star.Builder("EmptyRoot").center(0,0).radious(1).levelOfDetail(1).build();
		int randomSeed = (int) (Math.random()*10000);
		var b = new Spaceshuttle.Builder("TestCarrier"+randomSeed, carrierRoot);
		for(int i = 0;i<4;i++) {
			b.addDroneMount(DroneFactory::standardLaserDrone);
		}

		int randomPos = (int)(Math.random()*1000)+50;
		var carrier = b.color(Color.ALICEBLUE)
				 .size(4)
				 .orbitingDistance(randomPos)
				 .rotationSpeed(Math.PI)
				 .speed(-Math.PI)
				 .levelOfDetail(3)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardRocketLauncher)
				 .addMountedWeapon(WeaponFactory::standardLaserCannon)
				 .shallBeCarrier(true)
				 .build();
		
		ManagerRegistry.getUpdateManager().addSpaceObject(carrierRoot);
		ManagerRegistry.getUpdateManager().update();
		return carrier;
	}
	
	public static Spaceshuttle hugeLaserDrone(Spaceshuttle c) {
		//Incredibly big and closeby Drone to force collisions
		return ShipFactory.standardLaserDrone("LaserDrone of "+c.getName(), c, 100, 2, Math.PI/200);
	}
	
}
