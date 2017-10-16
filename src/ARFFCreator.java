import weka.core.*;
import weka.core.converters.*;
import weka.core.converters.ArffLoader.ArffReader;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class ARFFCreator {

	public static void main(String[] args) throws Exception {

		int i = 2;

		if(i == 0) {

			String instancesPath = "data/1800/train80_test20_url_tittle/train";
			String destinationPath = "data/1800/arff/train80_url_tittle.arff";
			
			createFromDir(instancesPath, destinationPath);

		}else if(i == 1) {
			/*
			 * Create from model
			 */

			String oldARFF = "data/1800/arff/train80_infogain_url_tittle.arff";
			String instancesPath = "data/1800/train80_test20_url_tittle/test/";
			String newARFF = "data/1800/arff/test20_infogain_url_tittle.arff";
			createFromModel(oldARFF, instancesPath, newARFF);
			
			
			
		}else if(i == 2){
			/*
			 * Create arff with tf-idf from model
			 */
			
			String oldARFF = "data/1800/arff/train80_infogain_url_tittle.arff";
			
			TfIdf tfIdf = new TfIdf("data/1800/documents_tf_idf_url_tittle");		
			
			
			String trainIntancesPath = "data/1800/train80_test20_url_tittle/train/";
			String newTrainARFF = "data/1800/arff/train80_infogain_url_tittle_tfidf.arff";
			createFromModel(oldARFF, trainIntancesPath, newTrainARFF, tfIdf);

			String testInstancesPath = "data/1800/train80_test20_url_tittle/test/";
			String newTestARFF = "data/1800/arff/test20_infogain_url_tittle_tfidf.arff";			
			createFromModel(oldARFF, testInstancesPath, newTestARFF, tfIdf);
		}
		
	}
	
	private static void createFromModel(String oldARFF, String instancesPath, 
			String newARFF, TfIdf tfIdf) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(new File(oldARFF)));
		ArffReader arff = new ArffReader(reader);         
		Instances data = arff.getStructure();

		String[] attrs = attributes(data);

		addInstances("positivo", instancesPath + "positivo", data, attrs, tfIdf);
		addInstances("negativo", instancesPath + "negativo", data, attrs, tfIdf);
		
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(newARFF));

		saver.writeBatch();
		
		System.out.println("CREATED FROM MODEL: " + newARFF);
		
	}

	private static void createFromModel(String oldARFF, String instancesPath, String newARFF) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(new File(oldARFF)));
		ArffReader arff = new ArffReader(reader);         
		Instances data = arff.getStructure();

		String[] attrs = attributes(data);

		addInstances("positivo", instancesPath + "positivo", data, attrs);
		addInstances("negativo", instancesPath + "negativo", data, attrs);
		
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(newARFF));

		saver.writeBatch();
		
		System.out.println("CREATED FROM MODEL: " + newARFF);
	}

	/*
	 * Create an ARFF from a directory with classes.
	 */

	public static void createFromDir(String instancesPath, String destinationPath) throws IOException {
		TextDirectoryLoader loader = new TextDirectoryLoader();


		loader.setDirectory(new File(instancesPath));

		Instances dataRaw = loader.getDataSet();

		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataRaw);
		saver.setFile(new File(destinationPath));

		saver.writeBatch();
		
		System.out.println("CREATED FROM DIR: " + destinationPath);
		
		
	}

	/*
	 * Create a new ARFF from a old ARFF and new Instances. The attributes in the new ARFF will be same as in the first ARFF.
	 * 
	 * oldARFF -> path to the ARFF model
	 * 
	 * instancesPath -> where the new instances are
	 * 
	 * newARFF -> where to save the ARFF
	 */
	
	public static int coun = 0;
	
	private static void addInstances(String type, String path, Instances data, String[] attrs, TfIdf tfIdf) throws Exception {
		File[] files = new File(path).listFiles();
		
		for(int i =0;i<files.length;i++) {

			String doc = fileToString(files[i]);
			
			double[] ar = new double[attrs.length];

			for(int x=0;x<attrs.length - 1;x++) {

				
				ar[x] = tfIdf.tfidf(doc, attrs[x]);

			}

			if(type.equals("positivo")) {
				ar[ar.length -1] = 1;
			}else {
				ar[ar.length -1] = 0;
			}
			
			
			data.add(new SparseInstance(1.0, ar));
			
			System.out.println(++coun);
		}
	}

	private static void addInstances(String type, String path, Instances data, String[] attrs) throws Exception {

		File[] files = new File(path).listFiles();

		for(int i =0;i<files.length;i++) {
			Map<String, Integer> page = mapPage(fileToString(files[i]));

			double[] ar = new double[attrs.length];

			for(int x=0;x<attrs.length - 1;x++) {

				if(page.containsKey(attrs[x]))
					ar[x] = page.get(attrs[x]);

			}

			if(type.equals("positivo")) {
				ar[ar.length -1] = 1;
			}else {
				ar[ar.length -1] = 0;
			}
			
			
			data.add(new SparseInstance(1.0, ar));
		}
	}




	private static Map<String,Integer> mapPage(String page){

		String[] ar = page.split(" ");

		HashMap<String, Integer> map = new HashMap<>();

		for(String at : ar) {
			if(map.containsKey(at)) {
				map.put(at, map.get(at) + 1);
			}else {
				map.put(at, 1);
			}
		}

		return map;
	}


	private static String[] attributes(Instances data) {

		Enumeration<Attribute> attrs = data.enumerateAttributes();
		String[] attrNames = new String[data.numAttributes()];

		int i =0;
		while(attrs.hasMoreElements()) {
			Attribute at = attrs.nextElement();
			attrNames[i] = at.name(); 

			i++;
		}

		return attrNames;
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