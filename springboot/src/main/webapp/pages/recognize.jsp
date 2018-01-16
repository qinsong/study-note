<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Index</title>
    <link href="<%=path%>/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="data:text/css;charset=utf-8," data-href="<%=path%>/js/bootstrap/css/bootstrap-theme.min.css"
          rel="stylesheet">
    <link href="<%=path%>/css/patch.css" rel="stylesheet">
    <link href="<%=path%>/css/docs.css" rel="stylesheet">

</head>
<body>

<div class="container bs-docs-container">

    <div class="row">
        <div role="main">
            <c:forEach items="${list}" var="img">
                <div class="bs-docs-section">
                    <img src="${img.src1}" height="400px" width="300px">
                    <img  id="img2" src="<%=path%>/img/test/nopic.jpg" data-src="${img.src2}" height="400px" width="300px">
                </div>
                <div>
                    <span id="score" style="display: none;font-weight: bold;font-size: 38px;">
                        <label>相似度：</label> ${img.result.score}
                    </span>
                </div>
            </c:forEach>
        </div>
        <div>
            <button onclick="showPic()">拍照</button>
            <button onclick="showScore()" style="display:none;">照片比对</button>
        </div>
    </div>
</div>

<script src="<%=path%>/js/jquery.js"></script>
<script src="<%=path%>/js/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">
    function showPic(){
        setTimeout(function(){
            var src = $("#img2").attr("data-src");
            $("#img2").attr("src",src);
            setTimeout(function(){ showScore(); }, 1900);
        }, 1000);


    }
    function showScore() {
        $("#score").show();
    }
    
</script>

</body>
</html>