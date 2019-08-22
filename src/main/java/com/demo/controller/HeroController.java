package com.demo.controller;

import com.demo.entity.Hero;
import com.demo.model.HeroModel;
import com.demo.util.JsonApiObject;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class HeroController extends HttpServlet {
    HeroModel model = new HeroModel();
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        if (name != null && name.length() > 0) {
            Hero hero = model.findByName(name);
            if (hero != null){
                JsonApiObject jsonApiObject = new JsonApiObject(
                        "Success",
                        HttpServletResponse.SC_OK
                );
                resp.getWriter().print(gson.toJson(jsonApiObject));
            }else {
                JsonApiObject jsonApiObject = new JsonApiObject(
                        "Not Found",
                        HttpServletResponse.SC_NOT_FOUND
                );
                resp.getWriter().print(gson.toJson(jsonApiObject));
            }
            resp.getWriter().print(gson.toJson(hero));
        } else {
            List<Hero> list = model.findAll();
            JsonApiObject jsonApiObject = new JsonApiObject(
                    "OK",
                    HttpServletResponse.SC_OK,
                    list
            );
            resp.getWriter().print(gson.toJson(jsonApiObject));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        BufferedReader reader = req.getReader();
        Hero hero = new Gson().fromJson(reader, Hero.class);

        Hero hero1 = new Hero();
        hero1.setName(hero.getName());
        hero1.setImage(hero.getImage());
        hero1.setDescription(hero.getDescription());
        hero1.setStatus(1);
        boolean check = model.save(hero1);
        if (check == true) {
            JsonApiObject jsonApiObject = new JsonApiObject(
                    "Success",
                    HttpServletResponse.SC_OK
            );
            resp.getWriter().print(gson.toJson(jsonApiObject));
        } else {
            JsonApiObject jsonApiObject = new JsonApiObject(
                    "Add game Failed",
                    HttpServletResponse.SC_BAD_REQUEST
            );
            resp.getWriter().print(gson.toJson(jsonApiObject));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setHeader("Cache-Control", "nocache");
        resp.setCharacterEncoding("utf-8");
        Hero hero = gson.fromJson(req.getReader(), Hero.class);
        String name = req.getParameter("name");
        int status = hero.getStatus();
        if (name != null && name.length() > 0 && status != -1) {
            Hero heroUpdate = model.findByName(name);
            if (heroUpdate != null) {
                heroUpdate.setName(hero.getName());
                heroUpdate.setImage(hero.getImage());
                heroUpdate.setDescription(hero.getDescription());
                boolean check = model.save(heroUpdate);
                if (check == true) {
                    JsonApiObject jsonApiObject = new JsonApiObject(
                            "Update success",
                            HttpServletResponse.SC_OK,
                            heroUpdate
                    );
                    resp.getWriter().print(gson.toJson(jsonApiObject));
                } else {
                    JsonApiObject jsonApiObject = new JsonApiObject(
                            "Update failed",
                            HttpServletResponse.SC_BAD_REQUEST
                    );
                    resp.getWriter().print(gson.toJson(jsonApiObject));
                }
            } else {
                JsonApiObject jsonApiObject = new JsonApiObject(
                        "Not Found",
                        HttpServletResponse.SC_NOT_FOUND
                );
                resp.getWriter().print(gson.toJson(jsonApiObject));
            }
        } else {
            JsonApiObject jsonApiObject = new JsonApiObject(
                    "Not Found",
                    HttpServletResponse.SC_NOT_FOUND
            );
            resp.getWriter().print(gson.toJson(jsonApiObject));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setHeader("Cache-Control", "nocache");
        resp.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        if (name != null && name.length() > 0) {
            Hero hero = model.findByName(name);
            int status = hero.getStatus();
            if (hero != null && status != -1) {
                hero.setStatus(-1);
                boolean check = model.delete(name);
                if (check == true) {
                    JsonApiObject jsonApiObject = new JsonApiObject(
                            "Delete Success",
                            HttpServletResponse.SC_OK
                    );
                    resp.getWriter().print(gson.toJson(jsonApiObject));
                } else {
                    JsonApiObject jsonApiObject = new JsonApiObject(
                            "Delete failed",
                            HttpServletResponse.SC_BAD_REQUEST
                    );
                    resp.getWriter().print(gson.toJson(jsonApiObject));
                }
            } else {
                JsonApiObject jsonApiObject = new JsonApiObject(
                        "Not Found or deleteds",
                        HttpServletResponse.SC_NOT_FOUND
                );
                resp.getWriter().print(gson.toJson(jsonApiObject));
            }
        } else {
            JsonApiObject jsonApiObject = new JsonApiObject(
                    "Not Found",
                    HttpServletResponse.SC_NOT_FOUND
            );
            resp.getWriter().print(gson.toJson(jsonApiObject));
        }
    }
}
