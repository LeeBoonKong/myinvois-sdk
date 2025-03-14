/*
 * Copyright 2011-2024 AmaSeng Software Sdn. Bhd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amaseng.myinvois;

import com.amaseng.myinvois.codelists.IDType;
import com.amaseng.myinvois.exception.EInvoiceAPIException;
import com.amaseng.myinvois.models.Document;
import com.amaseng.myinvois.models.DocumentSubmission;
import com.amaseng.myinvois.models.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class Api {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;

    private String accessToken;
    private Instant tokenExpireTime;

    public Api(String baseUrl, String clientId, String clientSecret) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = new OkHttpClient();
    }

    public Api(String baseUrl, String clientId, String clientSecret, OkHttpClient client) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.client = client;
    }

    public void init() throws IOException, EInvoiceAPIException {
        if (accessToken != null && tokenExpireTime != null && tokenExpireTime.isAfter(Instant.now())){
            //Skip and reuse if token is not expired yet
            return;
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("scope", "InvoicingAPI")
                .build();

        // Create URL object with the endpoint
        Request request = new Request.Builder()
                .url(baseUrl + "/connect/token")
                .post(requestBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> map = objectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {});
                accessToken = map.get("access_token");
                Integer expiresIn = Integer.parseInt(map.get("expires_in"));
                // Minus 100 seconds as a buffer zone before expiry
                tokenExpireTime = Instant.now().plusSeconds(expiresIn - 100);
            } else {
                throw EInvoiceAPIException.builder()
                        .requestHeaders(response.headers().toMultimap())
                        .statusCode(response.code())
                        .message(response.message())
                        .responseBody(response.body().string())
                        .build();
            }
        }
    }

    public boolean validateTin(String inputTin, String inputIdType, String inputId) throws IOException, EInvoiceAPIException {
        init();

        HttpUrl url = HttpUrl.parse(baseUrl + "/api/v1.0/taxpayer/validate/" + inputTin)
                .newBuilder()
                .addQueryParameter("idType", inputIdType)
                .addQueryParameter("idValue", inputId)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Accept", "application/json")
                .header("Accept-Language", "en")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.code() == 200;
        }
    }

    private String base64(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String sha256(String value) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private Document convertInvoice(Invoice invoice) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(invoice.toMap());
            //System.out.println("####Invoice JSON: " + json);
            JsonNode jsonNode = mapper.readValue(json, JsonNode.class);
            String minifiedJson = jsonNode.toString();
            
            return new Document("JSON", base64(minifiedJson), sha256(minifiedJson), invoice.getId());
        } catch (JsonProcessingException | NoSuchAlgorithmException | KeyStoreException | InvalidKeyException | SignatureException | UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String submitInvoices(Invoice[] invoices) throws IOException, EInvoiceAPIException {
        init();
        Document[] documents = Arrays.stream(invoices).map(this::convertInvoice).toArray(Document[]::new);
        DocumentSubmission submission = new DocumentSubmission(documents);
        Map<Object, Object> requestBodyMap = submission.toMap();

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestBodyMap);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBody);

        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1.0/documentsubmissions/")
                .post(body)
                .header("Accept", "application/json")
                .header("Accept-Language", "en")
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 202) {
                return response.body().string();
            } else {
                throw EInvoiceAPIException.builder()
                        .requestHeaders(response.headers().toMultimap())
                        .statusCode(response.code())
                        .message(response.message())
                        .responseBody(response.body().string())
                        .build();
            }
        }
    }

}
