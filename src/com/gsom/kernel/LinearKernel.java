/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gsom.kernel;

import com.gsom.util.Utils;
import java.util.Arrays;

/**
 *
 * @author maheshakya
 */
public class LinearKernel {
    
    public static double calcKernel(double[] vect1, double[] nodeWeights, int dimension)
    {
        double result = 0;
        for(int i=0; i<dimension; i++)
            result += vect1[i]*nodeWeights[i];
        return result;
    }
    
    public static double LinearKernelDistance(double[] vect1, double[] nodeWeights, int dimension){
        return Math.sqrt(calcKernel(vect1, vect1, dimension) + calcKernel(nodeWeights, nodeWeights, dimension)
                         - 2*calcKernel(vect1, nodeWeights, dimension));
    }
    
    // Function for multiple kernel GSOM
    
    public static double calcKernel(double[] vect1, double[] vect2, double[] nodeWeights, int dimension1, int dimension2, double[] coefs){
        double kernel1 =  calcKernel(vect1, Arrays.copyOfRange(nodeWeights, 0, dimension1), dimension1);
        
        double kernel2 =  calcKernel(vect2, Arrays.copyOfRange(nodeWeights, dimension1, dimension1+dimension2), dimension2);
        
        return coefs[0]*kernel1 + coefs[1]*kernel2;
    }
    
    public static double calcKernel(double[] vect1, double[] vect2, int dimension1, int dimension2, double[] coefs){
        double kernel1 =  calcKernel(vect1, vect1, dimension1);
        
        double kernel2 =  calcKernel(vect2, vect2, dimension2);
        
        return coefs[0]*kernel1 + coefs[1]*kernel2;
    }
    
    public static double calcKernel(double[] nodeWeights, int dimension1, int dimension2, double[] coefs){
        double kernel1 =  calcKernel(Arrays.copyOfRange(nodeWeights, 0, dimension1), Arrays.copyOfRange(nodeWeights, 0, dimension1), dimension1);
        
        double kernel2 =  calcKernel(Arrays.copyOfRange(nodeWeights, dimension1, dimension1+dimension2), Arrays.copyOfRange(nodeWeights, dimension1, dimension1+dimension2), dimension2);
        
        return coefs[0]*kernel1 + coefs[1]*kernel2;
    }
    
    public static double LinearKernelDistance(double[] vect1, double[] vect2, double[] nodeWeights, int dimension1, int dimension2, double[] coefs){
        // sqrt(2* ( number_of_kernels - global kernel ) )
        return Math.sqrt(calcKernel(vect1, vect2, dimension1, dimension2, coefs) + calcKernel(nodeWeights, dimension1, dimension2, coefs)
                         - 2*calcKernel(vect1, vect2, nodeWeights, dimension1, dimension2, coefs));

    }   
}
