package com.example.webflux.Controller;

import com.example.webflux.Domain.Career;
import com.example.webflux.Service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CareerController {
    @Autowired
    private final CareerService careerService;

    @GetMapping("/career/all")
    public Flux<Career> searchAllCareers() throws IOException {
        Flux<Career> allCareers;
        allCareers = careerService.getNaverCareers();
        allCareers = careerService.getNaverCloudCareers();
        allCareers = careerService.getLineCareers();

        return allCareers;
    }

    //@Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/naver")
    public Flux<Career> searchNaverCareers() throws IOException {
        return careerService.getNaverCareers();
    }

    //@Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/navercloud")
    public Flux<Career> searchNaverCloudCareers() throws IOException {
        return careerService.getNaverCloudCareers();
    }

    //@Scheduled(cron = "0 0 0/6 * * *")
    @GetMapping("/career/line")
    public Flux<Career> searchLineCareers() throws IOException{
        return careerService.getLineCareers();
    }
//
//    @Scheduled(cron = "0 0 0/6 * * *")
//    @GetMapping("/career/aws")
//    public Flux<Career> searchAWSCareers() {
//        return careerService.getAWSCareers();
//    }
//
//    @Scheduled(cron = "0 0 0/6 * * *")
//    @GetMapping("/career/kakao")
//    public Flux<Career> searchKakaoCareers() {
//        return careerService.getKakaoCareers();
//    }
//
//    @Scheduled(cron = "0 0 0/6 * * *")
//    @GetMapping("/career/coupang")
//    public Flux<Career> searchCoupangCareers() {
//        return careerService.getCoupangCareers();
//    }
}
