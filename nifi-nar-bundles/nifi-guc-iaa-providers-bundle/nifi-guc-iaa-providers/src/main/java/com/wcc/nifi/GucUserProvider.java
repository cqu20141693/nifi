package com.wcc.nifi;


import org.apache.nifi.authentication.*;
import org.apache.nifi.authentication.exception.IdentityAccessException;
import org.apache.nifi.authentication.exception.InvalidLoginCredentialsException;
import org.apache.nifi.authentication.exception.ProviderCreationException;
import org.apache.nifi.authentication.exception.ProviderDestructionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * guc 用户认证
 * wcc 2022/10/24
 */
public class GucUserProvider implements LoginIdentityProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(GucUserProvider.class);

    @Override
    public AuthenticationResponse authenticate(LoginCredentials credentials) throws InvalidLoginCredentialsException, IdentityAccessException {
        LOGGER.info("guc authenticate u={},p={}", credentials.getUsername(), credentials.getPassword());
        return new AuthenticationResponse(credentials.getUsername(), credentials.getUsername(), 1_000_000L, getClass().getSimpleName());
    }

    @Override
    public void initialize(LoginIdentityProviderInitializationContext initializationContext) throws ProviderCreationException {
        LOGGER.info("Initializing GUC Provider");
    }

    @Override
    public void onConfigured(LoginIdentityProviderConfigurationContext configurationContext) throws ProviderCreationException {
        LOGGER.info("onConfigured GUC Provider");
    }

    @Override
    public void preDestruction() throws ProviderDestructionException {
        LOGGER.info("preDestruction GUC Provider");
    }
}
