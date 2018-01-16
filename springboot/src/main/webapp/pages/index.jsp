<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/17
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
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
<a id="skippy" class="sr-only sr-only-focusable" href="#content">
    <div class="container"><span class="skiplink-text">Skip to main content</span></div>
</a>

<!-- Docs master nav -->
<header class="navbar navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar"
                    aria-controls="bs-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
    </div>
</header>

<!-- Docs page layout -->
<div class="bs-docs-header" id="content" tabindex="-1">
    <div class="container">
        <p>概览</p>
    </div>
</div>

<div class="container bs-docs-container">

    <div class="row">
        <div class="col-md-9" role="main">
            <div class="bs-docs-section">
                <p>计算机名：${localHost.hostName}</p>
                <p>本机IP：${localHost.hostAddress}</p>
                <p>USERNAME：${envMap.USERNAME}</p>
                <p>操作系统：${sysProperty["os.name"]} &nbsp; ${sysProperty["os.version"]}</p>
                <p>JDK版本：${sysProperty["java.version"]}</p>
                <p>JAVA_HOME：${envMap.JAVA_HOME}</p>
            </div>
            <div class="bs-docs-sectionn row">
                <div class="table-responsive">
                    <table class="table table-condensed">
                        <thead>
                            <tr>
                                <th class="col-md-2">名称</th>
                                <th class="col-md-1">版本</th>
                                <th class="col-md-1">安装日期</th>
                                <th class="col-md-2">安装路径</th>
                                <th class="col-md-3">产商</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${installInfo}" var="info">
                            <tr>
                                <td>${info.DisplayName}</td>
                                <td>${info.DisplayVersion}</td>
                                <td>${info.InstallDate}</td>
                                <td>${info.InstallLocation}</td>
                                <td>${info.Publisher}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>

        <div class="col-md-3" role="complementary">
            <nav class="bs-docs-sidebar hidden-print hidden-xs hidden-sm affix-top">
                <ul class="nav bs-docs-sidenav">
                    <li class="">
                        <a href="#overview">概览</a>
                    </li>
                </ul>
                <a class="back-to-top" href="#top">
                    返回顶部
                </a>
                <a href="#" class="bs-docs-theme-toggle" role="button">
                    主题预览
                </a>

            </nav>
        </div>

    </div>
</div>

<footer class="bs-docs-footer" role="contentinfo">
    <div class="container">
        <p>Maintained by Qsung.</p>
        <ul class="bs-docs-footer-links text-muted">
            <li>当前版本： v0.0.1</li>
        </ul>
    </div>
</footer>

<script src="<%=path%>/js/jquery.js"></script>
<script src="<%=path%>/js/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/js/bootstrap/js/docs.js"></script>
</body>
</html>