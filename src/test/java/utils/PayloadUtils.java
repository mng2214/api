package utils;

public class PayloadUtils {

    public static String getPetPayload(int petID, String petName, String status) {
        String payload =
                "{\n" +
                        "  \"id\": " + petID + ",\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"String\"\n" +
                        "  },\n" +
                        "  \"name\": \"" + petName + "\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"" + status + "\"\n" +
                        "}";
        return payload;
    }


    public static String getSlackMessagePayload(String yourMessage) {
        String payload = "{\n" +
                "  \"channel\": \"C052ZQBE39D\",\n" +
                "  \"text\": \"" + yourMessage + "\"\n" +
                "}";
        return payload;
    }



}
