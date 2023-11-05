package jungle.jungle_week_13.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대한 CORS 설정
                .allowedOrigins("*") // 모든 출처에서의 액세스 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }
}