package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v132.network.Network;
import org.openqa.selenium.devtools.v132.network.model.RequestId;
import org.openqa.selenium.devtools.v132.network.model.ResponseReceived;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import listeners.TestListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
 
public class HARRecorder {
    private static DevTools devTools;
    private static ConcurrentLinkedQueue<String> networkLogs = new ConcurrentLinkedQueue<>();
    private static WebDriver driver;

    public static void startRecording(WebDriver driver) {
        HARRecorder.driver = driver;
        if (driver instanceof ChromeDriver) {
            devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.addListener(Network.responseReceived(), responseReceived -> {
                String requestType = responseReceived.getType().name();
                if (requestType.equals("XHR") || requestType.equals("Fetch")) {
                    String json = convertResponseToJson(responseReceived);
                    networkLogs.add(json);
                    System.out.println("Response alýndý (XHR/Fetch): " + json);
                }
            });
            System.out.println("DevTools dinleyici baþlatýldý.");
        }
    }

    public static void stopRecordingAndSave(String testName) {
        if (devTools != null) {
            saveLogsAsJson(testName);
        }
    }

    public static ConcurrentLinkedQueue<String> getNetworkLogs() {
        return networkLogs;
    }

    private static String convertResponseToJson(ResponseReceived responseReceived) {
        StringBuilder json = new StringBuilder();
        RequestId requestId = responseReceived.getRequestId();
        String url = responseReceived.getResponse().getUrl();
        int status = responseReceived.getResponse().getStatus();
        String statusText = responseReceived.getResponse().getStatusText();
        String body = getResponseBody(requestId);

        if (body == null || body.isEmpty()) {
            return "{ \"error\": \"Response body is empty\" }";
        }

        try {
            String formattedBody = getFormattedResponse(body);
            JsonElement element = JsonParser.parseString(formattedBody); 
            Gson gson = new GsonBuilder().setLenient().create(); 
            formattedBody = gson.toJson(element);
            if (formattedBody.contains("\"error\"")) {
                return formattedBody;
            }

            String preview = extractPreview(requestId);
            String payload = extractPayload(requestId);

            json.append("{\n")
                .append(" \"url\": \"").append(url).append("\",\n")
                .append(" \"status\": ").append(status).append(",\n")
                .append(" \"statusText\": \"").append(statusText).append("\",\n")
                .append(" \"response\": {\n")
                .append(" \"body\": ").append(formattedBody).append("\n")
                .append(" },\n")
                .append(" \"preview\": ").append(preview != null ? preview : "\"Preview bulunamadý\"").append(",\n")
                .append(" \"payload\": ").append(payload != null ? payload : "\"Payload bulunamadý\"").append("\n")
                .append("}\n");
        } catch (Exception e) {
            e.printStackTrace();
            json.append("{ \"error\": \"Response body parsing failed: ").append(e.getMessage()).append("\" }");
        }

        return json.toString();
    }

    private static String extractPreview(RequestId requestId) {
        try {
            String previewData = devTools.send(Network.getResponseBody(requestId)).getBody();
            return previewData != null ? previewData : "\"Preview bulunamadý\"";
        } catch (Exception e) {
            e.printStackTrace();
            return "\"Preview bulunamadý\"";
        }
    }

    private static String extractPayload(RequestId requestId) {
        try {
            String requestData = devTools.send(Network.getRequestPostData(requestId));
            return (requestData != null && !requestData.isEmpty()) ? requestData : "\"Payload bulunamadý\"";
        } catch (Exception e) {
            e.printStackTrace();
            return "\"Payload bulunamadý\"";
        }
    }

    private static String getResponseBody(RequestId requestId) {
    	try {
            String responseBody = devTools.send(Network.getResponseBody(requestId)).getBody();
            if (responseBody == null || responseBody.isEmpty()) {
                System.err.println("Error: Response body is null or empty for requestId: " + requestId);
                return "{}"; 
            }
            return responseBody;
        } catch (Exception e) {
            System.err.println("Error getting response body for requestId: " + requestId + ": " + e.getMessage());
            return "{}"; 
        }
    }

    private static String getFormattedResponse(String body) {
        body = body.trim().replaceAll("\\\\n", " ");
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        try {
            JsonElement element = JsonParser.parseString(body);
            if (element.isJsonObject() || element.isJsonArray()) {
                return gson.toJson(element);
            } else {
                return "{ \"error\": \"Response body is not a valid JSON format\" }";
            }
        } catch (Exception e) {
            return "{ \"error\": \"Failed to parse response body: " + e.getMessage() + "\", \"raw\": \"" + body + "\" }";
        }
    }
    
    
    private static void saveLogsAsJson(String testName) {
    	  String directory = "har-archives/";
        String jsonFileName = directory+testName + "-har.json";
        String zipFileName = directory+ testName + "-har.zip";
      

        try (FileWriter file = new FileWriter(jsonFileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonArray = new JsonArray();
            for (String log : networkLogs) {
                try {
                    JsonElement jsonElement = JsonParser.parseString(log);
                    jsonArray.add(jsonElement);
                } catch (Exception e) {
                    jsonArray.add(new JsonPrimitive(log)); 
                }
            }
            
            gson.toJson(jsonArray, file); 
            System.out.println("HAR logs saved to " + jsonFileName);
            zipFile(jsonFileName, zipFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFile(String jsonFileName, String zipFileName) {
        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File jsonFile = new File(jsonFileName);
            ZipEntry zipEntry = new ZipEntry(jsonFile.getName());
            zipOut.putNextEntry(zipEntry);
            zipOut.write(java.nio.file.Files.readAllBytes(jsonFile.toPath()));
            zipOut.closeEntry();
            System.out.println("HAR logs zipped to " + zipFileName);

             if (jsonFile.delete()) {
                 System.out.println("JSON file deleted: " + jsonFileName);
             }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}