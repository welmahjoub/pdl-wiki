package utils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import model.Ligne;
import model.Tableau;

public class CSVUtils {
	
	

	private static final char DEFAULT_SEPARATOR = ',';
    
    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    //remplace simple guillemet double guillemet
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }
    
    
    
    
    
  //lorsau'il y a une virgule dans la cellule mettre le 
   /** 
    * TODO
    * S'il y'a une virgule dans la cellule on doit le remplacer par un point si c'est numeric : 12,5 => 12.5
     *Sinon on remplace par un espace : a,b => a b ou 12,a => 12 a etc...
     * 
     * @param value
     * @return
     */
    public static String assureFomatCSV(String value) {

        String result = value;
        if (result.contains(",")) {
            result = result.replace(",", " ");
        }
        return result;

    }

    
    
  //Pour former le nom du fichier gr�ce au nom du lien  
    public static String assureFomatDosTab(String url) {

        String result = url;
        
        if (result.contains("https:")) {
            result = result.replace("https:", "");
        }
        
        else
        	
        	if (result.contains("\"")) {
        		result = result.replace("\"", "_");
		}
        
        	else
	        	
	        	if (result.contains("*")) {
	        		result = result.replace("*", "_");
			}
	        	else
		        	
		        	if (result.contains("?")) {
		        		result = result.replace("?", "_");
				}
		        	else
			        	
			        	if (result.contains("<")) {
			        		result = result.replace("<", "_");
					}
			        	else
				        	
				        	if (result.contains(">")) {
				        		result = result.replace(">", "_");
						}
				        	else if (result.contains("|")){
				        		result = result.replace("|", "_");
				        	}
        						
				        	else if (result.contains("\\")){
				        		result = result.replace("\\", "_");
				        	}
        
        
        return result;

    }
    
    
    

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }
    
    public static void writeTable(Writer w, Tableau table) throws IOException { 
    	
    	for (Ligne  line : table.getlisteLignes()) {
    		
				w.append(line.toString());
				w.append("\n ");
		}
    }
    
    // supprimer un directory avec ses fichiers
    public static void deleteOutPutFiles(File tempFile) {
        try
        {
            if(tempFile.isDirectory()){
               File[] entries = tempFile.listFiles();
               for(File currentFile: entries){
            	   deleteOutPutFiles(currentFile);
               }
               tempFile.delete();
            }else{
               tempFile.delete();
            }
        //getLogger().info("DELETED Temporal File: " + tempFile.getPath());
        }
        catch(Throwable t)
        {
            //getLogger().error("Could not DELETE file: " + tempFile.getPath(), t);
        }
    }
    
    public static void creatOutPutFolder() {
    	
    	// supprimer ancien fichiers 
    	deleteOutPutFiles(new File(Constant.OUTPUT_PATH));
    	
    	
    	//cree a nouveau 
    	new File(Constant.OUTPUT_PATH).mkdir();
    	new File(Constant.CSV_HTML_PATH).mkdir();
    	new File(Constant.CSV_WIKI_PATH).mkdir();
    	
    }
    
    
    public static String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

    
    /**
     * Permet de verifier si le fichier csv est valide
     * @param le chemin du fichier csv
     * @return boolean
     * @throws 
     * **/
    
    public boolean isCsvFileValid(String filePaht) {
    	return true;
    }
	
}
