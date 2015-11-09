/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import static algo.CTUngrouped.round;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author El Gato
 */
public class CTGrouped {
    private int number_of_classes;
    private ArrayList<Double[]> classInterval;
    private ArrayList<Double> f;
    private ArrayList<Double> x;
    private ArrayList<Double> F;
    private ArrayList<Double> classWidth;
    private DefaultTableModel tableModel;
    //B is boundary
    private ArrayList<Double> LB;
    
    private double n;
    private double Efx;
    private double Efxx;
    
    
    public CTGrouped(
            int _number_of_classes,
            ArrayList<Double[]> _classInterval, 
            ArrayList<Double> _frequencies,
            ArrayList<Double> _classMarks,
            boolean _openFirst,
            boolean _openLast,
            DefaultTableModel _tableModel)
    {
        this.number_of_classes = _number_of_classes;
        this.classInterval = _classInterval;
        this.f = _frequencies;
        this.x = _classMarks;
        
        this.F = new ArrayList<Double>();
        this.LB = new ArrayList<Double>();
        this.classWidth = new ArrayList<Double>();
        this.tableModel = _tableModel;
        
        //generate population size <total frequency>
        for(int i=0;i<f.size(); i++){
            if(f.get(i) != null){
                n += f.get(i);
            }
        }
        
        //generate Efx
        for(int i=0 ; i < number_of_classes ; i++){
            if(f.get(i)!=null && x.get(i)!=null)
                Efx += f.get(i)*x.get(i);
        }
        
        //generate Efx²
        for(int i=0 ; i< number_of_classes ; i++){
            if(f.get(i)!=null && x.get(i)!=null)
                Efxx += f.get(i)*x.get(i)*x.get(i);
        }
        
        //cumulative frequency
        for(int i=0; i< number_of_classes ; i++){
            if(i==0 && f.get(i)!=null){
                F.add(f.get(i));
            }
            else{
                if(f.get(i)!=null){
                    F.add(F.get(i-1) + f.get(i));
                }
            }
        }
        
        //lower boundary of the classes
        for(int i = 0; i < number_of_classes ; i++){
            if(classInterval.get(i)[0]!=null){
                Double num = classInterval.get(i)[0];
                LB.add(num - 0.5);
            }
        }
        
        //class widths
        for(int i = 0 ; i < number_of_classes; i++){
            if(classInterval.get(i)[1]!=null && classInterval.get(i)[0]!=null){
                double ii = (classInterval.get(i)[1] - classInterval.get(i)[0]) + 1;
                classWidth.add(ii);
            }
            else
                classWidth.add(0.00);
        }
    }
    
    public double get_n(){
        return n;
    }
    
    public double get_Efx() {
        return Efx;
    }

    public double get_Efxx() {
        return Efxx;
    }
    
    //---------------------------CENTRAL TENDENCIES-----------------------------
    
    public double getMean(){
        return round(Efx/n,2);
    }
    
    public double getMedian(int _number_of_classes){
 
        int mid = _number_of_classes/2;

        //if even
        if(_number_of_classes%2 == 0){
            Double _median1 = LB.get(mid-1) + ((n/2) - F.get(mid-2) ) / f.get(mid) * classWidth.get(mid);
            Double _median2 = LB.get(mid+1) + ((n/2) - F.get(mid) ) / f.get(mid+1) * classWidth.get(mid+1);
            return round((_median1+_median2)/2,2);
        }
        else{
            Double _median = LB.get(mid) + (((n/2) - F.get(mid-1) ) / f.get(mid)) * classWidth.get(mid);
            return round(_median,2);
        }
    }
    
    public double getIQR() {
        int mid = number_of_classes/2;
        Double ClassQ1 = n/4;
        Double ClassQ3 = 3*n/4;
        int ClassQ1_index =-1;
        int ClassQ3_index =-1;
        for(int i = 0 ; i< number_of_classes ; i++){
            Double[] interval = classInterval.get(i);
            if(interval[0] > ClassQ1 && ClassQ1 < interval[1]){
                ClassQ1_index = i;
                break;
            }
        }
        
        for(int i = 0 ; i< number_of_classes ; i++){
           //tableModel.getValueAt(i, 0);
            Double[] interval = classInterval.get(i);
            
            if(interval[0] > ClassQ3 && ClassQ3 < interval[1]){
                ClassQ3_index = i-1;
                break;
            }
        }
        
        Double F1 = 0.00;
        Double Q1 = 0.00;
        System.out.println("class q1 index" + ClassQ1_index);
        
        if(ClassQ1_index > 0 ){
            System.out.println("yes if ClassQ1_index > 0");
            F1 = F.get(ClassQ1_index-1);
            
            Q1 = LB.get(ClassQ1_index) + 
                (ClassQ1 - F1)/f.get(ClassQ1_index) * 
                classWidth.get(ClassQ1_index);
            
        }
        else{
            Q1 = LB.get(ClassQ1_index) + 
                (ClassQ1 - F1)/f.get(ClassQ1_index) * 
                classWidth.get(ClassQ1_index);
        }
        
        
        
        
//        System.out.println("Class Q1 index " + ClassQ1_index);
//        System.out.println("Lq1 " + LB.get(ClassQ1_index));
//        System.out.println("n/4 : " + ClassQ1);
//        System.out.println("F : " + F.get(ClassQ1_index-1));
//        System.out.println("f: " + f.get(ClassQ1_index));
//        System.out.println("i: " + classWidth.get(ClassQ1_index));
//        
//        System.out.println("");
//        
//        System.out.println("Class Q3 index " + ClassQ3_index);
//        System.out.println("Lq3 " + LB.get(ClassQ3_index));
//        System.out.println("n/4 : " + ClassQ3);
//        System.out.println("F : " + F.get(ClassQ3_index-1));
//        System.out.println("f: " + f.get(ClassQ3_index));
//        System.out.println("i: " + classWidth.get(ClassQ3_index));
//        

        
        Double Q3 = LB.get(ClassQ3_index) + (ClassQ3 - F.get(ClassQ3_index-1))/f.get(ClassQ3_index) * classWidth.get(ClassQ3_index);
        
        
      
        System.out.println("Q1: " + Q1);
        System.out.println("Q3: " + Q3);

        
        return round(Q3 - Q1, 2);
    }
    
    public double getVariance(){
        return round((Efxx - ((Efx*Efx)/n))/n,2);
    }
    
    public double getStdDev(){
        return round(Math.sqrt(getVariance()),2);
    }

    
    public String getModalClass(){
        double max_value  = -1;
        int max_index = -1;
        for(int i = 0 ; i < number_of_classes ; i++ ){
            if(max_value < f.get(i)){
                max_value = f.get(i);
                max_index = i;
            }
            
        }
        return tableModel.getValueAt(max_index, 0).toString();
    }
    

        
    
}
