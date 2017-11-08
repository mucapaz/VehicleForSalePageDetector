import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdf {

	List<String> docs;
	Map<Integer, Map<String, Integer>> mapDoc;
	
	Map<String, Double> mapIdf;	
	
	public TfIdf(String docsPath) throws Exception {
		docs = new ArrayList<String>();
		
		mapIdf = new HashMap<String, Double>();
		
		File[] files = new File(docsPath).listFiles();
		
		System.out.println(docsPath);
		
		
		mapDoc = new HashMap<Integer, Map<String, Integer> >();
		
		for(int i =0;i<files.length;i++) {
			String doc = fileToString(files[i]);
			
			HashMap<String, Integer> hash = new HashMap<String, Integer>();
			
			docs.add(doc);
			
			String[] terms = doc.split(" ");
			
			for(String t : terms) {
				
				hash.put(t, 1);
				
//				if(hash.containsKey(t)) {
//					hash.put(t, hash.get(t) + 1);
//				}else {
//					hash.put(t, 1);
//				}
				
			}
			
			
			mapDoc.put(i, hash);
		}
		
	}
	
	public double tfidf(String doc, String term) {
		double tf = tf(doc,term);
		if(tf == 0.0) return 0.0;
		else {
			double idf = idf(term);
			
			return tf * idf;
		}
		
	}
	

	
	private double idf(String term) {
		double n = 0.0;
		
		
		for(int x=0;x<docs.size();x++) {
			
			if(mapDoc.get(x).containsKey(term)) {
				n++;
			}
		}
		
//		for(String doc : docs) {
//			
//			String[] terms = doc.split(" ");
//			
//			for(String t : terms) {
//				if(t.equals(term)) {
//					n++;
//					break;
//				}
//			}
//		}
	
		return Math.log(docs.size()/n);
	}

	private double tf(String doc, String term) {
		
		double tf = 0.0;
		
		String[] terms = doc.split(" ");
		
		for(String t : terms) {
			if(t.equals(term))
				tf++;
			
		}
		return tf;
	}

	private static String fileToString(File file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}

}
