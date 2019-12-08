import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心
 * EnableEurekaServer：申明这是一个Eureka服务
 *
 * @author lianggangda
 * @date 2019/12/6 15:14
 */
@SpringBootApplication
@EnableEurekaServer
// 把认证关闭，客户端才能连接服务端
// @EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

}
