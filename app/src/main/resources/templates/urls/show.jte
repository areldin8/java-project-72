@import hexlet.code.model.UrlCheck
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlPage

@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="container mt-5">
        @if (page.getUrl() != null)
            <div class="row">
                <div class="col text-center">
                    <h2>Обзор</h2>
                </div>
            </div>

            <div class="row">
                <table class="table table-bordered">
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
                <div class="col text-center">
                    <h3>Проверки</h3>
                </div>
            </div>

            <div class="row">
                @if (page.getUrlChecks() != null && !page.getUrlChecks().isEmpty())
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Код ответа</th>
                            <th scope="col">Заголовок</th>
                            <th scope="col">Оглавление</th>
                            <th scope="col">Описание</th>
                            <th scope="col">Дата проверки</th>
                        </tr>
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
                @else
                    <div class="alert alert-warning" role="alert">
                        Нет проверок для данного URL.
                    </div>
                @endif
                <form method="POST" action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" class="mt-3">
                    <button type="submit" class="btn btn-primary">Проверить</button>
                </form>
            </div>
        @endif
    </div>
`
)