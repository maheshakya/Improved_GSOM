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
public class LinearKernel implements Kernel{
    
    public static double calcKernel(double[] vect1, double[] nodeWeights, int dimension)
    {
        double result = 0;
        for(int i=0; i<dimension; i++)
            result += vect1[i]*nodeWeights[i];
        
        return result;
    }
    
    public static double kernelDistance(double[] vect1, double[] nodeWeights, int dimension){
        double ker1 = calcKernel(vect1, vect1, dimension);
        double ker2 = calcKernel(nodeWeights, nodeWeights, dimension);
        double ker3 = calcKernel(vect1, nodeWeights, dimension);
        
        double res = Math.sqrt(Math.abs(ker1 + ker2 - 2*ker3));
        
        return res;
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
        // 
        double ker1 = calcKernel(vect1, vect2, dimension1, dimension2, coefs);
        double ker2 = calcKernel(nodeWeights, dimension1, dimension2, coefs);
        double ker3 = calcKernel(vect1, vect2, nodeWeights, dimension1, dimension2, coefs);
        
        double res = Math.sqrt(Math.abs(ker1 + ker2 - 2*ker3));
        
        return res;

    }   

    @Override
    public void calcKernel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kernelDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
