package com.ck.backend.config;


import com.ck.backend.filter.Hander.AccessDeniedHandlerImpl;
import com.ck.backend.filter.Hander.AuthenticationEntryPointImpl;
import com.ck.backend.filter.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author HellSherrry
 * &#064;date 2023/12/17-12:15
 * @version 1.0
 * &#064;description
 */
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] WHITE_LIST_URLS = {"/ws1/**", "/hello", "/user/regist", "/public/**"};
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> {
         request.requestMatchers("/user/login").anonymous();
            request.requestMatchers(WHITE_LIST_URLS).permitAll();
//            request.requestMatchers(HttpMethod.OPTIONS).permitAll();
            request.anyRequest().authenticated();
        });
//        http.authorizeHttpRequests(request -> request
////                .requestMatchers("user/**").permitAll()
////                .requestMatchers("/public/**").anonymous()
//                .requestMatchers("/public/**").permitAll()
//                .anyRequest().authenticated());
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
            exceptionHandling.accessDeniedHandler(accessDeniedHandler);
        });

        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
           // 允许所有请求头
            config.addAllowedHeader("*");
            // 允许所有请求方法
            config.addAllowedMethod("*");
            // 允许所有请求方法
            config.addAllowedMethod("*");
            // 允许所有请求源
            config.addAllowedOrigin("*");
            // 允许所有暴露头
//            config.addExposedHeader("*");
//            config.setAllowedOrigins(Arrays.asList("*"));
//            config.setAllowedMethods(Arrays.asList("*"));
//            config.setAllowCredentials(true);
//            config.setAllowedHeaders(Arrays.asList("*"));
            config.setMaxAge(3600L);
            return  config;
        }));
////            .addMapping("/**")
////                    .allowedOriginPatterns("*")
////                    .allowCredentials(true)
////                    .allowedMethods("*").allowedHeaders("*")
////                    .maxAge(3600);
//            CorsConfiguration config = new CorsConfiguration();
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            config.addAllowedMethod("*");
//            config.addAllowedOrigin("*");
////            config.setAllowedOrigins(Arrays.asList("*"));
////            config.setAllowedMethods(Arrays.asList("*"));
//            config.setAllowCredentials(true);
////            config.setAllowedHeaders(Arrays.asList("*"));
//            config.setMaxAge(3600L);
//            return config;
//        }));
        return http.build();

    }


//@Bean
//    public WebSecurityCustomizer securityCustomizer(){
//        return (web)->{
//           web.ignoring().antMatchers(HttpMethod.GET, "/hello/hardcode");
//        };
//}

    /*
@Bean
public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/ws/**", "/ignore2");
}
 */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
