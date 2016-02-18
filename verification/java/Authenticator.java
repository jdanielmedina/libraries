package com.messente.verify;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Lennar Kallas
 */
public class Authenticator {

    /**
     * List of allowed keys for signature building.
     */
    private final List<String> allowedKeys = Arrays.asList(
            "user", "phone", "version",
            "callback_url", "sig", "status");

    /**
     * Generate signature.
     *
     * @param parameters Map with request parameters.
     * @param pass Messente account API password.
     * @return MD5 hashed string as signature.
     */
    public String generateSignature(Map<String, String> parameters, String pass) {

        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException("Parameters are missing - can't generate signature!");
        }

        if (!parameters.containsKey("user") && parameters.get("user").trim().isEmpty()) {
            throw new IllegalArgumentException("'user' parameter is missing - can't generate signature!");
        }

        if (pass == null || pass.trim().isEmpty()) {
            throw new IllegalArgumentException("Password not set - can't generate signature!");
        }

        if (!parameters.containsKey("version") && parameters.get("version").trim().isEmpty()) {
            throw new IllegalArgumentException("'version' parameter is missing - can't generate signature!");
        }

        parameters.put("pass", pass);

        return generateSignatureHash(parameters);
    }

    /**
     * Compares signatures.
     *
     * @param parameters Map with request parameters.
     * @param pass Messente account API password.
     * @return true if signatures are equal, otherwise false.
     */
    public boolean verifySignature(Map<String, String> parameters, String pass) {

        if (parameters == null || parameters.isEmpty()) {
            throw new IllegalArgumentException("Parameters are missing - can't compare signatures!");
        }

        if (!parameters.containsKey("sig") && parameters.get("sig").trim().isEmpty()) {
            throw new IllegalArgumentException("'sig' parameter is missing - can't compare signatures!");
        }

        if (pass == null || pass.trim().isEmpty()) {
            throw new IllegalArgumentException("Password not set - can't compare signatures!");
        }

        return parameters.get("sig").equals(generateSignature(parameters, pass));
    }

    /**
     * Generates string that is a MD5 hash.
     *
     * @param parameters Map with request parameters.
     * @return unique signature.
     */
    private String generateSignatureHash(Map<String, String> parameters) {

        // Add everything to sorted map
        TreeMap<String, String> sortedParams = new TreeMap<>(parameters);

        StringBuilder sigString = new StringBuilder();

        for (Map.Entry<String, String> item : sortedParams.entrySet()) {
            String key = item.getKey();
            String value = item.getValue();

            if ((allowedKeys.contains(key) && !key.equalsIgnoreCase("sig"))
                    || key.equalsIgnoreCase("pass")) {

                sigString
                        .append(key)
                        .append(value);
            }
        }

        return generateHash(sigString.toString());
    }

    /**
     * Generates MD5 signature hash.
     *
     * @param unhashed unhashed string.
     * @return hashed string.
     */
    private String generateHash(String unhashed) {

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(unhashed.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            //throw new Exception("Error occurred while hashing - " + ex.getMessage());
            return null;

        }
    }

}
