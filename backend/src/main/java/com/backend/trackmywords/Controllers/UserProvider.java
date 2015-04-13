package com.backend.trackmywords.Controllers;

import com.backend.trackmywords.QueryService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/query")
public class UserProvider {

    @GET
    @Path("/")
    @Produces("application/json")
    public Response get(@QueryParam("type") String type, @QueryParam("query") String query){
        if(!QueryService.validString(type)){
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"A non blank or whitespace query parameter |type| expected.\"}").build();
        }else if(!QueryService.validString(query)){
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"A non blank or whitespace query parameter |query| expected.\"}").build();
        }
        try {
            String res = "";
            if(type.equals("song")){
                res = QueryService.getSongList(query);
            }else if(type.equals("lyrics")){
                res = QueryService.getSongLyrics(query);
            }else{
                return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("\"{\"error\": \"Type parameter value must be either |song| or |lyrics|.\"}\"").build();
            }
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/")
    @Produces("text/plain")
    public Response post(){
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Only get requests are allowed.\"}").build();
    }
}
