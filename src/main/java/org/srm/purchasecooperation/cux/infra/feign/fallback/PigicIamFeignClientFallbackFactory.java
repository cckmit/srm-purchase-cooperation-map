package org.srm.purchasecooperation.cux.infra.feign.fallback;

import org.srm.purchasecooperation.cux.api.dto.UserVO;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.srm.purchasecooperation.cux.infra.feign.PigicIamFeignClient;

@Component
public class PigicIamFeignClientFallbackFactory implements FallbackFactory<PigicIamFeignClient> {
    private Logger logger = LoggerFactory.getLogger(PigicIamFeignClientFallbackFactory.class);

    public PigicIamFeignClientFallbackFactory() {
    }

    @Override
    public PigicIamFeignClient create(Throwable cause) {
        return new PigicIamFeignClient() {
            @Override
            public UserVO selectSelf() {
                this.logError();
                return new UserVO();
            }

            @Override
            public UserVO selectSelfDetail() {
                this.logError();
                return new UserVO();
            }

            private void logError() {
                PigicIamFeignClientFallbackFactory.this.logger.error("can not get response from hzero-iam, cause by");
                PigicIamFeignClientFallbackFactory.this.logger.error(cause.getMessage(), cause);
            }
        };
    }
}