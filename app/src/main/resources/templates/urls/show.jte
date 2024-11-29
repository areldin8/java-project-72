@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlPage

@param UrlPage page

@template.layout.page(
page = page,
content = @`

    <div>
        @if (page.getUrl() != null)

            <div class="container">
                <div class="row">
                    <div class="row justify-content-center">

                        <div class="row">
                            <h2>Обзор</h2>
                        </div>

                        <div class="row">
                            <table class="table">
                                <tbody>
                                <tr>
                                    <td>ID</td>
                                    <td>${page.getUrl().getId()}</td>
                                </tr>
                                <tr>
                                    <td>Ссылка</td>
                                    <td>${page.getUrl().getName()}</td>
                                </tr>
                                <tr>
                                    <td>Добавлена</td>
                                    <td>${page.getUrl().getCreatedAt().toString()}</td>
                                </tr>
                                <tr>
                                    <td>Проверок</td>
                                    <td>${page.getUrlChecks().size()}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="row">
                            <h3>Проверки</h3>
                        </div>

                        <div class="row">
                            @if (page.getUrlChecks() != null)
                                <table class="table">
                                    <thead>
                                    <th>ID</th>
                                    <th>Код ответа</th>
                                    <th>Заголовок</th>
                                    <th>Оглавление</th>
                                    <th>Описание</th>
                                    <th>Дата проверки</th>
                                    </thead>
                                    <tbody>
                                    @for (UrlCheck urlCheck : page.getUrlChecks())
                                        <tr>
                                            <td>${urlCheck.getId()}</td>
                                            <td>${urlCheck.getStatusCode()}</td>
                                            <td>${urlCheck.getTitle()}</td>
                                            <td>${urlCheck.getH1()}</td>
                                            <td>${urlCheck.getDescription()}</td>
                                            <td>${urlCheck.getCreatedAt().toString()}</td>
                                        </tr>
                                    @endfor
                                    </tbody>
                                </table>
                            @endif
                            <form method="POST" action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}">
                                <button type="submit" class="btn btn-primary">Проверить</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        @endif
    </div>
`
)