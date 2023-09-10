package lk.ijse.pos.servlet.servlet;

import lk.ijse.pos.servlet.bo.BOTypes;
import lk.ijse.pos.servlet.bo.FactoryBO;
import lk.ijse.pos.servlet.bo.custom.impl.ItemBOImpl;
import lk.ijse.pos.servlet.dto.ItemDTO;
import lk.ijse.pos.servlet.util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/item"})
public class ItemServlet extends HttpServlet {

    private final ItemBOImpl itemBO = (ItemBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.Item);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            //Initializing connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "sanu1234");
            PreparedStatement pstm = connection.prepareStatement("select * from Item");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                String qty = rst.getString(3);
                String unitPrice = rst.getString(4);

                JsonObjectBuilder itemObject = Json.createObjectBuilder();
                itemObject.add("code", code);
                itemObject.add("description", description);
                itemObject.add("qty", qty);
                itemObject.add("unitPrice", unitPrice);

                allItems.add(itemObject.build());
            }
            //create the response Object
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allItems.build()));
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
            if (itemBO.addItem(new ItemDTO(req.getParameter("code"), req.getParameter("description"), req.getParameter("itemQty"),req.getParameter("unitPrice")))) {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Successfully Added...!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Error", "Not Added...!"));
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
            if (itemBO.updateItem(new ItemDTO(jsonObject.getString("code"),jsonObject.getString("name"),req.getParameter("qtyOnHand"),req.getParameter("unitPrice")))) {
                resp.getWriter().print(ResponseUtil.genJson("OK","Successfully Updated...!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Error","Not Updated...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            if (itemBO.deleteItem(new ItemDTO(req.getParameter("code")))) {
                resp.getWriter().print(ResponseUtil.genJson("Ok","Successfully Deleted...!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Error","Not Deleted...!"));
            }
        }  catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

}
