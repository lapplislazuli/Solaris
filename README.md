# Solaris
[![Build](https://github.com/Twonki/Solaris/workflows/javaCI/badge.svg)](https://github.com/Twonki/Solaris/actions)
![License: MIT](https://img.shields.io/badge/License-MIT-hotpink.svg)

This repository contains a sample-java-project of a solar system for programmer-trainees.

This repository is for training both cooperation with git as well as a source for several java-patterns and best-practices.

Feel free to extract code, share your opinion or contribute!

## What to see

* [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern) (e.g. *UpdateManager*)
* [Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern) (*Planet, ShuttleNavigator*)
* [UnitTests](https://en.wikipedia.org/wiki/Unit_testing) with [JUnit5](https://junit.org/junit5/)
* Simple userinput with keyboard or mouse
* reading, altering and saving a JSON configurationfile, including keybindings and general settings (see package *config*)
* Simple logging with [Log4J2](https://logging.apache.org/log4j/2.x/) and simple sample-loggersettings
* Its a Maven and Java16Project!
* It is modular!
* Travis CI

**Further Goals:**

* reading the Galaxy from a file (either .json or .xml)
* Use of the [Factory Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern), connected to reading galaxy-files
* Extend the game with states and different screens (start-screen, game-screen, deathscreen)

However, some minor features and refactoring are always ongoing.

## How to Build and run

### Run, Test, Build

I am using Eclipse, so maybe your IDE varies how to perform certain tasks. However, the ideas used are from maven and therefore should also be doable by console.

For all the following you need to select the project and "Run as" -> "Maven Build ...".
For the goal to run, enter

`clean javafx:run`

to test

`clean test`

to compile a runnable jar into /shade/

`clean compile package`

**Don't forget to name your runconfigurations!** It's in the topmost entry field. Otherwise you will be stuck with some cryptic "Solaris(1)","Solaris(2)",etc. 

You can leave the `clean` from every goal, however the caching from maven can hurt you sometimes really bad. 

If you are aware of your dependencies and clean manually when required, these can be left out. For an easy and failsafe entry I spared you those. 

**With Console:** you have to put `mvn` in front of the build goals, e.g. `mvn clean install`. Don´t forget to navigate to the root of the folder where the pom.xml is. 

### The .Jar

Once Solaris.jar is build, you can run it via

`$> java -jar Solaris.jar`

You will need to have the jar executable (should be default, but sometimes linux is tricky) and you will need to have a **config.json next to the jar**.

The logfile should appear next to the jar after you (tried) to run it. If something breaks, first check your config, and if something else is faulty please open me an issue.

If you are not a developer and searching for a valid config, either see the repository here or open the Solaris.jar. It has a config.json in it's root.

### Debugging

*Sidenote*: If you want to test some parts of your program, not the total running program, consider just adding debug points into unit tests. The following is for running the full application in debugging.

Debugging the javafx application is a bit more complex. Not too much tho.
Run the program from console using

`mvn javafx:run@debug`

This will tell you that there is a debug port on 8000.

Now you need to attach a remote Java Program in your IDE, for eclipse go:

`Debug :bug: -> DebugConfigurations -> Remote Java Application`

Choose a nice name (such as SolarisRemoteDebug), select Solaris as the Project and keep the Port 8000. 
The logger output will be on the initial console, while the debugging runs normal.

Optionally, if you have added JavaFx properly in your system path, you can just click on `logic.program` *run as - debug*.

### Special Remarks

Currently when "running" you need another config.json in your root directory of the project.

I have it gitignored, so just copy the one from src/main/resources and you should be fine.

To have the config automaticly copied next to the jar is another goal of mine. But for now it needs to be done manually.

## Where to Start

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

## Build with

* Java16
* [Maven](https://maven.apache.org/)
* [JUnit5](https://junit.org/junit5/)
* [Log4J2](https://logging.apache.org/log4j/2.x/)
* [JavaFX16](https://openjfx.io/)
