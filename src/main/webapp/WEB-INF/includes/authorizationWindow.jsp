<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="sign-in-menu" class="modal">
    <form:form action='/main' method='POST' modelAttribute="loginForm">
    <header class="modal-card-head">
        <p class="modal-card-title">Sign in</p>
        <button class="delete" aria-label="close"></button>
    </header>
    <section class="modal-card-body">
        <div class="sign-in-form">
            <form:label path="email" class="form-label">Email address</form:label>
            <form:input path="email" required="true" class="form-control" id="Email1" placeholder="email@example.com"
                        pattern="[A-Za-z0-9-]{2,50}@[a-z]{2,20}.[a-z]{2,4}" value="${email}"/>
        </div>
        <div class="sign-in-form">
            <form:label path="password" class="form-label">Password</form:label>
            <form:password path="password" required="true" class="form-control" id="Password1"
                           pattern="[A-Za-z0-9]{2,50}" title="The password is in incorrect format" placeholder="Password" value=""/>
        </div>

        <c:if test="${error != null}">
            <div class="alert alert-warning" role="alert">${error}</div>
        </c:if>

    </section>

    <footer class="modal-card-foot">
        <div class="buttons">
            <input type="submit" class="button is-success" value='Sign in'/>
        </div>
    </footer>

    </form:form>

</div>
</div>
