package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter  extends OncePerRequestFilter {
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Extraire le JWT de la requete HTTP en appelant la methode getJWTFromRequest()
        String token = getJWTFromRequest(request);
        //Vérifie si le jeton existe et si celui-ci est valide en utilisant la methode validateToken() du tokenGenerator
        if(StringUtils.hasText(token) && jwtGenerator.validationToken(token)) {
            //Extraire le nom d'utilisateur du jeton JWT
            String username = jwtGenerator.getUsernameFromJwt(token);
            // Charger les détails de l'utilisateur à partir de la base de données
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            // Créer un token d'authentification basé sur les détails de l'utilisateur et les autorisations (roles) associés a l'utilisateur
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //Mettre l'authentification dans le contexte de sécurité de spring ce qui indique à spring que l'tulisateur est authentifié
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }
    //Méthode pour extraire le JWT depuis l'en-tete "Authorization" de la requete HTTP
    private String getJWTFromRequest(HttpServletRequest request) {
        //récuperer l'en-tete authorization de la requete
        String bearerToken= request.getHeader("Authorization");
        // Si l'en-tete contient un jeton e qu'il commence par bearer on extait le jeton sans le préfie bearer
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null ;
    }
}
