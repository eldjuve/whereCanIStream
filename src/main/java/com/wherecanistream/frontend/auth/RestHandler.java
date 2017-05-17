package com.wherecanistream.frontend.auth;

import com.wherecanistream.frontend.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by djuve on 10.05.2017.
 */


@Service
public class RestHandler {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String loginUri = "http://userservice.pkbaierwrc.us-west-2.elasticbeanstalk.com/get";
    private final String registerUri = "http://userservice.pkbaierwrc.us-west-2.elasticbeanstalk.com/put";
    private final String updateUri = "http://userservice.pkbaierwrc.us-west-2.elasticbeanstalk.com/update";

    public RestHandler(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
//        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    public User getUserByLoginID(String username){
        try {
            return this.restTemplate.postForObject(loginUri, username, User.class);
        }
        catch(Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    public String addNewUser (User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return this.restTemplate.postForObject(registerUri, user, String.class);
        }
        catch(Exception e){
            logger.error(e.toString());
            return "Network error";
        }
    }

    public String updateUser (User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return this.restTemplate.postForObject(updateUri, user, String.class);
        }
        catch(Exception e){
            logger.error(e.toString());
            return "Network error";
        }
    }
}
