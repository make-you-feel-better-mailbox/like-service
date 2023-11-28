package com.onetwo.likeservice.common.config;

import com.onetwo.likeservice.common.GlobalUrl;
import com.onetwo.likeservice.common.config.filter.FilterConfigure;
import com.onetwo.likeservice.common.jwt.JwtAccessDeniedHandler;
import com.onetwo.likeservice.common.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final FilterConfigure filterConfigure;

    private static final String[] WHITE_LIST = {
            "/favicon.ico", "/docs/**"
    };

    @Bean
    public MvcRequestMatcher.Builder mvcRequestMatcherBuilder(HandlerMappingIntrospector introspect) {
        return new MvcRequestMatcher.Builder(introspect);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(this.createMvcRequestMatcherForWhitelist(mvc)).permitAll()
                                .anyRequest().authenticated()
                )
                .apply(filterConfigure);

        return httpSecurity.build();
    }

    private MvcRequestMatcher[] createMvcRequestMatcherForWhitelist(MvcRequestMatcher.Builder mvc) {
        List<MvcRequestMatcher> mvcRequestMatcherList = Stream.of(WHITE_LIST).map(mvc::pattern).collect(Collectors.toList());

        mvcRequestMatcherList.add(mvc.pattern(HttpMethod.GET, GlobalUrl.LIKE_COUNT + GlobalUrl.EVERY_UNDER_ROUTE));
        mvcRequestMatcherList.add(mvc.pattern(HttpMethod.GET, GlobalUrl.LIKE_FILTER));

        return mvcRequestMatcherList.toArray(MvcRequestMatcher[]::new);
    }
}
