import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Crawler {

	public static void main(String[] args) throws Exception {
		
//		String folder = "sites_craw";
		
	
//		ArrayList<String> pos = readURLs("data/positivos");
//		craw("data/sites", "positivos", urls);
		
		
		ArrayList<String> neg = readURLs("data/negativos");
		craw("data/sites_craw", "negativos", neg);
		
	}

	public static void craw(String baseFolder, String type, ArrayList<String> urls) throws Exception {
		
		int index = 0;
		
		for(String url : urls) {
			System.out.println(url);
		
			Document doc = Jsoup.connect(url).userAgent("Mozilla").get();		
			
			URL site = new URL(url);
			
			String host = site.getHost();

			String path = site.getFile().replaceAll("[^a-zA-Z0-9.]", "|");
			
			if(path.length() > 140) {
				path = path.substring(0, 140);
			}
			
			String folder = baseFolder + "/" + host + "/" + type;
			
			File f = new File(folder);
			f.mkdirs();
			
			stringToFile(doc.toString(), folder + "/" + path);
			
			
			System.out.println("Done " + ++index + " " + type);
		}
		
		
	}
	
	public static ArrayList<String> readURLs(String location) throws FileNotFoundException {
		Scanner in = new Scanner(new File(location));
		
		ArrayList<String> urls = new ArrayList<>();
		
		while(in.hasNextLine()) {
			urls.add(in.nextLine());
//			System.out.println(urls.get(urls.size()-1));
		}
		
		return urls;
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
