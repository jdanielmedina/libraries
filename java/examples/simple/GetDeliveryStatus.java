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

import com.messente.response.MessenteDeliveryStatus;

/**
 * Example of how to get message delivery status.
 *
 * @author Lennar Kallas
 */
public class GetDeliveryStatus {

    public static final String API_USERNAME = "<api-username-here>";
    public static final String API_PASSWORD = "<api-password-here>";

    public static final String MESSAGE_ID = "api25eb1e22fb3bf041c9b6cd12cd15d682545454367";

    public static void main(String[] args) {

        // Create Messente client
        Messente messente = new Messente(API_USERNAME, API_PASSWORD);

        // Create delivery status object
        MessenteDeliveryStatus dlrStatus = null;

        try {

            dlrStatus = messente.getDeliveryStatus(MESSAGE_ID);

            // Checking the response status
            if (dlrStatus.isSuccess()) {

                // Get Messente server full response
                System.out.println("Server response: " + dlrStatus.getResponse());

                //Get delivery status part of the response
                System.out.println("Delivery status: " + dlrStatus.getResult());

                // Checking statuses
                switch (dlrStatus.getResult()) {
                    case MessenteDeliveryStatus.SENT:
                        System.out.println(dlrStatus.getResponseMessage());
						// Do something ...
                        break;
                    case MessenteDeliveryStatus.DELIVERED:
                        System.out.println(dlrStatus.getResponseMessage());
						// Do something ...
                        break;
                    case MessenteDeliveryStatus.FAILED:
                        System.out.println(dlrStatus.getResponseMessage());
						// Do something ...
                        break;
                    default:
                        System.out.println(dlrStatus.getResponseMessage());
                        break;
                }

            } else {
                // In case of failure get failure message                
                throw new RuntimeException(dlrStatus.getResponseMessage());
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to get delivery report! " + e.getMessage());
        }
    }

}
