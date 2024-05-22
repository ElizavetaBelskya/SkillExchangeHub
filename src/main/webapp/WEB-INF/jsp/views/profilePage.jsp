<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:baseHead title="My profile"/>
<body data-theme="dark">
<header>
  <%@include file="/WEB-INF/includes/userNavbar.jsp" %>
</header>
<div id = "placeholder">
</div>
<div class="container h-100" id="reg-container">
  <div class="row d-flex justify-content-center align-items-center h-100">
    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
      <div class="card reg-card">
        <div class="card-body p-5">
          <h2 class="text-uppercase text-center mb-5">Your profile</h2>

          <div>
            <p>
              Name: ${account.name}
            </p>
            <p>
              Email: ${user.email}
            </p>
            <p>
              Phone: ${user.phone}
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
              Total number of buddies: ${buddiesCount}
            </p>
            <p>
              Skills:
              <ul>
              <c:forEach var="entry" items="${mapSkillToAmount}">
                <li>${entry.key} - ${entry.value} buddies </li>
              </c:forEach>
          </ul>
            </p>
          </div>
          <div class="button-row">
            <form method="GET" action="<c:url value='/logout'/>">
              <input type='submit' class="button is-primary is-dark" value='Log out'/>
            </form>

            <form method="POST" action="${spring:mvcUrl('RC#deleteProfile').build()}">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <input type='submit' class="button is-primary is-dark" value='Delete account'/>
            </form>

          </div>

          <form method="POST" action="#">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-floating form-description">
              <input type="text" name="description" class="textarea" placeholder="Leave a comment here" id="floatingTextarea2" value="${user.description}"/>
              <label for="floatingTextarea2">Profile description</label>
            </div>
            <input type='submit' class="button is-primary is-dark" value='Update description'/>
          </form>

        </div>
      </div>
    </div>
  </div>
</div>


<c:if test="${reviewsList != null}">
  <c:forEach var="review" items="${reviewsList}">
    <t:comment author="author #${review.author.id}" rate="${review.rating}" comment="${review.comment}"/>
  </c:forEach>
</c:if>


</body>

</html>

