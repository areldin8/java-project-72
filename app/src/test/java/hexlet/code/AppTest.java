package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.sql.SQLException;


public class AppTest {

    private Javalin app;
    private static MockWebServer mockWebServer;
    private static String mockServerUrl;


    @BeforeAll
    public static void startMockServer() {
        mockWebServer = new MockWebServer();
        mockServerUrl = mockWebServer.url("/").toString();
        String mockServerBody = "<html><head><title>Test Title</title></head><body></body></html>";
        mockWebServer.enqueue(new MockResponse().setBody(mockServerBody));
    }

    @AfterAll
    public static void shutdownMockServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public final void setUp() {
        try {
            app = App.getApp();
        } catch (Exception e) {
            System.out.println("test exception: " + e.getMessage());
        }
    }

    @AfterEach
    public final void closeBase() {
        if (BaseRepository.dataSource != null) {
            BaseRepository.dataSource.close();
        }
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            Assertions.assertEquals(response.code(), 200);
            assert response.body() != null;
            Assertions.assertTrue(response.body().string().contains("Анализатор страниц"));
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            Assertions.assertEquals(response.code(), 200);
            assert response.body() != null;
            Assertions.assertTrue(response.body().string().contains("Список добавленных сайтов:"));
        });
    }

    @Test
    public void testCreateUrlWrong() {
        JavalinTest.test(app, (server, client) -> {
            var createResponse = client.post(NamedRoutes.urlsPath(), "url=testcom");
            Assertions.assertEquals(createResponse.code(), 200);
            var showResponse = client.get(NamedRoutes.urlPath(1));
            Assertions.assertEquals(showResponse.code(), 200);
            assert showResponse.body() != null;
            Assertions.assertFalse(showResponse.body().string().contains("testcom"));
        });
    }

    @Test

    public void testCreateUrlRight() {
        JavalinTest.test(app, (server, client) -> {
            var createResponse = client.post(NamedRoutes.urlsPath(), "url=https://github.com");
            Assertions.assertEquals(createResponse.code(), 200,
                    "Expected status code 200 but got: " + createResponse.code());
            var showResponse = client.get(NamedRoutes.urlPath(1));
            Assertions.assertEquals(showResponse.code(), 200,
                    "Expected status code 200 but got: " + showResponse.code());
            assert showResponse.body() != null;
            String responseBody = showResponse.body().string();
            Assertions.assertTrue(responseBody.contains("https://github.com"),
                    "Response body did not contain expected URL.");
        });

    }

    @Test
    public void testUrlChecks() {
        try {
            UrlRepository.save(Url.builder().name(mockServerUrl).build());
        } catch (SQLException exception) {
            throw new AssertionError();
        }
        JavalinTest.test(app, (server, client) -> {
            Url url = UrlRepository.findByName(mockServerUrl).orElseThrow(AssertionError::new);
            var addCheckResponse = client.post(NamedRoutes.urlChecksPath(url.getId()));
            Assertions.assertEquals(addCheckResponse.code(), 200);
            assert addCheckResponse.body() != null;
            Assertions.assertTrue(addCheckResponse.body().string().contains("Test Title"));
        });

    }

}


