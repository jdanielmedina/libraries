<?php

// Get config
require_once 'config.php';

// Allowed request parameter keys
$allowed_keys = array(
    'user',
    'phone',
    'version',
    'callback_url',
    'sig',
	'status'
);

// Get the status parameter
$status = $_POST['status'];


// Check if there is something posted
if (isset($status) && $status == 'VERIFIED') {

    require_once('signer.php');
    $signer = new Signer();

    // Initialize parameters array
    $params = array();

    // Add all POST parameters to array for signature comparison
    foreach ($_POST as $key => $value) {

        if (in_array($key, $allowed_keys)) {
            $params[$key] = $value;
        }
    }

    // Validate the signature
    if ($signer->verifySignatures($params, MESSENTE_API_PASSWORD)) {
        ?>

        <!DOCTYPE html>
        <html lang="en">
            <head>
                <title>Success</title>
                <meta charset="utf-8">

                <style>
                    h2 {
                        font-size: 24px;
                        color: #303030;
                        font-weight: 400;
                        margin-top: 30px;
                        text-align: center;
                    }
                </style>
            </head>

            <body>
                <h2>Verification process successfully completed! User has been redirected to the original site.</h2>
            </body>
        </html>

        <?php
    } else {
        $alert = http_build_query(array(
            'alert' => 'Verification failed [' . $_POST['sig'] . ' ]'
        ));

        // Redirect to login page with alert message
        header('Location: login.php?' . $alert);
        exit();
    }
} elseif (isset($status) && $status != 'VERIFIED') {

    $alert = http_build_query(array(
        'alert' => 'Verification failed [' . $_POST['status'] . ' ]'
    ));
    // User was not verified: redirect to login with alert
    header('Location: login.php?' . $alert);
    exit();
} else {
    // No data was posted so login has not taken place yet: go to login page
    header('Location: login.php');
    exit();
}
    

