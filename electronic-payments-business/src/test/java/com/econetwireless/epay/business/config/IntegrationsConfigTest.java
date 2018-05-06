package com.econetwireless.epay.business.config;

import com.econetwireless.in.webservice.IntelligentNetworkService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class IntegrationsConfigTest {
    private static final String NAMESPACE_KEY = "configs.econetwebservice.ws.namespace";
    private static final String NAMESPACE_VALUE = "http://service.soap.in.econetwireless.com/";
    private static final String SERVICE_KEY = "configs.econetwebservice.ws.serviceName";
    private static final String SERVICE_VALUE = "IntelligentNetworkService";
    private static final String PORT_KEY = "configs.econetwebservice.ws.portName";
    private static final String PORT_VALUE = "IntelligentNetworkPort";
    private static final String INTERFACE_VALUE = "configs.econetwebservice.ws.serviceInterface";
    public static final String WRONG_INTERFACE_VALUE = INTERFACE_VALUE + "extras";
    private static final String INTERFACE_KEY = "com.econetwireless.in.webservice.IntelligentNetworkService";
    private static final String WSDL_KEY = "configs.econetwebservice.ws.wsdl";
    private static final String WSDL_VALUE = "wsdls/IntelligentNetworkService.wsdl";
    private static final String ENDPOINT_KEY = "configs.econetwebservice.ws.endpoint.addres";
    private static final String ENDPOINT_VALUE = "http://localhost:8888/intelligent-network-api/IntelligentNetworkService?wsdl";
    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put(NAMESPACE_KEY, NAMESPACE_VALUE);
        map.put(SERVICE_KEY, SERVICE_VALUE);
        map.put(PORT_KEY, PORT_VALUE);
        map.put(INTERFACE_KEY, INTERFACE_VALUE);
        map.put(ENDPOINT_KEY, ENDPOINT_VALUE);
        map.put(WSDL_KEY, WSDL_VALUE);

    }

    private AnnotationConfigWebApplicationContext context;
    private MockEnvironment env;

    @Before
    public void init() {
        env = new MockEnvironment();
        map.forEach((key, value) -> {
            env.setProperty(key, value);
        });
    }

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void testIntegrationsConfigBeanCreationIsNotNull() {

        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setEnvironment(env);
        this.context.setServletContext(new MockServletContext());
        this.context.register(IntegrationsConfig.class);
        this.context.refresh();

        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);
        assertNotNull(integrationsConfig);

    }

    @Test
    public void testEnvironmentIsAutowiredAndNotNull() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setEnvironment(env);
        this.context.setServletContext(new MockServletContext());
        this.context.register(IntegrationsConfig.class);
        this.context.refresh();

        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);

        assertNotNull(integrationsConfig);
        assertNotNull(integrationsConfig.getEnv());

    }


    @Test
    public void testEnvironmentContainsTheProperties() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setEnvironment(env);
        this.context.setServletContext(new MockServletContext());
        this.context.register(IntegrationsConfig.class);
        this.context.refresh();

        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);

        assertNotNull(integrationsConfig);
        assertNotNull(integrationsConfig.getEnv());

        map.forEach((key, value) -> {
            assertEquals(value, integrationsConfig.getEnv().getProperty(key));
        });

        this.context.close();
    }

    @Test
    public void testJaxWsPortProxyFactoryBeanBeanCreation() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setEnvironment(env);
        this.context.setServletContext(new MockServletContext());
        this.context.register(IntegrationsConfig.class);
        this.context.refresh();

        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);

        assertNotNull(integrationsConfig);

        JaxWsPortProxyFactoryBean factoryBean = integrationsConfig.intelligentNetworkService();

        assertNotNull(factoryBean);

        assertEquals(factoryBean.getServiceName(), SERVICE_VALUE);
        assertEquals(factoryBean.getNamespaceUri(), NAMESPACE_VALUE);
        assertEquals(factoryBean.getServiceName(), SERVICE_VALUE);
        assertEquals(factoryBean.getEndpointAddress(), ENDPOINT_VALUE);
        assertEquals(factoryBean.getPortName(), PORT_VALUE);

    }


    @Test
    public void testIntelligentNetworkServiceBeanCreation() {
        this.context = new AnnotationConfigWebApplicationContext();
        this.context.setEnvironment(env);
        this.context.setServletContext(new MockServletContext());
        this.context.register(IntegrationsConfig.class);
        this.context.refresh();

        IntegrationsConfig integrationsConfig = this.context.getBean(IntegrationsConfig.class);

        assertNotNull(integrationsConfig);

        IntelligentNetworkService intelligentNetworkService = this.context.getBean(IntelligentNetworkService.class);

        assertNotNull(intelligentNetworkService);

    }

}