# Solaris
This Repo contains a Sample-Java-Project of a solar System for Trainees. 

This Repository is for training both cooperation with git aswell as a source for several java-patterns and Best-Practices. 

Feel free to extract code, share your oppinion or contribute!


## Currently Implemented:
* Intensive Use of  [Polymorphism](https://en.wikipedia.org/wiki/Polymorphism_(computer_science)) (Follow *SpaceObject -> MovingSpaceObject -> Satellite/SpaceShuttle/Planet*)
* Use of [Interfaces](https://en.wikipedia.org/wiki/Protocol_(object-oriented_programming)) (See *DrawingObject*'s implementations *SpaceObject* and *Effect* and their use in *UpdateManager*)
* [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern) (e.g. *UpdateManager*)
* [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern) (*Planet, ShuttleNavigator*)
* [UnitTests](https://en.wikipedia.org/wiki/Unit_testing) with [JUnit5](https://junit.org/junit5/)
* Simple Drawing (See *DistantGalaxy* as Background and *Star* for Items)
* Simple UserInput with KeyBoard or Mouse
* reading, altering and saving a JSON configurationfile, including keybindings and general settings (see package *config*) 

**Further Goals:**
* *(Professional)*-Logging with [Log4J](https://logging.apache.org/log4j/2.x/) and usefull sample-Loggersettings
* reading the Galaxy from a file (either .json or .xml)
* Use of the [Factory Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern), connected to reading galaxy-files
* Extend the Game with States and different Screens (Start-Screen, Game-Screen, Deathscreen)

## Where to Start?
I appreciate that you want to join the fun! Welcome on board of the Icarus. 

So what are the best places to start? 

* **Make new Shapes** and show them ! Got an idea for a new Satellite? New Alienships?
* **Make new Actions** and bind them to keys. There are always cool Actions we need to explore the galaxy. 
* **Make new effects**. Our ship still requires Energy Shields, Nuclear Boosts, and a Sonar Module. We require genious engineers like you!

How do you join the Crew? 

1. Open an Issue with your idea or tell under an existing Issue that your eager to fulfill
2. Please read the [Contribution Policy](https://github.com/Twonki/Solaris/blob/master/CONTRIBUTING.md)
3. Assign yourself to the Issue you want and go ahead!

Not sure if your code is fine? Consider tagging *Help Wanted* or for a deeper review open an pull-request for your changes. 
**There is no bad code - your help is always vital to the sake of our galaxy.**
