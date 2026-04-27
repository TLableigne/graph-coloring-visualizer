/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package info.iut.sae2.viewer;

import info.iut.sae2.graphs.GraphLoader;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.Size;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import static java.lang.Math.min;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class GraphViewer extends JFrame {

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    final int FRAME_WIDTH = 800;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    final int FRAME_HEIGHT = 800;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    final int SLIDER_WIDTH = 100;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    final int BUTTON_HEIGHT = 40;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    IGraph currentGraph;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    IGraph graph;

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    GraphCanvas gc;
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public GraphViewer(String title) {
        super(title);
        graph = null;
        currentGraph = null;
        buildUI();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void buildUI() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel filenameLabel = new JLabel("Graph Filename:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 0, 0);
        contentPane.add(filenameLabel, c);

        String filenames[] = {"Simple sample", "Simple sample 2","Simple sample 3","Simple sample 4", "Planar grid 10x10",  "Planar grid 100x100", "Planar 100", "Planar 500", "Planar 1000", "Planar 2000"};
        JComboBox fileChooser = new JComboBox<>(filenames);
        fileChooser.setSelectedIndex(-1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 3, 0, 0);
        contentPane.add(fileChooser, c);

        JLabel infos = new JLabel(String.format("%d Nodes and %d Edges", 0, 0));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1.;
        c.insets = new Insets(0, 3, 0, 0);
        contentPane.add(infos, c);

        JLabel timeLabel = new JLabel("Computation Time");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 1.;
        c.insets = new Insets(0, 10, 0, 0);
        contentPane.add(timeLabel, c);
        
        JLabel computationTime = new JLabel("---- (ms)");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 0;
        c.weightx = 1.;
        contentPane.add(computationTime, c);
        
        JButton WPButton = new JButton();
        WPButton.setText("Welsh & Powell");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        contentPane.add(WPButton, c);
        
        JButton SCButton = new JButton();
        SCButton.setText("6-Colors Planar");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        contentPane.add(SCButton, c);
        
        JButton FCButton = new JButton();
        FCButton.setText("5-Colors Planar");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        contentPane.add(FCButton, c);
        
        JSlider nodeSize = new JSlider(JSlider.HORIZONTAL, 1, 500, 10);
        nodeSize.setMinimumSize(new Dimension(SLIDER_WIDTH, BUTTON_HEIGHT));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 1;
        contentPane.add(nodeSize, c);

        JLabel edgeLabel = new JLabel("Edge transparency");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 1;
        contentPane.add(edgeLabel, c);

        JSlider edgeTransp = new JSlider(JSlider.HORIZONTAL, 0, 255, 60);
        edgeTransp.setMinimumSize(new Dimension(SLIDER_WIDTH, BUTTON_HEIGHT));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 1;
        contentPane.add(edgeTransp, c);

        JCheckBox smooth = new JCheckBox("Smooth", true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 6;
        c.gridy = 1;
        contentPane.add(smooth, c);

        gc = new GraphCanvas();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1.;
        c.weighty = 1.;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 0, 0);
        contentPane.add(gc, c);

        fileChooser.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) e.getItem();
                long startTime = System.nanoTime();
                loadGraph(item);
                long stopTime = System.nanoTime();
                displayComputationTime(computationTime, startTime, stopTime);
                infos.setText(String.format("%d Nodes and %d Edges", currentGraph.numberOfNodes(), currentGraph.numberOfEdges()));
            }
        });

       
        smooth.addItemListener((ItemEvent e) -> {
            gc.setSmooth(e.getStateChange() == ItemEvent.SELECTED);
            gc.repaint();
        });

        edgeTransp.addChangeListener((ChangeEvent e) -> {
            gc.setEdgeTransparency(edgeTransp.getValue());
            gc.repaint();
        });

        nodeSize.addChangeListener((ChangeEvent e) -> {
            double diameter = nodeSize.getValue() / 10.;
            graph.setAllNodesSizes(new Size(diameter, diameter));
            gc.setRadius(nodeSize.getValue());
            gc.repaint();
        });

        WPButton.addActionListener((ActionEvent e) -> {
            long startTime = System.nanoTime();
            welshAndPowell();
            long stopTime = System.nanoTime();
            displayComputationTime(computationTime, startTime, stopTime);
        });
        
        SCButton.addActionListener((ActionEvent e) -> {
            long startTime = System.nanoTime();
            sixColors();
            long stopTime = System.nanoTime();
            displayComputationTime(computationTime, startTime, stopTime);
        });

        FCButton.addActionListener((ActionEvent e) -> {
            long startTime = System.nanoTime();
            fiveColors();
            long stopTime = System.nanoTime();
            displayComputationTime(computationTime, startTime, stopTime);
        });
        
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateGraphView();
            }
        });
    }

    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void displayComputationTime(JLabel computationTime, long startTime, long stopTime){
        String time = String.format("%f", (stopTime-startTime)/1000000.);
        StringBuilder timeBuilder = new StringBuilder(time.substring(0, min(6, time.length())));
        timeBuilder.append(" (ms)");
        computationTime.setText(timeBuilder.toString());
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public void welshAndPowell(){
        if (graph == null) {
            return;
        }
        graph.welshAndPowell();
        currentGraph = graph;
        updateGraphView();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public void sixColors(){
        if (graph == null) {
            return;
        }
        graph.sixColors();
        currentGraph = graph;
        updateGraphView();
    }
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public void fiveColors(){
        if (graph == null) {
            return;
        }
        graph.fiveColors();
        currentGraph = graph;
        updateGraphView();
    }
    
    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public void clearGraph() {
        if (graph == null) {
            return;
        }
        currentGraph = graph;
        currentGraph.setAllEdgesPositions(new ArrayList<>());
        updateGraphView();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    public void loadGraph(String filename) {
        //spanningTree = null;
        switch (filename) {
            case "Simple sample" ->
                graph = GraphLoader.loadFromFile("dataset/simple_nodes.csv", "dataset/simple_edges.csv" );
            case "Simple sample 2" ->
                graph = GraphLoader.loadFromFile("dataset/testCJ_nodes.csv", "dataset/testCJ_edges.csv" );
            case "Simple sample 3" ->
                graph = GraphLoader.loadFromFile("dataset/testEB_nodes.csv", "dataset/testEB_edges.csv" );
            case "Simple sample 4" ->
                graph = GraphLoader.loadFromFile("dataset/testEBB_nodes.csv", "dataset/testEBB_edges.csv" );
            case "Planar grid 10x10" ->
                graph = GraphLoader.loadFromFile("dataset/planar_grid_10_nodes.csv", "dataset/planar_grid_10_edges.csv");
            case "Planar grid 100x100" ->
                graph = GraphLoader.loadFromFile("dataset/planar_grid_100_nodes.csv", "dataset/planar_grid_100_edges.csv");
            case "Planar 100" ->
                graph = GraphLoader.loadFromFile("dataset/planar_100_nodes.csv", "dataset/planar_100_edges.csv");
            case "Planar 500" ->
                graph = GraphLoader.loadFromFile("dataset/planar_500_nodes.csv", "dataset/planar_500_edges.csv");
            case "Planar 1000" ->
                graph = GraphLoader.loadFromFile("dataset/planar_1000_nodes.csv", "dataset/planar_1000_edges.csv");
            case "Planar 2000" ->
                graph = GraphLoader.loadFromFile("dataset/planar_2000_nodes.csv", "dataset/planar_2000_edges.csv");
            default ->
                graph = GraphLoader.loadFromFile("dataset/planar_2000_nodes.csv", "dataset/planar_2000_edges.csv");
        }
        currentGraph = graph;

        updateGraphView();
    }

    /**
     * No javadoc is provided, there is no need to understand/modify this part
     * of the code
     */
    private void updateGraphView() {
        gc.setGraph(currentGraph);
        gc.repaint();
    }
}
