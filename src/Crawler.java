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
	
	class Pair{
		public String folder;
		public String url;
		
		public Pair(String folder, String url) {
			this.folder = folder;
			this.url = url;
		}
		
	}
	
	public static void main(String[] args) throws Exception {
	
//		ArrayList<Pair> pos = readURLs("data/1800/positivos");
//		craw("data/1800/sites", "positivos", pos);
		
		
		ArrayList<Pair> neg = readURLs("data/1800/negativos");
		craw("data/1800/sites", "negativos", neg);
		
	}

	public static void craw(String baseFolder, String type, ArrayList<Pair> pairs) throws Exception {
		
		int index = 0;
		
		for(Pair pair : pairs) {
			
		
			Document doc = Jsoup.connect(pair.url).userAgent("Mozilla").get();		
			
			URL site = new URL(pair.url);
			
//			String host = site.getHost();

			String path = site.getFile().replaceAll("[^a-zA-Z0-9.]", "|");
			
			if(path.length() > 140) {
				path = path.substring(0, 140);
			}
			
			path += "|" + index;
			
			String folder = baseFolder + "/" + pair.folder + "/" + type;
			
			File f = new File(folder);
			f.mkdirs();
			
			System.out.println(folder + "/" + path);
			System.out.println(pair.url);
			
			stringToFile(doc.toString(), folder + "/" + path);
			
			
			System.out.println("Done " + ++index + " " + type);
		}
		
		
	}
	
	public static ArrayList<Pair> readURLs(String location) throws FileNotFoundException {
		Scanner in = new Scanner(new File(location));
		
		ArrayList<Pair> urls = new ArrayList<>();
		
		while(in.hasNextLine()) {
			
			String[] ar = in.nextLine().split(" ");
		
			if(ar.length >= 2) {
				Pair p = new Crawler().new Pair(ar[0], ar[1]);
				
				urls.add(p);
				
//				System.out.println(p.folder + " " + p.url);
			}
			
//			urls.add(in.nextLine());
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
