package com.example.PAP2022;

import com.example.PAP2022.yamlconfiguration.YamlConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBackendApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MyBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		YamlConfig yamlConfig = new YamlConfig();
//		yamlConfig.changeYamlProperties("create", "always"); // Initial properties
		yamlConfig.changeYamlProperties("update", "never");
	}
}
