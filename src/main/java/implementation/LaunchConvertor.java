package implementation;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import utils.CSVUtils;
import utils.Constant;
import utils.ConverterUtil;
import utils.StatistiqueUtils;
import utils.FilterTable;

/**
 * @author Hajar 
 * @author Jihad
 * @author yaya
 * @author El Mahjoub
 *
 */
public class LaunchConvertor {

	private ConverterImp convert;
	private List<String> listUrls;
	private Document html;
	private Document wiki;
	private FilterTable filter;
	Map<String,String> stats;
	FileWriter writerStats ;
	
	public  LaunchConvertor()
	{
		filter=new FilterTable();	
		convert=new ConverterImp(filter);
	}
	
	/**
	 * extraire tous les articles wikipedia et generer des fichiers scv
	 * parcourir la liste des urls et
	 *  chaque foit elle recuper les deux documents ( html et wikitext)
	 *   qui correspond au l'article  
	 *  puis elle fait appele au converter avec le document html 
	 *  puis le document wikitexte 
	 *  et generer les statistique concernant l'article 
	 *   a travers la classe filterTable
	 *  
	 * @throws Exception
	 */
	public void convertAllPages() throws Exception{
		
		//lire fichier input ( tous les urls ) 
		listUrls=CSVUtils.getListFromFile(Constant.WIKI_URL_PATH);
		
		// cree le fichier (rappor)  qui contient les statistique
		writerStats= new FileWriter("rappport.csv",false);
		StatistiqueUtils.writeHeaderFileStatistique(writerStats);
		
		 // create dossier output qui contient les fichiers extraits
		 CSVUtils.creatOutPutFolder();
		
		 // parcouire la liste des urls
		 
		for (String url : listUrls) {
			
			// convertion du  document html
		    
			html=ConverterUtil.getDocumentJsoup(Constant.BASE_WIKIPEDIA_URL+url);
		    
			convert.convertAllTablesToCsv(html, url,Constant.CSV_HTML_PATH);
			
			// recuperation des stats
			stats=filter.getStatistique();
			
			stats.put("Extractor", "Jsoup");
			stats.put("Article", url);
			// ecrire les stats dans le rapport
			StatistiqueUtils.writeStatByArticle(stats, writerStats);
			
			//  convertion du  document  wikitext 
		   
			wiki=ConverterUtil.getDocumentWiki(url);
			
			convert.convertAllTablesToCsv(wiki, url,Constant.CSV_WIKI_PATH);
			
			stats=filter.getStatistique();
			
			stats.put("Extractor", "Bliki");
			stats.put("Article", url);
			// ecrire les stats dans le rapport
			StatistiqueUtils.writeStatByArticle(stats, writerStats);
		}
			

		//fermer le fichier de stats ( rapport) 
		writerStats.flush();
		writerStats.close();
		
	}
	
/**
 * fonction main pour tester notre application sans executer les tests	
 * @param args
 */

public static void main(String[] args) {
	
	try {
		
		new LaunchConvertor().convertAllPages();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
