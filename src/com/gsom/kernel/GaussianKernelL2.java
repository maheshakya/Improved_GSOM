/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gsom.kernel;

/**
 *
 * @author maheshakya
 */
import com.gsom.util.Utils;
import com.gsom.util.GSOMConstants;

public class GaussianKernelL2 {
    
    public static double vectorSquaredMagnitude(double[] vect1, double[] vect2, int dimension){
        double result = 0;
        for (int i=0; i<dimension; i++)
            result += Math.pow((vect1[i] - vect2[i]),2);
        return result;
    }
    
    public static double vectorMagnitude(double[] vect1, double[] vect2, int dimension){
        return Math.sqrt(vectorSquaredMagnitude(vect1, vect2, dimension));
    }
    
    
    public static double calcKernel(double[] vect1, double[] vect2, int dimension){
        double result = 0;
        
        return Math.exp(-vectorSquaredMagnitude(vect1, vect2, dimension)/
                (2*Math.pow(GSOMConstants.SIGMA_FOR_GAUSSIAN, 2)));
    }
    
    
}
