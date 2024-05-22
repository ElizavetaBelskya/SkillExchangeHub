<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="navbar-item" href="${spring:mvcUrl('MC#login').build()}">
            SkillExchangeHub
        </a>

        <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item" href="${spring:mvcUrl('RC#getMyAuthors').build()}">
                My friends
            </a>

            <a class="navbar-item" href="${spring:mvcUrl('RC#getOrders').build()}">
                New orders for me
            </a>


            <a class="navbar-item" aria-current="page"
                   href="${spring:mvcUrl('AC#addOrderGet').build()}">Create a new order</a>

            <a class="navbar-item" aria-current="page"
                   href="${spring:mvcUrl('AC#getAuthorOrders').build()}">Created orders</a>

            <a class="navbar-item" aria-current="page"
                   href="${spring:mvcUrl('RC#getProfile').build()}">My profile</a>

        </div>

        <div class="navbar-end">
            <div class="navbar-item">
                <div class="buttons">
                    <a href="/logout" class="button is-primary">
                        <strong>Logout</strong>
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>