package com.example.PAP2022;

import com.example.PAP2022.yamlconfiguration.YamlConfigurator;
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

		YamlConfigurator yamlConfigurator = new YamlConfigurator();

		if (yamlConfigurator.checkOperationMode().equals("normal")){
			yamlConfigurator.changeYamlProperties("update", "never", "normal");
		} else if (yamlConfigurator.checkOperationMode().equals("init")) {
			yamlConfigurator.changeYamlProperties("create", "always", "normal");
		}
	}
}
