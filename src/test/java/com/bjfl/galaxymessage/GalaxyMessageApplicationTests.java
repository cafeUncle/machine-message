package com.bjfl.galaxymessage;

import com.bjfl.galaxymessage.util.ConfigAuto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GalaxyMessageApplicationTests {

    @Autowired
    ConfigAuto configAuto;

    @Test
    public void contextLoads() {
        System.out.println(configAuto.host);
    }

}
