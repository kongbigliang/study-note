package com.kongbig.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器
 * 加入到Spring容器
 *
 * @author lianggangda
 * @date 2019/12/18 10:07
 */
@Component
public class UserLoginZuulFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        // 该过滤器需要执行
        return true;
    }

    @Override
    public Object run() {
        // 编写业务逻辑
        // 判断请求中是否有token，如果有，认为就是已经登录；如果没有，就认为是非法请求，响应401；
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            // 过滤该请求，不对其进行路由
            requestContext.setSendZuulResponse(false);
            // 设置响应状态码
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("token is empty!!");
            return null;
        }
        return null;
    }

    @Override
    public String filterType() {
        // 设置过滤器类型为：pre
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 设置执行顺序为0
        return 0;
    }

}
