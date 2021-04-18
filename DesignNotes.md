# Design Notes

This markdown holds some of the thoughts I had while making this project.
I had the idea quite late, but well, better late than never, right? 


## Technical 

###  Points as Java-Records

Java Records are pretty cool, but when this Game started off there were none around (J11). 
At the time of this writing, there was already an 
issue with current implementation that required absolute Points to be set during the game. 
However, the Record does not allow points to have setters.
The only way to do this with a record is to create a new record with the new value in place. 

But, unfortunately, the new point is not exactly the same as beforehand, which means that the references are exchanged. 
As many objects have e.g. a relative point, that is one that is relative to an absolute point, clearing the reference is not doable.
I think the biggest point is the "move" method, which would need to be used with a return value, so basically a lot of the following: 
```java
// Old 
planet.point.move(newX,newY);
// New
planet.point = planet.point.move(newX,newY);
```

However, I also think it's nicer with points and a small rewrite should be doable - I just hadn't had the time. 
Most Tests should still be usable and hence most logic should be alright when the tests go green. 

