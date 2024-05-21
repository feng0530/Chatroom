package tw.idv.frank.chatroom.common.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.service.jwt.JwtService;
import tw.idv.frank.chatroom.manager.model.dto.ManagerDetails;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.manager.model.entity.Manager;
import tw.idv.frank.chatroom.users.model.dto.UsersDetails;
import tw.idv.frank.chatroom.users.model.dto.UsersRes;
import tw.idv.frank.chatroom.users.model.entity.Users;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = jwt.substring(BEARER_PREFIX.length());
        Claims claims = jwtService.parseToken(jwt);
        if (claims.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = null;
        if ("Users".equals(claims.get("type"))) {
            authentication = getUsersAuthentication(claims);
            refreshUsersToken(request, response, claims);
        }else if ("Manager".equals(claims.get("type"))) {
            authentication = getManagerAuthentication(claims);
            refreshManagerToken(request, response, claims);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private void refreshManagerToken(HttpServletRequest request, HttpServletResponse response, Claims claims) {

        if ("/manager/logout".equals(request.getRequestURI())) {
            return;
        }

        if ("Refresh".equals(claims.getSubject())) {
            ManagerRes managerRes = modelMapper.map(claims.get("detail"), ManagerRes.class);
            LoginRes loginRes = jwtService.generalToken(managerRes);
            responseFunc(response, loginRes);
        }
    }

    private void refreshUsersToken(HttpServletRequest request, HttpServletResponse response, Claims claims) {

        if ("/users/logout".equals(request.getRequestURI())) {
            return;
        }

        if ("Refresh".equals(claims.getSubject())) {
            UsersRes usersRes = modelMapper.map(claims.get("detail"), UsersRes.class);
            LoginRes loginRes = jwtService.generalToken(usersRes);
            responseFunc(response, loginRes);
        }
    }

    private Authentication getManagerAuthentication(Claims claims) {
        Manager manager = modelMapper.map(claims.get("detail"), Manager.class);
        ManagerDetails managerDetails = new ManagerDetails(manager);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                managerDetails,
                null,
                managerDetails.getAuthorities()
        );
        return authentication;
    }

    private Authentication getUsersAuthentication(Claims claims) {
        Users users = modelMapper.map(claims.get("detail"), Users.class);
        UsersDetails usersDetails = new UsersDetails(users);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                usersDetails,
                null,
                null
        );
        return authentication;
    }

    private void responseFunc(HttpServletResponse response, LoginRes loginRes) {
        // 瀏覽器中為 "Accesstoken","Refreshtoken"
        response.setHeader("accessToken", loginRes.getAccessToken());
        response.setHeader("refreshToken", loginRes.getRefreshToken());

        // 增加 CORS的安全清單，未在安全清單中的 Header，無法在跨域請求中被 JS獲取
        response.setHeader("Access-Control-Expose-Headers","Accesstoken,Refreshtoken");
    }
}
