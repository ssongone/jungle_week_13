# jungle_week_13
spring assignment


3.36.130.238


## API 명세서

![api명세서](https://velog.velcdn.com/images/song42782/post/9c9ea7c9-2517-4a2c-a9de-7cf11feedddb/image.png)

## ERD

![ERD](https://velog.velcdn.com/images/song42782/post/bf23da6e-42f4-4e99-ad0a-3bd92f2c7eb0/image.png)



## 특징

### 1. Spring Data JPA

``` java
public interface CommentRepository extends JpaRepository<Comment, Long>


```

### 2. Spring Security

``` java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/posts").hasAnyRole("USER", "ADMIN")
                .antMatchers("/comments").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

```

### 3. ControllerAdvice 와 사용자 정의 예외
``` java
@RestControllerAdvice
public class MyControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BasicResponse illegalExHandle(IllegalArgumentException e) {
        log.error("IllegalArgumentException ", e);
        return new BasicResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

```

``` java
public class UnauthorizedAccessException extends RuntimeException

if (post.getAuthor().getId() != userByToken.getId())
	throw new UnauthorizedAccessException("작성자만 수정할 수 있습니다");


```

