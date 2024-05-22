<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<t:baseHead title="SkillExchangeHub" scriptLink="/js/mainScript.js"/>

<body data-theme="dark">

<header>

    <security:authorize access="hasAuthority('USER')">
        <%@ include file="/WEB-INF/includes/userNavbar.jsp" %>
    </security:authorize>
    <security:authorize access="isAnonymous()">
        <%@ include file="/WEB-INF/includes/anonNavbar.jsp" %>
    </security:authorize>

</header>

<div id="gray"></div>


<%@include file="/WEB-INF/includes/authorizationWindow.jsp" %>

<div class="container">
<div class = "row g-0 .mx-0">
    <div class = "col-8">
        <div id="carousel-presentation" class="carousel slide" data-bs-ride="false">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#carouselPresentation" data-bs-slide-to="0" class="active"
                            aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#carouselPresentation" data-bs-slide-to="1"
                            aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#carouselPresentation" data-bs-slide-to="2"
                            aria-label="Slide 3"></button>
                </div>

                <div class="carousel-inner">
                    <div class="carousel-item active" data-bs-interval="5000">
                        <img src="<c:url value="/images/depositphotos.jpg"/>"
                             class="d-block w-100" alt="close-up-author-reading-book"/>
                        <div class="carousel-caption d-none d-md-block">
                            <h2>Freedom in education for every person</h2>
                            <p>Dive into a diverse array of skill categories, from technology and business to creative arts and personal development.</p>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <img src="<c:url value="/images/bussinessman.jpg"/>"
                             class="d-block w-100" alt="medium-shot-kid-holding-hand-up"/>
                        <div class="carousel-caption d-none d-md-block" data-bs-interval="5000">
                            <h2>We guarantee that no question will go unnoticed</h2>
                            <p>Embrace a culture of collaboration, innovation, and continuous improvement.</p>
                        </div>
                    </div>
                    <div class="carousel-item" data-bs-interval="5000">
                        <img src="<c:url value="/images/e-learing-distance-education.jpg"/>"
                             class="d-block w-100" alt="learing-distance"/>
                        <div class="carousel-caption d-none d-md-block">
                            <h2>Study anywhere</h2>
                            <p>Study is available wherever there is internet access</p>
                        </div>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-presentation" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carousel-presentation" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
        </div>
    </div>

    <div class="col-4">
        <div class = "welcome-card">
            <h3 class="big-title">Welcome to the independent education in SkillExchangeHub!</h3>
            <h6 class="welcome-text">Online education is a new stage in the development of the education system, which allows you to gain knowledge without leaving home, anywhere and at any time.
                This is an opportunity to get an education with the help of modern technologies, without wasting time, effort and money on trips to other cities and countries.</h6>
        </div>
    </div>



    <div class="row">

        <div class="col-6">
            <h3 class="welcome-text-white-title">
                Why choose SkillExchangeHub?
            </h3>
            <h5 class = "welcome-text-white">
                Connect with a diverse community of professionals eager to exchange knowledge and skills across industries.
                Expand your skill set through personalized exchanges and hands-on workshops led by industry experts.
                Stay ahead of the curve with access to the latest trends, tools, and techniques in your field.
                Foster meaningful connections and collaborations that propel your career forward.
            </h5>
        </div>
        <div class="col-6">
            <img class="placeholder-image-elearinig" src="<c:url value="/images/elearning.png"/>"/>
        </div>

    </div>

    <div class="row">

        <div class="col-6">
            <img class="placeholder-image-classroom" src="<c:url value="/images/classroom.png"/>"/>
        </div>

        <div class="col-6">
            <h3 class="welcome-text-white-title">
                Unlock Your Full Potential with SkillExchangeHub!
            </h3>
            <h5 class = "welcome-text-white">
                Join a vibrant community of professionals where skills meet opportunity. SkillExchangeHub is your gateway
                to limitless growth and collaboration. Whether you're a seasoned expert or just starting your journey,
                our platform connects you with like-minded individuals eager to exchange knowledge, sharpen skills,
                and propel careers forward.
            </h5>
        </div>

    </div>
</div>
</div>

</body>
</html>