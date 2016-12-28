package com.foladesoft.test.single_neuron_predictor;

import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

public class LineChart {

    XYDataset dataset;
    JFreeChart chart;

    int dimX;
	int dimY;
    String title;
    int thickness;
    Color color;
    String xAxisLabel;
	String yAxisLabel;

    // <editor-fold desc="Constructors" defaultstate="collapsed">
    public LineChart(Double[][] data, String title) {
        this(data, 0, 1, title, "X", "Y", 2, Color.RED);
    }
	
    public LineChart(Double[][] data, int dimX, int dimY,
                     String title, String xAxisLabel, String yAxisLabel,
                     int thickness, Color color) 
    {
        this.dimX = dimX;
        this.dimY = dimY;
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.thickness = thickness;
        this.color = color;
        
        dataset = createDataset(data);
        chart = createChart();
    }

    // </editor-fold>
    //    
    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private XYDataset createDataset(Double[][] arr) {

        final XYSeries series = new XYSeries("Line");
        for (Double[] point : arr) {
            series.add(point[dimX], point[dimY]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;

    }

    /**
     * Creates a chart.
     *
     * @param dataset the data for the chart.
     *
     * @return a chart.
     */
    private JFreeChart createChart() {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesPaint(0, color);
        renderer.setSeriesStroke(0, new BasicStroke(thickness));
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }

    public void draw(javax.swing.JPanel panel) {
        ChartPanel cpnl = new ChartPanel(chart);
        panel.add(cpnl);
        panel.getParent().revalidate();
    }
}
