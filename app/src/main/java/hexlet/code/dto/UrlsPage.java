package hexlet.code.dto;

import hexlet.code.model.Url;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private Map<Long, LocalDateTime> lastChecks;
    private Map<Long, Integer> lastStatus;
}
