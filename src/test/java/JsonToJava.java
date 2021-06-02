import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

public class JsonToJava {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;
        ResultSet rs;

        ArrayList<CustomerDetails> a = new ArrayList<>();
        JsonArray ja = new JsonArray();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Business", "root", "admin");
            Statement st = con.createStatement();
            rs = st.executeQuery("select * from CustomerInfo where purchasedDate=CURDATE() and Location ='Asia';");
            while (rs.next()) {

                CustomerDetails c = new CustomerDetails();
                c.setCustomerName(rs.getString(1));
                c.setPurchaseDate(rs.getString(2));
                c.setAmount(rs.getInt(3));
                c.setLocation(rs.getString(4));
                a.add(c);
                System.out.println(c.getCustomerName());
                System.out.println(c.getPurchaseDate());
                System.out.println(c.getAmount());
                System.out.println(c.getLocation());
            }
            for (int i = 0; i < a.size(); i++) {
                ObjectMapper o = new ObjectMapper();
                o.writeValue(new File("src/test/jsondata/CustomerDetails" + i + ".json"), a.get(i));
                Gson gs = new Gson();
                String jsonString = gs.toJson(a.get(i));
                ja.add(jsonString);
            }
            JSONObject js = new JSONObject();
            js.put("data", ja);
            System.out.println(js.toJSONString());

            String unescaped = StringEscapeUtils.unescapeJava(js.toJSONString());
            System.out.println(unescaped);
            String string1 = unescaped.replace("\"{", "{");
            String finalString = string1.replace("}\"", "}");
            System.out.println(finalString);
            try (FileWriter fi = new FileWriter("src/test/jsondata/FinalJson.json")){
                fi.write(finalString);
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
