package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class UrlChecksController {
    public static void create(Context context) {
        Long urlId = context.pathParamAsClass("id", Long.class).get();
        try {
            Url url = UrlRepository.findById(urlId).orElseThrow(NotFoundResponse::new);
            Connection connection = Jsoup.connect(url.getName());
            Document document = connection.get();
            int statusCode = connection.response().statusCode();
            String title = document.title();
            Element h1Element = document.selectFirst("h1");
            String h1 = (h1Element == null) ? "" : h1Element.text();
            Element descriptionElement = document.selectFirst("meta[name=description]");
            String description = (descriptionElement == null) ? "" : descriptionElement.attr("content");
            UrlCheckRepository.save(UrlCheck.builder()
                    .statusCode(statusCode)
                    .title(title)
                    .h1(h1)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .urlId(urlId)
                    .build());
        } catch (SQLException e) {
            context.sessionAttribute("flash", "Ошибка в работе СУБД: " + e.getMessage());
        } catch (Exception e) {
            context.sessionAttribute("flash", "Ссылка не работает: " + e.getMessage());
        } finally {
            context.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}




