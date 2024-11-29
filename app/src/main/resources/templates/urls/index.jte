@import hexlet.code.model.Url
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <div class="container mt-5">
        @if (page.getUrls() != null && !page.getUrls().isEmpty())
            <div class="row">
                <div class="col text-center">
                    <h2>Список добавленных сайтов</h2>
                </div>
            </div>

            <div class="row">
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Ссылка</th>
                        <th scope="col">Добавлена</th>
                        <th scope="col">Последняя проверка</th>
                        <th scope="col">Статус</th>
                    </tr>
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
        @else
            <div class="alert alert-info" role="alert">
                Нет добавленных сайтов.
            </div>
        @endif
    </div>
`
)