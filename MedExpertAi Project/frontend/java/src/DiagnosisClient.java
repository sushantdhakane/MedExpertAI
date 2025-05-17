import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;

public class DiagnosisClient {
    public static String getDiagnosis(List<String> symptoms) {
        try {
            URL url = new URL("http://localhost:5000/diagnose");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("symptoms", symptoms);

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.optString("diagnosis", "No response received.");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}