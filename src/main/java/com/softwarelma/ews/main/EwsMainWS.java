package com.softwarelma.ews.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.InitialContext;

import com.softwarelma.ews.ejb.EjbEws;
import com.softwarelma.ews.ejb.IEjbEws;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class EwsMainWS {

    private transient Logger logger = Logger.getLogger(EwsMainWS.class.getName());
    private IEjbEws ejbEws;

    public EwsMainWS() {
        this.init();
    }

    /**
     * app-name = the name of the .ear (without the .ear suffix) or the application name configured via application.xml
     * deployment descriptor. If the application isn't packaged in a .ear then there will be no app-name part to the
     * JNDI string.
     * 
     * module-name = the name of the .jar or .war (without the .jar/.war suffix) in which the bean is deployed or the
     * module-name configured in web.xml/ejb-jar.xml of the deployment. The module name is mandatory part in the JNDI
     * string.
     */
    private void init() {
        try {
            InitialContext ctx = new InitialContext();
            // this.ejbEws = (EjbEws) ctx.lookup("java:global/ews/EjbEws!com.softwarelma.ews.ejb.EjbEws");
            // this.ejbEws = (EjbEws) ctx.lookup("java:app/ews/EjbEws!com.softwarelma.ews.ejb.EjbEws");
            this.ejbEws = (EjbEws) ctx.lookup("java:module/EjbEws!com.softwarelma.ews.ejb.EjbEws");

            // String appName = "";// + "/";
            // String moduleName = "ews2" + "/";
            // String distinctName = "";// + "/";
            // String beanName = "EjbEws" + "/";
            // String viewClassName = IEjbEws.class.getName();

            // this.ejbEws = (IEjbEws) ctx
            // .lookup("ejb:" + appName + moduleName + distinctName + beanName + "!" + viewClassName);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "\tinit. Message: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "sayHello")
    public String sayHello(@WebParam(name = "name") String name) {
        try {
            this.logger.info("sayHello - inizio");
            this.logger.info("\tname: " + name);
            this.logger.info("\ttext: " + this.ejbEws.getText());
            if (name == null)
                return "Hello";
            this.logger.info("sayHello - fine");
            return "Hello " + name;
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "\tsayHello, name: " + name + ". Message: " + e.getMessage(), e);
            this.logger.info("sayHello - errore");
            return e.getClass().getName() + ": " + e.getMessage();
        }
    }

}
