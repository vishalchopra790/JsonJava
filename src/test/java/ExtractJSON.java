import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ExtractJSON {

    public static void main(String[] args) throws IOException {
        ObjectMapper ob=new ObjectMapper();
        CustomerDetailsAppium cu=ob.readValue(new File("src/test/jsondata/CustomerDetails0.json"),CustomerDetailsAppium.class);
        System.out.println(cu.getAmount());
        System.out.println(cu.getStudentName());
    }
}
