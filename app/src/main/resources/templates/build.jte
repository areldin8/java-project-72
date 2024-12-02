@import hexlet.code.dto.LinkPage
@import hexlet.code.util.NamedRoutes

@param LinkPage page

@template.layout.page(
page = page,
content = @`

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <style>
            body {
                background-color: #000;
                color: #fff;
            }
            .text-left {
                text-align: left;
            }
        </style>
    </head>

    <div class="container mt-5">
        <h1 class="text-left mb-4">Анализатор страниц</h1>
        <form method="POST" action="${NamedRoutes.urlsPath()}" class="text-left">
            <div class="mb-3">
                <label for="url" class="form-label">Бесплатно проверяйте сайты на SEO пригодность</label>
                <input type="text" id="url" name="url" class="form-control" placeholder="Введите ссылку" value="${page.getLink()}" required />
            </div>
            <button type="submit" class="btn btn-primary">Проверить</button>
        </form>
    </div >
`
)
