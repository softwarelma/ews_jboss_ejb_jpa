package com.softwarelma.ews.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class EwsSoapHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // for response message only, true for outbound messages, false for inbound
        if (!isRequest) {
            try {
                SOAPMessage soapMsg = context.getMessage();
                // SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
                // SOAPHeader soapHeader = soapEnv.getHeader();
                OutputStream out = new ByteArrayOutputStream();
                soapMsg.writeTo(out);
                String xml = out.toString();
                System.out.println(xml);
            } catch (SOAPException e) {
                System.err.println(e);
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        // continue other handler chain
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

}
