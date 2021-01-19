package com.okay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Integer> test(@RequestParam(value = "username") String username) {
        Integer tryCount = redisTemplate.opsForValue().get(username) != null ? redisTemplate.opsForValue().get(username) : 0;
        redisTemplate.opsForValue().set(username, tryCount += 1);
        return new ResponseEntity<>(tryCount, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Integer> get(@RequestParam(value = "username") String username) {
        return new ResponseEntity<>(redisTemplate.opsForValue().get(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@RequestParam(value = "username") String username) {
        return new ResponseEntity<>(redisTemplate.delete(username), HttpStatus.OK);
    }
}
