import weka.core.*;
import weka.core.converters.*;
import weka.core.converters.ArffLoader.ArffReader;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class ARFFCreator {

	public static void main(String[] args) throws Exception {

		int i = 1;

		if(i == 0) {

			String instancesPath = "/home/pringles/Desktop/TextCategorisation/data/" + "/train80_test20_url_tittle/train";
			String destinationPath = "data/arff/train80_test20_url_tittle_tf_idf.arff";
			
			createFromDir(instancesPath, destinationPath);

		}else if(i == 1) {
			String modelPath = "data/arff/train80_test20.arff";
			String instancesPath = "data/train80_test20/test/";
			String destinationPath = "data/arff/test20.arff";
			createFromModel(modelPath, instancesPath, destinationPath);

		}
	}


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

	private static void createFromModel(String modelPath, String instancesPath, String destinationPath) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(new File(modelPath)));
		ArffReader arff = new ArffReader(reader);         
		Instances data = arff.getStructure();

		String[] attrs = attributes(data);

		addInstances("positivo", instancesPath + "positivo", data, attrs);
		addInstances("negativo", instancesPath + "negativo", data, attrs);
		
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(destinationPath));

		saver.writeBatch();
		
		System.out.println("CREATED FROM MODEL: " + destinationPath);
	}



	public static void addInstances(String type, String path, Instances data, String[] attrs) throws Exception {

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




	public static Map<String,Integer> mapPage(String page){

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


	public static String[] attributes(Instances data) {

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



	public static String fileToString(File file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}



}