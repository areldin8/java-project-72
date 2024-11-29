@import gg.jte.Content
@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.BasePage

@param Content content
@param BasePage page = null

<!DOCTYPE HTML>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Анализатор страниц</title>
</head>
<body>
<nav class="navbar navbar-expand-lg">
    <div>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.rootPath()}">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
            </li>
        </ul>
    </div>
</nav>

<div>
    @if (page != null && page.getFlash() != null)
        <div class="alert">
            ${page.getFlash()}
        </div>
    @endif
</div>

<main>
    ${content}
</main>
</body>
</html>