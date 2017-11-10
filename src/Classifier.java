import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;

public class Classifier {

	public static void main(String[] args) throws Exception {

		BufferedReader reader1 = new BufferedReader(
				new FileReader("data/1800/arff/train80_infogain_url_tittle_tfidf.arff"));

		Instances train = new Instances(reader1);
		reader1.close();
		train.setClassIndex(train.numAttributes() -1);

		
		
		BufferedReader reader2 = new BufferedReader(
				new FileReader("data/1800/arff/test20_infogain_url_tittle_tfidf.arff"));

		Instances test = new Instances(reader2);
		reader2.close();
		test.setClassIndex(test.numAttributes() - 1);

		AdaBoostM1 cls = new AdaBoostM1();
		String[] op = {"-I", "30"};
		cls.setOptions(op);
		cls.buildClassifier(train);

		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(cls, test);

		System.out.println(eval.toSummaryString("\nResults\n======\n", false));


		// serialize model
		ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream("data/1800/model/ADABoostM1_30iterations.model"));
		oos.writeObject(cls);
		oos.flush();
		oos.close();
	}

}
