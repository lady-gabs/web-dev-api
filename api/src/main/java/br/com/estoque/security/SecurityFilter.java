package br.com.estoque.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.estoque.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    // Método que será chamado quando o filtro for invocado
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtém o token da requisição
        var token = this.recoverToken(request);
        if (token != null) {

            var login = tokenService.validateToken(token);
            System.out.println(login);
            UserDetails user = usuarioRepository.findByLogin(login);
            if (user != null) {
                System.out.println(user);
                // Obtém todas as informações usadas pelos restantes dos filtros
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // Salva no contexto da autenticação esse usuário
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Caso não encontre token nenhum, não salva nenhum contexto e continua para o próximo filtro
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader == null){
            System.out.println(authHeader);
            return null;
        }
        var aux = authHeader.replace("Bearer ", "");
        System.out.println(aux);
        return aux;
    }

}