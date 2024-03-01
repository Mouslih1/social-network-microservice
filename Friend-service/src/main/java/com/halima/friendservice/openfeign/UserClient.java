package com.halima.friendservice.openfeign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "http://localhost:8083/api/v1/users/")
public interface UserClient {

}
