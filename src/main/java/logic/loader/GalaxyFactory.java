package logic.loader;

import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.spacecraft.AggressiveNavigator;
import javafx.scene.paint.Color;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;
import space.advanced.AsteroidBelt;
import space.advanced.DistantGalaxy;
import space.core.Planet;
import space.core.Satellite;
import space.core.Star;
import space.spacecraft.ships.devices.WeaponFactory;
import space.spacecrafts.navigators.ArmedShuttleNavigator;
import space.spacecrafts.navigators.BaseNavigator;
import space.spacecrafts.ships.Spaceshuttle;

public final class GalaxyFactory {
	
	public static final void defaultGalaxy() {
			UpdateManager updateManager = ManagerRegistry.getUpdateManager();
			
			DistantGalaxy milkyway = new DistantGalaxy("MilkyWay",250);
			
			Star sun = new Star("Sun",new JavaFXDrawingInformation(Color.ORANGE),new AbsolutePoint(600,400),30);
			Planet earth = (new Planet.Builder("Earth", sun))
					.size(13)
					.distance(150)
					.levelOfDetail(10)
					.speed(Math.PI/2000)
					.rotationSpeed(Math.PI*2/365)
					.color(Color.DARKCYAN)
					.build();
			Planet mars = (new Planet.Builder("Mars", sun))
					.size(19)
					.distance(280)
					.levelOfDetail(20)
					.speed(Math.PI/1000)
					.rotationSpeed(Math.PI*2/800)
					.color(Color.INDIANRED)
					.build();

			Planet moon = (new Planet.Builder("Moon", earth))
					.size(3)
					.distance(30)
					.levelOfDetail(7)
					.speed(Math.PI/800)
					.rotationSpeed(Math.PI*2/30)
					.color(Color.LIGHTGRAY)
					.build();
					
			Planet saturn = (new Planet.Builder("Saturn", sun))
					.size(20)
					.color(Color.LIGHTGOLDENRODYELLOW)
					.speed(Math.PI/4000)
					.rotationSpeed(Math.PI/10000)
					.distance(560)
					.build();
			
			AsteroidBelt andromeda = (new AsteroidBelt.Builder("Andromeda",sun))
					.distance(400)
					.speed(Math.PI/4000)
					.asteroids(60)
					.build();
			
			var playerShuttle = new Spaceshuttle.Builder("Ikarus", earth)
									.size(4)
									.orbitingDistance(45)
									.speed(Math.PI/150)
									.isPlayer()
									.color(Color.LIGHTSALMON)
									.spawnSpaceTrashOnDestruct(false)
									.setStandardWeaponry(true)
									.build();
			
			//AggressiveNavigator playerNav = ArmedShuttleNavigator.PlayerNavigator("Nasa",playerShuttle);
			AggressiveNavigator playerNav = new ArmedShuttleNavigator("Nasa",playerShuttle,true);
			playerNav.getRoute().add(sun);
			
			ManagerRegistry.getPlayerManager().registerPlayerNavigator(playerNav);
			
			Satellite astra = (new Satellite.Builder("Astra", earth))
					.size(2,2)
					.distance(17)
					.levelOfDetail(8)
					.speed(-Math.PI/400)
					.color(Color.LIGHTGRAY)
					.build();
			
			Satellite hubble = (new Satellite.Builder("Hubble-Telescope", sun))
					.size(3,3)
					.distance(70)
					.levelOfDetail(8)
					.speed(-Math.PI/800)
					.color(Color.CADETBLUE)
					.build();
			
			Planet phobos = (new Planet.Builder("Phobos", mars))
					.size(3)
					.distance(55)
					.levelOfDetail(3)
					.speed(-Math.PI/300)
					.rotationSpeed(-Math.PI/400)
					.color(Color.LIGHTGRAY)
					.build();
			
			Planet deimos = (new Planet.Builder("Deimos", mars))
					.size(2)
					.distance(35)
					.levelOfDetail(3)
					.speed(Math.PI/600)
					.rotationSpeed(Math.PI*2/800)
					.color(Color.GRAY)
					.build();
			
			Spaceshuttle martians = new Spaceshuttle.Builder("Martians", mars)
										.size(3)
										.orbitingDistance(80)
										.speed(Math.PI/100)
										.setStandardWeaponry(true)
										.spawnSpaceTrashOnDestruct(true)
										.color(Color.DARKKHAKI)
										.build();
			ArmedShuttleNavigator aliens = new ArmedShuttleNavigator("Alien Invader",martians,true);
			aliens.getRoute().add(saturn);
			aliens.getRoute().add(sun);
			aliens.setAutoAttack(true);
			
			Spaceshuttle chineseShip = new Spaceshuttle.Builder("Chinese", sun)
											.size(2)
											.orbitingDistance(80)
											.levelOfDetail(2)
											.speed(Math.PI/250)
											.build();
			BaseNavigator chinNav = new BaseNavigator("Xin Ping", chineseShip,true);
			chinNav.getRoute().add(mars);
			
			Spaceshuttle mothership = new Spaceshuttle.Builder("Mothership",sun)
					.size(6)
					.speed(Math.PI/600)
					.color(Color.DARKSLATEGREY)
					.orbitingDistance(480)
					.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
					.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
					.addMountedWeapon(WeaponFactory::standardLaserDroneMount)
					.build();
			ArmedShuttleNavigator carrierNav = new ArmedShuttleNavigator("Overlord",mothership,false);
			carrierNav.getRoute().add(earth);
			carrierNav.setAutoAttack(true);
			
			updateManager.addSpaceObject(milkyway);
			updateManager.addSpaceObject(sun);
			
			updateManager.update();
	}
}
