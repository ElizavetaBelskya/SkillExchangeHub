<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="${spring:mvcUrl('MC#login').build()}">
            SkillExchangeHub
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">

        <div class="navbar-end">
            <div class="navbar-item">
                <div class="buttons">
                    <a href="${spring:mvcUrl('MC#register').build()}" class="button is-light">
                        <strong>Sign up</strong>
                    </a>
                    <span id="log-in-btn" class="button is-primary">
                        <strong>Log in</strong>
                    </span>
                </div>
            </div>
        </div>
    </div>
</nav>