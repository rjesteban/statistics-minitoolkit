/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anslab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author El Gato
 */
public class Test {
    
    //private DefaultTableModel model;
    private static ArrayList<String> labels;
    private static ArrayList<Double> labelsD;
    private ArrayList<Integer> frequency;
    private ArrayList<Double> relFrequency;
    //private HashMap<String, Integer> map;
    private static double n;
    
    
    public Test(){}
    
    
    public static boolean typesMatch(String _input, int dataType){
        
        if(dataType == 1){
            //parse sa Alphabetic
            Pattern p = Pattern.compile("^[a-zA-Z]*$");
            Matcher m = p.matcher(_input);
            if(!m.matches()){
                JOptionPane.showMessageDialog(null, "Wrong Format! you chose Alphabet");
                return false;
            }
        }
        
        else if(dataType == 2){
            //parse sa Numeric
            try{
                Double.parseDouble(_input);
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, "Wrong Format! you chose numeric");
                return false;
            }
        }
        else if(dataType == 3){
            //regex sa String
        }
        return true;
    }
    
    public static ArrayList sortData(ArrayList toSort,int dataType){
        n = toSort.size();
        
        if(dataType == 1){
            ArrayList<String> alphabetic = new ArrayList<String>();
            
            for(Object data: toSort){;
                alphabetic.add(String.valueOf(data));
            }
            Collections.sort(alphabetic);
            labels = alphabetic;
            return alphabetic;
        }
        else if (dataType == 2 ){
            ArrayList<Double> numeric = new ArrayList<Double>();
            
            for(Object data: toSort){
                numeric.add(Double.valueOf(String.valueOf(data)));
            }
            Collections.sort(numeric);
            labelsD = numeric;
            return numeric;
        }
        
        else if (dataType == 3){
            ArrayList<String> string = new ArrayList<String>();
            
            for(Object data: toSort){
                string.add(String.valueOf(data));
            }
            Collections.sort(string);
            labels = string;
            return string;
        }
        return null;
    }
    
   
}
