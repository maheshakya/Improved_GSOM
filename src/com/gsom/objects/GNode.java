package com.gsom.objects;

import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;

import com.gsom.ui.MainWindow;
import com.gsom.kernel.GaussianKernelL2;
import java.util.Arrays;

public class GNode {

	private int X;
	private int Y;
	private double[] weights;
	private double errorValue;
	private int hitValue;
        
	public GNode(int x,int y,double[] weights){
		this.X = x;
		this.Y = y;
		this.weights = weights;
	}	
			
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void calcAndUpdateErr(double[] iWeight){
            if (MainWindow.distance==3)
                this.errorValue += Utils.calcGausssianKernelDistance(this.weights, iWeight,GSOMConstants.DIMENSIONS);
            else if (MainWindow.distance==4)
                this.errorValue += Utils.calcLinearKernelDistance(this.weights, iWeight,GSOMConstants.DIMENSIONS);
            else
		this.errorValue += Utils.calcEucDist(this.weights, iWeight,GSOMConstants.DIMENSIONS);
	}
        
        // Function for multiple kernel
        public void calcAndUpdateErr(double[] iWeight1, double[] iWeight2, double[] coefs ){
            if (MainWindow.distance==5)
                this.errorValue += Utils.calcMultipleLinearKernelDistance(iWeight1, iWeight2, this.weights, iWeight1.length, iWeight2.length, coefs);
            
            if (MainWindow.distance==6)
                this.errorValue += Utils.calcMultipleGaussianLinearDistance(iWeight1, iWeight2, this.weights, iWeight1.length, iWeight2.length, coefs);
     	}
	
	public double getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(double errorValue) {
		this.errorValue = errorValue;
	}
	
	public void adjustWeights(double[] iWeights,double influence,double learningRate){

		for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			weights[i] += influence*learningRate*(iWeights[i]-weights[i]);
		}
	}
        
        public void adjustWeightsGausssian(double[] iWeights,double influence,double learningRate){              
           
                double coef = influence*learningRate*
                        (1/2*Math.pow(GSOMConstants.SIGMA_FOR_GAUSSIAN, 2))*
                        GaussianKernelL2.calcKernel(iWeights, weights, GSOMConstants.DIMENSIONS);
		for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			weights[i] += coef*(iWeights[i]-weights[i]);
		}
                

	}
        
        public void adjustWeightsLinear(double[] iWeights,double influence,double learningRate){           
           
                for(int i=0;i<GSOMConstants.DIMENSIONS;i++){
			weights[i] += influence*learningRate*2*(iWeights[i] - weights[i]);
		}
                

	}
        
        // Function for multiple kernels
        public void adjustWeightsLinear(double[] iWeights1, double[] iWeights2, double influence,double learningRate, double[] coefs){           
           
                for(int i=0;i<iWeights1.length;i++){
			weights[i] += influence*learningRate*2*(iWeights1[i] - weights[i])*coefs[0];
		}
                
                for(int i=iWeights1.length;i<iWeights1.length+iWeights2.length;i++){
			weights[i] += influence*learningRate*2*(iWeights2[i-iWeights1.length] - weights[i])*coefs[0];
		}               

	}
        
        public void adjustWeightsGaussianLinear(double[] iWeights1, double[] iWeights2, double influence, double learningRate, double[] coefs){           
           
                for(int i=0;i<iWeights1.length;i++){
			weights[i] += influence*learningRate*2*(iWeights1[i] - weights[i])*coefs[0];
		}
                
                double coef = influence*learningRate*
                        (1/2*Math.pow(GSOMConstants.SIGMA_FOR_GAUSSIAN, 2))*
                        GaussianKernelL2.calcKernel(iWeights2, Arrays.copyOfRange(weights, iWeights1.length, iWeights1.length+iWeights2.length), iWeights2.length);
                
                for(int i=iWeights1.length;i<iWeights1.length+iWeights2.length;i++){
			weights[i] += coef*(iWeights2[i-iWeights1.length]-weights[i])*coefs[1];
		}
                

	}
        
        

    /**
     * @return the hitValue
     */
    public int getHitValue() {
        return hitValue;
    }

    /**
     * @param hitValue the hitValue to set
     */
    public void setHitValue(int hitValue) {
        this.hitValue = hitValue;
    }
        
        
}
