package com.example.webflux.Controller;

import com.example.webflux.Domain.Career;
import com.example.webflux.Service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class CareerController {
    private final CareerService careerService;

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/naver")
    public Flux<Career> searchNaverCareers() {
        return careerService.getNaverCareers();
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/navercloud")
    public Flux<Career> searchNaverCloudCareers() {
        return careerService.getNaverCloudCareers();
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/line")
    public Flux<Career> searchLineCareers() {
        return careerService.getLineCareers();
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/aws")
    public Flux<Career> searchAWSCareers() {
        return careerService.getAWSCareers();
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/kakao")
    public Flux<Career> searchKakaoCareers() {
        return careerService.getKakaoCareers();
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/coupang")
    public Flux<Career> searchCoupangCareers() {
        return careerService.getCoupangCareers();
    }
}
