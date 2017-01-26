package com.sameperson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

    @GET
    @Path("annotations")
    public String getParamWithAnnotations(@MatrixParam("param") String matrixParam,
                                          @HeaderParam("headerValue") String headerParam,
                                          @CookieParam("cookieParam") String cookieParam){
        return "Matrix param "
                + matrixParam
                + ", header param "
                + headerParam
                + ", cookie param "
                + cookieParam;
    }

}
