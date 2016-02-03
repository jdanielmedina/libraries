<?php

require_once 'config.php';
require_once 'signer.php';

$user = $_POST['user'];
$pass = $_POST['pass'];

// Check if username and password were submitted with the form
if (isset($user) && isset($pass)) {
    authenticate($user, $pass);
} else {
    goBack('Username or password missing!');
}

/**
 * Redirect back to login page.
 * @param type $message
 */
function goBack($message) {
    header("Location: login.php?alert=$message"); /* Redirect browser */
    exit();
}

/**
 * Authenticate the user by username and password and then by Messente's verification widget.
 * @param type $username
 * @param type $password
 */
function authenticate($username, $password) {

    // Primitive and unsecure authentication just for illustrating this example
    if ($username == 'test' && $password == '1234') {

        $request_params = array(
            'user' => MESSENTE_API_USERNAME,
            'version' => VERSION,
            'pass' => MESSENTE_API_PASSWORD,
            'callback_url' => CALLBACK_URL
        );

        // Add phone number if it was submitted
        if (isset($_POST['phone']) && !empty($_POST['phone'])) {
            $request_params['phone'] = $_POST['phone'];
        }
		
		// Initialize signature calculation object
        $signer = new Signer();

		// Generate signature
        $sig = $signer->generateSignature($request_params);

        // Remove password for security reasons
        unset($request_params['pass']);

        // Add signature to array
        $request_params['sig'] = $sig;

        // Redirect to Messente
        verify($request_params);
    } else {
        goBack('Wrong credentials!');
    }
}

/**
 * Redirect to Messente's verification page.
 * @param type $params
 */
function verify($params) {
    $url = 'http://verify.messente.com?' . http_build_query($params);
	// Redirect browser to messente
    header("Location: $url");
    exit();
}
