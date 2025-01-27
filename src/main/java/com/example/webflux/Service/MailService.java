package com.example.webflux.Service;

import com.example.webflux.Domain.Career;
import com.example.webflux.Domain.User;
import com.example.webflux.Repository.CareerRepository;
import com.example.webflux.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    private final CareerRepository careerRepository;

    @Value("$smtp.username")
    private String from;

    public Flux<Void> sendMails() {
        Flux<String> tos = userRepository.findAllEmails();
        Flux<Career> careers = careerRepository.findCurDateCareers();

        StringBuilder company = new StringBuilder();
        StringBuilder link = new StringBuilder();
        Flux.from(careers)
                .map(career -> {
                    company.append(career.getCompany());
                    company.append(", ");
                    link.append(career.getCompany());
                    link.append(": ");
                    link.append(career.getLink()+"\n");
                    return null;
                });
        return Flux.from(tos)
                .flatMap(to -> sendMail(to, company.substring(0, -2), link.toString()));
    }

    public Mono<Void> sendMail(String to, String company, String link) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            StringBuilder sb = new StringBuilder();
            sb.append(company);
            sb.append("으로부터 새로운 공고가 작성되었습니다.");
            message.setSubject(sb.toString());
            sb = new StringBuilder();
            sb.append("다음은 공고의 링크입니다.\n" );
            sb.append("자세한 내용은 링크로 접속하여 확인하세요.");
            sb.append("\n\n");
            sb.append(link);
            message.setText(sb.toString());
            message.setFrom(from);

            mailSender.send(message);
        });
    }
}
