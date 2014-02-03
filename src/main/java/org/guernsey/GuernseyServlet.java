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
package org.guernsey;

import org.guernsey.internal.get.GETAdapter;
import org.guernsey.internal.RestMethods;
import org.guernsey.internal.RestResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuernseyServlet extends HttpServlet {

    private RestMethods methods;
    @Override
    public void init() throws ServletException {
        methods = new RestMethods(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        GETAdapter getAdapter = new GETAdapter(methods, path);
        RestResponse restResponse = getAdapter.restResponse();
        String body = restResponse.getBody();
        if (body != null) {
            ServletOutputStream outputStream = resp.getOutputStream();
            try {
                outputStream.write(body.getBytes());
            } finally {
                outputStream.close();
            }
        }
        resp.setStatus(restResponse.getStatus());
    }


}
