package com.tech.picpay.challenge.client;

import com.tech.picpay.challenge.client.dto.CheckAuthorizeDTO;
import com.tech.picpay.challenge.client.dto.SendNotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "DeviClient",
        url = "${client.devi.url}"
)
public interface DeviClient {
    @PostMapping("v1/notify")
    ResponseEntity<Void> sendNotification(@RequestBody SendNotificationDTO transfer);

    @GetMapping("v2/authorize")
    ResponseEntity<CheckAuthorizeDTO> checkAuthorize();
}