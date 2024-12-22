package hexlet.code.controller;

import hexlet.code.dto.LinkPage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import java.util.Map;
import java.util.stream.Collectors;

import io.javalin.http.NotFoundResponse;
import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {

    public static void build(Context context) {
        var page = new LinkPage();
        page.setFlash(context.consumeSessionAttribute("flash"));
        page.setLink(context.consumeSessionAttribute("link"));
        context.render("build.jte", model("page", page));
    }


    public static void index(Context context) {
        UrlsPage page = new UrlsPage();
        try {
            page.setUrls(UrlRepository.getEntities());
            var lastChecks = UrlCheckRepository.getLastChecks();
            Map<Long, UrlCheck> lastStatus = lastChecks.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                            ));
            page.setUrlChecks(lastStatus);

            context.render("urls/index.jte", model("page", page));
        } catch (SQLException e) {
            page.setFlash("Ошибка в работе СУБД");
            context.render("urls/index.jte", model("page", page));
        }
    }

    public static void show(Context context) {
        UrlPage page = new UrlPage();
        try {
            Long id = context.pathParamAsClass("id", Long.class).get();
            page.setUrl(UrlRepository.findById(id)
                    .orElseThrow(() -> new NotFoundResponse("URL с id = " + id + " не найден")));
            page.setUrlChecks(UrlCheckRepository.getEntitiesByUrlId(id));

            page.setFlash(context.consumeSessionAttribute("flash"));
            context.render("urls/show.jte", model("page", page));
        } catch (NotFoundResponse e) {
            context.sessionAttribute("flash", "URL не найден" + e.getMessage());
            context.redirect(NamedRoutes.rootPath());
        } catch (SQLException e) {
            context.sessionAttribute("flash", "Ошибка в работе СУБД: " + e.getMessage());
            page.setFlash(context.consumeSessionAttribute("flash"));
            context.render("urls/show.jte", model("page", page));
        } catch (Exception e) {
            context.sessionAttribute("flash", "Произошла ошибка: " + e.getMessage());
            page.setFlash(context.consumeSessionAttribute("flash"));
            context.render("urls/show.jte", model("page", page));
        }
    }

    public static void create(Context context) {
        String rawLink = context.formParamAsClass("url", String.class).get().toLowerCase().trim();
        context.sessionAttribute("link", rawLink);

        if (!isValidUrl(rawLink)) {
            context.sessionAttribute("flash",
                    "Неверная ссылка: допустимы только буквы, цифры, точки и символы / : ? # &");
            context.redirect(NamedRoutes.rootPath());
            return; // Не выполняем редирект, просто возвращаем
        }

        try {
            URL linkUrl = new URI(rawLink).toURL();
            rawLink = linkUrl.getProtocol() + "://" + linkUrl.getHost()
                    + (linkUrl.getPort() != -1 ? ":" + linkUrl.getPort() : "");

            if (UrlRepository.findByName(rawLink).isPresent()) {
                context.sessionAttribute("flash", "Ссылка уже содержится");
                context.redirect(NamedRoutes.rootPath());
                return;
            }

            UrlRepository.save(new Url(rawLink));
            context.sessionAttribute("flash", "Ссылка успешно добавлена");
            context.consumeSessionAttribute("link");
            context.redirect(NamedRoutes.urlsPath());

        } catch (URISyntaxException | MalformedURLException e) {
            context.sessionAttribute("flash", "Неверная ссылка");
            context.redirect(NamedRoutes.rootPath());
        } catch (SQLException e) {
            context.sessionAttribute("flash", "Ошибка в работе СУБД");
            context.redirect(NamedRoutes.rootPath());
        }
    }

    private static boolean isValidUrl(String url) {
        // Регулярное выражение для проверки допустимых символов
        return url.matches("^[a-zA-Z0-9:/?.#&=]+$");
    }

}



