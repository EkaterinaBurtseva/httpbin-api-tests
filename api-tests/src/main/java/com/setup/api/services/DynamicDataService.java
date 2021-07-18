package com.setup.api.services;

import io.qameta.allure.Step;

public class DynamicDataService extends BaseService {

    @Step("decode base 64url string with id {stringToDecode}")
    public String decodeString(String stringToDecode) {
       return setUp().when().get(String.format("base64/%s", stringToDecode))
               .then().log().ifError().extract().body().asString();
    }

    @Step("get delayed response for {sec} seconds")
    public int deleteDelayedResponse(int sec) {
        return setUp().when().delete(String.format("delay/%s", sec)).then()
                .log().ifError().extract().statusCode();
    }

}
