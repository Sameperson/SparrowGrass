package com.sameperson.exception;

import com.sameperson.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//commented out to disable Jersey register of the mapper
//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 500, "null");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }

}
