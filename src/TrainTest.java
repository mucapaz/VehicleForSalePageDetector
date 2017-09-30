import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;


public class TrainTest {

	static String[] train = {
			"www.autoline.com.br",
			"www.autoshow.com.br",
			"carro.mercadolivre.com.br",
			"www.carrosnaweb.com.br",
			"www.compreauto.com.br",
			"www.vrum.com.br",
			"salaodocarro.com.br",
			"www.webclassicos.com.br",
			"www.webmotors.com.br",
			"www.vivalocal.com",
			"www.itavema.com.br",
			"www.icarros.com.br",
			"www.lugardecomprarcarro.com.br",
			"www.meucadillac.com",
			"seminovositaliana.com.br",
			"www.classificadoscb.com.br",
			"www.litoralcar.com.br",
			"www.autoshoppingcuritiba.com.br",
			"www.gncseminovos.com.br",
			"www.boulevardshoppingcar.com.br",
			"www.bariguiseminovos.com.br",
			"classificados.atarde.uol.com.br",
			"www.nacionalveiculos.com.br",
			"www.usadosbr.com"
	};

	static String[] test = {
			"www.meucarango.com.br",
			"www.meucarronovo.com.br",
			"olx.com.br",
			"primeiramao.band.com.br",
			"www.compreblindados.com.br",
			"carros-saopaulo-zc.temusados.com.br"
	};

	public static void main(String[] args) throws Exception {

		String folder = "data/train80_test20_url_tittle";
		
		
		createFolders(folder + "/train/positivo",  folder +"/train/negativo");
		createFolders(folder + "/test/positivo", folder + "/test/negativo");


		/*
		 * TRAIN
		 */
		
		int index = 0;

		for(String tr : train) {
			System.out.println(tr);
			
			String[] pos = new File("data/texts_url_tittle/" + tr + "/positivos").list();

			for(String p : pos) {
				File file = new File("data/texts_url_tittle/" + tr + "/positivos/" + p);
				
				String s = fileToString(file);
				stringToFile(s, folder + "/train/positivo/" + index++);

			}


			String[] neg = new File("data/texts_url_tittle/" + tr + "/negativos").list();
			
			for(String n : neg) {

				File file = new File("data/texts_url_tittle/" + tr + "/negativos/" + n);
				
				String s = fileToString(file);
				
				stringToFile(s, folder + "/train/negativo/" + index++);
				
			}
		}
		
		/*
		 * TEST
		 */
		
		for(String te : test) {
			System.out.println(te);			
			
			String[] pos = new File("data/texts_url_tittle/" + te + "/positivos").list();

			for(String p : pos) {
				File file = new File("data/texts_url_tittle/" + te + "/positivos/" + p);
				
				String s = fileToString(file);
				stringToFile(s, folder + "/test/positivo/" + index++);

			}


			String[] neg = new File("data/texts_url_tittle/" + te + "/negativos").list();
			
			for(String n : neg) {

				File file = new File("data/texts_url_tittle/" + te + "/negativos/" + n);
				
				String s = fileToString(file);
				
				stringToFile(s, folder + "/test/negativo/" + index++);
				
			}
		}
		
	
	}


	public static void createFolders(String pos, String neg) {
		File fpos = new File(pos);
		fpos.mkdirs();

		File fneg = new File(neg);
		fneg.mkdirs();
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
