package com.sameperson.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class CommentResource {

    @GET
    public String test() {
        return "new subresource";
    }

    @GET
    @Path("/{commentId}")
    public String test2(@PathParam("commentId") long commentId) {
        return "mtd: " + commentId;
    }

}
