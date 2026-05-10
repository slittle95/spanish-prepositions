package findPrepositions;

import java.io.BufferedReader;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;

public class FindPrepositions {
	//csv file structure:
	//file	line number	 spanish prep found	 index of spanish prep	 translated to	 code	 original Spanish	 original English
    public static void main(String[] args) {
    	//source data is a collection of text files
    	String directory = "C:\\Users\\lenovo\\Documents\\pythonforfinal\\gold_data\\eng-spa-gold_";
    	String[] movies = {"bettercallsaul.txt", "murder.txt", "3body.txt", "yellowstone.txt", "outerrange.txt"};
    	//String[] = {"for","to","by","that","per","in","from","so","about","on","of","through","here","with","around","at","via","out of","as","at least","finally","therefore","at last", "over there","over here", "completely","due to","hereby","last","after","into"};
    	
    	String[] words = {"por","para","a", "del", "desde", "ante", "bajo", "cabe", "durante", "mediante", "so", "versus", "vía", "tras", "hacia", "sobre", "entre", "en", "con", "sin", "contra", "de", "al", "hasta", "según", "sin"};
    	File output = new File("C:\\Users\\lenovo\\Documents\\pythonforfinal\\gold_data\\combined_all.csv");
    	try {
    		FileWriter obj = new FileWriter(output);
    		obj.append("file, line number,spanish prep found,index of spanish prep,code,original Spanish,original English\n"
);
            String English = "placeholder";
            String Spanish = "placeholder";
            
            //turn this collection of text files into a .csv
	    	for (String i : movies) {
	    		int countLines = 0;
	    		String path = directory + i;    	
		        File file = new File(path);
		        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		            String line="placeholder";
		            System.out.println(i);
		            while (line != null) {
		            	line = br.readLine(); 
		            	English = line;
		            	line = br.readLine();
		            	Spanish = line;	
		            	line = br.readLine(); //blank line skip
		            	for (String j : words){
		            		//if the line with the Spanish contains a preposition
			            	if (Spanish.contains(" "+j+" ")|| Spanish.contains(j.substring(0,1).toUpperCase() + j.substring(1,j.length()) + " ")) {
			            		String translatedTo = getCode(English.toLowerCase(), Spanish.toLowerCase(), j);
				            	obj.append(i+","+countLines+","+j+",," + translatedTo +","+Spanish.replaceAll(",","")+","+English.replaceAll(",","")+"\n");
			            	}
			            	countLines++;
		            	}//end for
			         }//end while
		        } catch (IOException e) {
		            System.err.println("Error reading file: " + e.getMessage());
		        } catch(NullPointerException ex) {
	            	System.err.println(English);
		        	System.err.println(Spanish);
		        }
		        
	        }//end for
	    	obj.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }//end main
    
    //assigns as many codes automatically as possible
    public static String getCode(String English, String Spanish, String j) {
    	if (j.equals("con") && (Spanish.contains("con el") || Spanish.contains("con la"))) return "ME";
    	else if (j.equals("con")) return "CO";
    	else if (j.equals("sobre")) return "TO";
    	else if (j.equals("bajo")||j.equals("entre")||j.equals("contra")) return "RS";
    	else if (j.equals("sin")) return "AD";
    	else if (j.equals("desde") || English.contains("from")) return "SO";
    	else if (j.equals("so")||j.equals("tras")) return "SS";
    	else if (j.equals("vía")) return "IS";
    	else if (j.equals("de") && Spanish.contains("un par de")) return "MO";
    	else if (j.equals("de") && (Spanish.contains("de verdad") || Spanish.contains("de acuerdo"))) return "MO";
    	else if (j.equals("de") && (Spanish.contains("antes") || Spanish.contains("después de"))) return "RT";
    	else if ( j.equals("del") || (j.equals("de") && Spanish.contains("de la")) ) return "SP";
    	else if (j.equals("por") && (Spanish.contains("por favor") || Spanish.contains("por dios"))) return "ID";	
    	else if (j.equals("por") && Spanish.contains("por siempre")) return "IT";
    	else if (j.equals("por") && Spanish.contains("por teléfono")) return "ME";
    	else if (j.equals("por") && (Spanish.contains("por mañana") || Spanish.contains("por noche"))) return "IT";
    	else if (j.equals("por") && (Spanish.contains("por la mañana") || Spanish.contains("por la noche"))) return "ST";
    	else if (j.equals("en") && Spanish.contains("en nombre")) return "MO";
    	else if (j.equals("en") && Spanish.contains("en cuenta")) return "ID";
    	else if (j.equals("en") && Spanish.contains("en realidad")) return "MO";
    	else if (j.equals("para")&& paraRegex(Spanish)) return "PU";
    	else if (j.equals("para") && Spanish.contains("regalo")) return "RE";
    	else if (j.equals("para") && (Spanish.contains("para mí")||Spanish.contains("para tí")||Spanish.contains("para ella")||Spanish.contains("para él"))) return "PE"; 	
    	else if (English.contains("cause")) return "CA";
    	else return "";
    }
    
    //regex to spot verbs--usually the PU form
    public static Boolean paraRegex(String stringToBeMatched) {
    	String regex = "[P| p]ara .+[a|i|e]r(se)?(la)?(le)?(lo)?";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(stringToBeMatched);
    	return(matcher.find());
    }
}
