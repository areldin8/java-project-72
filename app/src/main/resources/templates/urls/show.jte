@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.Utils
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <h1>Сайт: ${page.getUrl().getName()}</h1>
    <table class="table table-bordered">
        <tr>
            <td>ID</td>
            <td>${page.getUrl().getId()}</td>
        </tr>
        <tr>
            <td>Имя</td>
            <td>${page.getUrl().getName()}</td>
        </tr>
        <tr>
            <td>Дата создания</td>
            <td>${Utils.formatDate(page.getUrl().getCreatedAt())}</td>
        </tr>
    </table>
    <h1>Проверки</h1>
    <form method="post" action="${NamedRoutes.urlsCheckPath(page.getUrl().getId())}">
        <button type="submit" class="btn btn-primary">Запустить проверку</button>
    </form>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Код ответа</th>
            <th scope="col">title</th>
            <th scope="col">h1</th>
            <th scope="col">description</th>
            <th scope="col">Дата проверки</th>
        </tr>
        </thead>
        <tbody>
        @for(var urlCheck : page.getUrl().getUrlChecks())
            <tr>
                <td>${urlCheck.getId()}</td>
                <td>${urlCheck.getStatusCode()}</td>
                <td>${urlCheck.getTitle()}</td>
                <td>${urlCheck.getH1()}</td>
                <td>${urlCheck.getDescription()}</td>
                <td>${Utils.formatDate(urlCheck.getCreatedAt())}</td>
            </tr>
        @endfor
        </tbody>
    </table>
`
)