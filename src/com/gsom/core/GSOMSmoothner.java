package com.gsom.core;

import java.util.ArrayList;
import java.util.Map;

import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.Utils;

public class GSOMSmoothner {

    public GSOMSmoothner() {
        
        //GSOMConstants.MAX_NEIGHBORHOOD_RADIUS = GSOMConstants.MAX_NEIGHBORHOOD_RADIUS/2;
    }

    public Map<String, GNode> smoothGSOM(Map<String, GNode> map, ArrayList<double[]> inputs) {
        GSOMConstants.START_LEARNING_RATE = GSOMConstants.START_LEARNING_RATE / 2;
        GSOMConstants.MAX_NEIGHBORHOOD_RADIUS = GSOMConstants.MAX_NEIGHBORHOOD_RADIUS / 2;
        for (int iter = 0; iter < GSOMConstants.MAX_ITERATIONS; iter++) {
            double learningRate = Utils.getLearningRate(iter, map.size());
            double radius = Utils.getRadius(iter, Utils.getTimeConst());
            for (double[] singleInput : inputs) {
                if (singleInput.length == GSOMConstants.DIMENSIONS) {
                    smoothSingleIterSingleInput(map, iter, singleInput, learningRate, radius);
                } else {
                    //error
                }
            }
        }
        return map;
    }
    
    // Function for Multiple kernels
    public Map<String, GNode> smoothGSOM(Map<String, GNode> map, ArrayList<double[]> inputs1, ArrayList<double[]> inputs2, double[] coefs) {
        GSOMConstants.START_LEARNING_RATE = GSOMConstants.START_LEARNING_RATE / 2;
        GSOMConstants.MAX_NEIGHBORHOOD_RADIUS = GSOMConstants.MAX_NEIGHBORHOOD_RADIUS / 2;
        int inputLength = inputs1.size();
        for (int iter = 0; iter < GSOMConstants.MAX_ITERATIONS; iter++) {
            double learningRate = Utils.getLearningRate(iter, map.size());
            double radius = Utils.getRadius(iter, Utils.getTimeConst());
            for(int i=0; i< inputLength; i++){
                smoothSingleIterSingleInput(map, iter, inputs1.get(i), inputs2.get(i), coefs, learningRate, radius);
            }
        }
        return map;
    }
    
    private void smoothSingleIterSingleInput(Map<String, GNode> map, int iter, double[] input, double learningRate, double radius) {
        GNode winner = Utils.selectWinner(map, input);

        String leftNode = Utils.generateIndexString(winner.getX() - 1, winner.getY());
        String rightNode = Utils.generateIndexString(winner.getX() + 1, winner.getY());
        String topNode = Utils.generateIndexString(winner.getX(), winner.getY() + 1);
        String bottomNode = Utils.generateIndexString(winner.getX(), winner.getY() - 1);

        if (map.containsKey(leftNode)) {
            map.put(leftNode, Utils.adjustNeighbourWeight(map.get(leftNode), winner, input, radius, learningRate));
        } else if (map.containsKey(rightNode)) {
            map.put(rightNode, Utils.adjustNeighbourWeight(map.get(rightNode), winner, input, radius, learningRate));
        } else if (map.containsKey(topNode)) {
            map.put(topNode, Utils.adjustNeighbourWeight(map.get(topNode), winner, input, radius, learningRate));
        } else if (map.containsKey(bottomNode)) {
            map.put(bottomNode, Utils.adjustNeighbourWeight(map.get(bottomNode), winner, input, radius, learningRate));
        }
    }
    
    // Function for Multiple kernels
    private void smoothSingleIterSingleInput(Map<String, GNode> map, int iter, double[] input1, double[] input2, double[] coefs, double learningRate, double radius) {
        GNode winner = Utils.selectWinner(map, input1, input2, coefs);

        String leftNode = Utils.generateIndexString(winner.getX() - 1, winner.getY());
        String rightNode = Utils.generateIndexString(winner.getX() + 1, winner.getY());
        String topNode = Utils.generateIndexString(winner.getX(), winner.getY() + 1);
        String bottomNode = Utils.generateIndexString(winner.getX(), winner.getY() - 1);

        if (map.containsKey(leftNode)) {
            map.put(leftNode, Utils.adjustNeighbourWeight(map.get(leftNode), winner, input1, input2, radius, learningRate));
        } else if (map.containsKey(rightNode)) {
            map.put(rightNode, Utils.adjustNeighbourWeight(map.get(rightNode), winner, input1, input2, radius, learningRate));
        } else if (map.containsKey(topNode)) {
            map.put(topNode, Utils.adjustNeighbourWeight(map.get(topNode), winner, input1, input2, radius, learningRate));
        } else if (map.containsKey(bottomNode)) {
            map.put(bottomNode, Utils.adjustNeighbourWeight(map.get(bottomNode), winner, input1, input2, radius, learningRate));
        }
    }
}
