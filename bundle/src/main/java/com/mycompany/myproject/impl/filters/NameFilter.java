package com.mycompany.myproject.impl.filters;

import com.adobe.acs.commons.util.BufferingResponse;
import com.mycompany.myproject.HelloService;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* Simple servlet filter component that logs incoming requests.
*/
@SlingFilter(generateComponent = true, generateService = true, order = -700, scope = SlingFilterScope.REQUEST)
@Properties({
        @Property(name = "sling.filter.pattern", value=".*/geometrixx/(.*html$|json$)", propertyPrivate = false)
})
public class NameFilter implements Filter {
    
    private Logger logger = LoggerFactory.getLogger(NameFilter.class);

    @Reference
    private HelloService helloService;

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
        if (!(servletRequest instanceof HttpServletRequest)
                || !(servletResponse instanceof HttpServletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        final BufferingResponse capturedResponse = new BufferingResponse(response);

        filterChain.doFilter(request, capturedResponse);

        if (capturedResponse.getContentType()!=null){
            String content = capturedResponse.getContents();
            String companyName = helloService.getCompanyName();
            logger.debug(capturedResponse.getContentType());
            logger.debug("company name is " + companyName);
            if (content!=null){
                if (capturedResponse.getContentType().contains("text"))
                    response.getWriter().write(content.replaceAll("Geometrixx", companyName));
                else
                    response.getWriter().write(content);
            }
        }
    }

    public void destroy() {
    }

}
