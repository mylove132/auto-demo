package com.lzh.demo.listener;

import com.alibaba.fastjson.JSONObject;
import com.lzh.demo.config.ReporterData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;
import org.testng.*;
import org.testng.xml.XmlSuite;
import java.io.*;
import java.util.*;

public class ReportListener implements IReporter {

    public Logger log = Logger.getLogger(ReportListener.class);

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        try {
            log.info("报告数据："+ JSONObject.toJSONString(xmlSuites));
            //freemaker的配置
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setClassForTemplateLoading(this.getClass(),"/templates");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //freemaker的模板文件
            Template temp = cfg.getTemplate("report.ftl");
            Map context = new HashMap();
            for (ISuite suite : suites) {
                Map<String, ISuiteResult> suiteResults = suite.getResults();
                for (ISuiteResult suiteResult : suiteResults.values()) {
                    ReporterData data = new ReporterData();
                    ITestContext testContext = suiteResult.getTestContext();
                    // 把数据填入上下文
                    context.put("overView", data.testContext(testContext));//测试结果汇总信息
                    //ITestNGMethod[] allTests = testContext.getAllTestMethods();//所有的测试方法
                    //Collection<ITestNGMethod> excludeTests = testContext.getExcludedMethods();//未执行的测试方法
                    IResultMap passedTests = testContext.getPassedTests();//测试通过的测试方法
                    IResultMap failedTests = testContext.getFailedTests();//测试失败的测试方法
                    IResultMap skippedTests = testContext.getSkippedTests();//测试跳过的测试方法

                    context.put("pass", data.testResults(passedTests, ITestResult.SUCCESS));
                    context.put("fail", data.testResults(failedTests, ITestResult.FAILURE));
                    context.put("skip", data.testResults(skippedTests, ITestResult.FAILURE));

                }
            }
            System.out.println(context.get("overView").toString());
            // 输出流
            //Writer writer = new BufferedWriter(new FileWriter("report.html"));
            OutputStream out=new FileOutputStream("target/report.html");
            Writer writer = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));//解决乱码问题
            // 转换输出
            temp.process(context,writer);
            writer.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
