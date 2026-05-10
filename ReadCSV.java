package readCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;

public class ReadCSV {
	public static int countLabels;
	public static int countPreps;

	public static void main(String[] args) {
		
		//prep the maps
		String[] labels = {"AD", "AT", "CA", "COA", "COM", "COMP", "CO", "DE", "DI", "IS", "IT", "IN", "ME", "MO", "MOM", "PE", "POS", "PU", "RE", "RS", "RT", "SO", "SP", "SS", "ST", "TD", "TO"};
		//dictionary mapping semantic function label to index
		HashMap<String, Integer> labelMap = new HashMap<>();
		countLabels = labels.length;		
		for(int i=0;i<countLabels;i++) {
        	labelMap.put(labels[i],i);
            System.out.println(i+' '+labels[i]);
        }		
		String[] preps = {"a", "bajo", "con", "de","desde", "durante", "en","entre", "hasta","por","para", "sin", "sobre"};
		countPreps = preps.length;
		//dictionary mapping preposition to different index
		HashMap<String, Integer> prepMap = new HashMap<>();
        for(int i=0;i<countPreps;i++) {
        	prepMap.put(preps[i],i);
            System.out.println(i + ' ' + preps[i]);
        }
        prepMap.put("al", 0); //al is a form of a, del is a form of de, so map to same index
        prepMap.put("del", 3);
		
        //prep countMatrix as matrix full of 0s
	    int[][] countMatrix = new int[countPreps][countLabels];	
	    for (int j=0;j<countPreps;j++){
	        Arrays.fill(countMatrix[j], 0);
	    }
	    
	    //read file
	    int counter = 0;
		File myObj = new File("P:\\combined_all.csv");
	    try (Scanner myReader = new Scanner(myObj)) {
    	myReader.nextLine(); //skip the headers
	      while (myReader.hasNextLine()) {
	        String placeholder = myReader.nextLine();	        
	        String[] data = placeholder.split(",");
	        //System.out.println(data[2]+data[4]);
	        //given one axis is the semantic function and the other is the preposition
	        //look up where in the matrix the two from this line in the file meet and increment the number there
	        if (prepMap.containsKey(data[2]) && labelMap.containsKey(data[4])) {
		        int j = prepMap.get(data[2]);
		        //System.out.println(j);
		        int i = labelMap.get(data[4]);
		        //System.out.println(i);
		        countMatrix[j][i]++;
	        }
	       /* else {
	        	System.out.println("Either "+data[2]+" or "+data[4]+" does not exist");
	        	}*/
	        counter++;
	      }
	      System.out.println(counter);
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	    
	    //printMatrix(countMatrix, labels, preps);
	    //this gives us countMatrix.txt
	    try {
			saveMatrix(countMatrix, "countMatrix",labels, preps);

		} catch (IOException e) {
			e.printStackTrace();
		}
	   
	    //creating ocMatrix out of countMatrix--turn all 0s to 6s and all else to 1s
	    //becomes a yes/no binary
	    int[][] ocMatrix = countMatrix.clone();
	    for (int i=0;i<countLabels;i++){
	    	for (int j=0;j<countPreps;j++){
	    		if (ocMatrix[j][i] > 0) {
	    			ocMatrix[j][i] = 1;
	    		}
	    		else ocMatrix[j][i] = 6;
	    	}
	    	System.out.println("");
	    }
	    
	}// end main
	
	//unused, transitioned to saveMatrix
	  private static void printMatrix(int[][] map, String[] labels, String[] preps) {
		  System.out.print(printPreps(preps)+"\n");
	    for (int i=0;i<countLabels;i++){
	    	System.out.print(labels[i]+'\t');
	    	for (int j=0;j<countPreps;j++){
	    		System.out.print(map[j][i]+"\t");
	    	}
	    	System.out.print("\n");
	    }
	}//end printMatrix
	  
	  //writes the matrix to a tab-delimited text file, meanwhile prints to screen
	  private static void saveMatrix(int[][] map, String name, String[] labels, String[] preps) throws IOException {
		  try (FileWriter obj = new FileWriter(String.format("P:\\%s.txt", name))) {
			obj.write(printPreps(preps)+"del\n");
			System.out.print(printPreps(preps)+"del\n");
			    for (int i=0;i<countLabels;i++){
			    	obj.write(labels[i]+'\t');
			    	System.out.print(labels[i]+'\t');
			    	for (int j=0;j<countPreps;j++){
			    		obj.write(map[j][i]+"\t");
			    		System.out.print(map[j][i]+"\t");
			    	}
			    	obj.write('\n');
			    	System.out.print("\n");
			    }
		  }
		}//end saveMatrix
	  
	  //to plot only a subsection, has not turned out useful yet
	  private static void saveMatrixIf(int[][] map, String name, String[] labels, String[] preps, String rows) throws IOException {
		  try (FileWriter obj = new FileWriter(String.format("P:\\%s.txt", name))) {
			obj.write(printPreps(preps)+"del\n");
			System.out.print(printPreps(preps)+"del\n");
			    for (int i=0;i<countLabels;i++){
			    	if (rows.contains(", "+String.valueOf(labels[i])+",")) {
			    		obj.write(labels[i]+'\t');
			    		System.out.print(labels[i]+'\t');
			    		for (int j=0;j<countPreps;j++){
			    			obj.write(map[j][i]+"\t");
			    			System.out.print(map[j][i]+"\t");
			    		}
			    	obj.write('\n');
			    	System.out.print("\n");
			    	}
			    }
		  }
		}//end saveMatrix	  
	  
	  //helper function for saveMatrix
	  private static String printPreps(String[] preps) {
		  String answer="\t";
		  for (int i=0;i<countPreps;i++) {
			  answer += preps[i]+'\t';
		  }
		  return answer;
	  }
}
