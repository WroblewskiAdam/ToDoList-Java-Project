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
<<<<<<< HEAD
//		YamlConfig yamlConfig = new YamlConfig();
//		yamlConfig.changeYamlProperties("create", "always"); - Initial properties
//		yamlConfig.changeYamlProperties("update", "never");
=======
		YamlConfigurator yamlConfigurator = new YamlConfigurator();

		if (yamlConfigurator.checkOperationMode().equals("normal")){
			yamlConfigurator.changeYamlProperties("update", "never", "normal");
		} else if (yamlConfigurator.checkOperationMode().equals("init")) {
			yamlConfigurator.changeYamlProperties("create", "always", "normal");
		}
>>>>>>> bf6995f50a573fd8eed18d823312176d05f3ab7f
	}
}
