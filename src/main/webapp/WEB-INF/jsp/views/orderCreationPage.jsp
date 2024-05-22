<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:baseHead title="Create order" scriptLink="/js/orderCreationScript.js"/>
<body data-theme="dark">
<header>
    <%@include file="/WEB-INF/includes/userNavbar.jsp" %>
</header>
<div class="container">
    <div class="row main-form">
        <div class="card" id="order-creation-card" >
            <div class="card-body">
                <h5 class="card-title">Please, fill in all fields</h5>

                <form:form method="POST" modelAttribute="order">

                    <form:label  path="skill">Skill</form:label>
                    <br>

                    <div class="field">
                        <p class="control has-icons-left">
                                <span class="select">
                                  <form:select path="skill" items="${skills}"/>
                                </span>

                            <span class="icon is-small is-left">
                              <i class="fas fa-globe"></i>
                            </span>
                        </p>
                    </div>

                    <br>


                    <div class="control">
                        <label class="radio">
                            <form:radiobutton path="gender" value="MALE"/>
                            Male
                        </label>
                        <label class="radio">
                            <form:radiobutton path="gender" value="FEMALE"/>
                            Female
                        </label>
                        <label class="radio">
                            <form:radiobutton path="gender" value="BOTH" checked="true"/>
                            Both
                        </label>
                    </div>

                    <br>


                    <label class="checkbox">
                        <form:checkbox path="rating"/>
                        Only with a rating greater than 4.0
                    </label>

                    <form:errors path="rating" />
                    <br>

                    <form:label path="description">
                        Description
                    </form:label>
                    <br>


                    <form:textarea class="textarea" maxLength="120" path="description"/>


                    <form:errors path="description" />
                    <br>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="submit" class="button" value='Create' />

                </form:form>

            </div>
        </div>

    </div>
</div>


<c:if test="${answer != null}">
    <t:modal answer="${answer}" answerTitle = "${answerTitle}"/>
</c:if>

</div>

</body>
</html>


