package com.lzh.demo.testcases;

import com.lzh.demo.pojo.HttpRequestDto;

/**
 * 测试用例的抽象类
 */
public abstract class AbstractTestCase {

    public abstract void run(HttpRequestDto requestDto);

}
