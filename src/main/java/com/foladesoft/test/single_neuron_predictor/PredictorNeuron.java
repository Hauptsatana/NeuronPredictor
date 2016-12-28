/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foladesoft.test.single_neuron_predictor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author haupt_000
 */
public class PredictorNeuron {

	private double w1 = 1;
	private double w2 = 1;
	private double w3 = 1;

	private double speed;

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
		return Math.exp(-s) / Math.pow(1 + Math.exp(-s), 2);
	}

	public double predictNext(double x1, double x2, double x3) {
		double s = calcS(x1, x2, x3);
		return calcActivator(s);
	}

	// Error value is returned
	public List<Double> learn(List<Double> seriesData) {

		double errorCur = Double.MAX_VALUE;
		double errorPrev = 0;
		
		List<Double> res = new LinkedList<>();

		int iterCount = 0;
		do {			
			errorPrev = errorCur;
			errorCur = 0;
			
			double dw1 = 0;
			double dw2 = 0;
			double dw3 = 0;
			for (int i = 0; i < seriesData.size() - 3; i++) {

				double x1 = seriesData.get(i);
				double x2 = seriesData.get(i + 1);
				double x3 = seriesData.get(i + 2);
				double y = seriesData.get(i + 3);

				double s_i = calcS(x1, x2, x3);
				double e_i = calcActivator(s_i) - y;
				errorCur += e_i * e_i;

				dw1 += e_i * calcActivatorDerivative(s_i) * x1;
				dw2 += e_i * calcActivatorDerivative(s_i) * x2;
				dw3 += e_i * calcActivatorDerivative(s_i) * x3;

			}
			
			dw1 /= seriesData.size() - 3;
			dw2 /= seriesData.size() - 3;
			dw3 /= seriesData.size() - 3;
			
			w1 -= speed * dw1;
			w2 -= speed * dw2;
			w3 -= speed * dw3;
			
			iterCount++;
			res.add(errorCur);
			
			if (iterCount == 100) {
				speed = 0.1;
			}
			
		} while (Math.abs(errorPrev - errorCur) > 1e-3 && iterCount < 1e5);

		return res;
	}

}
