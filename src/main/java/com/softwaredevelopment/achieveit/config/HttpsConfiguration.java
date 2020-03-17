package com.softwaredevelopment.achieveit.config;


/**
 * a
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-03-01 21:42
 */
//@Configuration
//public class HttpsConfiguration {
//    @Value("${http-port}")
//    private int port;
//
//    @Value("${server.port}")
//    private int sslPort;
//
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(redirectConnector());
//        return tomcat;
//    }
//
//    private Connector redirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(port);
//        connector.setSecure(false);
//        connector.setRedirectPort(sslPort);
//        return connector;
//    }
//}
