<%@include file="/common/tablib.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value='views/css/login-css.css'/>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

        <title>Login</title>
    </head>
    <body>
        <div class="container">
            <form action="<c:url value='/api-login'/>" method="post" id="frm-login" name="frm-login">
                <div class="imgcontainer">
                    <img src="https://thelifetank.com/wp-content/uploads/2018/08/avatar-default-icon.png"
                         alt="Avatar" class="img-fluid my-5" style="width: 200px;" />
                </div>

                <div class="container">
                    <label for="uname"><b>Username</b></label>
                    <input type="text" placeholder="Username" id="username" name="username" required>

                    <label for="psw"><b>Password</b></label>
                    <input type="password" placeholder="Password" id="pwd" name="pwd" required>
<!--                    <input type="hidden" value="login" name="action"/>-->

                    <button type="button" class="btn btn-primary" onclick="login()">Login</button>

                    <span class="psw">Or <a href="api-register">register?</a></span>
                </div>

            </form>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <script>
                        var msg = null;

                        function login() {
                            if ($('#username').val() != "") {
                                if ($('#pwd').val() != "") {

                                    var formArray = $('#frm-login').serializeArray();
                                    var returnArray = {};
                                    for (var i = 0; i < formArray.length; i++) {
                                        returnArray[formArray[i]['name']] = formArray[i]['value'];
                                    }

                                    $.ajax({
                                        type: 'POST',
                                        url: 'api-login',
                                        data: JSON.stringify(returnArray),
                                        dataType: 'JSON',

                                        success: function (data) {
                                            console.log(data);
                                            if (data.error == 0) {
                                                alert("Login Fail");
                                            } else
                                                location.href = "/profile-detail-page";
                                        }

                                    })
                                } else {
                                    alert("Wrong password");
                                }
                            } else {
                                alert("Wrong username");
                            }
                        }
        </script>
    </body>
</html>