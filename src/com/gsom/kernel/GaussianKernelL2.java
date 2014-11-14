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
    
    
    public static double calcKernel(double[] vect1, double[] nodeWeights, int dimension){
        double result = 0;
        
        return Math.exp(-vectorSquaredMagnitude(vect1, nodeWeights, dimension)/
                (2*Math.pow(GSOMConstants.SIGMA_FOR_GAUSSIAN, 2)));
    }
    
    public static double gaussianKernelDistance(double[] vect1, double[] nodeWeights, int dimension){
        return Math.sqrt(2*(1-calcKernel(vect1, nodeWeights, dimension)));
    }   
    
    public static double calcKernel(double[] vect1, double[] vect2, double[] nodeWeights, int dimension1, int dimension2, double[] coefs){
        double kernel1 =  calcKernel(vect1, nodeWeights, dimension1);
        
        double kernel2 =  calcKernel(vect2, nodeWeights, dimension2);
        
        return coefs[0]*kernel1 + coefs[1]*kernel2;
    }
    
    public static double gaussianKernelDistance(double[] vect1, double[] vect2, double[] nodeWeights, int dimension1, int dimension2, double[] coefs){
        // sqrt(2* ( sum_of_coefficients - global kernel ) )
        return Math.sqrt(2*((coefs[0] + coefs[1])-calcKernel(vect1, vect2, nodeWeights, dimension1, dimension2, coefs)));
    }   

    
}
