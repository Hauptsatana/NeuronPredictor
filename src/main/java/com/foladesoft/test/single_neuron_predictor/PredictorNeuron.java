/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foladesoft.test.single_neuron_predictor;

import java.math.BigDecimal;

/**
 *
 * @author haupt_000
 */
public class PredictorNeuron {
    
    private double w1 = 1;
    private double w2 = 1;
    private double w3 = 1;
    
    private final double speed;

    public PredictorNeuron(double speed) {
        this.speed = speed;
    }
    
    private double calcS(double x1, double x2, double x3) {
        return x1 * w1 + x2 * w2 + x3 * w3;
    }
    
    private double calcActivator(double s) {
        return 1.0 / (1 + Math.exp(-s)) * 10;
    }
    
    private double calcActivatorDerivative(double s) {
        return 10* Math.exp(-s) / Math.pow(1 + Math.exp(-s), 2);
    }
    
    public double predictNext(double x1, double x2, double x3) {
        double s = calcS(x1, x2, x3);
        return calcActivator(s);
    }
    
    // Error value is returned
    public double learn(double x1, double x2, double x3, double res) {
        
        double errorCur = Double.MAX_VALUE;
        double errorPrev = 0;
        //do {
            errorPrev = errorCur;
            double s = calcS(x1, x2, x3);
            double myres = calcActivator(s);

            errorCur = myres - res;
            double dw1 = errorCur * calcActivatorDerivative(s) * x1;
            double dw2 = errorCur * calcActivatorDerivative(s) * x2;
            double dw3 = errorCur * calcActivatorDerivative(s) * x3;

            w1 -= speed * dw1;
            w2 -= speed * dw2;
            w3 -= speed * dw3;            
        //} while ((int)(errorPrev * 100) != (int)(errorCur * 100));
        
        return errorCur;
    }
    
}
