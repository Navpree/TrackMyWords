package com.backend.trackmywords.Controllers;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Path("/file")
public class FileServer {

    private Response buildResponse(ServletContext context, String fileName, String path) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream(path + fileName)));
        StringBuilder builder = new StringBuilder("");
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String res = builder.toString().trim();
        if (res.equals("")) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("//Could not load file + fileName").build();
        }
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET
    @Produces("text/html")
    @Path("/html/{page}")
    public Response getFile(@Context ServletContext context, @PathParam("page") String page) {
        return buildResponse(context, page, "/WEB-INF/html_pages/views/");
    }

    @GET
    @Produces("text/html")
    @Path("/public/html/{page}")
    public Response getFilePublic(@Context ServletContext context, @PathParam("page") String page) {
        return buildResponse(context, page, "/WEB-INF/html_pages/views/");
    }

    @GET
    @Path("/js/{page}")
    @Produces("text/javascript")
    public Response getScript(@Context ServletContext context, @PathParam("page")String page){
        return buildResponse(context, page, "/WEB-INF/html_pages/public/js/");
    }

    @GET
    @Path("/public/js/{page}")
    @Produces("text/javascript")
    public Response getScriptPublic(@Context ServletContext context, @PathParam("page")String page){
        return buildResponse(context, page, "/WEB-INF/html_pages/public/js/");
    }

    @GET
    @Path("/public/js/controllers/{page}")
    @Produces("text/javascript")
    public Response getController(@Context ServletContext context, @PathParam("page")String page){
        return buildResponse(context, page, "/WEB-INF/html_pages/public/js/controllers/");
    }

    @GET
    @Path("/public/js/libs/{page}")
    @Produces("text/javascript")
    public Response getLib(@Context ServletContext context, @PathParam("page")String page){
        return buildResponse(context, page, "/WEB-INF/html_pages/public/js/libs/");
    }

    @GET
    @Path("/stylesheets/{page}")
    @Produces("text/css")
    public Response getCss(@Context ServletContext context, @PathParam("page")String page){
    return buildResponse(context, page, "/WEB-INF/html_pages/public/css/");
    }

    @GET
    @Path("/public/stylesheets/{page}")
    @Produces("text/css")
    public Response getCssPublic(@Context ServletContext context, @PathParam("page")String page){
        return buildResponse(context, page, "/WEB-INF/html_pages/public/css/");
    }

}
