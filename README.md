# Solaris
This repository contains a sample-java-project of a solar system for programmer-trainees. 

This repository is for training both cooperation with git aswell as a source for several java-patterns and best-practices. 

Feel free to extract code, share your oppinion or contribute!


## Currently implemented:
* Intensive use of [polymorphism](https://en.wikipedia.org/wiki/Polymorphism_(computer_science)) (follow *SpaceObject -> MovingSpaceObject -> Satellite/SpaceShuttle/Planet*)
* Use of [Interfaces](https://en.wikipedia.org/wiki/Protocol_(object-oriented_programming)) (See *DrawingObject*'s implementations *SpaceObject* and *Effect* and their use in *UpdateManager*)
* [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern) (e.g. *UpdateManager*)
* [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern) (*Planet, ShuttleNavigator*)
* [UnitTests](https://en.wikipedia.org/wiki/Unit_testing) with [JUnit5](https://junit.org/junit5/)
* Simple drawing (See *DistantGalaxy* as Background and *Star* for Items)
* Simple userinput with keyboard or mouse
* reading, altering and saving a JSON configurationfile, including keybindings and general settings (see package *config*) 
* Simple logging with [TinyLog](https://tinylog.org)

**Further Goals:**
* *(Professional)*-logging with [Log4J](https://logging.apache.org/log4j/2.x/) and usefull sample-loggersettings
* reading the Galaxy from a file (either .json or .xml)
* Use of the [Factory Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern), connected to reading galaxy-files
* Extend the game with states and different screens (start-screen, game-screen, deathscreen)
* Migrate Solaris to a maven project

## Where to Start?
I appreciate that you want to join the fun! Welcome on board of the Icarus. 

So what are the best places to start? 

* **Make new shapes** and show them ! Got an idea for a new satellite? New alienships? cool ice-asteroids?
* **Make new actions** and bind them to keys. There are always cool actions we need to explore the galaxy. 
* **Make new effects**. Our ship still requires energyshields, nuclearboosts, and a sonarmodule. We are in need of genious engineers like you!

How do you join the Crew? 

1. Open an issue with your idea or tell under an existing issue that your eager to fulfill
2. Please read the [contribution policy](https://github.com/Twonki/Solaris/blob/master/CONTRIBUTING.md)
3. Assign yourself to the issue you want and go ahead!

Not sure if your code is fine? Consider tagging *Help Wanted* or for a deeper review open an pull-request of your changes. 
**There is no bad code - your help is always vital to the sake of our galaxy.**
