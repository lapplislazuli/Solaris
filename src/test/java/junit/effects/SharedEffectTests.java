package junit.effects;

import org.junit.jupiter.api.Test;

interface SharedEffectTests {

	void testConstructor_shouldBeInEffectManager();

	void testConstructor_shouldNotBeInUpdateManager();

	void testConstructor_isNotOrphaned();

	void testRemove_isOrphaned();

	void testIsCovered_pointIsIn_shouldBeTrue();

	void testIsCovered_pointIsOut_shouldBeFalse();

	void testToString_isNameAtPoint();

}