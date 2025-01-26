package com.example.webflux.Service;

import com.example.webflux.Domain.Career;
import com.example.webflux.Repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CareerService {
    private Connection connection;
    private Document document;
    private Elements elements;

    private final CareerRepository careerRepository;

    public Flux<Career> getNaverCareers() throws IOException {
        connection = Jsoup.connect("https://recruit.navercorp.com/rcrt/list.do");
        document = connection.get();
        elements = document.select("ul[class=card_list]").select("li.card_item").select("a.card_link");

        ArrayList<Career> careers = new ArrayList<>();
        for(Element e: elements) {
            String link = e.select("a.card_link").attr("onclick").substring(6, 14);
            String title = e.select("h4.card_title").text();
            String company = title.split("]")[0].replace("[", "");
            Iterator<Element> iterator = e.select("dd.info_text").stream().iterator();
            String category = iterator.next().text();
            String team = iterator.next().text();
            String experience = iterator.next().text();
            String condition = iterator.next().text();
            String duration = iterator.next().text();

            connection = Jsoup.connect("https://recruit.navercorp.com/rcrt/view.do?annoId="+link);
            document = connection.get();
            elements = document.select("div.detail_box");

            System.out.println(link);
            for(Element e2: elements) {
                // cloud사) 하나의 공고 페이지에 직무 여러 개 존재 예외 사항 존재
                if(e2.select("div.detail_togglebox").size() > 0) {
                    System.out.println(e2.select("div.detail_togglebox").select("h4.detail_title").text());
                }
                /*
                 본사) 하나의 공고 페이지에 직무 여러 개 존재 예외 사항 존재
                 똑같은 주제 관련 내용이라도 p와 span, div 등 다양하게 태그를 처리하고 있어 동일 조건으로 파싱이 어려운 문제 존재
                 */
                if(e2.select("p").size() > 14) {
                    Elements ps = e2.select("p");
                    for(Element p: ps) {
                        if(p.text().length() > 0) {
                            System.out.println(p.text());
                        } else {
                            System.out.println(p.text());
                        }
                    }
                } else {
                    System.out.println(e2.text());
                }
            }

            careers.add(
                    Career.builder()
                            .link(link)
                            .title(title)
                            .company(company)
                            .category(category)
                            .team(team)
                            .experience(experience)
                            .conditions(condition)
                            .duration(duration)
                            .build()
            );
        }

        return careerRepository.saveAll(
                careers
        );
    }

    public Flux<Career> getNaverCloudCareers() throws IOException {
        connection = Jsoup.connect("https://recruit.navercloudcorp.com/rcrt/list.do");
        document = connection.get();
        elements = document.select("ul[class=card_list]").select("li.card_item").select("a.card_link");

        ArrayList<Career> careers = new ArrayList<>();
        for(Element e: elements) {
            String link = e.select("a.card_link").attr("onclick").toString();//.substring(6, -3);
            String title = e.select("h4.card_title").text();
            String company = title.split("]")[0].replace("[", "");
            Iterator<Element> iterator = e.select("dd.info_text").stream().iterator();
            String category = iterator.next().text();
            String team = iterator.next().text();
            String experience = iterator.next().text();
            String condition = iterator.next().text();
            String duration = iterator.next().text();

            careers.add(
                    Career.builder()
                            .link(link)
                            .title(title)
                            .company(company)
                            .category(category)
                            .team(team)
                            .experience(experience)
                            .conditions(condition)
                            .duration(duration)
                            .build()
            );
        }

        return careerRepository.saveAll(
                careers
        );
    }

    public Flux<Career> getLineCareers() throws IOException {
        connection = Jsoup.connect("https://careers.linecorp.com/ko/jobs?ca=Engineering&ci=Gwacheon,Bundang&co=East%20Asia");
        document = connection.get();
        elements = document.select("ul[class=job_list]").select("li").select("a");

        ArrayList<Career> careers = new ArrayList<>();
        for(Element e: elements) {
            String link = e.select("a").attr("href").toString();
            String title = e.select("a").select("h3.title").text();
            Iterator<Element> iterator = e.select("a").select("div.text_filter").select("span").stream().iterator();
            String location = iterator.next().text();
            String company = iterator.next().text();
            String category = iterator.next().text();
            String team = ""; //= iterator.next().text();
            String experience = ""; //= iterator.next().text();
            String condition = iterator.next().text();
            String duration = e.select("a").select("span.date").text();

            careers.add(
                    Career.builder()
                            .link(link)
                            .title(title)
                            .company(company)
                            .category(category)
                            .team(team)
                            .experience(experience)
                            .conditions(condition)
                            .duration(duration)
                            .build()
            );
        }

        return careerRepository.saveAll(
                careers
        );
    }
}
