package cn.cps.springsecurity.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
//redis配置session会话管理
//@EnableRedisHttpSession
//redis配置session会话管理 同时 设置redis超时时间
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)
public class SpringsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);
    }

}
