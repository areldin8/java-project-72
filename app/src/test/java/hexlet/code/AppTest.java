package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;
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
import java.util.List;

import static hexlet.code.App.readResourceFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;


public class AppTest {

    private Javalin app;
    private static MockWebServer mockWebServer;
    private static String mockServerUrl;

    private static String strToUtf8(String str) {
        return new String(str.getBytes(), UTF_8);
    }

    @BeforeAll
    public static void startMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockServerUrl = mockWebServer.url("/").toString();
        MockResponse mockResponse = new MockResponse().setBody(readResourceFile("test.html"));
        mockWebServer.enqueue(mockResponse);
    }

    @AfterAll
    public static void shutdownMockServer() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
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
            Assertions.assertTrue(response.body().string()
                    .contains(strToUtf8("Анализатор страниц")));
        });
    }

    @Test

    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            Assertions.assertEquals(response.code(), 200);
            assert response.body() != null;
            Assertions.assertTrue(response.body().string()
                    .contains(strToUtf8("Список добавленных сайтов")));
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

    public void testUrlChecks() throws SQLException {
        Url url = new Url(mockServerUrl);
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            try (var response = client.post(NamedRoutes.urlChecksPath(url.getId()))) {
                assertThat(response.code()).isEqualTo(200);

                List<UrlCheck> checks = UrlCheckRepository.getEntitiesByUrlId(url.getId());
                assertThat(checks).isNotEmpty();

                UrlCheck check = checks.get(0);
                assertThat(check.getTitle()).isEqualTo("Test Title");
            } catch (final Exception th) {
                Assertions.fail("Exception occurred: " + th.getMessage());
            }
        });
    }

}





