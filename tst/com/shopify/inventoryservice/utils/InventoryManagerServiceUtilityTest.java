package com.shopify.inventoryservice.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerServiceUtilityTest {

    @Test
    void isValidEmailAddress_emptyString_returnsFalse() {
        // GIVEN
        String emptyEmailAddress = "";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidEmailAddress(emptyEmailAddress);

        // THEN
        assertFalse(result, "Expected empty email address to return false: " + emptyEmailAddress);
    }

    @Test
    void isValidEmailAddress_invalidEmailAddress_returnsFalse() {
        // GIVEN
        String invalidEmailAddress= "gmail.com@email";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidEmailAddress(invalidEmailAddress);

        // THEN
        assertFalse(result, "Expected string with an invalid Email Address to return false: " + invalidEmailAddress);
    }

    @Test
    void isValidEmailAddress_validEmailAddress_returnsTrue() {
        // GIVEN
        String validEmailAddress = "email@gmail.com";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidEmailAddress(validEmailAddress);

        // THEN
        assertTrue(result, "Expected string with a validEmailAddress to return true: " + validEmailAddress);
    }

    @Test
    void isValidPassword_emptyString_returnsFalse() {
        // GIVEN
        String emptyPassword = "";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidPassword(emptyPassword);

        // THEN
        assertFalse(result, "Expected empty password to return false: " + emptyPassword);
    }

    @Test
    void isValidPassword_invalidPassword_returnsFalse() {
        // GIVEN
        String invalidPassword= "hacked";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidPassword(invalidPassword);

        // THEN
        assertFalse(result, "Expected string with an invalid Password to return false: " + invalidPassword);
    }

    @Test
    void isValidPassword_validPassword_returnsTrue() {
        // GIVEN
        String validPassword = "$ecur1Ty";

        // WHEN
        boolean result = InventoryManagerServiceUtility.isValidPassword(validPassword);

        // THEN
        assertTrue(result, "Expected string with a valid Password to return true: " + validPassword);
    }

    @Test
    void generateId_returnsRandomTenCharacterString() {
        // WHEN
        String id = InventoryManagerServiceUtility.generateId();

        // THEN
        assertEquals(10, id.length(), "Expected random ID length to be 10 characters.");
    }
}
