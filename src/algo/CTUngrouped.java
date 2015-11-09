/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CTUngrouped{
    double[] rawdata;
    public double[] sortedData;
    double size;

    public CTUngrouped(double[] rawdata){
        this.rawdata = rawdata;
        sortedData = new double[rawdata.length];
        System.arraycopy(rawdata, 0, sortedData, 0, sortedData.length);
        Arrays.sort(sortedData);
        size = rawdata.length;
    }

    public double getMean(){
        double sum = 0.0;
        for(double a : rawdata)
            sum += a;
        return round(sum/size, 2);
    }

    public double getVariance(){
        double mean = getMean();
        double temp = 0;
        for(double a :rawdata)
            temp += (mean-a)*(mean-a);
        return round(temp/size, 2);
    }

    public double getStdDev(){
        return round(Math.sqrt(getVariance()), 2);
    }

    public double getMedian(){
       double[] b = new double[sortedData.length];
       System.arraycopy(sortedData, 0, b, 0, b.length);

       if (sortedData.length % 2 == 0){
          return round((b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0 , 2);
       } 
       else{
          return round(b[b.length / 2] , 2);
       }
    }
    
    public ArrayList<Double> getMode(){
        ArrayList<Double> modes = new ArrayList<>();
        TreeMap<Double, Integer> map = new TreeMap<>();
        
        int changelog=0;
        double preval=0;
        
        for(int i=0; i<sortedData.length; i++){
            
            if(i==0){
                preval = sortedData[0];
                changelog++;
            }
            else{
                if(preval!=sortedData[i])
                    changelog++;
                preval = sortedData[i];

            }
            map.put(sortedData[i],(map.get(sortedData[i]) == null) ? 1 :
                        map.get(sortedData[i]) + 1);
        }
        
        if(changelog==sortedData.length)
            return null;
    
        else{
            //getmax
            ArrayList<Integer> frequencies = new ArrayList<>(map.values());
            Collections.sort(frequencies);
            int max = frequencies.get(frequencies.size()-1);
            
            if(max>1){
                for(Map.Entry<Double, Integer> entry: map.entrySet()){
                    if(entry.getValue().equals(max))
                        modes.add(entry.getKey());
                }
                //return modes;
            }
            return modes;
        }
    }
    
    
    public double getIQR(){
        int length = sortedData.length;
        int half = length/2;
        
        //split the array into two parts
        double[] firstHalf = new double[half];
        double[] secondHalf = new double[half];
        
        //if even
        if(length%2==0){
            //distribute
            for(int i=0, j=0; i<length ;i++){
                if(i<length/2)
                    firstHalf[i] = sortedData[i];
                
                else{
                    secondHalf[j] = sortedData[i];
                    j++;
                }
            }
        }
        
        //odd
        else if(length%2==1){
            for(int i=0, j=0; i<length ;i++){
                if(i<length/2)
                    firstHalf[i] = sortedData[i];
                
                else{
                    if(i==length/2)
                        i++;
                    secondHalf[j] = sortedData[i];
                    j++;
                }
            }
        }
        
        return (getMedian(secondHalf) - getMedian(firstHalf));
    }
    
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    
    private static double getMedian(double[] arr){
        Arrays.sort(arr);
        double[] b = new double[arr.length];
        System.arraycopy(arr, 0, b, 0, b.length);

        if (arr.length % 2 == 0){
          return round((b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0 , 2);
        } 
        else{
            return round(b[b.length / 2] , 2);
        }
    }

}