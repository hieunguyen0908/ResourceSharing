/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.servlets;

import hieunnm.daos.UserDAO;
import hieunnm.dtos.UserDTO;
import hieunnm.dtos.UserErrDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author PC
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    Pattern idPattern = Pattern.compile("[A-Za-z0-9]{2,30}");
    Pattern namePattern = Pattern.compile("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s.]{1,100}$");
//    Pattern emailPattern = Pattern.compile("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s.]{1,64}$");
    Pattern addressPattern = Pattern.compile("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s.]{0,200}$");
    Pattern numberPattern = Pattern.compile("[0-9]{0,10}");
    Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private static final String ACTIVE_PAGE = "activeAccount.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final String REGISTER_PAGE = "register.jsp";

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
            throws ServletException, IOException, NullPointerException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        UserErrDTO userError = new UserErrDTO("", "", "", "", "", "", "");
        try {
            String userID = request.getParameter("userID");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");
            java.sql.Date createDate = java.sql.Date.valueOf(LocalDate.now());
            int defaultRoleID = 3; // Employee
            boolean isDeleted = false;
            String defaultStatus = "new";
            int verifyCode = 0;
            boolean flag = true;

            if (userID.trim().isBlank()) {
                flag = false;
                userError.setUserIDError("UserID must not empty");
            }
            if (!idPattern.matcher(userID).matches()) {
                flag = false;
                userError.setUserIDError("User ID contains alphabet chars and range 1-50 chars");
            }

            if (!namePattern.matcher(userName).matches()) {
                flag = false;
                userError.setUserIDError("User Name contains alphabet chars and range 2-30 chars");
            }
            if (userName.length() > 30 || userName.length() < 1 || userName.isBlank()) {
                flag = false;
                userError.setUserNameError("User Name must be [1-30] and not empty");
            }

            if (!password.equals(confirmPassword)) {
                flag = false;
                userError.setConfirmPasswordError("2 password khong giong nhau!");
            }
            if (phone.isBlank()) {
                flag = false;
                userError.setPhoneError("Phone number must  not empty");
            }
            if (!numberPattern.matcher(phone).matches()) {
                flag = false;
                userError.setPhoneError("Phone number must be [1-11]");

            }
            if (address.length() > 200 || address.length() < 1 || address.isBlank()) {
                flag = false;
                userError.setAddressError("Address must be [1-200] and not empty");
            }
            if (email.trim().isBlank()) {
                flag = false;
                userError.setEmailError("Email must not empty");
            }
            if (!emailPattern.matcher(email).matches()) {
                flag = false;
                userError.setEmailError("Email wrong format (Ex: example@gmail.com)");
            }

            if (flag) {
                UserDTO user = new UserDTO(userID, password, userName, phone, address, createDate,
                        verifyCode, email, defaultStatus, defaultRoleID);

                UserDAO dao = new UserDAO();

                //
                HttpSession session = request.getSession();
                session.setAttribute("USER", user);
                //
                
                dao.insertNew(user);
                url = ACTIVE_PAGE;
            } else {
                url = REGISTER_PAGE;
                request.setAttribute("MSG_ERROR", userError);

            }
        } catch (Exception ex) {
            if (ex.toString().contains("duplicate")) {
                url = REGISTER_PAGE;
                userError.setUserIDError("UserID duplicate!");
                request.setAttribute("MSG_ERROR", userError);
            } else {
                LOGGER.error(ex.getMessage());
                request.setAttribute("ERROR", "Some thing was wrong");
                url = ERROR_PAGE;
            }
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
