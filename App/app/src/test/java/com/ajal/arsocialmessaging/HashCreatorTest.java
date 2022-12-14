package com.ajal.arsocialmessaging;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.ajal.arsocialmessaging.util.HashCreator;

public class HashCreatorTest {

    String inputA = "Hello world!";
    String inputB = "Foo bar";

    @Test
    public void test_HashFunctionChangesInput() throws NoSuchAlgorithmException {
        String output = HashCreator.createSHAHash(inputA);
        assertNotEquals(inputA, output);
    }

    @Test
    public void test_SameInputGivesSameOutput() throws NoSuchAlgorithmException {
        String output1 = HashCreator.createSHAHash(inputA);
        String output2 = HashCreator.createSHAHash(inputA);
        assertEquals(output1, output2);
    }

    @Test
    public void test_DifferentOutputGivesDifferentOutput() throws NoSuchAlgorithmException {
        String output1 = HashCreator.createSHAHash(inputA);
        String output2 = HashCreator.createSHAHash(inputB);
        assertNotEquals(output1, output2);
    }

}

