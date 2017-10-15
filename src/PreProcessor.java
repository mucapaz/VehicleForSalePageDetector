import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class PreProcessor {
	public static void main(String[] args) throws Exception {
		
		String sites = "data/1800/sites";
		String texts = "data/1800/texts";
		
		String[] ar = new File(sites).list();
		
	
		for(int x=0;x<ar.length;x++) {

			System.out.println(ar[x]);

			
//			createTexts(sites, texts, ar[x], "positivos");
			createTexts(sites, texts, ar[x], "negativos");	
//			break;
		}
		
	}

	public static void createTexts(String sites, String texts, String site, String type) throws Exception {
		
		File posText = new File(texts + "/" + site + "/" + type);
		posText.mkdirs();
		
		String[] posHtml = new File(sites + "/" +  site + "/" + type).list(); 
		
		int done = 0;
		
		for(String s : posHtml) {
			String v = fileToString(new File(sites + "/" + site + "/" + type + "/" + s ));
		
			Document doc = Jsoup.parse(v);
		
			String text = doc.text().toLowerCase();
			
			System.out.println(doc.title());
			System.out.println(s);
			
			// add features
//			text = Feature.addFEATURE_TITTLE(doc.title(), text);
//			
//			text = Feature.addFEATURE_URL(s, text);
//			
//			text = text.toLowerCase();
			
//			System.out.println(text);
			
			stringToFile(text, texts + "/" + site + "/" + type + "/" + s);	
			System.out.println("Done " + ++done + " " + texts + "/" + site + "/" + type + "/" + s);
			
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
