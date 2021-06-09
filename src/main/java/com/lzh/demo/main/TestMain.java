package com.lzh.demo.main;
import org.testng.TestNG;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        List<String> testngXmlList = new ArrayList<>();
        testngXmlList.add("D:\\liuzhanhui\\Downloads\\auto-demo\\testng.xml");
        testNG.setTestSuites(testngXmlList);
        testNG.run();
    }
}
