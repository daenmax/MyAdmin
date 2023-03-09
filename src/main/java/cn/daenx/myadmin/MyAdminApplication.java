package cn.daenx.myadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication
@Slf4j
public class MyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAdminApplication.class, args);
        log.info("  __  __                   _           _           _                                _             \n" +
                " |  \\/  |         /\\      | |         (_)         (_)                              (_)            \n" +
                " | \\  / |_   _   /  \\   __| |_ __ ___  _ _ __      _ ___     _ __ _   _ _ __  _ __  _ _ __   __ _ \n" +
                " | |\\/| | | | | / /\\ \\ / _` | '_ ` _ \\| | '_ \\    | / __|   | '__| | | | '_ \\| '_ \\| | '_ \\ / _` |\n" +
                " | |  | | |_| |/ ____ \\ (_| | | | | | | | | | |   | \\__ \\   | |  | |_| | | | | | | | | | | | (_| |\n" +
                " |_|  |_|\\__, /_/    \\_\\__,_|_| |_| |_|_|_| |_|   |_|___/   |_|   \\__,_|_| |_|_| |_|_|_| |_|\\__, |\n" +
                "          __/ |                                                                              __/ |\n" +
                "         |___/                                                                              |___/ ");
    }

}
