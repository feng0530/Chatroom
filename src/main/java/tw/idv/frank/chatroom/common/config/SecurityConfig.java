package tw.idv.frank.chatroom.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.idv.frank.chatroom.common.filter.JwtFilter;
import tw.idv.frank.chatroom.common.handler.AccessDeniedHandlerImpl;
import tw.idv.frank.chatroom.common.handler.AuthenticationEntryPointHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * Security 過濾器鍊
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter
    ) throws Exception {
        String[] authorityList = new String[]{"ADMIN","MANAGER"};

        http.authorizeHttpRequests(registry -> registry
                        // 放行 Swagger相關資源
                        .requestMatchers("/swagger-ui/**", "/webjars/**", "/v3/**", "/swagger-resources/**").permitAll()
                        // "/users/logout",  "/manager/logout",
                        .requestMatchers(  "/users/login", "/manager/login", "/health").permitAll()
                        .requestMatchers("/manager/**").hasAuthority(authorityList[0])
                        .requestMatchers(HttpMethod.PUT,"/users/enable").hasAnyAuthority(authorityList)
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority(authorityList)
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(new AuthenticationEntryPointHandlerImpl())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .csrf(csrf -> csrf.disable());
//                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Spring Security 內建的密碼加密工具
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * 驗證管理器
//     * @param httpSecurity
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
//    }

//    /**
//     * 認證提供者
//     * DaoAuthenticationProvider:依據 DB 中的資料來做認證
//     * @param managerDetailsService 取得 manager的資訊
//     * @param passwordEncoder 將輸入的密碼加密後，提供比對用途
//     * @return
//     */
//    @Bean
//    public AuthenticationProvider managerProvider(
//            UserDetailsService managerDetailsService,
//            PasswordEncoder passwordEncoder
//    ) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(managerDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
}
