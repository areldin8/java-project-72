package hexlet.code.controller;

import hexlet.code.dto.RootPage;
import io.javalin.http.Context;

import static io.javalin.rendering.template.TemplateUtil.model;

public class RootController {
    public static void index(Context context) {
        RootPage page = new RootPage();
        page.setFlash(context.consumeSessionAttribute("flash"));
        page.setLink(context.consumeSessionAttribute("link"));
        context.render("build.jte", model("page", page));
    }
}
