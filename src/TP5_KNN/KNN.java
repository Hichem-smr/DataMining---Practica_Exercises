package TP5_KNN;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Iterator;

import Tp6_Kmeans.instance;


import java.lang.Math;

public class KNN {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<instance> data = loadDataset("Dataset-Exos.txt");
		
		String k_class = getDominantClass(data, 3);
		System.out.println(k_class);
		
		instance element = new instance("5.2, 3.5, 1.41, 0.25, ??") ;
		sortByDistance(data, element) ;
		
	}
	
    static ArrayList<instance> loadDataset(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        ArrayList<instance> data = new ArrayList<>();
        while (line != null) {
            if (!line.isEmpty())
                data.add(new instance(line));
            line = reader.readLine();
        }
        reader.close();
        return data;
    }
    
    public static double euclideanDist(instance point1, instance point2) {
    	
    	float[] coords1 = point1.attr ;
    	float[] coords2 = point2.attr ;
    	
    	double somme = 0 ;
    	for (int i = 0 ; i < coords1.length ; i++) {
    		somme = somme + Math.pow(coords1[i] - coords2[i], 2) ;
    	}
    	return Math.sqrt(somme);
    }
    
    public static void sortByDistance(ArrayList<instance> dataset, instance point1) {
    	
    	double distance = 0;
    	for(instance e : dataset) {
    		if (e == point1) continue ;
    		distance = euclideanDist(e, point1) ;
    		e.distance = distance ;
    	}
    	
    	Collections.sort(dataset);
    	
    	for(instance e : dataset) {
    		System.out.println(e.distance);
    	}
    }

    
    public static String getDominantClass(ArrayList<instance> dataset, int k) {
    	
    	int count1= 0, count2 = 0, count3 = 0 ;
    	
    	for(int i = 0 ; i<k ; i++) {
    		if(dataset.get(i).type.compareTo("Iris-versicolor") == 0) count2++ ;
    		else if (dataset.get(i).type.compareTo("Iris-setosa") == 0) count1++ ;
    		else count3++ ;
    	}
    	
    	int max = Math.max(count1, count2) ;
    	max = Math.max(max, count3) ;
    	
    	if (max == count1) return "Iris-setosa" ;
    	else if (max == count2) return "Iris-versicolor" ;
    	else return "Iris-virginica";
    	
    }

}
