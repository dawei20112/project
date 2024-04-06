package com.example.demo.scurityAndJwt.security.jwt;

import com.example.demo.scurityAndJwt.security.CustomerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFliter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
//        private final CustomerUserDetailsService customerUserDetailsService;
    private final CustomerUserDetailsService userDetailsService;

    public JWTAuthenticationFliter(JWTUtil jwtUtil, CustomerUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
//        this.customerUserDetailsService = customerUserDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        //todo 一定要注意判断语句的定义
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
//        System.out.println(authHeader.substring(7));
        String subject = jwtUtil.getSubject(jwt);
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            customerUserDetailsService.
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            //todo 数据没有办法输出
            if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //todo 查询到了数据但是无法传递到出去
        filterChain.doFilter(request,response);

    }
}
