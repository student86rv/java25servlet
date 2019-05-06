package com.company.api;

import com.company.dao.UserDao;
import com.company.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class TestApi {

    private Gson gson = new GsonBuilder().create();
    private UserDao userDao = new UserDao();

    @GET
    @Path("test")
    public Response sayTest() {
        User testUser = new User("TestUser", "1980-01-01");
        String result = gson.toJson(testUser);
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_all")
    public Response getAllUsers() {
        String result;
        try {
            result = gson.toJson(userDao.getAllUsers());
        } catch (Exception e) {
            result = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get/{id}")
    public Response get(@PathParam("id") int id) {
        String result;
        try {
            result = gson.toJson(userDao.get(id));
        } catch (Exception e) {
            result = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_by_name/{name}")
    public Response getByName(@PathParam("name") String name) {
        String result;
        try {
            result = gson.toJson(userDao.getByName(name));
        } catch (Exception e) {
            result = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("get_by_age/{from_age}/{to_age}")
    public Response getByAge(@PathParam("from_age") int fromAge,
                             @PathParam("to_age") int toAge) {
        String result;
        try {
            result = gson.toJson(userDao.getUsersByAge(fromAge, toAge));
        } catch (Exception e) {
            result = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Path("add")
    public Response addUser(String inputJson) {
        String resultText;
        try {
            User user = gson.fromJson(inputJson, User.class);
            userDao.add(user);
            resultText = "Add new User: " + user;
        } catch (Exception e) {
            resultText = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(resultText).build();
    }

    @PUT
    @Path("update")
    public Response update(String inputJson) {
        String resultText;
        try {
            User user = gson.fromJson(inputJson, User.class);
            userDao.update(user);
            resultText = "Updated: " + user;
        } catch (Exception e) {
            resultText = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(resultText).build();
    }

    @PUT
    @Path("remove")
    public Response remove(String inputJson) {
        String resultText;
        try {
            User user = gson.fromJson(inputJson, User.class);
            userDao.remove(user);
            resultText = "Removed: " + user;
        } catch (Exception e) {
            resultText = "Can not decode message from client";
        }
        return Response.status(Response.Status.OK).entity(resultText).build();
    }
}
