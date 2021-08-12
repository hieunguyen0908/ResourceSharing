/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import hieunnm.dtos.ResourceDTO;
import hieunnm.object.Cart;

/**
 *
 * @author PC
 */
@WebServlet(name = "ConfirmCartServlet", urlPatterns = {"/ConfirmCartServlet"})
public class ConfirmCartServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ConfirmCartServlet.class);
    private static final String ERROR_PAGE = "error.jsp";
    private static final String SEARCH_PAGE = "SearchServlet";
    private static final String VIEWCART_PAGE = "viewCart.jsp";

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
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            Map<String, ResourceDTO> invalidResourceQuantity = cart.checkQuantity();

            if (invalidResourceQuantity.isEmpty()) {
                if (cart.confirm()) {
                    request.setAttribute("MSG_SUCCESS", "Booking resource successful");
                    url = SEARCH_PAGE;
                } else {
                    request.setAttribute("MSG_ERROR", "Something was wrong.");
                    url = VIEWCART_PAGE;
                }

            } else {
                String invalidErr = "";
                for (Map.Entry<String, ResourceDTO> e : invalidResourceQuantity.entrySet()) {
                    ResourceDTO resource = e.getValue();
                    if (resource.getQuantity() > 0) {
                        invalidErr += e.getValue().getName() + " has only " + e.getValue().getQuantity() + "\n";
                    } else if (resource.getQuantity() == 0) {
                        invalidErr += e.getValue().getName() + " is out of stock\n";
                    } else {
                        invalidErr += e.getValue().getName() + " not exist\n";
                    }
                }
                request.setAttribute("MSG_ERROR", invalidErr);
                url = VIEWCART_PAGE;
            }
        } catch (SQLException | NamingException | NullPointerException e) {
//            LOGGER.error(e.getMessage());
//            url = ERROR_PAGE;
//            request.setAttribute("ERROR", "Something was wrong here");
            System.out.println(e);

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
