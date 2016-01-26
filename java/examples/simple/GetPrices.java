/**
 * Copyright 2016 Lennar Kallas, Messente Communications Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messente.examples.simple;

import com.messente.enums.Country;
import com.messente.response.MessenteResponse;

/**
 * Example of how to get the list of prices for specified country.
 *
 * @author Lennar Kallas
 */
public class GetPrices {

    public static final String API_USERNAME = "<api-username-here>";
    public static final String API_PASSWORD = "<api-password-here>";

    public static void main(String[] args) {

        // Create Messente client
        Messente messente = new Messente(API_USERNAME, API_PASSWORD);

        // Create response object
        MessenteResponse response = null;

        try {

            response = messente.getPriceList(Country.HUNGARY);

            // Checking the response status
            if (response.isSuccess()) {

                // Get Messente server full response
                System.out.println("Server response: " + response.getResponse());

                //Get account balance part of the response
                System.out.println("Prices: " + response.getResult());

                //As you can see the outcome of getResponse and getResult methods are the same so you can use either one of them.
            } else {
                // In case of failure get failure message                
                throw new RuntimeException(response.getResponseMessage());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to get prices! " + e.getMessage());
        }

    }

}
