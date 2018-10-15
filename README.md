# Solaris
This Repo contains a Sample-Java-Project of a solar System for Trainees. 

This Repository is for training both cooperation with git aswell as a source for several java-patterns and Best-Practices. 

Feel free to extract code, share your oppinion or contribute!

**Currently Implemented:**
* Intensive Use of  [Polymorphism](https://en.wikipedia.org/wiki/Polymorphism_(computer_science)) (Follow *SpaceObject -> MovingSpaceObject -> Satellite/SpaceShuttle/Planet*)
* Use of [Interfaces](https://en.wikipedia.org/wiki/Protocol_(object-oriented_programming)) (See *DrawingObject*'s implementations *SpaceObject* and *Effect* and their use in *UpdateManager*)
* [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern) (e.g. *UpdateManager*)
* [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern) (*Planet, ShuttleNavigator*)
* [UnitTests](https://en.wikipedia.org/wiki/Unit_testing) with [JUnit5](https://junit.org/junit5/)
* Simple Drawing (See *DistantGalaxy* as Background and *Star* for Items)
* Simple UserInput with KeyBoard or Mouse

**Further Goals:**
* *(Professional)*-Logging with [Log4J](https://logging.apache.org/log4j/2.x/) and usefull sample-Loggersettings
* reading settings from a settings.json
* reading the Galaxy from a file (either .json or .xml)
* Use of the [Factory Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern), connected to reading galaxy-files
* Extend the Game with States and different Screens (Start-Screen, Game-Screen, Deathscreen)
