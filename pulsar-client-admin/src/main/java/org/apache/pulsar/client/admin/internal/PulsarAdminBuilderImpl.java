/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pulsar.client.admin.internal;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminBuilder;
import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.PulsarClientException.UnsupportedAuthenticationException;
import org.apache.pulsar.client.impl.conf.ClientConfigurationData;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PulsarAdminBuilderImpl implements PulsarAdminBuilder {

    protected final ClientConfigurationData conf;
    private int connectTimeout = PulsarAdmin.DEFAULT_CONNECT_TIMEOUT_SECONDS;
    private int readTimeout = PulsarAdmin.DEFAULT_READ_TIMEOUT_SECONDS;
    private int requestTimeout = PulsarAdmin.DEFAULT_REQUEST_TIMEOUT_SECONDS;
    private TimeUnit connectTimeoutUnit = TimeUnit.SECONDS;
    private TimeUnit readTimeoutUnit = TimeUnit.SECONDS;
    private TimeUnit requestTimeoutUnit = TimeUnit.SECONDS;

    @Override
    public PulsarAdmin build() throws PulsarClientException {
        return new PulsarAdmin(conf.getServiceUrl(),
                conf, connectTimeout, connectTimeoutUnit, readTimeout, readTimeoutUnit,
                requestTimeout, requestTimeoutUnit);
    }

    public PulsarAdminBuilderImpl() {
        this.conf = new ClientConfigurationData();
    }

    private PulsarAdminBuilderImpl(ClientConfigurationData conf) {
        this.conf = conf;
    }

    @Override
    public PulsarAdminBuilder clone() {
        return new PulsarAdminBuilderImpl(conf.clone());
    }

    @Override
    public PulsarAdminBuilder serviceHttpUrl(String serviceHttpUrl) {
        conf.setServiceUrl(serviceHttpUrl);
        return this;
    }

    @Override
    public PulsarAdminBuilder authentication(Authentication authentication) {
        conf.setAuthentication(authentication);
        return this;
    }

    @Override
    public PulsarAdminBuilder authentication(String authPluginClassName, Map<String, String> authParams)
            throws UnsupportedAuthenticationException {
        conf.setAuthentication(AuthenticationFactory.create(authPluginClassName, authParams));
        return this;
    }

    @Override
    public PulsarAdminBuilder authentication(String authPluginClassName, String authParamsString)
            throws UnsupportedAuthenticationException {
        conf.setAuthentication(AuthenticationFactory.create(authPluginClassName, authParamsString));
        return this;
    }

    @Override
    public PulsarAdminBuilder tlsTrustCertsFilePath(String tlsTrustCertsFilePath) {
        conf.setTlsTrustCertsFilePath(tlsTrustCertsFilePath);
        return this;
    }

    @Override
    public PulsarAdminBuilder allowTlsInsecureConnection(boolean allowTlsInsecureConnection) {
        conf.setTlsAllowInsecureConnection(allowTlsInsecureConnection);
        return this;
    }

    @Override
    public PulsarAdminBuilder enableTlsHostnameVerification(boolean enableTlsHostnameVerification) {
        conf.setTlsHostnameVerificationEnable(enableTlsHostnameVerification);
        return this;
    }

    @Override
    public PulsarAdminBuilder connectionTimeout(int connectionTimeout, TimeUnit connectionTimeoutUnit) {
        this.connectTimeout = connectionTimeout;
        this.connectTimeoutUnit = connectionTimeoutUnit;
        return this;
    }

    @Override
    public PulsarAdminBuilder readTimeout(int readTimeout, TimeUnit readTimeoutUnit) {
        this.readTimeout = readTimeout;
        this.readTimeoutUnit = readTimeoutUnit;
        return this;
    }

    @Override
    public PulsarAdminBuilder requestTimeout(int requestTimeout, TimeUnit requestTimeoutUnit) {
        this.requestTimeout = requestTimeout;
        this.requestTimeoutUnit = requestTimeoutUnit;
        return this;
    }
}
