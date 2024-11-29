@import hexlet.code.model.Url
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
page = page,
content = @`

    <div>
        @if (page.getUrls() != null)

            <div class="container">
                <div class="row">
                    <div class="row justify-content-center">

                        <div class="row">
                            <h2>Список добавленных сайтов</h2>
                        </div>

                        <div class="row">
                            <table class="table">
                                <thead>
                                <th>ID</th>
                                <th>Ссылка</th>
                                <th>Добавлена</th>
                                <th>Последняя проверка</th>
                                <th>Статус</th>
                                </thead>
                                <tbody>
                                @for (Url url : page.getUrls())
                                    <tr>
                                        <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getId()}</a></td>
                                        <td>${url.getName()}</td>
                                        <td>${url.getCreatedAt().toString()}</td>
                                        <td>
                                            @if (page.getLastChecks().get(url.getId()) == null)
                                                Проверок не было
                                            @else
                                                ${page.getLastChecks().get(url.getId()).toString()}
                                            @endif
                                        </td>
                                        <td>
                                            @if (page.getLastStatus().get(url.getId()) == null)
                                                Проверок не было
                                            @else
                                                ${page.getLastStatus().get(url.getId()).toString()}
                                            @endif
                                        </td>
                                    </tr>
                                @endfor
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>

        @endif
    </div>
`
)