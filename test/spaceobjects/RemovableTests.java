package spaceobjects;

import org.junit.jupiter.api.Test;

public interface RemovableTests {

	void testConstructors_IsInParentsChildren();

	void testConstructors_isNotOrphaned();

	void testRemove_isNotInParentsChildren();

	void testRemove_isOrphaned();

	void testRemove_doubleRemove_shouldThrowNullpoint();

}