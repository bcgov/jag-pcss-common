package ca.bc.gov.open.pcss.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(
        faultCode = FaultCode.CLIENT,
        faultStringOrReason = "A report name is a required parameter")
public class BadRequestException extends Exception {
    public BadRequestException() {
        super();
    }
}
