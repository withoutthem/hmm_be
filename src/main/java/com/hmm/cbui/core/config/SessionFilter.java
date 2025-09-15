/* (C) 2025 HMM Corp. All rights reserved. */
package com.hmm.cbui.core.config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    //    HttpSession session = request.getSession(false);
    //        if (session != null && session.getAttribute("userId") != null) {
    //            // 세션 기반 인증 처리
    //            String userId = (String) session.getAttribute("userId");
    //            UsernamePasswordAuthenticationToken auth = new
    // UsernamePasswordAuthenticationToken(userId, null, authorities);
    //            SecurityContextHolder.getContext().setAuthentication(auth);
    //        }

    filterChain.doFilter(request, response);
  }
}
