<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/common/tablib.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value='views/css/styles.css' />" rel="stylesheet"  type="text/css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <title>Profile Detail</title>
    </head>
    <body>
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light bg-light d-flex justify-content-end">
                <a class="btn btn-danger my-2 my-sm-0" href="logout">Logout</a>
            </nav>
        </div>
        <!--        <section class="vh-100" style="background-color: #f4f5f7;"> -->
        <div class="container">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col mb-4 mb-lg-0">
                    <div class="card mb-3" style="border-radius: .5rem;">
                        <div class="row g-0">
                            <div class="col-md-4 gradient-custom text-center text-white" 
                                 style="border-top-left-radius: .5rem; border-bottom-left-radius: .5rem;">
                                <img src="https://thelifetank.com/wp-content/uploads/2018/08/avatar-default-icon.png"
                                     alt="Avatar" class="img-fluid my-5" style="width: 80px;" />
                                <h1>${profile.fullname}</h1>
                                <p>Web Developer</p>
                                <i class="far fa-edit mb-5"></i>
                            </div>

                            <div class="col-md-8">
                                <div class="card-body p-4 pb-1">
                                    <h3>Information</h3>
                                    <hr class="mt-0 mb-4">
                                    <div class="row pt-1">
                                        <div class="col-6 mb-3">
                                            <h5>Gender</h5>
                                            <p class="text-muted">${profile.gender}</p>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <h5>Day of birth</h5>
                                            <p class="text-muted">${profile.dob}</p>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <h5>Address</h5>
                                            <p class="text-muted">${profile.address}</p>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <h5>Phone</h5>
                                            <p class="text-muted">${profile.phone}</p>
                                        </div>
                                        <div class="col-6 mb-3">
                                            <h5>Email</h5>
                                            <p class="text-muted">${profile.email}</p>
                                        </div>
                                        <div class="col-6 md-3">
                                            <div class="d-flex justify-content-start">
                                                <a href="https://www.facebook.com/nqp2805/"><i class="fab fa-facebook-f fa-lg me-3"></i></a>
                                                <a href="https://github.com/nquangphu"><i class="fab fa-github fa-lg me-3"></i></a>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--</section>-->
    </body>
</html>
