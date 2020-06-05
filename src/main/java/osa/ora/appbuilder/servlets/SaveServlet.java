/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.servlets;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import osa.ora.appbuilder.beans.Application;
import osa.ora.appbuilder.beans.Component;
import osa.ora.appbuilder.beans.Connection;
import osa.ora.appbuilder.config.GenConfig;
import osa.ora.appbuilder.config.IGenerator;

/**
 *
 * @author Osama Oransa
 */
@WebServlet(name = "SaveServlet", urlPatterns = {"/SaveServlet"})
public class SaveServlet extends HttpServlet {
    //generation configuration
    private static GenConfig genConfig=GenConfig.getInstance();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String type=request.getParameter("type");
        //get the path to images to remove it from the save file
        String path="";
        if(request.getProtocol().startsWith("HTTP/", 0)){
            path="http://";
        }else{
            path="https://";
        }
        path+=request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        //System.out.println("URL="+path);
        if(type==null) type="1";
        //save the json content of the diagram
        String data=request.getParameter("data");
        //conver path into relative path images/image_name.png
        data=data.replaceAll(path, "");
        if("1".equals(type)){
            response.setHeader("Content-disposition", "attachment; filename=app.json");
            response.setContentType("application/json");
            OutputStream o = response.getOutputStream();
            o.write(data.getBytes());
            o.flush();
            o.close();
        //generate the code ..   
        //TODO: logic can be improved later
        }else{
            Application app=new Gson().fromJson(data, Application.class);
            System.out.println("app:"+app.getGroupId());
            System.out.println("Components:"+app.getComponents().length);
            System.out.println("Connections:"+app.getConnections().length);
            //invoke generator for type
            for (Component comp : app.getComponents()) {
                IGenerator gen=genConfig.getGeneratorForType(comp.getType());               
                if(gen!=null){
                    gen.generateArtifact(app.getGroupId(), app.getVersion(), app.getBuild(),
                            comp.getCaption(),comp.getDependencies());
                }
            }    
            //TODO: return to the user the generated code in zip file
            //or commit it to a git repository
            OutputStream o = response.getOutputStream();
            o.write(new Gson().toJson(app).getBytes());
            o.flush();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
