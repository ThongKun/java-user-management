package controller;

import dao.RoleDAO;
import dao.UserDAO;
import entity.Role;
import entity.User;
import exception.PreexistingEntityException;
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
import org.apache.log4j.Logger;
/**
 *
 * @author HOME
 */
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/create-new-user"})
public class CreateUserServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CreateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDAO = new RoleDAO();
        request.setAttribute("ROLES", roleDAO.findAll());
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.CREATE_NEW_USER_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = null;
        String email = null;
        String password = null;
        String phone = null;
        String roleId = null;

        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        if (!isMultiPart) {

        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = (List) upload.parseRequest(new ServletRequestContext(request));
            } catch (FileUploadException e) {
                LOGGER.error(e);
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
                        LOGGER.error(e);
                    }
                }
            }//end while

            boolean pass = true; //check if validation is ok
            ErrorValidationHandling errors = new ErrorValidationHandling();

            name = (String) params.get("name");
            email = (String) params.get("email");
            password = (String) params.get("password");
            roleId = (String) params.get("role");
            phone = (String) params.get("phone");

            if (!UserValidator.validateName(name)) {
                pass = false;
                errors.setNameError("name format only contains alphabet characters<br/>length of name must be from 1 - 30 characters");
            }
            if (!UserValidator.validateEmail(email)) {
                pass = false;
                errors.setEmailError("First Character must be a-z<br/>limit 55 characters<br/>Email format sample: thong131@gmail.com");
            }
            if (!UserValidator.validatePassword(password)) {
                pass = false;
                errors.setPasswordError("length of password must be from 6 - 30 characters");
            }

            UserDAO userDao = new UserDAO();
            if (pass) {
                try {
                    if (userDao.findUserByEmail(email) != null) {
                        pass = false;
                        errors.setDuplicateEmailError("Account is existed. Please try different email!");
                        request.setAttribute("errors", errors);
                    }
                } catch (Exception ex) {
                    LOGGER.error(ex);
                }
            }

            if (!pass) {
                request.setAttribute("errors", errors);
            } else {
                //Pass true -> Validate Success
                User user = new User();
                user.setEmail(email);
                user.setName(name);

                String encyptedPassword = EncryptPassword.encrypt(password);
                user.setEncryptedPassword(encyptedPassword);
                user.setImg(filename);
                user.setPhone(phone);

                RoleDAO roleDao = new RoleDAO();
                Role role = roleDao.findRole(Integer.parseInt(roleId));
                request.setAttribute("role", role);
                user.setRole(role);
                try {
                    userDao.create(user);
                    request.setAttribute("success", "Create Account Success!");
                } catch (PreexistingEntityException ex) {
                    LOGGER.error(ex);
                }
            }

        }

        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("phone", phone);

        RoleDAO roleDAO = new RoleDAO();
        request.setAttribute("ROLES", roleDAO.findAll());
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.CREATE_NEW_USER_PAGE);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void init() throws ServletException {
        LOGGER.info("INITILIZED");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
}
