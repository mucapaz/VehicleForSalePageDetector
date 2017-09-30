import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class PreProcessor {
	public static void main(String[] args) throws Exception {

		
		String[] sites = new File("data/sites").list();
		
		String location = "texts_url_tittle";
		
		for(int x=0;x<sites.length;x++) {

			System.out.println(sites[x]);

			
			createTexts(location, sites[x], "positivos");
			createTexts(location, sites[x], "negativos");	
//			break;
		}
		
	}

	public static void createTexts(String location, String site, String type) throws Exception {
		
		File posText = new File("data/" + location + "/" + site + "/" + type);
		posText.mkdirs();
		
		String[] posHtml = new File("data/sites/" + site + "/" + type).list(); 
		
		for(String s : posHtml) {
			String v = fileToString(new File("data/sites/" + site + "/" + type + "/" + s ));
		
			Document doc = Jsoup.parse(v);
		
			String text = doc.text().toLowerCase();
			
			System.out.println(doc.title());
			System.out.println(s);
			
			// add features
			text = Feature.addFEATURE_TITTLE(doc.title(), text);
			
			text = Feature.addFEATURE_URL(s, text);
			
			text = text.toLowerCase();
			
//			System.out.println(text);
			
			stringToFile(text,"data/" + location + "/" + site + "/" + type + "/" + s);		
		}
		
	}
	

	
	
	public static String fileToString(File file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}

	public static void stringToFile(String content, String destination) throws Exception{
		File file = new File(destination);
//		System.out.println(destination);		
		file.createNewFile();

		PrintWriter writer = new PrintWriter(destination, "UTF-8");
		writer.println(content);
		writer.close();
	}

}
