import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfIdf {

	List<String> docs;
	Map<String, Double> mapIdf;	
	
	public TfIdf(String docsPath) throws Exception {
		docs = new ArrayList<String>();
		
		mapIdf = new HashMap<String, Double>();
		
		File[] files = new File(docsPath).listFiles();
		
		System.out.println(docsPath);
		
		for(int i =0;i<files.length;i++) {
			docs.add(fileToString(files[i]));
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
		
		for(String doc : docs) {
			
			String[] terms = doc.split(" ");
			
			for(String t : terms) {
				if(t.equals(term)) {
					n++;
					break;
				}
			}
		}
	
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
