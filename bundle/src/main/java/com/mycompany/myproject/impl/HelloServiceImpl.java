package com.mycompany.myproject.impl;

import com.mycompany.myproject.HelloService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;

import java.util.Dictionary;

/**
 * One implementation of the {@link HelloService}. Note that
 * the repository is injected, not retrieved.
 */
@Service(HelloService.class)
@Component(immediate=true, metatype = true)
public class HelloServiceImpl implements HelloService {

    public static final String NAMING_PROP="com.mycompany.myproject.name";
    
    public final static String DEFAULT_NAME="Geometrio, LLC";
    
    @Property(name=NAMING_PROP, label="Company name", value=DEFAULT_NAME)
    private static String companyName;
    
    @Activate
    @Modified
    protected void activate(ComponentContext ctx){
        Dictionary<String, Object> properties = ctx.getProperties();
        companyName = (String)properties.get(NAMING_PROP);
    }

    public String getCompanyName() {
        return companyName;
    }
}
