package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

/**
 * @author yaya
 * @author El Mahjoub
 *
 */
public class filterTable {

	List<String> listeClasses;
	List<String> listeattr;
	
	private Map<String,String> stats;
	
	/**
	 * Permet de filtrer les tableaux (url) : supprimer les tableaux qui ne repondent pas aux crit�re de pertinence
	 * @parm url du page wikipedia
	 * @return les tableaux apr�s filtrage
	 * @throws
	 * @throws
	 * 
	 * */
	
	public filterTable()
	{
		try {
				stats=new HashMap<String, String>();
			
			 listeattr=CSVUtils.getListFromFile(Constant.ATTRIBUT_TO_REMOVE);
			
			 listeClasses=CSVUtils.getListFromFile(Constant.CLASS_TO_REMOVE);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public Elements filterTables(Document doc ) throws Exception {
		Elements tables;
		
		stats.put("title", doc.title());
		
		tables = removeTableByClass(doc);
		
		tables = removeTableByAttribut(tables);
		
		return tables;
		
		
	}
	
	/**
	 * Supprime tous les tableaux contenant les class definis dans le fichier classtoremove
	 * 
	 * */
	public  Elements removeTableByClass(Document doc) throws Exception {
		
		
		Elements tables = doc.select("table");
		Elements tablesToRemove;
				
		for(String classe:listeClasses)
		{  	
			//Recup�re tous les elements dans les elements se trouvant dans le doc ayant la class pass�e en param
			//Ensuite selectionne que les tableaux qui seront supprim�s
			tablesToRemove = Collector.collect(new Evaluator.Class(classe), doc).select("table");		
			
			stats.put(classe, String.valueOf(tablesToRemove.size()));
			
			tables.removeAll(tablesToRemove);
	
		}
		
			
		return tables;

	}
	
	/**
	 * Supprime tous les tableaux contenant les attributs definis dans le fichier attributstoremove
	 * 
	 * */
	public  Elements removeTableByAttribut(Elements tables) throws Exception {

		
		List<Element> listTablesToRemove = new ArrayList<Element>();

		for(String attr:listeattr)
		{
			int nb=0;
			for (Element table : tables) {
				
				Elements trToRemoves =Collector.collect(new Evaluator.Attribute(attr), table);
				
				if(!trToRemoves.isEmpty())
				{ 
					listTablesToRemove.add(table);
					nb++;
				}
				
			}
			stats.put(attr, String.valueOf(nb));
		}
	
		tables.removeAll(listTablesToRemove);
		
		return tables;

	}
	
	/**
	 * Supprime les tables ayant moins de ligne
	 * */
	public Elements removeTablesWithMinRowOrColum(Elements tables) {
		
		List<Element> listTablesToRemove =new ArrayList<Element>();
		
		for (Element table : tables) {
			
			Elements trs = table.select("tr");

			if(trs.size() <= Constant.MIN_ROW ) {
				
				listTablesToRemove.add(table);
				
			}else
			{
				Elements tds = trs.get(0).select("td");
		
				if(tds.size() <= Constant.MIN_COLUM) {
					
					listTablesToRemove.add(table);	
				}
				
			}
			
		}
		
		tables.removeAll(listTablesToRemove);
		
		return tables;
	}
	
	
	
	public Map<String, String> getStatistique()
	{
		return this.stats;
	}

		
}