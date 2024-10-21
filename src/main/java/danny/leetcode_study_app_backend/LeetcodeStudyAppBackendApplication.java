package danny.leetcode_study_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LeetcodeStudyAppBackendApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load(); // Loads .env file

		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));

		SpringApplication.run(LeetcodeStudyAppBackendApplication.class, args);
	}

}
