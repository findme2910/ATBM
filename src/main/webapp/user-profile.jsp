<%@ page import="bean.User" %>
<%@ page import="org.jdbi.v3.core.Jdbi" %>
<%@ page import="db.JDBIConnector" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="controller.EmailSender" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>bs4 edit profile page - Bootdey.com</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
    	body{
            margin-top:20px;
            background:#7fad39
        }
        #genkeyButton{
            background-color: #fd4545;
            border: #e75c5c;"
        }
        #info-keyButton{
            background-color: #1a8be8;
            border: #1a8be8;"
        }
        .lightbox {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
        }

        .lightbox-content {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            width: 50%;
            max-width: 500px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 25px;
            font-size: 40px;
            font-weight: bold;
            color: #fff;
            cursor: pointer;

        }



</style>
</head>
<%
    User user = (User) session.getAttribute("user");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime lastActiveTime = user.getLastActiveTime();
    String lastSeenMessage = "Last seen time not available";
    //tính toán thời gian seen, tính lại khi đăng nhập lại
    if (lastActiveTime != null) {
        long minutesAgo = ChronoUnit.MINUTES.between(lastActiveTime, now);
        long hoursAgo = ChronoUnit.HOURS.between(lastActiveTime, now);
        long daysAgo = ChronoUnit.DAYS.between(lastActiveTime, now);

        if (daysAgo > 0) {
            lastSeenMessage = "Last seen " + daysAgo + " days ago";
        } else if (hoursAgo > 0) {
            lastSeenMessage = "Last seen " + hoursAgo + " hours ago";
        } else {
            lastSeenMessage = "Last seen " + minutesAgo + " minutes ago";
        }
    }




%>

<%
    // xác thực userid có publickey không
    boolean hasPublicKey = true;
    String InfopublicKey = "Không tồn tại";

    if (user != null) {
        try {
            Jdbi jdbi = JDBIConnector.get();
            String publicKey = jdbi.withHandle(handle ->
                    handle.createQuery("SELECT publicKey FROM `keys` WHERE userId = :userId")
                            .bind("userId", user.getId())
                            .mapTo(String.class)
                            .findOne()
                            .orElse(null)
            );

            hasPublicKey = (publicKey != null && !publicKey.trim().isEmpty());
            InfopublicKey = publicKey;
            System.out.println("User ID: " + user.getId());
            System.out.println("Public Key Retrieved: " + publicKey);
            System.out.println("Has Public Key: " + hasPublicKey);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<%

    if (user != null) {
        String userEmail = user.getEmail();

        // Gửi email thông báo cho người dùng
        String subject = "Thông báo report";
        String body = "Hộp thư đã được gửi đến email " + userEmail;

        // Gửi email thông qua EmailSender
        EmailSender.sendEmail(userEmail, subject, body);
    }
%>


<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container">
    <div class="row flex-lg-nowrap">
<div class="col-12 col-lg-auto mb-3" style="width: 200px;"></div>
    <div class="col">
        <div class="row">
            <div class="col mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="e-profile">
                            <div class="row">
                                <div class="col-12 col-sm-auto mb-3">
                                    <div class="mx-auto" style="width: 140px;">
                                        <!--ảnh profile-->
                                        <div class="d-flex justify-content-center align-items-center rounded" style="height: 140px; background-color: rgb(233, 236, 239);">
                                            <img src="<%= user.getPicture()%>" alt="Profile Picture" style="width: 140px; height: 140px; object-fit: cover;">
                                        </div>
                                    </div>
                                </div>
                                <div class="col d-flex flex-column flex-sm-row justify-content-between mb-3">
                                    <div class="text-center text-sm-left mb-2 mb-sm-0">
                                        <h4 class="pt-sm-2 pb-1 mb-0 text-nowrap"></h4>
                                        <p class="mb-0"><%= user.getEmail() %></p>
                                        <div class="text-muted"><small><%= lastSeenMessage %></small></div>
                                        <div class="mt-2">
                                            <form action="upload" method="post" enctype="multipart/form-data">
                                                <input type="file" name="profilePic" accept="image/*" style="display: none;" id="fileInput">
                                                <div style="display: flex; align-items: center;">
                                                    <button class="btn btn-primary" type="button" style="background-color: #7fad39; border: #7fad39; margin-right: 10px;" onclick="document.getElementById('fileInput').click();">
                                                        <i class="fa fa-fw fa-camera"></i>
                                                        <span>Change Photo</span>
                                                    </button>
                                                    <button class="btn btn-secondary" type="button" style="background-color: #3498db; border: #3498db; background-color: red" onclick="window.location.href='/wishlistController';">
                                                        <i class="fa fa-fw fa-heart"></i>
                                                        <span>Sản phẩm yêu thích</span>
                                                    </button>
                                                </div>
                                                <input type="submit" value="Upload" style="display: none;" id="submitBtn">
                                            </form>
                                        </div>

                                        <script>
                                            document.getElementById('fileInput').onchange = function() {
                                                document.getElementById('submitBtn').click();
                                            };
                                        </script>
                                    </div>
                                    <div class="text-center text-sm-right">
                                        <div class="text-muted"><small>Joined <fmt:formatDate value="<%=user.getCreateAt()%>" pattern="yyyy-MM-dd"/></small></div>

                                    </div>
                                </div>
                            </div>
                            <div class="tab-content pt-3">
                                <div class="tab-pane active">
                                <form class="form" action="userEdit" method="post">
                                    <%
                                        String notifyS = (String) session.getAttribute("notifySuccess");
                                        String notifyF = (String) session.getAttribute("notifyFails");
                                    %>
                                    <% if(notifyS != null) {%>
                                    <p class="text-success"><%= notifyS %></p>
                                    <% } else {%>
                                    <p hidden="hidden" class="text-success"><%= notifyS %></p>
                                    <% } %>
                                    <% if(notifyF != null) {%>
                                    <p class="text-danger"><%= notifyF %></p>
                                    <% } else {%>
                                    <p hidden="hidden" class="text-danger"><%= notifyF %></p>
                                    <% } %>
                                    <div class="row">
                                        <div class="col">
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Họ</label>
                                                        <input class="form-control" id="surname" type="text" name="surname" placeholder="<%= user.getSurName() %>">
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Tên</label>
                                                        <input class="form-control" id="lastname" type="text" name="lastname" placeholder="<%= user.getLastName() %>">
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Tên người dùng</label>
                                                        <input class="form-control" id="username" type="text" name="username" placeholder="<%= user.getUsername() %>">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Email</label>
                                                        <input readonly class="form-control" type="text" placeholder="<%= user.getEmail() %>">
                                                        <label>Phone</label>
                                                        <input class="form-control" id="phone" type="text" name="phone" placeholder="<%= user.getPhone() %>">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col mb-3"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-12 col-sm-6 mb-3">
                                            <h2>Thay đổi mật khẩu</h2>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Mật khẩu hiện tại</label>
                                                        <div class="input-group">
                                                            <input id="currentPassword" name="password" type="password" placeholder="••••••••••" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Mật khẩu mới</label>
                                                        <div class="input-group">
                                                            <input id="newPassword" name="newPassword" type="password" placeholder="••••••••••" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>Xác nhận mật khẩu</label>
                                                        <div class="input-group">
                                                            <input id="confirmPassword" name="confirmPassword" type="password" placeholder="••••••••••" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="form-group">
                                                        <label>
                                                            <input type="checkbox" id="showPasswordCheckbox">
                                                            Hiển thị mật khẩu
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 col-sm-5 offset-sm-1 mb-3"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col d-flex justify-content-start">
                                        <button class="btn btn-primary" type="submit" style="background-color: #7fad39; border: #7fad39;">Save Changes</button>
                                        </div>
                                        <div class="col d-flex justify-content-end">
                                            <button id="genkeyButton" class="btn btn-secondary" style="<%= hasPublicKey ? "display:none;" : "display:block;" %>"><a href="gen-key.jsp" target="_blank" style="text-decoration: none; text-underline: none; color: white;">Genkey</a></button>
                                            <button id="info-keyButton" class="btn btn-secondary" style="<%= hasPublicKey ? "display:block;" : "display:none;" %>">Báo cáo</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-12 col-md-3 mb-3">
            <div class="card mb-3">
                <div class="card-body">
                    <div class="px-xl-3">
                        <button class="btn btn-block btn-secondary" style="background-color: #7fad39; border: #7fad39;">
                        <i class="fa fa-sign-out"></i>
                        <a href="HomePageController" style="text-decoration: none; text-underline: none; color: white;">Trang chủ</a>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
</div>

<!-- Lightbox -->
<div id="lightbox" class="lightbox">
    <div class="lightbox-content">
        <span class="close-btn" id="closeLightbox">&times;</span>
        <h4>Xác nhận Báo cáo</h4>
        <p>Public Key: <input type="text" id="publicKey" value="<%= InfopublicKey %>" readonly class="form-control"></p>
        <p>Bạn có chắc chắn muốn báo cáo key? Thao tác này sẽ gửi email đến bạn.</p>
        <button id="confirm-report" class="btn btn-danger">Đồng ý</button>
        <button id="cancel-report" class="btn btn-secondary">Hủy</button>
    </div>
</div>
<!-- SweetAlert2 CDN -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $('#showPasswordCheckbox').change(function () {
            var passwordField = $('#currentPassword, #newPassword, #confirmPassword');
            var fieldType = $(this).prop('checked') ? 'text' : 'password';
            passwordField.attr('type', fieldType);
        });
    });


    document.addEventListener("DOMContentLoaded", function () {
        const reportButton = document.getElementById("info-keyButton");
        const lightbox = document.getElementById("lightbox");
        const closeLightbox = document.getElementById("closeLightbox");

        reportButton.addEventListener("click", function (e) {
            e.preventDefault();
            lightbox.style.display = "flex";
        });

        closeLightbox.addEventListener("click", function () {
            lightbox.style.display = "none";
        });

        window.addEventListener("click", function (e) {
            if (e.target === lightbox) {
                lightbox.style.display = "none";
            }
        });
    });


    const lightbox = $('#lightbox');

    // Open lightbox
    $('#info-keyButton').click(function () {
        lightbox.fadeIn(); // Hiển thị lightbox
    });

    // Close lightbox
    $('#closeLightbox, #cancel-report').click(function () {
        lightbox.fadeOut(); // Đóng lightbox
    });

    // Confirm report
    $('#confirm-report').click(function () {
        // Gửi yêu cầu AJAX đến servlet
        $.ajax({
            url: '/reportServlet',
            type: 'GET',
            data: {
                userId: '<%= user.getId() %>',
                email: '<%= user.getEmail() %>'
            },
            success: function (response) {
                // Hiển thị thông báo thành công
                Swal.fire({
                    title: 'Thành công!',
                    text: 'Email đã được gửi!',
                    icon: 'success',
                    confirmButtonText: 'OK',
                });
                lightbox.fadeOut(); // Đóng lightbox sau khi gửi thành công
            },
            error: function () {
                // Hiển thị thông báo lỗi
                Swal.fire({
                    title: 'Lỗi!',
                    text: 'Đã xảy ra lỗi khi gửi email. Vui lòng thử lại.',
                    icon: 'error',
                    confirmButtonText: 'OK',
                });
            },
        });
    });


</script>
</body>
</html>