package TP2_HISTOGRAMS_BOXPLOTS;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYBoxAndWhiskerRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import TP1_DATASETS.Instance;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<Instance> dataset = loadDataset("Dataset-Exos.txt");
        generateScatterplot(1, 3, dataset);
        generateHistogram(2,dataset);
        generateBoxplot(3,dataset);

    }

    static ArrayList<Instance> loadDataset(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        ArrayList<Instance> data = new ArrayList<>();
        while (line != null) {
            if (!line.isEmpty())
                data.add(new Instance(line));
            line = reader.readLine();
        }
        reader.close();
        return data;
    }

    static void generateScatterplot(int attrX, int attrY, ArrayList<Instance> data) {
        if (attrX < 0 || attrX >= 4 || attrY < 0 || attrY >= 4) {
            System.out.println("INVALID INDEX");
            return;
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries xySeries = new XYSeries("");
        for (Instance item : data) {
            xySeries.add(item.attr[attrX], item.attr[attrY]);
        }
        dataset.addSeries(xySeries);
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "DISPERSION DIAGRAM",
                "X",
                "Y",
                dataset, PlotOrientation.VERTICAL,
                true,
                true,
                true
        );
        ChartPanel chartPanel = new ChartPanel(scatterPlot);
        JFrame jFrame = new JFrame();
        jFrame.add(chartPanel);
        jFrame.setVisible(true);
        jFrame.setSize(new Dimension(600, 600));
        XYPlot xyPlot = (XYPlot) scatterPlot.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        xyPlot.setRenderer(renderer);
    }

    static void generateHistogram(int attrIndex, ArrayList<Instance> data) {
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.setType(HistogramType.FREQUENCY);
        double[] values = new double[data.size()];
        int i = 0;
        for (Instance f : data) {
            values[i++] = f.attr[attrIndex];
        }
        histogramDataset.addSeries("", values, 100);
        JFreeChart histogramchart = ChartFactory.createHistogram("HISTOGRAM ",
                "ATTRIBUTE " + attrIndex,
                "FREQUENCY ",
                histogramDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
        );
        ChartPanel chartPanel = new ChartPanel(histogramchart);
        JFrame jFrame = new JFrame();
        jFrame.add(chartPanel);
        jFrame.setVisible(true);
        jFrame.setSize(new Dimension(600, 600));
        XYPlot xyPlot = (XYPlot) histogramchart.getPlot();
        XYBarRenderer renderer = new XYBarRenderer();
        xyPlot.setRenderer(renderer);
    }

    static void generateBoxplot(int attrIndex, ArrayList<Instance> data){
        ArrayList<Float> dataList = new ArrayList<>();
        for(Instance f : data){
            dataList.add(f.attr[attrIndex]);
        }
        DefaultBoxAndWhiskerXYDataset boxWiskerDataset = new DefaultBoxAndWhiskerXYDataset("");
        BoxAndWhiskerItem dataItem = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(dataList);
        boxWiskerDataset.add(new Date(),dataItem);
        JFreeChart boxPlotChart = ChartFactory.createBoxAndWhiskerChart("BOX PLOT",
                "ATTRIBUTE " + attrIndex,
                "VALUES ",
                boxWiskerDataset,
                false
        );
        ChartPanel chartPanel = new ChartPanel(boxPlotChart);
        JFrame jFrame = new JFrame();
        jFrame.add(chartPanel);
        jFrame.setVisible(true);
        jFrame.setSize(new Dimension(600, 600));
        XYPlot xyPlot = (XYPlot) boxPlotChart.getPlot();
        XYBoxAndWhiskerRenderer renderer = new XYBoxAndWhiskerRenderer();
        xyPlot.setRenderer(renderer);
    }
}
