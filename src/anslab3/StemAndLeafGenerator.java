/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anslab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

/**
 *
 * @author El Gato
 */
public class StemAndLeafGenerator {

    private static double leafUnit;
    private static double stemUnit;
    private static int hStem1;
    private static int hStem2;
    private static int set1Size;
    private static int set2Size;
    private static String label1;
    private static String label2;
    private static ArrayList<String> theOutPut;
    private outPut OutPutView;

    public double getLeafUnit() {
        return leafUnit;
    }

    public void setLeafUnit(double aLeafUnit) {
        leafUnit = aLeafUnit;
    }

    public double getStemUnit() {
        return stemUnit;
    }

    public void setStemUnit(double aStemUnit) {
        stemUnit = aStemUnit;
    }

    public  int gethStem1() {
        return hStem1;
    }

    public void sethStem1(int ahStem1) {
        hStem1 = ahStem1;
    }

    public int gethStem2() {
        return hStem2;
    }

    public void sethStem2(int ahStem2) {
        hStem2 = ahStem2;
    }

    public int getSet1Size() {
        return set1Size;
    }

    public void setSet1Size(int aSet1Size) {
        set1Size = aSet1Size;
    }

    public int getSet2Size() {
        return set2Size;
    }

    public void setSet2Size(int aSet2Size) {
        set2Size = aSet2Size;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String aLabel1) {
        label1 = aLabel1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String aLabel2) {
        label2 = aLabel2;
    }

    public ArrayList<String> getTheOutPut() {
        return theOutPut;
    }

    public void setTheOutPut(ArrayList<String> aTheOutPut) {
        theOutPut = aTheOutPut;
    }

    public outPut getOutPutView() {
        return OutPutView;
    }

    public void setOutPutView(outPut aOutPutView) {
        OutPutView = aOutPutView;
    }

    public StemAndLeafGenerator() {
        theOutPut = new ArrayList<String>();
    }

    public Map<Integer, List<Integer>> createPlot(List<Double> data) {

        Map<Integer, List<Integer>> plot = new TreeMap<Integer, List<Integer>>();
        int highestStem = -1; //for filling in stems with no leaves

        for (double datum : data) {
            int leaf = generateLeaf(datum);
            int stem = generateStem(datum); //integer division
            if (stem > highestStem) {
                highestStem = stem;
            }
            if (plot.containsKey(stem)) {
                plot.get(stem).add(leaf);
            } else {
                LinkedList<Integer> list = new LinkedList<Integer>();
                list.add(leaf);
                plot.put(stem, list);
            }
        }

        if (plot.keySet().size() < highestStem + 1 /*highest stem value and 0*/) {
            for (int i = 0; i <= highestStem; i++) {
                if (!plot.containsKey(i)) {
                    LinkedList<Integer> list = new LinkedList<Integer>();
                    plot.put(i, list);
                }
            }
        }
        return plot;
    }

    public int getHigestStem(List<Double> data) {
        Map<Integer, List<Integer>> plot = new TreeMap<Integer, List<Integer>>();
        int highestStem = -1; //for filling in stems with no leaves
        for (double datum : data) {
            int leaf = generateLeaf(datum);
            int stem = generateStem(datum); //integer division
            if (stem > highestStem) {
                highestStem = stem;
            }
            if (plot.containsKey(stem)) {
                plot.get(stem).add(leaf);
            } else {
                LinkedList<Integer> list = new LinkedList<Integer>();
                list.add(leaf);
                plot.put(stem, list);
            }
        }
        return highestStem;
    }

    public void printSinglePlot(Map<Integer, List<Integer>> plot) {
        
        JOptionPane.showMessageDialog(null,
                "Stem and Leaf Plot (Single)" + "\n" +
                "Data set label: " + label1 + "\n" +
                "size: " + set1Size + "\n" +
                "Leaf Unit: " + leafUnit + "\n" +
                "Stem Unit: " + stemUnit + "\n"); 
        
        for (Map.Entry<Integer, List<Integer>> line : plot.entrySet()) {
            Collections.sort(line.getValue());
            
            String s = line.getValue().toString();
            s = s.substring(1, s.length()-1);
            s = s.replaceAll(",", " ");
            
            if (line.getKey() < 10) {
                System.out.println("\t" + " " + line.getKey() + " | " + s);
                theOutPut.add("\t" + " " + line.getKey() + " | " + s);
            } else {
                System.out.println("\t" + line.getKey() + " | " + s);
                theOutPut.add("\t" + line.getKey() + " | " + s);
            }
        }
        OutPutView = new outPut(theOutPut);
        OutPutView.setVisible(true);
    }

    public void printParallelPlot(Map<Integer, List<Integer>> plot1,
            Map<Integer, List<Integer>> plot2) {

        JOptionPane.showMessageDialog(null,
                "Stem and Leaf Plot (Parallel)" + "\n" +
                "Data set label (left): " + label2 + "\n" +
                "size: " + set2Size + "\n" +
                "Data set label (right): " + label1 + "\n" +
                "size: " + set1Size + "\n" + "\n" +
                "Leaf Unit: " + leafUnit + "\n" +
                "Stem Unit: " + stemUnit );
        
        Map<Integer, String> set1 = new TreeMap<Integer, String>();
        Map<Integer, String> set2 = new TreeMap<Integer, String>();

        int maxSize = getMaxSize(plot1);

        if (plot1.size() > plot2.size()) {
            for (Map.Entry<Integer, List<Integer>> line : plot1.entrySet()) {
                Collections.sort(line.getValue());
                
                List lines = new LinkedList(line.getValue());
                while (lines.size() < maxSize) {
                    lines.add(0, " ");
                }
                String sb = lines.toString();
                String dataset1 = sb.replaceAll(",", " ");
                String ds1 = dataset1.substring(1, dataset1.length() - 1);
                set1.put(line.getKey(), ds1);
            }

            for (Map.Entry<Integer, List<Integer>> line : plot1.entrySet()) {
                set2.putIfAbsent(line.getKey(), "");
            }

            for (Map.Entry<Integer, List<Integer>> line : plot2.entrySet()) {
                Collections.sort(line.getValue());

                String sb2 = line.getValue().toString();
                String dataset2 = sb2.replaceAll(",", " ");
                String ds2 = dataset2.substring(1, dataset2.length() - 1);

                set2.put(line.getKey(), ds2);
            }

        } else {
            for (Map.Entry<Integer, List<Integer>> line : plot1.entrySet()) {
                //Collections.sort(line.getValue());
                Collections.reverse(line.getValue());
                List lines = new LinkedList(line.getValue());
                while (lines.size() < maxSize) {
                    lines.add(0, " ");
                }
                String sb = lines.toString();
                String dataset1 = sb.replaceAll(",", " ");
                String ds1 = dataset1.substring(1, dataset1.length() - 1);
                set1.put(line.getKey(), ds1);
            }
            String l = "";
            while (l.length() < maxSize * 3 - 2) {
                l = l.concat(" ");
            }

            for (Map.Entry<Integer, List<Integer>> line : plot2.entrySet()) {
                set1.putIfAbsent(line.getKey(), l);
            }

            for (Map.Entry<Integer, List<Integer>> line : plot2.entrySet()) {
                Collections.sort(line.getValue());
                

                String sb2 = line.getValue().toString();
                String dataset2 = sb2.replaceAll(",", " ");
                String ds2 = dataset2.substring(1, dataset2.length() - 1);

                set2.put(line.getKey(), ds2);
            }

        }

        for (Map.Entry<Integer, String> line : set1.entrySet()) {
            if (line.getKey() < 10) {
                theOutPut.add("\t" + line.getValue() + " |  " + line.getKey() + " | " + set2.get(line.getKey()));
            } else {
                theOutPut.add("\t" + line.getValue() + " |  " + line.getKey() + " | " + set2.get(line.getKey()));
            }
        }
        OutPutView = new outPut(theOutPut);
        OutPutView.setVisible(true);

    }

    public int generateLeaf(double datum) {
        if ((1.0 - leafUnit) > 0) {
            Double unit = leafUnit;
            int power = 1;

            while (unit != 1) {
                power *= 10;
                unit *= 10;
            }
            return (int) (datum * power) % 10;
        } else {
            int unit = (int) leafUnit;
            //return (int) datum % (unit * 10) / unit;
            return (int) datum% ((int)stemUnit)/unit;
        }
    }

    public int generateStem(double datum) {
        if ((1.0 - stemUnit) > 0) {
            Double unit = stemUnit;
            int power = 1;

            while (unit != 1) {
                power *= 10;
                unit *= 10;
            }
            return (int) (datum * power) % 10;
        } else {
            return (int) (datum / stemUnit);
        }

    }

    //returns the size of the largest array among all list values
    public int getMaxSize(Map<Integer, List<Integer>> plot) {
        int maxSize = -1;
        for (Map.Entry<Integer, List<Integer>> line : plot.entrySet()) {
            if (maxSize < line.getValue().size()) {
                maxSize = line.getValue().size();
            }
        }
        return maxSize;
    }

    public LinkedList<Double> convertToArray(String n){
        List<String> list = Arrays.asList(n.split("\\s*,\\s*"));
        LinkedList<Double> l = new LinkedList<Double>();
        
        for(String ll:list){
            try{
                l.add(Double.parseDouble(ll));
                
            }
            catch(NumberFormatException nfe){
                System.out.println(nfe);
                return null;
            }
        }
        return l;
    }
    
    
}
