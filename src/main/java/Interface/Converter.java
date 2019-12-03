package Interface;

import org.jsoup.nodes.Document;

/**
 * @author Hajar 
 * @author Jihad
 * @author yaya
 * @author El Mahjoub
 * @author kONATE
 */
public interface Converter {

	/**
	 * genere des fichiers CSV a partir des tableaux d'une page donn�e
	 * elle fait appel au extractor pour extraire la page 
	 * @param url
	 * @param output  ou sera stocke le fichier generer 
	 * appelle la methode convertTableToCsv
	 * */
	public void convertAllTablesToCsv(Document doc,String url,String output) throws Exception;

	
}
