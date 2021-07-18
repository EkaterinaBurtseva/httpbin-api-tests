package com.setup.api.services;

import io.qameta.allure.Step;

public class RedirectsService extends BaseService {

    @Step("post: redirects to the {url} url")
    public int postRedirectsToUrl(String url) {
        return setUp()
                .queryParam("url", url)
                .when()
                .post("redirect-to").then()
                .log().ifError().extract().statusCode();
    }

    @Step("put: redirects to the {url} url")
    public int putRedirectsToUrl(String url) {
        return setUp().queryParam("url", url)
                .when()
                .put("redirect-to").then()
                .log().ifError().extract().statusCode();
    }

}
