package Tp6_Kmeans;

public class instance implements Comparable<instance>{
    public float[] attr;
    public boolean[] missing;
    public String type,rawData;
    public double distance ;
    public int cluster ;


    public instance(String data) {
        this.rawData = data;
        String[] splitResults = data.split(",");
        attr = new float[4];
        missing = new boolean[4];
        for(int i = 0 ; i < 4 ; i++){
            try{
                attr[i] = Float.parseFloat(splitResults[i]);
                missing[i] = false;
            }catch (Exception e){
                missing[i] = true;
            }

        }
        type = splitResults[4];
        cluster = -1 ;

    }
    
    public instance() {
    	
    }
    
    public int compareTo(instance p)  {
    	
    	if(distance - p.distance > 0) return 1; 
    	else if (distance - p.distance < 0) return -1 ;
    	else return 0 ;
    }
    
    public int compare(instance p1, instance p2) {
    	
    	if(p1.distance - p2.distance > 0) return 1; 
    	else if (p1.distance - p2.distance < 0) return -1 ;
    	else return 0 ;
    	
    }
    
    public String toString() {
    	
    	return "" + attr[0] + " " + attr[1] + " " + attr[2] + " " + attr[3] + " " + type + " "+ cluster +"\n";
    }
    
}
