import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class PreProcessor {
	public static void main(String[] args) throws Exception {

//		String[] posHtml = new File("data/sites/"+ "olx.com.br" + "/" + "negativos").list(); 
//		
//		System.out.println(posHtml);
//		
//		
		
		String[] sites = new File("data/sites").list();
		
		for(int x=0;x<sites.length;x++) {

			System.out.println(sites[x]);

			
			createTexts(sites[x], "positivos");
			createTexts(sites[x], "negativos");	
			
			
		}
		
	}

	public static void createTexts(String site, String type) throws Exception {
		
		File posText = new File("data/texts/" + site + "/" + type);
		posText.mkdirs();
		
		String[] posHtml = new File("data/sites/" + site + "/" + type).list(); 
		
		for(String s : posHtml) {
			String v = fileToString(new File("data/sites/" + site + "/" + type + "/" + s ));
		
			Document doc = Jsoup.parse(v);
		
			String text = doc.text().toLowerCase();
			
			stringToFile(text,"data/texts/" + site + "/" + type + "/" + s);		
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
