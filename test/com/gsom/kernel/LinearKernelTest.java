/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gsom.kernel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maheshakya
 */
public class LinearKernelTest {
    
    public LinearKernelTest() {
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
     * Test of calcKernel method, of class LinearKernel.
     */
    @Test
    public void testCalcKernel_3args() {
        System.out.println("calcKernel");
        double[] vect1 = null;
        double[] nodeWeights = null;
        int dimension = 0;
        double expResult = 0.0;
        double result = LinearKernel.calcKernel(vect1, nodeWeights, dimension);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kernelDistance method, of class LinearKernel.
     */
    @Test
    public void testKernelDistance_3args() {
        System.out.println("kernelDistance");
        double[] vect1 = null;
        double[] nodeWeights = null;
        int dimension = 0;
        double expResult = 0.0;
        double result = LinearKernel.kernelDistance(vect1, nodeWeights, dimension);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcKernel method, of class LinearKernel.
     */
    @Test
    public void testCalcKernel_6args() {
        System.out.println("calcKernel");
        double[] vect1 = null;
        double[] vect2 = null;
        double[] nodeWeights = null;
        int dimension1 = 0;
        int dimension2 = 0;
        double[] coefs = null;
        double expResult = 0.0;
        double result = LinearKernel.calcKernel(vect1, vect2, nodeWeights, dimension1, dimension2, coefs);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcKernel method, of class LinearKernel.
     */
    @Test
    public void testCalcKernel_5args() {
        System.out.println("calcKernel");
        double[] vect1 = null;
        double[] vect2 = null;
        int dimension1 = 0;
        int dimension2 = 0;
        double[] coefs = null;
        double expResult = 0.0;
        double result = LinearKernel.calcKernel(vect1, vect2, dimension1, dimension2, coefs);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcKernel method, of class LinearKernel.
     */
    @Test
    public void testCalcKernel_4args() {
        System.out.println("calcKernel");
        double[] nodeWeights = null;
        int dimension1 = 0;
        int dimension2 = 0;
        double[] coefs = null;
        double expResult = 0.0;
        double result = LinearKernel.calcKernel(nodeWeights, dimension1, dimension2, coefs);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LinearKernelDistance method, of class LinearKernel.
     */
    @Test
    public void testLinearKernelDistance() {
        System.out.println("LinearKernelDistance");
        double[] vect1 = null;
        double[] vect2 = null;
        double[] nodeWeights = null;
        int dimension1 = 0;
        int dimension2 = 0;
        double[] coefs = null;
        double expResult = 0.0;
        double result = LinearKernel.LinearKernelDistance(vect1, vect2, nodeWeights, dimension1, dimension2, coefs);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcKernel method, of class LinearKernel.
     */
    @Test
    public void testCalcKernel_0args() {
        System.out.println("calcKernel");
        LinearKernel instance = new LinearKernel();
        instance.calcKernel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kernelDistance method, of class LinearKernel.
     */
    @Test
    public void testKernelDistance_0args() {
        System.out.println("kernelDistance");
        LinearKernel instance = new LinearKernel();
        instance.kernelDistance();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
