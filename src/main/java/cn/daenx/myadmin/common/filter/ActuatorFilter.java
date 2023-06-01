package cn.daenx.myadmin.common.filter;


import cn.daenx.myadmin.common.vo.Result;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Actuator过滤器
 */
public class ActuatorFilter implements Filter {
    private String actuatorToken;

    public ActuatorFilter(String actuatorToken) {
        this.actuatorToken = actuatorToken;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String token = req.getHeader("token");
        if (ObjectUtil.isNotEmpty(actuatorToken)) {
            if (ObjectUtil.isEmpty(token) || !actuatorToken.equals(token)) {
                resp.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
                resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                Result result = Result.error(HttpStatus.HTTP_UNAUTHORIZED, "非法访问");
                resp.getWriter().print(JSONUtil.toJsonStr(result));
                return;
            }
        }
        if (request instanceof HttpServletRequest) {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
