@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.RootPage

@param RootPage page

@template.layout.page(
page = page,
content = @`


    <div>
        <form method="POST" action="${NamedRoutes.urlsPath()}">
            <div class="container">
                <div class="row">
                    <div class="row justify-content-center">

                        <div>
                            <h1>Анализатор страниц</h1>
                        </div>

                        <div class="row">
                            <label> Сайт для проверки:<input type="text" name="url" placeholder="введите ссылку" value="${page.getLink()}" required />
                                <button type="submit" class="btn btn-primary">Добавить</button>
                            </label>
                        </div>

                    </div>
                </div>
            </div>
        </form>
    </div>
`
)
