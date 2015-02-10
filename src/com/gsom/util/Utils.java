package com.gsom.util;

import com.gsom.util.GSOMConstants;
import java.util.Map;

import com.gsom.objects.GNode;
import com.gsom.ui.MainWindow;

import com.gsom.kernel.GaussianKernelL2;
import com.gsom.kernel.LinearKernel;
import java.util.Arrays;

public class Utils {

    
    public static double[] generateLinearArray(int dimensions,double startVal){
        double[] arr = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            arr[i]=startVal;
        }
        return arr;
    }
    
    public static double[] generateRandomArray(int dimensions) {
        double[] arr = new double[dimensions];
        double sum = 0;
        for (int i = 0; i < dimensions; i++) {
            arr[i] = Math.random();
            sum += arr[i];
        }
        
        // For convex coefficients
        for (int i = 0; i < dimensions; i++)
            arr[i] /= sum;
        
        
        return arr;
    }
    
    // Funtion for multiple kernel
    public static double[] generateRandomArray(int dimension1, int dimension2) {
        double[] arr = new double[dimension1+dimension2];
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < dimension1; i++) {
            arr[i] = Math.random();
            sum1 += arr[i];
        }
        
        for (int i = dimension1; i < dimension1+dimension2; i++) {
            arr[i] = Math.random();
            sum2 += arr[i];
        }
        
        // For convex coefficients
        for (int i = 0; i < dimension1; i++)
            arr[i] /= sum1;
        
        for (int i = dimension1; i < dimension1+dimension2; i++)
            arr[i] /= sum2;
        
        return arr;
    }

    public static String generateIndexString(int x, int y) {
        return x + "," + y;
    }

    public static double getLearningRate(int iter, int nodeCount) {
        return GSOMConstants.START_LEARNING_RATE * Math.exp(-(double) iter / GSOMConstants.MAX_ITERATIONS) * (1 - (double)(3.8 / nodeCount));
    }

    public static double getTimeConst() {
        return (double) GSOMConstants.MAX_ITERATIONS / Math.log(GSOMConstants.MAX_NEIGHBORHOOD_RADIUS);
    }

    //get the node with the minimal ED to the input (winner)
    public static GNode selectWinner(Map<String, GNode> nodeMap, double[] input) {
        GNode winner = null;
        double currDist = Double.MAX_VALUE;
        double minDist = Double.MAX_VALUE;

        if (MainWindow.distance == 0) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcEucDist(input, entry.getValue().getWeights(), GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        } else if (MainWindow.distance == 1) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcChiDistance(input, entry.getValue().getWeights(),GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        } else if (MainWindow.distance == 2) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcCosineDist(input, entry.getValue().getWeights(),GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        }
        else if (MainWindow.distance == 3) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = -GaussianKernelL2.calcKernel(input, entry.getValue().getWeights(),GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        }
        else if (MainWindow.distance == 4) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = LinearKernel.calcKernel(entry.getValue().getWeights(), entry.getValue().getWeights(),GSOMConstants.DIMENSIONS) - 
                        2*LinearKernel.calcKernel(input, entry.getValue().getWeights(),GSOMConstants.DIMENSIONS);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
            return winner;
        }
        
        return null;

    }
    
    // Winner selection for multiple kernels
    public static GNode selectWinner(Map<String, GNode> nodeMap, double[] input1, double[] input2, double[] coefs) {
        GNode winner = null;
        double currDist = Double.MAX_VALUE;
        double minDist = Double.MAX_VALUE;
        
        if (MainWindow.distance == 5) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcMultipleLinearKernelDistance(input1, input2, entry.getValue().getWeights(), input1.length, input2.length, coefs);
                //System.out.println(minDist + ", " + currDist);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
       
        }
        if (MainWindow.distance == 6) {
            for (Map.Entry<String, GNode> entry : nodeMap.entrySet()) {
                currDist = Utils.calcMultipleGaussianLinearDistance(input1, input2, entry.getValue().getWeights(), input1.length, input2.length, coefs);
                //System.out.println(minDist + ", " + currDist);

                if (currDist < minDist) {
                    winner = entry.getValue();
                    minDist = currDist;
                }
            }
       
        }  
        return winner;
    }

    //---TESTED INline with the C# code---
    public static GNode adjustNeighbourWeight(GNode node, GNode winner, double[] input, double radius, double learningRate) {
        double nodeDistSqr = Math.pow(winner.getX() - node.getX(), 2) + Math.pow(winner.getY() - node.getY(), 2);
        double radiusSqr = radius*radius;
        //if node is within the radius
        if (nodeDistSqr < radiusSqr) {
            double influence = Math.exp(-(double)nodeDistSqr / (2 * radiusSqr));
            if(MainWindow.distance==3)
                node.adjustWeightsGausssian(input, influence, learningRate);
            else if (MainWindow.distance==4)
                node.adjustWeightsLinear(input, influence, learningRate);
            else
                node.adjustWeights(input, influence, learningRate);
        }
        return node;
    }
    
    // Function for multiple kernels
    public static GNode adjustNeighbourWeight(GNode node, GNode winner, double[] input1, double[] input2, double radius, double learningRate, double[] coefs) {
        double nodeDistSqr = Math.pow(winner.getX() - node.getX(), 2) + Math.pow(winner.getY() - node.getY(), 2);
        double radiusSqr = radius*radius;
        //if node is within the radius
        if (nodeDistSqr < radiusSqr) {
            double influence = Math.exp(-(double)nodeDistSqr / (2 * radiusSqr));
            if(MainWindow.distance==5)
                node.adjustWeightsLinear(input1, input2, influence, learningRate, coefs);
            
            if(MainWindow.distance==6)
                node.adjustWeightsGaussianLinear(input1, input2, influence, learningRate, coefs);
        }
        return node;
    }
    
     public static double getInfluence(GNode node, GNode winner, double radius, double learningRate) {
        double nodeDistSqr = Math.pow(winner.getX() - node.getX(), 2) + Math.pow(winner.getY() - node.getY(), 2);
        double radiusSqr = radius*radius;
        //if node is within the radius
        
        double influence = 0;
        if (nodeDistSqr < radiusSqr)
            influence = Math.exp(-(double)nodeDistSqr / (2 * radiusSqr));
        return influence;
    }

    public static double getRadius(int iter, double timeConst) {
        return GSOMConstants.MAX_NEIGHBORHOOD_RADIUS * Math.exp(-(double) iter / timeConst);
    }

    public static double calcEucDist(double[] in1, double[] in2, int dimensions) {
        double dist = 0.0;
        for (int i = 0; i < dimensions; i++) {
            dist += Math.pow(in1[i] - in2[i], 2);
        }

        return Math.sqrt(dist);
    }

    public static double calcChiDistance(double[] vec1, double[] vec2,int dimensions) {
        
        double total=0;
        for(int i=0;i<dimensions;i++){
            total += Math.pow(vec1[i]-vec2[i], 2)/(vec1[i]+vec2[i]);
        }
        return 0.5*total;
    }

    public static double calcIntersectionDist(double[] vec1, double[] vec2, int dimensions) {
        double total = 0;
        for (int i = 0; i < dimensions; i++) {
            total += Math.min(vec1[i], vec2[i]);
        }
        return 1-total;
    }
    
    public static double calcCosineDist(double[] vec1,double[] vec2,int dimensions){
        double total = 0;        
        for(int i=0;i<dimensions;i++){
            total += vec1[i]*vec2[i];
        }
        
        return total/(dimensions*dimensions);
    }
    
    public static double calcGausssianKernelDistance(double[] vec1, double[] vec2,
            int dimensions){

        return GaussianKernelL2.gaussianKernelDistance(vec1, vec2, dimensions);
    }
    
    public static double calcLinearKernelDistance(double[] vec1, double[] vec2,
            int dimensions){

        return LinearKernel.kernelDistance(vec1, vec2, dimensions);
    }
    
    public static double calcMultipleLinearKernelDistance(double[] vec1, double[] vec2, double[] nodeWeights,
            int dimension1, int dimension2, double[] coefs){

        return LinearKernel.LinearKernelDistance(vec1, vec2, nodeWeights, dimension1, dimension2, coefs);
    }
    
    public static double calcMultipleGaussianLinearDistance(double[] vec1, double[] vec2, double[] nodeWeights,
            int dimension1, int dimension2, double[] coefs){
        
        double[] weights1 = Arrays.copyOfRange(nodeWeights, 0, dimension1);
        double[] weights2 = Arrays.copyOfRange(nodeWeights, dimension1, dimension1+dimension2);
        
        double linearComp = coefs[0] * (LinearKernel.calcKernel(vec1, vec1, dimension1) + 
                LinearKernel.calcKernel(weights1, weights1, dimension1) -
                2*LinearKernel.calcKernel(vec1, weights1, dimension1));
        
        double gaussianComp = coefs[1] * (2 - 2*GaussianKernelL2.calcKernel(vec2, weights2, dimension2));       

        return Math.sqrt(linearComp + gaussianComp);
    }
    
    public static double[] vectorSubstraction(double[] vec1, double[] vec2,
            int dimensions){
        double[] result = new double[dimensions];
        for (int i = 0; i<dimensions; i++)
            result[i] = vec1[i] - vec2[i];
        
        return result;
    }
    
    public static double[] normalizeVector(double[] vec){
//        double max1 = Double.MIN_VALUE;
//        double min1 = Double.MAX_VALUE;
//        
//        for(int i=0;i<vec.length;i++){
//            max1 = Math.max(max1, vec[i]);
//            min1 = Math.min(min1, vec[i]);
//        }
//        double denominator = max1 - min1;
//        for(int i=0;i<vec.length;i++){
//            vec[i]=((double)vec[i] - min1)/denominator;           
//        }
        
        double[] zeroVector = new double[vec.length];
        for (int i = 0; i < vec.length; i++){
            zeroVector[i] = 0;
        }
        double denominator = calcEucDist(vec, zeroVector, vec.length);

        for(int i=0;i<vec.length;i++){
            vec[i]= vec[i]/denominator;           
        }
        return vec;
    
    }
    
    public static double[] normalizeVectorMinMax(double[] vec){
        double max1 = Double.MIN_VALUE;
        double min1 = Double.MAX_VALUE;
        
        for(int i=0;i<vec.length;i++){
            max1 = Math.max(max1, vec[i]);
            min1 = Math.min(min1, vec[i]);
        }
        double denominator = max1 - min1;
        for(int i=0;i<vec.length;i++){
            vec[i]=((double)vec[i] - min1)/denominator;           
                
        }
        return vec;
    
    }
    // For GNodes
    public static double calcNodeInterDistance(GNode gNode1, GNode gNode2){
        // Squared distance is returned for performance.
        double distance = Math.pow(gNode1.getX()-gNode2.getX(), 2) + Math.pow(gNode1.getY()-gNode2.getY(), 2);        
        return distance;
    }
    
    // For coordinates
    public static double calcNodeInterDistance(double x1, double y1, double x2, double y2){
        // Squared distance is returned for performance.
        double distance = Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2);        
        return distance;
    }
}
