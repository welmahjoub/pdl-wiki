package wikiText;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import model.Page;
import model.Tableau;
import utils.CSVUtils;
import utils.Constant;
import utils.Stat;
import utils.StatPrinter;

public class ConverterWikiTextImpl implements ConverterWikitext{
	
	private ExtractorWikitext extractorWiki;
	private Writer writerStats;
	
	/**
	 * Constructor
	 */
	public ConverterWikiTextImpl()
	{
		extractorWiki=new ExtractorWikiTextImpl();
		
	}
	


	/**
	 * {@inheritDoc}
	 * 
	 * */
	public void convertAllTablesToCsv(String url) throws Exception  {
		
		String fileName;
		FileWriter w=null;
		String tableauCSV;
		fileName= CSVUtils.constructFileName(url);		
		
		Page page=extractorWiki.extractTables(url,false);
		
		page.setNomPage(fileName);
		
		//Stat.generateStatByPage(page,writerStats);
		
		StatPrinter.printStatPage(page) ;
		
		for (Tableau tab : page.getListeTableau()) {
			
			//Forme le chemin du fichier, nomPage+numTab
			tableauCSV =Constant.CSV_WIKI_PATH + CSVUtils.mkCSVFileName(fileName, tab.getNumeroTableau());
			 w = new FileWriter(tableauCSV,false);
			
			CSVUtils.writeTable(w, tab);//
			
		}
		
		if(w!=null)
		{
			w.flush();
			w.close();
		}
		
		
	}
	
	
	/**
	 * {@inheritDoc}
	 * 
	 * */
	
	public void convertAllPages() throws Exception{
		
		
		List<String> listUrls=CSVUtils.getListFromFile(Constant.WIKI_URL_PATH);
		 
		// ouvirir le fichier de statistique 
		 writerStats= new FileWriter(Constant.OUTPUT_PATH+"stats.csv",true);
		
		 
		for (String url : listUrls) {
			
			convertAllTablesToCsv(url);
		}
		
		// fermer le fichier stats
		writerStats.flush();
		writerStats.close();
		
		
	}
	
	

}
