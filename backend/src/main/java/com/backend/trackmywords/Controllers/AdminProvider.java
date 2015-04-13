package com.backend.trackmywords.Controllers;

import com.backend.trackmywords.QueryService;
import com.backend.trackmywords.beans.Lyrics;
import com.backend.trackmywords.beans.Song;

import javax.management.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Path("/admin")
public class AdminProvider {

    @GET
    @Path("/view")
    @Produces("application/json")
    public Response getInvalidResponse() {
        return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Please provide a page number as a path parameter.\"}").build();
    }

    @GET
    @Path("/view/{page}")
    @Produces("application/json")
    public Response getSongView(@PathParam("page") String rawPage, @QueryParam("title") String title, @QueryParam("letter")String letter, @QueryParam("sort") String sort, @QueryParam("order") String order) {
        if (!QueryService.validIntString(rawPage)) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"No valid path param was provided in request.\"}").build();
        }
        if (!QueryService.validString(sort)) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"No valid sort parameter was provided in request.\"}").build();
        }
        try {
            int pageNum = Integer.parseInt(rawPage) - 1;
            String filter = "";
            if (QueryService.validString(title)) {
                filter = "Song.title like '%" + title + "%'";
            }else if(QueryService.validString(letter)){
                filter = "Song.title like '" + letter + "%'";
            }
            if (QueryService.validString(sort)) {
                filter += String.format(" group by Song.title order by Song.%s %s", sort, (QueryService.validString(order)) ? order : "desc");
            }
            int count = QueryService.getSongCount(filter);
            int pages = (int) Math.floor(count / QueryService.RESULTS_PER_PAGE) + 1;
            String entity = QueryService.getSongsBetween((int) (pageNum * QueryService.RESULTS_PER_PAGE), pages, filter);
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(entity).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/update")
    @Produces("application/json")
    public Response updateSongGet() {
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Please provide a song id as a path parameter.\"}").build();
    }

    @POST
    @Path("/update")
    @Produces("application/json")
    public Response updateSongPost() {
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Please provide a song id as a path parameter.\"}").build();
    }

    @GET
    @Path("/update/{id}")
    @Produces("application/json")
    public Response updateSongIDGet(){
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Only post requests are allowed for updating a song.\"}").build();
    }

    @POST
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response updateSong(@PathParam("id") String id, @FormParam("title") String title, @FormParam("lyrics") String lyrics, @FormParam("artist")String artist, @FormParam("album")String album) {
        if (!QueryService.validIntString(id)) {
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"The provided path id is not a valid integer value.\"}").build();
        }
        System.out.println(title);
        System.out.println(lyrics);
        if(!QueryService.validString(title) || !QueryService.validString(lyrics)){
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\":\"The provided title or lyric value is not a valid string value.\"}").build();
        }
        try {
            Lyrics song = new Lyrics();
            song.setId(Integer.parseInt(id));
            song.setSongTitle(title);
            song.setSongLyrics(lyrics);
            song.setArtistName(artist);
            song.setAlbumTitle(album);
            boolean success = QueryService.updateSong(song);
            String res = String.format("{\"status\": \"%s\"}", (success) ? "Success" : "Failure");
            return Response.status(Response.Status.OK).entity("{\"status\": \"Successful\"}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/insert")
    @Produces("application/json")
    public Response insertSongGet(){
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Only post requests are allowed at this point.\"}").build();
    }

    private static String[] FORM_KEYS = {"title", "lyrics", "release_date", "album", "artist", "draft"};
    private static String[] KEY_TYPES = {"String", "String", "Date", "String", "String", "Boolean"};

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/insert")
    @Produces("application/json")
    public Response insertSong(MultivaluedMap<String, String> map){
        for(int i=0; i<FORM_KEYS.length; i++){
            String key = FORM_KEYS[i];
            if(!map.containsKey(key) || map.get(key).size() == 0){
                return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Required key not found: " + key + "\"}").build();
            }
            String value = map.get(key).get(0);
            boolean valid = true;
            switch(KEY_TYPES[i]){
                case "String":
                    valid = QueryService.validString(value);
                    break;
                case "Date":
                    valid = QueryService.validDateString(value);
                    break;
                case "Boolean":
                    valid = QueryService.validBooleanString(value);
                    break;
            }
            if (!valid) {
                return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity(String.format("{\"error\": \"The value '%s' is not valid for the value '%s'.\"}", key, value)).build();
            }
        }
        try {
            String status = (QueryService.insertSong(map)) ? "Success" : "Failure";
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity("{\"status\": \"" + status + "\"}").build();
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/delete")
    @Produces("application/json")
    public Response deleteSongNoId() {
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"No id was provided as a path parameter in request.\"}").build();
    }

    @POST
    @Path("/delete/{id}")
    @Produces("application/json")
    public Response deleteSongPost(@PathParam("id")String id){
        return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"Only get requests are allowed at this point.\"}").build();
    }

    @GET
    @Path("/delete/{id}")
    @Produces("application/json")
    public Response deleteSong(@PathParam("id")String id){
        if(!QueryService.validIntString(id)){
            return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"The provided id is not a valid id value.\"}").build();
        }
        try {
            QueryService.deleteSong(Integer.parseInt(id));
            return Response.ok("{}").header("Access-Control-Allow-Origin", "*").build();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
