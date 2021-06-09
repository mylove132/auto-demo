package com.lzh.demo.testcases.user;
import com.lzh.demo.pojo.HttpRequestDto;
import com.lzh.demo.testcases.BaseTestCase;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({com.lzh.demo.listener.AutoListener.class})
public class LoginTestCase extends BaseTestCase {

    @Test(dataProvider = "providerMethod", groups={"normal"})
    public void login(HttpRequestDto dataDto){
        run(dataDto);
    }

    @Test(dataProvider = "providerMethod", groups = {"exception"})
    public void nameNotExist (HttpRequestDto dataDto) {
        run(dataDto);
    }
}
