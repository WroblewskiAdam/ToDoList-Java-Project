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
public class YamlConfig {

    private int SQL_INIT = 1;


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

    public void changeYamlProperties(String ddlAutoConfig, String sqlInitConfig) throws FileNotFoundException {
        Map<String, Object> data =  getYamlProperties();
        Map<String, Map<String, Map<String, String>>> spring = (Map<String, Map<String, Map<String, String>>>) data.get("spring");

        Map<String, Map<String, String>> jpa = spring.get("jpa");
        Map<String, String> hibernate = jpa.get("hibernate");

        Map<String, Map<String, String>> sql = spring.get("sql");
        Map<String, String> init = sql.get("init");

        if (ddlAutoConfig.equals("update")) {
            hibernate.replace("ddl-auto", "update");
        } else if (ddlAutoConfig.equals("create")) {
            hibernate.replace("ddl-auto", "create");
        }

        if (sqlInitConfig.equals("never")) {
            init.replace("mode", "never");
        } else if (sqlInitConfig.equals("always")) {
            init.replace("mode", "always");
        }

        jpa.replace("hibernate", hibernate);
        spring.replace("jpa", jpa);

        sql.replace("init", init);
        spring.replace("sql", sql);

        data.replace("spring", spring);

        setYamlProperties(data);
    }

//    public void changeSqlInit(Boolean sqlInitConfiguration) throws FileNotFoundException {
//        Map<String, Object> data =  getYamlProperties();
//        Map<String, Map<String, Map<String, String>>> spring = (Map<String, Map<String, Map<String, String>>>) data.get("spring");
//        Map<String, Map<String, String>> sql = spring.get("sql");
//        Map<String, String> init = sql.get("init");
//
//        if (sqlInitConfiguration) {
//            init.replace("mode", "never");
//        } else {
//            init.replace("mode", "always");
//        }
//
//        sql.replace("init", init);
//        spring.replace("sql", sql);
//        data.replace("spring", spring);
//
//        setYamlProperties(data);
//    }

//    public void changeJpaDdlAuto(Boolean ddlAutoConfiguration) throws FileNotFoundException {
//        Map<String, Object> data =  getYamlProperties();
//        Map<String, Map<String, Map<String, String>>> spring = (Map<String, Map<String, Map<String, String>>>) data.get("spring");
//        Map<String, Map<String, String>> jpa = spring.get("jpa");
//        Map<String, String> hibernate = jpa.get("hibernate");
//
//        if (ddlAutoConfiguration) {
//            hibernate.replace("ddl-auto", "update");
//        } else {
//            hibernate.replace("ddl-auto", "create-drop");
//        }
//
//        jpa.replace("hibernate", hibernate);
//        spring.replace("jpa", jpa);
//        data.replace("spring", spring);
//
//        setYamlProperties(data);
//    }
}