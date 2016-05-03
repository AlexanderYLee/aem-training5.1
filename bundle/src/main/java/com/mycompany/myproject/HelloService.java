package com.mycompany.myproject;

import com.mycompany.myproject.impl.HelloServiceImpl;
import org.apache.felix.scr.annotations.Property;

/**
 * A simple service interface
 */
public interface HelloService {

    String getCompanyName();
}