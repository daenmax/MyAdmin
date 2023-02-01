package cn.daenx.myadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = "cn.daenx.myadmin.**.mapper")
public class MyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAdminApplication.class, args);
    }

}
