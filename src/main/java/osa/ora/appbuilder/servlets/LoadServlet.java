/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osa.ora.appbuilder.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 *
 * @author Osama Oransa
 */
@WebServlet(name = "LoadServlet", urlPatterns = {"/LoadServlet"})
@MultipartConfig
public class LoadServlet extends HttpServlet {
    private static final String ACTION_LOAD_FILE="1";
    private static final String ACTION_BACK_TO_DESIGNER="2";
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
        String type=request.getParameter("type2");
        if(type==null) type=ACTION_BACK_TO_DESIGNER;
        if(ACTION_LOAD_FILE.equals(type)){
            try {
                Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
                String fileName = filePart.getHeader("content-disposition");
                if(!fileName.isEmpty()){
                    fileName=fileName.substring(fileName.indexOf("filename=\"")+10,fileName.lastIndexOf("\""));
                }
                System.out.println("FileName="+fileName);
                InputStream inputStream = filePart.getInputStream();            
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int read;
                while ((read = inputStream.read(buf)) != -1) {
                    out.write(buf, 0, read);
                }
                String data=out.toString();
                data=data.replaceAll("\n", "").replaceAll("\r", "");
                request.getSession().setAttribute("NAME",  fileName);
                request.getSession().setAttribute("DATA",  data);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (IOException e) {
                e.printStackTrace();
                request.getSession().setAttribute("ERROR_MSG",  e.getMessage());
                request.getRequestDispatcher("error.jsp");
            }
        }else if(ACTION_BACK_TO_DESIGNER.equals(type)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);            
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
