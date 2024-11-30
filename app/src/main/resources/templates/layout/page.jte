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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <title>Анализатор страниц</title>
    <style>
        body {
            background-color: #f8f9fa; /* Светлый фон */
        }
        .navbar {
            background-color: #343a40; /* Темный фон для навигации */
        }
        .navbar-nav .nav-link {
            color: #ffffff; /* Белый цвет текста */
        }
        .alert {
            margin: 20px;
        }
        main {
            padding: 20px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
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

<div class="container mt-3>
    @if (page != null && page.getFlash() != null)
        <div class="alert alert-info">
            ${page.getFlash()}
        </div>
    @endif
</div>

<main class="container>
    ${content}
</main>
</body>
</html>