package com.sameperson.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

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

    @GET
    @Path("context")
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
        String path = uriInfo.getAbsolutePath().toString();
        String cookies = httpHeaders.getCookies().toString();
        return "Path: " + path + " | Cookies: " + cookies;
    }

}
