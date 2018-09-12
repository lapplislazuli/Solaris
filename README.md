# Solaris
Sample Project of a solar System for Trainees. 

This Repository is for training both cooperation with git aswell as a source for several java-patterns and Best-Practices. 

Feel free to extract code, share your oppinion or contribute!

Currently Implemented:
* Strong Polymorphism (Follow *SpaceObject -> MovingSpaceObject -> Satellite/SpaceShuttle/Planet*)
* Use of Interfaces (See *DrawingObject*'s implementations *SpaceObject* and *Effect* and their use in UpdateManager)
* Singleton Pattern (e.g. *UpdateManager*)
* Builder Pattern (*Planet, ShuttleNavigator*)
* UnitTests with JUnit5
* Simple Drawing (Look for Distant Galaxy as Background and Star for Items)
* Simple UserInput with KeyBoard or Mouse

Further Goals:
* *(Professional)*-Logging with Log4J and usefull sample-Loggersettings
* reading settings from a settings.json
* reading the Galaxy from a file (either .json or .xml)
* Extend the Game with States and different Screens (Start-Screen, Game-Screen, Deathscreen)
## Contribution
### Branching Policy
We are using a shrunk form of the common Git Flow.

The central repo holds two main branches with an infinite lifetime: master and dev.

* The **master** branch is the branch where the source code always reflects a production-ready state.
* The **dev**elop branch is the branch where the source code always reflects a state with the latest delivered development changes for the next release.
Feature branches are used to develop new features for the upcoming release. They are derived from the issue board. Every feature-branch is always created from the dev branch and will solely be remerged into dev.

A feature-branch's lifetime should be as short as possible.

Commits into the master are strictly forbidden - always use a pull request.

### Further information
* Create code,commits and assets in English (only).
* Note the number of your issue in your commit-messages to take full advtantage of the issueboard.
* Assign yourself to an issue in the issue board.
* If you are stuck at an issue, consider adding the Label Help Wanted.
* Consider pushing your feature branches remote, so others can help or pick up your work. Tidy up when the issue is closed.
* Close the issue only if you are changes are visible to other (a remote branch is ok). 
