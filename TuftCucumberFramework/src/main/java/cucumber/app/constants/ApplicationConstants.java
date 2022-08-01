package cucumber.app.constants;

import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

	public abstract class ApplicationConstants {

		public static String URL = "https://uat.tuftapp.com/en/clients";
		public static String browser = "chrome";
		public static String environment = "qa";

		//public static final Duration EXP_WAIT = TimeUnit.MILLISECONDS;
		//public static final Duration EXP_WAIT = Duration.ofSeconds(30);
		public static final Duration EXP_WAIT = Duration.ofSeconds(30);


		public static final long Page_load_timeout=20;
		public static final long IMP_WAIT=10;
		
		public static final String EMAIL = new String(Base64.getDecoder().decode("dGltaXIudHJpdmVkaUBzeW5vdmVyZ2UuY29t"));
		public static final String PASSWORD =new String(Base64.getDecoder().decode("U3lub3ZlcmdlQDEyMw=="));
		
		/* To encode any String use below code
		public static void main(String[] args) {
			String originalInput = "yourName.surname@synoverge.com";
			String encryptedData = Base64.getEncoder().encodeToString(originalInput.getBytes());
			System.out.println("encryptedData: "+encryptedData);
		}*/
		
		//Azure storage constants
		public static final String STORAGE_CONNECTION_STRING = 
			"DefaultEndpointsProtocol=https;" +
			"AccountName=SynoTest;" +
			"AccountKey=dgdsgdf";
}
