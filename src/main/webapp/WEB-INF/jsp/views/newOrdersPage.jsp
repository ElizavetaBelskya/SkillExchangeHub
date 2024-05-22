<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:baseHead title="New orders" scriptLink="/js/tutorOrders.js"/>

<body data-theme="dark">
<header>
    <%@include file="/WEB-INF/includes/userNavbar.jsp" %>
</header>
<c:if test="${orders != null}">
<div id="order-row" class="row">
            <c:forEach var="i" begin="0" end="${orders.size()}">
                <c:if test="${orders.size() > i}">
                <div class="col">
                    <div class="card" id="card-order">
                        <div class="card-body">
                            <h5 class="card-title">Skill: ${orders.get(i).skill}</h5>
                            <p class="card-text">Description: ${orders.get(i).description}</p>
                            <p class="card-text">Minimal rating: ${orders.get(i).minRating}</p>
                            I can help with:
                            <ul>
                                <c:forEach var="j" begin="0" end="${orders.get(i).authorSkills.size() - 1}">
                                <li>${orders.get(i).authorSkills.get(j)}</li>
                                </c:forEach>
                            </ul>
                            <input type="hidden" class="order-id" value="${orders.get(i).id}">
                            <input type="submit" class = "card-btn button is-primary" value="Take it!"/>
                        </div>
                    </div>
                </div>

            </c:if>
                </c:forEach>
<div/>

</c:if>

<c:if test="${orders == null}">
    <t:infoText bigText="There are no suitable orders for you" middleText="Wait for a while, please"/>
</c:if>

<%--                <form action="" method="GET">--%>
<%--                    <div class="order-pagination">--%>
<%--                        <c:if test = "${countOfPages > 1}">--%>
<%--                            <nav aria-label="Page navigation example">--%>
<%--                                <ul class="pagination">--%>
<%--                                    <c:if test = "${page > 1}">--%>
<%--                                        <li class="page-item">--%>
<%--                                            <input type="submit" name="page" class="page-link" value="${page-1}">--%>
<%--                                        </li>--%>
<%--                                    </c:if>--%>
<%--                                    <input type="submit" name="page" class="page-link" value="${page}">--%>
<%--                                    <c:if test="${page < countOfPages}">--%>
<%--                                        <input type="submit" name="page" class="page-link" value="${page+1}">--%>
<%--                                    </c:if>--%>
<%--                                </ul>--%>
<%--                            </nav>--%>
<%--                        </c:if>--%>
<%--                    </div>--%>
<%--                </form>--%>


</body>
</html>
