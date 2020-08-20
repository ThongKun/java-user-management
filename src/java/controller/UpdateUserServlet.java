package controller;

import dao.RoleDAO;
import dao.UserDAO;
import entity.Role;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import util.EncryptPassword;
import util.URLConstants;
import validation.ErrorValidationHandling;
import validation.UserValidator;

/**
 *
 * @author HOME
 */
@WebServlet(name = "UpdateUserServlet", urlPatterns = {"/update-user"})
public class UpdateUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserById(Integer.parseInt(id));
            request.setAttribute("selected_user", user);

            RoleDAO roleDAO = new RoleDAO();
            request.setAttribute("ROLES", roleDAO.findAll());

            RequestDispatcher rd = request.getRequestDispatcher(URLConstants.UPDATE_USER_PAGE);
            rd.forward(request, response);
        } else {
            response.sendRedirect(URLConstants.SEARCH_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDao = new RoleDAO();
        UserDAO userDAO = new UserDAO();

        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if (!isMultiPart) {

        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = (List) upload.parseRequest(new ServletRequestContext(request));
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator iter = items.iterator();
            Hashtable params = new Hashtable();
            String filename = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                } else {
                    try {
                        String itemName = item.getName();
                        if (itemName.isEmpty()) {
                            continue;
                        }
                        filename = itemName;
                        String realPath = getServletContext().getInitParameter("upload.location") + "\\images\\" + filename;
                        File savedFile = new File(realPath);
                        item.write(savedFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }//end while

            boolean pass = true; //check if validation is ok
            ErrorValidationHandling errors = new ErrorValidationHandling();

            String name = (String) params.get("name");
            String email = (String) params.get("email");
            String password = (String) params.get("password");
            String roleId = (String) params.get("role");
            String phone = (String) params.get("phone");

            if (!UserValidator.validateName(name)) {
                pass = false;
                errors.setNameError("name format only contains alphabet characters<br/>length of name must be from 1 - 30 characters");
            }
            if (!UserValidator.validatePassword(password)) {
                pass = false;
                errors.setPasswordError("length of password must be from 6 - 30 characters");
            }

            if (!pass) {
                request.setAttribute("errors", errors);
            } else {
                //Pass true -> Validate Success
                User selectedUser = userDAO.findUserByEmail(email);

                User tempUser = new User();
                tempUser.setEmail(email);
                tempUser.setName(name);
                tempUser.setEncryptedPassword(EncryptPassword.encrypt(password));
                if (filename != null && !filename.isEmpty()) {
                    tempUser.setImg(filename);
                } else if (selectedUser.getImg() != null) {
                    tempUser.setImg(selectedUser.getImg());
                }

                tempUser.setPhone(phone);

                Role role = roleDao.findRole(Integer.parseInt(roleId));
                tempUser.setRole(role);
                userDAO.update(tempUser);
                request.setAttribute("success", "Update User Info Success!");
            }

        }

        request.setAttribute("ROLES", roleDao.findAll());
        User user = userDAO.findUserById(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("selected_user", user);

        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.UPDATE_USER_PAGE + "?id=" + request.getParameter("id"));
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
