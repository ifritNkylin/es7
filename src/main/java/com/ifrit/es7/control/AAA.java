package com.ifrit.es7.control;

public class AAA {
    public static String tableMapping = "{\n" +
            "      \"properties\" : {\n" +
            "        \"creator\" : {\n" +
            "          \"type\" : \"nested\"\n" +
            "        },\n" +
            "        \"es_bool\" : {\n" +
            "          \"type\" : \"boolean\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"es_int\" : {\n" +
            "          \"type\" : \"integer\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"es_text\" : {\n" +
            "          \"type\" : \"keyword\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"gmtCreate\" : {\n" +
            "          \"type\" : \"date\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"gmtModified\" : {\n" +
            "          \"type\" : \"date\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"id\" : {\n" +
            "          \"type\" : \"keyword\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"updater\" : {\n" +
            "          \"type\" : \"nested\"\n" +
            "        }\n" +
            "      }\n" +
            "    }";
    public static String mapStr = "{\n" +
            "      \"properties\" : {\n" +
            "        \"amount\" : {\n" +
            "          \"type\" : \"long\"\n" +
            "        },\n" +
            "        \"name\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"notes\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        },\n" +
            "        \"notes2\" : {\n" +
            "          \"type\" : \"text\",\n" +
            "          \"fields\" : {\n" +
            "            \"keyword\" : {\n" +
            "              \"type\" : \"keyword\",\n" +
            "              \"ignore_above\" : 256\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }";

    public static String original = "{\"properties\":{\"gmtModified\":{\"type\":\"date\"},\"creator\":{\"type\":\"text\"},\"id\":{\"type\":\"keyword\"},\"gmtCreate\":{\"type\":\"date\"},\"updater\":{\"type\":\"text\"}}}";

    public static String right = "{\n" +
            "    \"properties\": {\n" +
            "        \"gmtModified\": {\n" +
            "            \"type\": \"date\"\n" +
            "        },\n" +
            "        \"creator\": {\n" +
            "            \"properties\":{\n" +
            "                \"amount\" : {\n" +
            "                  \"type\" : \"long\"\n" +
            "                },\n" +
            "                \"name\" : {\n" +
            "                  \"type\" : \"text\",\n" +
            "                  \"fields\" : {\n" +
            "                    \"keyword\" : {\n" +
            "                      \"type\" : \"keyword\",\n" +
            "                      \"ignore_above\" : 256\n" +
            "                    }\n" +
            "                  }\n" +
            "                },\n" +
            "                \"notes\" : {\n" +
            "                  \"type\" : \"text\",\n" +
            "                  \"fields\" : {\n" +
            "                    \"keyword\" : {\n" +
            "                      \"type\" : \"keyword\",\n" +
            "                      \"ignore_above\" : 256\n" +
            "                    }\n" +
            "                  }\n" +
            "                },\n" +
            "                \"notes2\" : {\n" +
            "                  \"type\" : \"text\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"id\": {\n" +
            "            \"type\": \"keyword\"\n" +
            "        },\n" +
            "        \"gmtCreate\": {\n" +
            "            \"type\": \"date\"\n" +
            "        },\n" +
            "        \"updater\": {\n" +
            "            \"type\": \"object\"\n" +
            "        }\n" +
            "    }\n" +
            "}";
}
