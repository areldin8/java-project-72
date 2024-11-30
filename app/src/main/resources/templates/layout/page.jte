@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.BasePage

@param Content content
@param BasePage page = null

<!DOCTYPE HTML>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAq046MgnoM88zW1RWuH61DGLWZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBI4zVFs8G1M5b4hcpxyD9F7jL+jjxkk+Q2h455rYXK/7HAUOJ1+814" crossorigin="anonymous"></script>
    <title>Анализатор страниц</title>
</head>
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${NamedRoutes.rootPath()}">Анализатор страниц</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${NamedRoutes.rootPath()}">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-3">
    @if (page != null && page.getFlash() != null)
        <div class="alert alert-info">
            ${page.getFlash()}
        </div>
    @endif

    <main>
        ${content}
    </main>
</div>

<footer class="footer border-top py-3 mt-5 bg-light">
    <div class="container text-center">
        <span class="text-muted">© 2024 Анализатор страниц</span>
    </div>
</footer>
</body>
</html>