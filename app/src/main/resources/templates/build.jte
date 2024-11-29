@import hexlet.code.dto.LinkPage
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.LinkPage

@param LinkPage page

@template.layout.page(
page = page,
content = @`

    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </head>


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
