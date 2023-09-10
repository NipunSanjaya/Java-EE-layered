package lk.ijse.pos.servlet.servlet;



import lk.ijse.pos.servlet.bo.BOTypes;
import lk.ijse.pos.servlet.bo.FactoryBO;
import lk.ijse.pos.servlet.bo.custom.impl.CustomerBOImpl;
import lk.ijse.pos.servlet.dto.CustomerDTO;
import lk.ijse.pos.servlet.util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/customer"})
public class CustomerServlet extends HttpServlet {

    private final CustomerBOImpl customerBO = (CustomerBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.Customer);
    public CustomerServlet(){
        System.out.println("Customer Servlet Constructor Invoked");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Initializing connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                customerObject.add("salary", salary);
                allCustomers.add(customerObject.build());
            }
            //create the response Object
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
        } catch (ClassNotFoundException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            if (customerBO.addCustomer(new CustomerDTO(req.getParameter("cusID"),req.getParameter("cusName"),req.getParameter("cusAddress"),req.getParameter("cusSalary")))) {
                resp.getWriter().print(ResponseUtil.genJson("Ok","Successfully Added...!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Error","Not Added...!"));
            }
        }
             catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            if (customerBO.updateCustomer(new CustomerDTO(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("address"),jsonObject.getString("salary")))) {
                resp.getWriter().print(ResponseUtil.genJson("Ok","Successfully Updated...!"));
            }else{
                resp.getWriter().print(ResponseUtil.genJson("Error","Not Updated...!"));
            }
        }
             catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        try {
            if (customerBO.deleteCustomer(new CustomerDTO(req.getParameter("cusID")))) {
                resp.getWriter().print(ResponseUtil.genJson("Ok","Successfully Deleted...!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Error","Not Deleted...!"));
            }
        }
             catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

}
