<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<t:baseHead title="Tutor profile"/>
<body data-theme="dark">
<header>
    <security:authorize access="hasAuthority('USER')">
        <%@ include file="/WEB-INF/includes/userNavbar.jsp" %>
    </security:authorize>
    <security:authorize access="isAnonymous()">
        <%@ include file="/WEB-INF/includes/anonNavbar.jsp" %>
    </security:authorize>
</header>
<div id = "placeholder">
</div>
<div class="container h-100" id="reg-container">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-9 col-lg-7 col-xl-6">
            <div class="card reg-card">
                <div class="card-body p-5">
                    <h3 class="text-uppercase text-center mb-5">${account.name}</h3>

                    <div>
                        <p>
                            Name: ${account.name}
                        </p>
                        <p>
                            Rating: ${user.rating}
                        </p>
                        <p>
                            Gender: ${user.gender}
                        </p>
                        <p>
                            Description: ${user.description}
                        </p>
                        <p>
                            Total number of authors: ${buddiesCount}
                        </p>
                        <p>
                            skills:
                        <ul>
                            <c:forEach var="entry" items="${mapSkillToAmount}">
                                <li>${entry.key} - ${entry.value} buddies</li>
                            </c:forEach>
                        </ul>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${reviewsList != null}">
    <c:forEach var="review" items="${reviewsList}">
        <t:comment author="${review.author.account.name}" rate="${review.rating}" comment="${review.comment}"/>
    </c:forEach>
</c:if>

</body>
</html>

