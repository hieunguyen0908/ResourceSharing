/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import hieunnm.daos.ResourceDAO;
import hieunnm.dtos.ResourceDTO;
import hieunnm.daos.CategoryDAO;
import hieunnm.dtos.CategoryDTO;
import java.text.ParseException;
import java.util.logging.Level;

/**
 *
 * @author PC
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);
    private static final String ERROR_PAGE = "error.jsp";
    private static final String SEARCH_PAGE = "search.jsp";
    private static final int    PAGE_SIZE = 20; // So luong RESOURCE hien thi 

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR_PAGE;
        try {
            // get ALL Categories
            List<CategoryDTO> cats = getAllCategory();
            request.setAttribute("CATE_LIST", cats);

            // data
            String usingDate = request.getParameter("txtUsingDate"); // YYYY-MM-DD
            String name = request.getParameter("txtName");
            String category = request.getParameter("categories");

            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 0;

            ResourceDAO resourceDao = new ResourceDAO();

            int countResource = resourceDao.countResource();
            int paging = (int) Math.ceil(countResource / PAGE_SIZE);

            if (usingDate == null && name == null && category == null) {
                // get all
                List<ResourceDTO> listResource = resourceDao.search("", null, null, page * PAGE_SIZE, PAGE_SIZE);
                if (listResource != null) {
                    request.setAttribute("SEARCH_RESULT", listResource);
                    request.setAttribute("paging", paging);

                }

            } else {
                try {
                    if (usingDate.equals("")) {
                        usingDate = null;
                    }
                    // category data
                    if (category.equals("all") || category.isEmpty()) {
                        category = null;
                    }

                    // Search Data
                    List<ResourceDTO> listResource = resourceDao.search(name, category, usingDate, page * PAGE_SIZE, PAGE_SIZE);
                    if (listResource != null) {
                        request.setAttribute("SEARCH_RESULT", listResource);
                        request.setAttribute("paging", paging);

                    }

                } catch (NullPointerException e) {
                    request.setAttribute("MSG_ERROR", "Something was wrong here");
                } catch (SQLException e) {
                    request.setAttribute("MSG_ERROR", "Wrong using date format: dd/mm/yyyy");
                }
            }

            url = SEARCH_PAGE;
        } catch (NullPointerException ex) {
            LOGGER.error(ex.getMessage());
            request.setAttribute("ERROR", "Something was wrong here");
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    private List<CategoryDTO> getAllCategory() throws SQLException, NamingException {
        CategoryDAO cateDao = new CategoryDAO();
        return cateDao.getAllCate();
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
        try {
            try {
                processRequest(request, response);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            try {
                processRequest(request, response);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
