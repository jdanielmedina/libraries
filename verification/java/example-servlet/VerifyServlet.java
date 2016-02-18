package messente.com.verify.example;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Example servlet for handling Messente's verification widget response.
 *
 * @author Lennar Kallas
 */
public class VerifyServlet extends HttpServlet {

    // Example credentials
    private final String MESSENTE_API_USERNAME = "your_username_here";
    private final String MESSENTE_API_PASSWORD = "your_password_here";
    private final String PHONE_NR = "+372512345678";
    private final String VERSION = "1";
    private final String CALLBACK_URL = "http://yourserver.com/verification/";


    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {

        // Get the map with request parameters
        Map<String, String[]> reqParams = request.getParameterMap();

        if (reqParams.isEmpty()) {
            return;
        }
        // Create map with strings
        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, String[]> entry : reqParams.entrySet()) {
            String key = entry.getKey();
            String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "");

            // For debugging
            System.out.println(key + " -> " + value);
            params.put(key, value);
        }

        // Response text
        String respStr = null;

        if (params.containsKey("status")
                && params.get("status").equalsIgnoreCase("VERIFIED")) {

            // You may want to compare phone numbers before allowing the user in
            if (!params.get("phone").equalsIgnoreCase(PHONE_NR)) {

                respStr = "Phone number that you used for authenticating doesn't "
                        + "match with the one registered in your account!";

                System.out.println("VERIFICATION ERROR: Phone number does not match!");
            } else {


                // Initialize authenticator
                Authenticator authenticator = new Authenticator();

                // Verify signatures by comparing signatures that Messente returned (param 'sig') 
                // and the signature you can calculate from response parameters
                respStr = authenticator.verifySignature(params, MESSENTE_API_PASSWORD)
                        ? "You are verified!" : "Sorry! Verification failed!";
            }

        } else {
            respStr = "Oops! Something went wrong! Verification status "
                    + params.get("status");
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(respStr);

    }

}
