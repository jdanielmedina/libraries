<?php

/**
 * Takes care of generating signature for verification and signature comparison.
 */
class Signer {

    // Keys that are allowed to use for generating a signature.
    private $allowed_keys = array(
        'user',
        'phone',
        'version',
        'callback_url',
        'sig',
		'status'
    );

    /**
     * Generates signature.
     * 
     * @param array $parameters with required elements for generating a signature.
     * @return string with a signature hash.
     */
    public function generateSignature($parameters) {

        if (!isset($parameters) && empty($parameters)) {
            die('Missing parameters - cannot generate signature!');
        }

        // Do some validation
        if (!array_key_exists('user', $parameters) || !isset($parameters['user']) || !preg_match('/\S/', $parameters['user'])) {
            die('Username is not set!');
        }

        if (!array_key_exists('pass', $parameters) || !isset($parameters['pass']) || !preg_match('/\S/', $parameters['pass'])) {
            die('Password is not set!');
        }

        if (!array_key_exists('version', $parameters) || !isset($parameters['version']) || preg_match('/^\D?$/', $parameters['version'])) {
            die('Version is not set or not numerical!');
        }

        return $this->hashIt($parameters);
    }

    /**
     * Verifies if signatures match.
     * 
     * @param array $parameters with required elements for generating and verifying signature.
     * @return boolean indicating if the signatures match.
     */
    public function verifySignatures($parameters) {

        if (!isset($parameters) && empty($parameters)) {
            return false;
        }

        if ($parameters['sig'] === $this->generateSignature($parameters)) {
            return true;
        }

        return false;
    }

    /**
     * Generates a unique MD5 hash out of alphabetized request parameters.
     * 
     * @param array $parameters with required elements for generating a signature.
     * @return string with hash.
     */
    private function hashIt($parameters) {

        // Sort array by keys
        ksort($parameters);

        $sigstr = '';

        foreach ($parameters as $key => $value) {

            if ((in_array($key, $this->allowed_keys) && $key != 'sig') || $key == 'pass') {
                $sigstr = $sigstr . $key . $value;
            }
        }

        // Create MD5 hash
        $sig = md5($sigstr);

        return $sig;
    }

}
