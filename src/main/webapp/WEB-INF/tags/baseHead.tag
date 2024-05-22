<%@ tag description = "head" pageEncoding="utf-8"%>
<%@ attribute name="title"%>
<%@ attribute name="scriptLink"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>${title}</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/styles.css"/>">

    <link href="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css"/>"
          rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
          crossorigin="anonymous">

    <script src="<c:url value="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"/>"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
            crossorigin="anonymous"></script>

    <script src="<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js"/>"
            integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk"
            crossorigin="anonymous"></script>

    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bulma@1.0.0/css/bulma.min.css"
    >



    <c:if test="${scriptLink != null}">
        <script charset="UTF-8" src="<c:url value="${scriptLink}"/>"></script>
    </c:if>

    <script>
        contextName = "${pageContext.request.contextPath}";
    </script>

    <link rel="shortcut icon" href="<c:url value="/images/free-icon-studying-1903172.png"/>" type="image/png">

</head>