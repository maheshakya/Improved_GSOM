package com.gsom.core;

import com.gsom.core.cluster.ClusterQualityEvaluator;
import com.gsom.core.cluster.SilhouetteCoeffEval;
import com.gsom.enums.InitType;
import com.gsom.enums.InputDataType;
import com.gsom.kernel.MultipleKernelTrainer;
import com.gsom.listeners.GSOMRunListener;
import com.gsom.listeners.InputParsedListener;
import com.gsom.objects.GCluster;
import com.gsom.objects.GNode;
import com.gsom.util.GSOMConstants;
import com.gsom.util.input.parsing.InputParser;
import com.gsom.util.input.parsing.InputParserFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;

import com.gsom.ui.MainWindow;

public class GSOMRun implements InputParsedListener{

    private InputParserFactory parserFactory;
    private InputParser parser;
    private InputParser parser2;
    private GSOMTrainer trainer;
    private MultipleKernelTrainer mkTrainer;
    private GCoordAdjuster adjuster;
    private GSOMSmoothner smoothner;
    private GSOMTester tester;
    private KMeanClusterer clusterer;
    
    private Map<String, GNode> map;
    private Map<String, String> testResults;
    private ArrayList<ArrayList<GCluster>> allClusters;
    private int bestCCount;
    
    private GSOMRunListener listener;
   
    private InitType initType;
    
    private ClusterQualityEvaluator coeffEval;
    
    // For multiple kernel dimensions
    private int dimension1;
    private int dimension2;
    
    public GSOMRun(InitType initType,GSOMRunListener listener) {
        this.listener = listener;
        this.initType = initType;
        
        parserFactory = new InputParserFactory();
        trainer = new GSOMTrainer(initType);
        mkTrainer = new MultipleKernelTrainer(initType);
        adjuster = new GCoordAdjuster();
        smoothner = new GSOMSmoothner();
        tester = new GSOMTester();
        clusterer = new KMeanClusterer();        
        coeffEval = new SilhouetteCoeffEval();
    }

    public void runTraining(String fileName, InputDataType type) {

        if (type==InputDataType.FLAGS) {
            parser = parserFactory.getInputParser(InputDataType.FLAGS);
        } else if (type==InputDataType.NUMERICAL) {
            parser = parserFactory.getInputParser(InputDataType.NUMERICAL);
        }
        parser.parseInput(this, fileName);
        GSOMConstants.DIMENSIONS = parser.getWeights().get(0).length;
        runAllSteps();
    }
    
    // Function for multiple kernels
    public void runTraining(String fileName, String fileName2, InputDataType type) {

        if (type==InputDataType.FLAGS) {
            parser = parserFactory.getInputParser(InputDataType.FLAGS);
            parser2 = parserFactory.getInputParser(InputDataType.FLAGS);
        } else if (type==InputDataType.NUMERICAL) {
            parser = parserFactory.getInputParser(InputDataType.NUMERICAL);
            parser2 = parserFactory.getInputParser(InputDataType.NUMERICAL);
        }
        parser.parseInput(this, fileName);
        parser2.parseInput(this, fileName2);
        
        if (MainWindow.distance == 5 || MainWindow.distance == 6){
           GSOMConstants.DIMENSIONS = parser.getWeights().get(0).length + parser2.getWeights().get(0).length;
           dimension1 = parser.getWeights().get(0).length;
           dimension2 = parser.getWeights().get(0).length;
        }
        else
            GSOMConstants.DIMENSIONS = parser.getWeights().get(0).length;
        
        runAllSteps();
    }

    private void runAllSteps(){
        
        GSOMConstants.FD = GSOMConstants.SPREAD_FACTOR/GSOMConstants.DIMENSIONS;
        
        if (MainWindow.distance == 5 || MainWindow.distance == 6){
            map = mkTrainer.trainNetwork(parser2.getStrForWeights(), parser.getWeights(), parser2.getWeights());
            listener.stepCompleted("GSOM Training completed!");
            
            double[] coefficients = mkTrainer.getCoefficients();

            map = adjuster.adjustMapCoords(map);
            listener.stepCompleted("Node position Adjustment Complete");

            map = smoothner.smoothGSOM(map, parser.getWeights(), parser2.getWeights(), coefficients);
            listener.stepCompleted("Smoothing phase completed!");
            
            coefficients = smoothner.getCoefficients();

            try{
                FileWriter fw = new FileWriter("GNODE_MAP.txt");

                System.out.println(map.size());
                Iterator<String>  keys = map.keySet().iterator();

                while(keys.hasNext()){
                    String key = keys.next();
                    String weights_str = "";
                    double[] weights = map.get(key).getWeights();
                    for (int i = 0; i < weights.length; i++)
                        weights_str += "," + String.valueOf(weights[i]);

                    System.out.println(key + weights_str);

                    fw.write(key + weights_str + "\n");           
                }
                fw.close();
            }catch(IOException e){
                System.err.println(e);
            }

            tester.testGSOM(map, parser.getWeights(), parser2.getWeights(), coefficients, parser2.getStrForWeights());
            this.testResults = tester.getTestResultMap();

            clusterer.runClustering(map, coefficients, dimension1, dimension1);
            this.allClusters = clusterer.getAllClusters();
            this.bestCCount = clusterer.getBestClusterCount();

            listener.stepCompleted("Clustering completed!");
            listener.stepCompleted("------------------------------------------------");

            listener.executionCompleted();
            
            System.out.println(coefficients[0] + " , " + coefficients[1]);
        }
            
        
        else{
        
            map = trainer.trainNetwork(parser.getStrForWeights(), parser.getWeights());
            listener.stepCompleted("GSOM Training completed!");

            map = adjuster.adjustMapCoords(map);
            listener.stepCompleted("Node position Adjustment Complete");

            map = smoothner.smoothGSOM(map, parser.getWeights());
            listener.stepCompleted("Smoothing phase completed!");

            try{
                FileWriter fw = new FileWriter("GNODE_MAP.txt");

                System.out.println(map.size());
                Iterator<String>  keys = map.keySet().iterator();

                while(keys.hasNext()){
                    String key = keys.next();
                    String weights_str = "";
                    double[] weights = map.get(key).getWeights();
                    for (int i = 0; i < weights.length; i++)
                        weights_str += "," + String.valueOf(weights[i]);

                    System.out.println(key + weights_str);

                    fw.write(key + weights_str + "\n");           
                }
                fw.close();
            }catch(IOException e){
                System.err.println(e);
            }

            tester.testGSOM(map, parser.getWeights(), parser.getStrForWeights());
            this.testResults = tester.getTestResultMap();

            clusterer.runClustering(map);
            this.allClusters = clusterer.getAllClusters();
            this.bestCCount = clusterer.getBestClusterCount();

            listener.stepCompleted("Clustering completed!");
            listener.stepCompleted("------------------------------------------------");

            listener.executionCompleted(); 
            }
        
    }
    

    @Override
    public void inputParseComplete() {
        listener.stepCompleted("Input parsing,normalization completed");  
        

    }

 

    public Map<String, GNode> getGSOMMap() {
        return this.map;
    }

    public Map<String, String> getTestResultMap() {
        return this.testResults;
    }

    public double getSilCoeff(int numCluster){
        //return clusterer.getSilhoutteCoefficient(numCluster);
        coeffEval.evaluate(this.allClusters.get(numCluster-2));
        SilhouetteCoeffEval sCoeffEval = (SilhouetteCoeffEval)coeffEval;
        return sCoeffEval.getSilCoeff();
    }
    
    public ArrayList<ArrayList<GCluster>> getAllClusters() {
        return this.allClusters;
    }

    public int getBestCount(){
        return this.bestCCount;
    }
    
}
