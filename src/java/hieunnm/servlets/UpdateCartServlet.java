/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.servlets;

import java.io.IOException;
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
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateCartServlet.class);
    private static final String VIEWCART_PAGE = "viewCart.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    /**
     * Processes requests for resourceth HTTP <code>GET</code> and <code>POST</code>
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
            String id = request.getParameter("id");
            String quantityS = request.getParameter("quantity");
            Integer quantity = null;
            try {
                quantity = Integer.parseInt(quantityS);
            } catch (NumberFormatException e) {
                request.setAttribute("MSG_ERROR", "Quantity must be number");
            }
            if (quantity != null) {
                if (quantity < 1) {
                    request.setAttribute("MSG_ERROR", "Quantity must be greater than 0");
                } else {
                    HttpSession session = request.getSession();
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null) {
                        ResourceDTO resource = cart.get(id);
                        if (resource == null) {
                            request.setAttribute("MSG_ERROR", "This resource is not exist in cart");
                        } else {
                            resource.setQuantity(quantity);
                            cart.put(id, resource);
                            request.setAttribute("MSG_SUCCESS", "Update [" + resource.getName() + "] success (quantity: " + resource.getQuantity() + ")");
                        }
                    }
                }
            }
            url = VIEWCART_PAGE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            request.setAttribute("ERROR", e.getMessage());
            url = ERROR_PAGE;
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
