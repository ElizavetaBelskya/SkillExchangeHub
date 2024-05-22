<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<t:baseHead title="Error"/>
<body data-theme="dark">
<header>
<nav class="navbar navbar-expand-lg">
    <div class="container-fluid">

        <a class="navbar-brand" href="${spring:mvcUrl('MC#login').build()}">
            <img src="<c:url value="/images/free-icon-studying-1903172.png"/>" alt="IE" width="40" height="40">
        </a>

        <a class="navbar-brand text-white" href="${spring:mvcUrl('MC#login').build()}">SkillExchangeHub</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    </div>
</nav>
</header>


<div class="alert alert-danger" role="alert">
    ${alert}
</div>

</body>
</html>
