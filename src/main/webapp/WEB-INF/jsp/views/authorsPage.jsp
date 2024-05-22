<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:baseHead title="My friends" scriptLink="/js/candidatesListScript.js"/>
<body data-theme="dark">
<header>
    <%@include file="/WEB-INF/includes/userNavbar.jsp" %>
</header>
<c:if test="${orders != null}">
<div id="carousel-author" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <c:forEach var="i" begin="0" end="${orders.size()-1}" step="3">
        <c:if test = "${i == 0}">
        <div class="carousel-item active">
            </c:if>
            <c:if test = "${i != 0}">
            <div class="carousel-item">
                </c:if>
                <div class="row">
                    <div class="col">
                        <div class="card order-card-for-delete">
                            <div class="card-body">
                                <h6 class="card-subtitle mb-2 text-muted">${"Order #".concat(orders.get(i).getId().toString())}</h6>
                                <h5 class="card-title order-card-editable">
                                    <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${accounts.get(i).getId()}">
                                            ${accounts.get(i).getName()}
                                    </a>
                                </h5>
                                <h6 class="card-subtitle mb-2 text-muted">${authors.get(i).getPhone()}</h6>
                                <h6 class="card-subtitle mb-2 text-muted">${authors.get(i).getEmail()}</h6>
                                <ul class="card-subtitle mb-2 text-muted">
                                    <li class = "list-group-item">${orders.get(i).getSkill()}</li>
                                </ul>
                                <form action="" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="reject" value="${orders.get(i).getId()}">
                                    <button type="submit" value="Reject" class="button is-danger is-outlined">
                                        <span>Reject</span>
                                    </button>
                                </form>

                                <button class="button btn-outline-info rate" value="${accounts.get(i).getId()}">Rate</button>
                            </div>
                        </div>
                    </div>
                    <c:if test="${i+1 < orders.size()}">
                        <div class="col">
                            <div class="card order-card-for-delete">
                                <div class="card-body">
                                    <h6 class="card-subtitle mb-2 text-muted">${"Order #".concat(orders.get(i+1).getId().toString())}</h6>
                                    <h5 class="card-title order-card-editable">
                                        <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${accounts.get(i+1).getId()}">
                                                ${accounts.get(i+1).getName()}
                                        </a>
                                    </h5>
                                    <h6 class="card-subtitle mb-2 text-muted">${authors.get(i+1).getPhone()}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">${authors.get(i+1).getEmail()}</h6>
                                    <ul class="card-subtitle mb-2 text-muted">
                                        <li class = "list-group-item">${orders.get(i+1).getSkill()}</li>
                                    </ul>
                                    <form action="" method="post">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="reject" value="${orders.get(i+1).getId()}">
                                        <button type="submit" value="Reject" class="button is-danger is-outlined">
                                            <span>Reject</span>
                                        </button>
                                    </form>

                                    <button class="button btn-outline-info rate" value="${accounts.get(i+1).getId()}">Rate</button>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${i+2 < orders.size()}">
                        <div class="col">
                            <div class="card order-card-for-delete">
                                <div class="card-body">
                                    <h6 class="card-subtitle mb-2 text-muted">${"Order #".concat(orders.get(i+2).getId().toString())}</h6>
                                    <h5 class="card-title order-card-editable">
                                        <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${accounts.get(i+2).getId()}">
                                                ${accounts.get(i+2).getName()}
                                        </a>
                                    </h5>
                                    <h6 class="card-subtitle mb-2 text-muted">${authors.get(i+2).getPhone()}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">${authors.get(i+2).getEmail()}</h6>
                                    <ul class="card-subtitle mb-2 text-muted">
                                        <li class = "list-group-item">${orders.get(i+2).getSkill()}</li>
                                    </ul>
                                    <form action="" method="post">
                                        <input type="hidden" name="reject" value="${orders.get(i+2).getId()}">
                                        <button type="submit" value="Reject" class="button  is-danger is-outlined">
                                            <span>Reject</span>
                                        </button>
                                    </form>

                                    <button class="button btn-outline-info rate" value="${accounts.get(i+2).getId()}">Rate</button>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
            </c:forEach>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carousel-author" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carousel-author" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>
</c:if>

<c:if test="${orders == null}">
    <t:infoTextWithButton text="There are no answers to your orders yet" action="Orders" link="${spring:mvcUrl('AC#getAuthorOrders').build()}"/>
</c:if>


<c:if test="${approvedRecipients != null && approvedRecipients.size() > 0}">
    <h3 class="candidates-title">Your friends (you are initiator)</h3>
    <div id="carousel-approved-tutors" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <c:forEach var="i" begin="0" end="${approvedRecipients.size()-1}" step="3">
            <c:if test = "${i == 0}">
            <div class="carousel-item active">
                </c:if>
                <c:if test = "${i != 0}">
                <div class="carousel-item">
                    </c:if>
                    <div class="row">
                        <div class="col">
                            <div class="card candidate-card">
                                <div class="card-body">
                                    <h5 class="card-title order-card-editable">
                                        <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${approvedRecipients.get(i).getId()}">
                                                ${approvedRecipients.get(i).getAccount().getName()}
                                        </a></h5>
                                    <h6 class="card-subtitle mb-2 text-muted">Rating: ${approvedRecipients.get(i).getRating()}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">Email: ${approvedRecipients.get(i).getEmail()}</h6>
                                    <h6 class="card-subtitle mb-2 text-muted">Phone: ${approvedRecipients.get(i).getPhone()}</h6>
                                    <form action="/author/my_recipients?action=reject" method="post">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="rejectId" value="${approvedRecipients.get(i).getId()}">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" value="Reject" class="button is-danger is-outlined">
                                            <span>Reject</span>
                                        </button>
                                    </form>

                                    <button class="button  btn-outline-info rate" value="${approvedRecipients.get(i).getId()}">Rate</button>
                                </div>
                            </div>
                        </div>
                        <c:if test="${i+1 < approvedRecipients.size()}">
                            <div class="col">
                                <div class="card candidate-card">
                                    <div class="card-body">
                                        <h5 class="card-title order-card-editable">
                                            <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${approvedRecipients.get(i+1).getId()}">
                                                    ${approvedRecipients.get(i+1).getAccount().getName()}
                                            </a>
                                        </h5>
                                        <h6 class="card-subtitle mb-2 text-muted">Rating: ${approvedRecipients.get(i+1).getRating()}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted">Email: ${approvedRecipients.get(i+1).getDescription()}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted">Phone: ${approvedRecipients.get(i+1).getPhone()}</h6>
                                        <form action="/author/my_recipients?action=reject" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="rejectId" value="${approvedRecipients.get(i+1).getId()}">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" value="Reject" class="button is-danger is-outlined">
                                                <span>Reject</span>
                                            </button>
                                        </form>

                                        <button class="button btn-outline-info rate" value="${approvedRecipients.get(i+1).getId()}">Rate</button>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${i+2 < approvedRecipients.size()}">
                            <div class="col">
                                <div class="card candidate-card">
                                    <div class="card-body">
                                        <h5 class="card-title order-card-editable">
                                            <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${approvedRecipients.get(i+2).getId()}">
                                                    ${approvedRecipients.get(i+2).getAccount().getName()}
                                            </a>
                                        </h5>
                                        <h6 class="card-subtitle mb-2 text-muted">Rating: ${approvedRecipients.get(i+2).getRating()}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted">Email: ${approvedRecipients.get(i+2).getDescription()}</h6>
                                        <h6 class="card-subtitle mb-2 text-muted">Phone: ${approvedRecipients.get(i+2).getPhone()}</h6>

                                        <form action="/author/my_recipients?action=reject" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="rejectId" value="${approvedRecipients.get(i+2).getId()}">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" value="Reject" class="button is-danger is-outlined">
                                                <span>Reject</span>
                                            </button>
                                        </form>
                                        <button class="button btn-outline-info rate" value="${approvedRecipients.get(i+2).getId()}">Rate</button>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
                </c:forEach>


            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carousel-approved-tutors" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carousel-approved-tutors" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
</c:if>
<c:if test="${uncompletedOrders.size() > 0}">
    <c:forEach var="j" begin="0" end="${uncompletedOrders.size()-1}">
        <c:if test="${candidatesLists.get(j).size() > 0}">
            <h3 class="candidates-title">Candidates for order # ${uncompletedOrders.get(j).getId()}</h3>
            <div id="carousel-candidates-${j}" class="carousel slide carousel-candidates" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:forEach var="i" begin="0" end="${candidatesLists.get(j).size()-1}" step="3">
                    <c:if test = "${i == 0}">
                    <div class="carousel-item active">
                        </c:if>
                        <c:if test = "${i != 0}">
                        <div class="carousel-item">
                            </c:if>
                            <div class="row">
                                <div class="col">
                                    <div class="card candidate-card">
                                        <div class="card-body">
                                            <h5 class="card-title order-card-editable">
                                                <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${candidatesLists.get(j).get(i).getId()}">
                                                        ${candidatesLists.get(j).get(i).getAccount().getName()}
                                                </a></h5>
                                            <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i).getRating()}</h6>
                                            <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i).getDescription()}</h6>
                                            <form action="/author/my_recipients?action=choose" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="chooseOrderId" value="${uncompletedOrders.get(j).getId()}">
                                                <input type="hidden" name="chooseRecipientId" value="${candidatesLists.get(j).get(i).getId()}">
                                                <input type="submit" value="Choose" class="btn btn-outline-info"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${i+1 < candidatesLists.get(j).size()}">
                                    <div class="col">
                                        <div class="card candidate-card">
                                            <div class="card-body">
                                                <h5 class="card-title order-card-editable">
                                                    <a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${candidatesLists.get(j).get(i+1).getId()}">
                                                            ${candidatesLists.get(j).get(i+1).getAccount().getName()}
                                                    </a></h5>
                                                <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i+1).getRating()}</h6>
                                                <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i+1).getDescription()}</h6>
                                                <form action="/author/my_recipients?action=choose" method="post">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                    <input type="hidden" name="chooseOrderId" value="${uncompletedOrders.get(j).getId()}">
                                                    <input type="hidden" name="chooseRecipientId" value="${candidatesLists.get(j).get(i+1).getId()}">
                                                    <input type="submit" value="Choose" class="btn btn-outline-info"/>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${i+2 < candidatesLists.get(j).size()}">
                                    <div class="col">
                                        <div class="card candidate-card">
                                            <div class="card-body">
                                                <<h5 class="card-title order-card-editable"><a href="${spring:mvcUrl('MC#getRecipientProfile').build()}${candidatesLists.get(j).get(i+2).getId()}">
                                                    ${candidatesLists.get(j).get(i+2).getAccount().getName()}</a></h5>
                                                <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i+2).getRating()}</h6>
                                                <h6 class="card-subtitle mb-2 text-muted">${candidatesLists.get(j).get(i+2).getDescription()}</h6>
                                                <form action="/author/my_recipients?action=choose" method="post">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                    <input type="hidden" name="chooseOrderId" value="${uncompletedOrders.get(j).getId()}">
                                                    <input type="hidden" name="chooseRecipientId" value="${candidatesLists.get(j).get(i+2).getId()}">
                                                    <input type="submit" value="Choose" class="btn btn-outline-info"/>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        </c:forEach>

                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#carousel-candidates-${j}" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#carousel-candidates-${j}" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
        </c:if>
    </c:forEach>
</c:if>

<div class="modal" id="rate-modal" data-theme="light">
    <div class="modal-background"></div>
    <div class="modal-content">

        <div class="modal-header">
            Rate professional
        </div>
        <div class="modal-body">
            <div class="stars">
                <div class="star active"></div>
                <div class="star"></div>
                <div class="star"></div>
                <div class="star"></div>
                <div class="star"></div>
            </div>
        </div>

        <div class="form-group">
            <label for="comment-text" class="form-label">Comment:</label>
            <textarea class="textarea" placeholder="Write your comment to review" id="comment-text" name="comment-text" maxLength="120"></textarea>
        </div>

        <div>
            <form action="/author/my_recipients?action=rated" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" id="id-rated" name="idRated" value=""/>
                <input type="hidden" id = "star-count" name="starCount" value="0"/>
                <input type="hidden" id = "comment" name="comment" value=""/>
                <input type="hidden" name="action" value="rated">
                <input type="submit" value="Save" class="button is-info is-rounded"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>
