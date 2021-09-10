package org.srm.purchasecooperation.cux.infra.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.srm.purchasecooperation.cux.api.dto.UserVO;
import org.srm.purchasecooperation.cux.infra.feign.fallback.PigicIamFeignClientFallbackFactory;

@FeignClient(
        value = "hzero-iam",
        fallbackFactory = PigicIamFeignClientFallbackFactory.class
)
public interface PigicIamFeignClient {

    @GetMapping({"/hzero/v1/users/self"})
    UserVO selectSelf();

    @GetMapping({"/hzero/v1/users/self/detail"})
    UserVO selectSelfDetail();
}