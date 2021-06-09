package com.lzh.demo.main;
import org.testng.TestNG;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        List<String> testngXmlList = new ArrayList<>();
        if (args.length > 0) {
            testngXmlList.addAll(Arrays.asList(args));
        } else {
            testngXmlList.add("/var/jenkins_home/testxml/testng.xml");
        }
        testNG.setTestSuites(testngXmlList);
        testNG.run();
    }
}
