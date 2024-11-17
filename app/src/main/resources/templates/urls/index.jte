@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage
@import hexlet.code.util.Utils
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <h1>Сайты</h1>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Имя</th>
            <th scope="col">Последняя проверка</th>
            <th scope="col">Код ответа</th>
        </tr>
        </thead>
        <tbody>
        @for(var url : page.getUrls())
            <tr>
                <td>${url.getId()}</td>
                <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                @if(!url.getUrlChecks().isEmpty())
                    <td>${Utils.formatDate(url.getUrlChecks().getFirst().getCreatedAt())}</td>
                    <td>${url.getUrlChecks().getFirst().getStatusCode()}</td>
                @else
                    <td></td>
                    <td></td>
                @endif
            </tr>
        @endfor
        </tbody>
    </table>
`
)