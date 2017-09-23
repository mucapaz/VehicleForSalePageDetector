import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.trees.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.NonSparseToSparse;

import java.io.*;

/**
 * Example class that converts HTML files stored in a directory structure into 
 * and ARFF file using the TextDirectoryLoader converter. It then applies the
 * StringToWordVector to the data and feeds a J48 classifier with it.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class ARFFCreator {

  /**
   * Expects the first parameter to point to the directory with the text files.
   * In that directory, each sub-directory represents a class and the text
   * files in these sub-directories will be labeled as such.
   *
   * @param args        the commandline arguments
   * @throws Exception  if something goes wrong
   */
  public static void main(String[] args) throws Exception {
	  
	  
    // convert the directory into a dataset
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File("/home/pringles/Desktop/TextCategorisation/data/train_test"));
    
    Instances dataRaw = loader.getDataSet();
    
    StringToWordVector filter = new StringToWordVector();
    filter.setInputFormat(dataRaw);
    Instances dataFiltered = Filter.useFilter(dataRaw, filter);
    
    ArffSaver saver = new ArffSaver();
	saver.setInstances(dataFiltered);
	saver.setFile(new File("train_test.arff"));

	saver.writeBatch();

  }
}