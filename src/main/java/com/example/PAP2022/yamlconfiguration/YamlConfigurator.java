package com.example.PAP2022.yamlconfiguration;

import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.Map;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;

@NoArgsConstructor
public class YamlConfigurator {

    public Map<String, Object> getYamlProperties() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }

    public void setYamlProperties(Map<String, Object> data) throws FileNotFoundException {
        DumperOptions options = new DumperOptions();
        options.setIndent(5);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        PrintWriter writer = new PrintWriter(new File("./src/main/resources/application.yaml"));
        Yaml yaml = new Yaml(options);
        yaml.dump(data, writer);
    }

    public void changeYamlProperties(String ddlAutoConfig, String sqlInitConfig, String operatingModeConfig) throws FileNotFoundException {
        Map<String, Object> data =  getYamlProperties();
        Map<String, Map<String, Map<String, String>>> spring = (Map<String, Map<String, Map<String, String>>>) data.get("spring");

        Map<String, Map<String, String>> jpa = spring.get("jpa");
        Map<String, String> hibernate = jpa.get("hibernate");

        Map<String, Map<String, String>> sql = spring.get("sql");
        Map<String, String> init = sql.get("init");

        hibernate.replace("ddl-auto", ddlAutoConfig);
        init.replace("mode", sqlInitConfig);

        jpa.replace("hibernate", hibernate);
        spring.replace("jpa", jpa);

        sql.replace("init", init);
        spring.replace("sql", sql);

        data.replace("spring", spring);
        data.replace("operating-mode", operatingModeConfig);

        setYamlProperties(data);
    }

    public String checkOperationMode() throws FileNotFoundException {
        Map<String, Object> data =  getYamlProperties();
        return (String) data.get("operating-mode");
    }
}