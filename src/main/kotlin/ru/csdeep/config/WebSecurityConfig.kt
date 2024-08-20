package ru.csdeep.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.csdeep.security.jwt.AuthEntryPointJwt
import ru.csdeep.security.jwt.AuthTokenFilter
import ru.csdeep.security.services.UserDetailsServiceImpl
import ru.csdeep.security.services.UserService
import java.util.*

/**
 * Security configurator form based + (user file | active directory).
 * @author Evgenii Melnikov
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(WebSecuritySetting::class)
class WebSecurityConfig(
    @Qualifier("csdeep.security-ru.csdeep.config.WebSecuritySetting")
    val setting: WebSecuritySetting,
    var unauthorizedHandler: AuthEntryPointJwt,
    var userDetailsService: UserDetailsServiceImpl,
    val userService: UserService
) {
    /**
     * AuthTokenFilter.
     */
    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter? {
        return AuthTokenFilter()
    }

    /**
     * Filter chain.
     */
    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity, authManager: AuthenticationManager): SecurityFilterChain? {
        if (setting.userDatabase.isEnabled) {
            http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/services/api/auth/**").permitAll()
                .antMatchers(
                    "/", "/static/**", "/**/*.png", "/**/*.css", "/favicon.ico",
                    "/index.html", "/robots.txt"
                ).permitAll()
                .antMatchers("/**/*.js").permitAll()
                .antMatchers("/services/api/config/appWindowBackground").permitAll()
                .antMatchers("/services/api/config/loginInfo").permitAll()
                .antMatchers("/services/api/config/version").permitAll()
                .antMatchers("/steam/*").permitAll()
                .anyRequest().authenticated()

            http.authenticationManager(authManager)
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        } else {
            http.csrf().disable().authorizeRequests().anyRequest().permitAll()
        }
        return http.build()
    }

    /**
     * GrantedAuthorityDefaults.
     */
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults? {
        return GrantedAuthorityDefaults("") // Remove the ROLE_ prefix
    }

    /**
     * PasswordEncoder.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    /**
     * AuthenticationProvider.
     */
    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    /**
     * Configure authentication.
     */
    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        resourceLoader: ResourceLoader
    ): AuthenticationManager {
        val auth: AuthenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)

        if (setting.userDatabase.isEnabled) {
            auth.authenticationProvider(
                DaoAuthenticationProvider().apply {
                    setUserDetailsService(
                        UserDetailsServiceImpl(userService)
                    )
                    setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
                }
            )
        }

        return auth.parentAuthenticationManager(null).build()
    }

    /**
     * jwt secret for current instance.
     */
    @Bean
    fun jwtSecretString(): String {
        return UUID.randomUUID().toString()
    }
}
