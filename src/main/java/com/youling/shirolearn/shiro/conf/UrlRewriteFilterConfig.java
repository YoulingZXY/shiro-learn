package com.youling.shirolearn.shiro.conf;

import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

@Configuration
public class UrlRewriteFilterConfig extends UrlRewriteFilter {

    private static final String URL_REWRITE = "classpath:/urlrewrite.xml";

    //从给定位置注入资源
    @Value(URL_REWRITE)
    private Resource resource;

    //覆盖loadUrlRewriter方法，并编写自己的实现
    protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
        try {
            //使用注入的资源创建一个UrlRewrite Conf对象
            Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(),
                    "@@traceability@@");
            checkConf(conf);
        } catch (IOException ex) {
            throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
        }
    }
}
