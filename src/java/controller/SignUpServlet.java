package controller;

import dao.RoleDAO;
import dao.UserDAO;
import entity.Role;
import entity.User;
import exception.PreexistingEntityException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
@WebServlet(name = "SignUpServlet", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleDAO roleDAO = new RoleDAO();
        request.setAttribute("ROLES", roleDAO.findAll());
        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.SIGN_UP_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                        if (itemName.isEmpty()) continue;
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
                    Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!pass) {
                request.setAttribute("errors", errors);
            } else {
                //Pass true -> Validate Success
                System.out.println("Validated TRUE");
                User user = new User();
                user.setEmail(email);
                user.setName(name);

                String encyptedPassword = EncryptPassword.encrypt(password);
                user.setEncryptedPassword(encyptedPassword);
                
                user.setImg(filename);
                
                RoleDAO roleDao = new RoleDAO();
                Role role = roleDao.findRole(Integer.parseInt(roleId));
                user.setRole(role);
                try {
                    userDao.create(user);
                    request.setAttribute("success", "Create Account Success!");
                } catch (PreexistingEntityException ex) {
                    Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        RequestDispatcher rd = request.getRequestDispatcher(URLConstants.SIGN_UP_PAGE);
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private static String getValue(Part part) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
        StringBuilder value = new StringBuilder();
        char[] buffer = new char[1024];
        for (int length = 0; (length = reader.read(buffer)) > 0;) {
            value.append(buffer, 0, length);
        }
        return value.toString();
    }
}
