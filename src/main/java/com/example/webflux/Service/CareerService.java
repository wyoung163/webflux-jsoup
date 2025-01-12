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

        String[] companies = new String[elements.size()];
        String[] categories = new String[elements.size()];
        String[] teams = new String[elements.size()];
        String[] titles = new String[elements.size()];
        String[] contents = new String[elements.size()];
        String[] links = new String[elements.size()];
        String[] experiences = new String[elements.size()];
        String[] conditions = new String[elements.size()];
        String[] requirements = new String[elements.size()];
        String[] preferences = new String[elements.size()];
        String[] durations = new String[elements.size()];

        int idx = 0;
        for(Element e: elements) {
            String link = e.select("a.card_link").attr("onclick").toString(); //.substring(6, -3);
            links[idx] = link;
            String title = e.select("h4.card_title").text();
            titles[idx] = title;
            String company = title.split("]")[0].replace("[", "");
            companies[idx] = company;
            String[] info = e.select("dd.info_text").text().split(" ");
            String category = info[0];
            categories[idx] = category;
            String team = info[1];
            teams[idx] = team;
            String experience = info[2];
            experiences[idx] = experience;
            String condition = info[3];
            conditions[idx] = condition;
            String duration = info[4];
            durations[idx++] = duration;
        }

        return careerRepository.saveAll(
                companies,
                categories,
                teams,
                titles,
                contents,
                links,
                experiences,
                conditions,
                requirements,
                preferences,
                durations
        );
    }
}
