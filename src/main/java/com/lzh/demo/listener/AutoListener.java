package com.lzh.demo.listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class AutoListener implements ITestListener {

    private Logger log = Logger.getLogger(AutoListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("测试类：%s开始执行".format(context.getAllTestMethods()[0].getRealClass().getName()));
    }
}
