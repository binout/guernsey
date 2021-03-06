/*
 * Copyright 2013 Benoît Prioux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.guernsey.internal;

public class RestResponse {

    private int status;
    private String body;
    private String contentType;

    public RestResponse withStatus(int status) {
        this.status = status;
        return this;
    }

    public RestResponse withBody(String body) {
        this.body = body;
        return this;
    }

    public RestResponse withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public String getContentType() {
        return contentType;
    }
}
