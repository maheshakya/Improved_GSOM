/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gsom.core;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author thilina
 */
public class ARCTest {

    public ARCTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of update method, of class ARC.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String Coordinate = "1,2";
        ARC instance = new ARC();
        instance.update(Coordinate);

        assert (instance.get_Size() == 1);
    }

}
