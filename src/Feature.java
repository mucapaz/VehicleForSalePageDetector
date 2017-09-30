
public class Feature {

	static final String FEATURE_TITTLE = "tittle#";

	static final String FEATURE_URL = "url#";

	public static String addFEATURE_URL(String url, String text) {
		String ret = text;

//		System.out.println("url " + url);

		String[] urls = url.split("\\|");

		for(String at : urls) {

			if(at.length() > 0) {

//				System.out.println(at);

				ret = FEATURE_URL + at + " " + ret;			

			}
		}

		return ret;
	}


	public static String addFEATURE_TITTLE(String tittle, String text) {
		String ret = text;

		String[] tits = tittle.split(" ");

		for(String at : tits) {

			if(at.length() > 0) {

//				System.out.println(at);
				
				ret = FEATURE_TITTLE + at +  " " + ret;
			}
		}

		return ret;
	}

}
