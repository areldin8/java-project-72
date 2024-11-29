@import hexlet.code.dto.LinkPage
@import hexlet.code.util.NamedRoutes

@param LinkPage page

@template.layout.page(
page = page,
content = @`
    <div>
        <head>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        </head>

        <div class="container mt-5">
            <h1 class="text-center">Анализатор страниц</h1>
            <form method="POST" action="${NamedRoutes.urlsPath()}" class="mt-4">
                <div class="mb-3">
                    <label for="urlInput" class="form-label">Сайт для проверки:</label>
                    <input type="text" id="urlInput" name="url" class="form-control" placeholder="введите ссылку" value="${page.getLink()}" required />
                </div>
                <button type="submit" class="btn btn-primary">Добавить</button>
            </form>
        </div>
    </div>
`
)