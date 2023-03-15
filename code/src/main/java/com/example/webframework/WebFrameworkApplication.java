package com.example.webframework;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class WebFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFrameworkApplication.class, args);
    }

    @GetMapping("/")
    public String welcome() {

        //创建json对象作为requestBody
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "");
        jsonObject.put("password", "123");
        Map<String, String> heads = new HashMap<>();
        heads.put("Content-Type", "application/json;charset=UTF-8");
        heads.put("X-CMC_PRO_API_KEY","6cb1bdad-3369-4504-89fc-0272749f1722");
        HttpResponse response = HttpRequest.get("https://pro-api.coinmarketcap.com/v2/cryptocurrency/quotes/latest?id=7334")
                .headerMap(heads, false)
                .timeout(5 * 60 * 1000)
                .execute();
        return JSONUtil.toJsonStr(response.body());
    }

    @GetMapping("/fcheaders")
    public ResponseEntity<Map<String, String>> listHeaders(
            @RequestHeader Map<String, String> headers) {
        Map<String, String> fcHeaders = new HashMap<>();
        headers.forEach((key, value) -> {
            if (key.startsWith("x-fc")) {
                fcHeaders.put(key, value);
            }

        });

        return new ResponseEntity<>(fcHeaders, HttpStatus.OK);
    }
}
