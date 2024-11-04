package hexlet.code;


import io.javalin.Javalin;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppTest {
    private static Javalin appTest;
    private static final OkHttpClient APP_CLIENT = new OkHttpClient();

    @BeforeAll
    public static void setUp() {
        appTest = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        appTest.get("/", ctx -> ctx.result("Hello World"));
        appTest.start(7070);
    }

    @AfterAll
    public static void tearDown() {
        appTest.stop();
    }

    @Test
    public void testHelloWorldEndpoint() throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:7070/")
                .build();

        try (Response response = APP_CLIENT.newCall(request).execute()) {
            assertEquals("Hello World", response.body().string());
        }
    }
}


