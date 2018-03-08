/*
 * Copyright (c) 2017.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

package com.dewnaveen.texteditor.data.network;


public final class ApiEndPoint {

    static String BASE_LOCAL = "http://192.168.43.6/naveen_resume/public/";
    private static final String BASE_SERVER = "http://dewnaveen.info/";

    public static final String ENDPOINT_CONTENT_LIST = BASE_SERVER + "api/get/contentList";

    public static final String ENDPOINT_CONTENT_IMAGE_PATH = BASE_SERVER + "upload/images/";

    public static final String ENDPOINT_CONTENT_BY_ID = BASE_SERVER + "api/getContentById/{id}";

    public static final String ENDPOINT_UPLOAD_CONTENT = BASE_SERVER + "api/get/upload";

    public static final String ENDPOINT_PORTFOLIO = "http://www.mocky.io/v2/5aa112c23200004e2ce9fef7";

    public static final String ENDPOINT_RESUME = "https://drive.google.com/open?id=13h5bVV5S8mW-18BwuPgxPpgOG_H7vUrY";

    private ApiEndPoint() {
    }

}
