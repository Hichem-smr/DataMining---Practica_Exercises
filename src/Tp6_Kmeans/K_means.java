package Tp6_Kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


public class K_means {

	public static void main(String[] args) throws IOException {
		ArrayList<instance> data = loadDataset("Dataset-Exos.txt");
		ArrayList<instance> centroides = K_means(3, data);
		Iterator<instance> element = centroides.iterator();
		while(element.hasNext()) {
			System.out.println(element.next());
		}
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
	
	public static double manhattanDist(instance point1, instance point2) {
    	
    	float[] coords1 = point1.attr ;
    	float[] coords2 = point2.attr ;
    	
    	double somme = 0 ;
    	for (int i = 0 ; i < coords1.length ; i++) {
    		somme = somme + Math.abs(coords1[i] - coords2[i]) ;
    	}
    	return somme;
    }
	
	public static instance[] generateKcentroide(int k, ArrayList<instance> dataset){
		instance[] centroides = new instance[k] ;
		
		for(int i = 0 ; i < k ; i++) {
			int j = ThreadLocalRandom.current().nextInt(0, 149) ;
			centroides[i] = dataset.get(j) ; 
			dataset.get(j).cluster = i ;
//			System.out.println(j);
		}
		
		return centroides ;
	}
	
	public static ArrayList<instance>  K_means(int k, ArrayList<instance> dataset){
		instance[] initCentroides = generateKcentroide(k, dataset) ;
		boolean change ;
		do {
			change = false ; 
			for(int j = 0 ; j<dataset.size() ; j++) {
				double min = Double.MAX_VALUE; 
				int cluster = 0 ;
				for(int i = 0 ; i < k ; i++) {
					double distance = manhattanDist(dataset.get(j), initCentroides[i]) ;
					if (distance < min) {
						min = distance ;
						cluster = i ;
					}
					
				}
				if(dataset.get(j).cluster != cluster) {
					dataset.get(j).cluster = cluster ;
					change = true ; 
				}
				
			}
			
			float[][] sums = new float[k][4] ;
			int[] clusterSizes = new int[k] ;
			for(int j = 0 ; j<dataset.size() ; j++) {
				instance current = dataset.get(j) ;
				for (int i = 0 ; i < 4 ; i++) {
					sums[current.cluster][i] = sums[current.cluster][i] + current.attr[i] ;
				}
				clusterSizes[current.cluster]++ ;
			}
			
			for(int i = 0 ; i<k ; i++) {
				for (int j = 0 ; j < 4 ; j++) {
					sums[i][j] = sums[i][j] / clusterSizes[i] ;
				}
				
				instance newCentroid = new instance();
				newCentroid.attr=sums[i] ;
				newCentroid.cluster = i ;
				initCentroides[i] = newCentroid ;
			}
		}while(change) ;
			
		return dataset ;
			
	}

}
