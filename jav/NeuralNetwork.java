package jav;
  import java.util.Random;
                                                                                
  public class NeuralNetwork {
                                                                                
      private double[][][] weights;                                             
      private double[][] biases;
      private double[][] activations;                                         
      private int[] layerSizes;

      public NeuralNetwork(int[] layerSizes) {                                  
          this.layerSizes = layerSizes;
          Random rand = new Random();                                           
          weights = new double[layerSizes.length - 1][][];
          biases  = new double[layerSizes.length - 1][];
                                                                                
          for (int i = 0; i < weights.length; i++) {
              weights[i] = new double[layerSizes[i]][layerSizes[i + 1]];        
              biases[i]  = new double[layerSizes[i + 1]];                       
              for (double[] row : weights[i])
                  for (int j = 0; j < row.length; j++)                          
                      row[j] = rand.nextGaussian() * 0.1;
          }                                                                     
      }           
                                                                                
      private double sigmoid(double x) {
          return 1.0 / (1.0 + Math.exp(-x));                                    
      }           

      private double sigmoidDerivative(double x) {
          return x * (1 - x);
      }                                                                         
   
      private double[] forward(double[] input) {                                
          double[] current = input;
          activations = new double[layerSizes.length][];
          activations[0] = input;                                               
   
          for (int layer = 0; layer < weights.length; layer++) {                
              double[] next = new double[layerSizes[layer + 1]];
              for (int j = 0; j < next.length; j++) {                           
                  double sum = biases[layer][j];
                  for (int i = 0; i < current.length; i++)                      
                      sum += current[i] * weights[layer][i][j];                 
                  next[j] = sigmoid(sum);
              }                                                                 
              activations[layer + 1] = next;
              current = next;
          }                                                                     
          return current;
      }                                                                         
                  
      private void backward(double[] input, double[] expected, double lr) {
          double[] delta = new double[expected.length];
          double[] output = activations[activations.length - 1];                
  
          for (int j = 0; j < delta.length; j++)                                
              delta[j] = output[j] - expected[j];  // how wrong we were
                                                                                
          for (int layer = weights.length - 1; layer >= 0; layer--) {           
              double[] prevActivation = activations[layer];                     
              double[] newDelta = new double[layerSizes[layer]];                
                                                                                
              for (int i = 0; i < weights[layer].length; i++) {
                  for (int j = 0; j < weights[layer][i].length; j++) {          
                      weights[layer][i][j] -= lr * prevActivation[i] * delta[j];
                      newDelta[i] += delta[j] * weights[layer][i][j];           
                  }
              }                                                                 
              for (int j = 0; j < biases[layer].length; j++)
                  biases[layer][j] -= lr * delta[j];                            
                  
              if (layer > 0) {                                                  
                  double[] act = activations[layer];
                  for (int i = 0; i < newDelta.length; i++)                     
                      newDelta[i] *= sigmoidDerivative(act[i]);
              }                                                                 
              delta = newDelta;
          }                                                                     
      }           

      public void train(double[][] X, double[][] y, int epochs, double lr) {    
          for (int epoch = 0; epoch < epochs; epoch++) {
              double totalLoss = 0;                                             
              for (int i = 0; i < X.length; i++) {
                  double[] output = forward(X[i]);                              
                  backward(X[i], y[i], lr);
                  double diff = output[0] - y[i][0];                            
                  totalLoss += diff * diff;                                     
              }
              if (epoch % 500 == 0)                                             
                  System.out.printf("Epoch %4d | Loss: %.4f%n", epoch, totalLoss
   / X.length);                                                                 
          }
      }                                                                         
                  
      public int predict(double[] input) {
          return forward(input)[0] > 0.5 ? 1 : 0;
      }                                                                         
  
      public static void main(String[] args) {                                  
          // XOR problem — no straight line can solve this
          double[][] X = {{0,0}, {0,1}, {1,0}, {1,1}};                          
          double[][] y = {{0},   {1},   {1},   {0}};
                                                                                
          NeuralNetwork nn = new NeuralNetwork(new int[]{2, 4, 1});             
          nn.train(X, y, 5000, 0.1);
                                                                                
          System.out.println("\nPredictions:");
          for (double[] input : X)
              System.out.printf("  [%.0f, %.0f] -> %d%n", input[0], input[1],   
  nn.predict(input));                                                           
      }                                                                         
  }