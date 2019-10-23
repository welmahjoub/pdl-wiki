package test;

import org.junit.jupiter.api.Test;

import html.API_JsoupImp;
import wikiText.API_BlikiImp;

import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BenchTest {
	/*
	*  the challenge is to extract as many relevant tables as possible
	*  and save them into CSV files  
	*  from the 300+ Wikipedia URLs given
	*  see below for more details
	**/
	
	/* Main */
	public static void main(String[] args) throws Exception {
		
	
	}
	
	
	/*
	*  the challenge is to extract as many relevant tables as possible
	*  and save them into CSV files  
	*  from the 300+ Wikipedia URLs given
	*  see below for more details
	**/
	
	@SuppressWarnings("unused")
	@Test
	public void testBenchExtractors() throws Exception {
		
		int nbreTable = 0;
		int countTable = 0; 
		
		API_JsoupImp testExtraTable = new API_JsoupImp();
		API_BlikiImp testExtraTableBliki = new API_BlikiImp();
		
		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		// directory where CSV files are exported (HTML extractor) 
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		// directory where CSV files are exported (Wikitext extractor) 
		String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
		assertTrue(new File(outputDirWikitext).isDirectory());
		
		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String url;
	    int nurl = 0;
	    while ((url = br.readLine()) != null) {
	       String wurl = BASE_WIKIPEDIA_URL + url; 
	       System.out.println("Wikipedia url: " + wurl);
	       // TODO: do something with the Wikipedia URL 
	       // (ie extract relevant tables for correct URL, with the two extractors)

	       testExtraTable.extractionTableJsoup(url);
	       testExtraTableBliki.extractionTableJsoup(url);
	      
	       
	       // for exporting to CSV files, we will use mkCSVFileName 
	       // example: for https://en.wikipedia.org/wiki/Comparison_of_operating_system_kernels
	       // the *first* extracted table will be exported to a CSV file called 
	       // "Comparison_of_operating_system_kernels-1.csv"
	       String csvFileName = mkCSVFileName(url, 1);
	       System.out.println("CSV file name: " + csvFileName);
	       // the *second* (if any) will be exported to a CSV file called
	       // "Comparison_of_operating_system_kernels-2.csv"
	       
	       // TODO: the HTML extractor should save CSV files into output/HTML
	       // see outputDirHtml 
	       
	       // TODO: the Wikitext extractor should save CSV files into output/wikitext
	       // see outputDirWikitext      
	       
	       nurl++;	       
	    }
	    
	    br.close();	    
	    assertEquals(nurl, 336);
	    
	}

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

	
}
