package com.tommy.embed;

import java.util.Optional;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class BootStrap {
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    public static final String TEST_SERVLET = "TestServlet";

    public static void run() throws Exception{
        System.out.println("Starting tomcat...");

        String contextPath = "/";
        String appBase = ".";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port.orElse("8090") ));
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);
        Context context = tomcat.addContext(contextPath, appBase);

        tomcat.addServlet(contextPath, TEST_SERVLET, new TestServlet());
        context.addServletMappingDecoded("/*", TEST_SERVLET);

        tomcat.start();
        tomcat.getServer().await();
    }
}
