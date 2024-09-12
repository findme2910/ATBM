<%@ page import="java.util.List" %>
<%@ page import="bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<User> dsUser = (List<User>) request.getAttribute("dsUser");
    if (dsUser == null) dsUser = new ArrayList<>(); %>
<% Integer roleInt2 = (Integer) request.getAttribute("roleInt2");%>
<%--Tag này không biết để làm gì--%>
<% Integer tagAttribute = (Integer) request.getAttribute("tag");
    int tag = (tagAttribute != null) ? tagAttribute.intValue() : 1; %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit =no">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>Quản lý người dùng</title>
    <style>
        a {
            text-decoration: none !important;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button {
            padding: 0.5rem 0.75rem;
            margin-left: -1px;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            color: #007bff;
            background-color: #fff;
            text-decoration: none;
            cursor: pointer;
        }
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            color: #0056b3;
            background-color: #e9ecef;
            border-color: #dee2e6;
        }
        .dataTables_wrapper .dataTables_filter input {
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            padding: 0.375rem 0.75rem;
        }
        input[type="search"] {
            background-color: #fff;
        }
        #togglePassword{
         cursor:pointer;
        }
    </style>
</head>
<body>
<div class="container my-5">
    <div class="text-center">
        <h3 class="page-title" style="color:black; font-weight: bold; margin-top: 30px; margin-bottom: 40px">
            <%= (roleInt2 == 1) ? "Quản lý nhân viên" : "Quản lý khách hàng" %>
        </h3>
    </div>
    <input type="hidden" id="roleInt2" value="<%= roleInt2 %>">
    <div class="mb-5 d-flex align-items-center">
        <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal" <%= (roleInt2 == 1) ? "" : "hidden=\"hidden\"" %>>+ Thêm người dùng</a>
        <button class="btn btn-info ml-3" style="color:white;" id="exportButton">=> Xuất file</button>
    </div>
    <div id="userContent">
        <table id="quanlyTable" class="table table-striped table-bordered" style="width:100%">
            <thead>
            <tr>
                <th>Mã</th>
                <th style="width: 100px;font-weight: bold">Tên</th>
                <th style="width: 100px;font-weight: bold ">Email</th>
                <th style="width: 100px;font-weight: bold">Số điện thoại</th>
                <th style="width: 100px;font-weight: bold">Vai trò</th>
                <th style="width: 100px;font-weight: bold">Trạng Thái</th>
                <th style="width: 150px;font-weight: bold">Tính Năng</th>
            </tr>
            </thead>
            <tbody>
            <% for (User a : dsUser) { %>
            <tr id="row_user_<%=a.getId()%>">
                <th><%=a.getId()%></th>
                <th><%=a.getUsername()%></th>
                <th><%=a.getEmail()%></th>
                <th><%=a.getPhone()%></th>
                <th><%=a.roleString()%></th>
                <th><%= a.getActive() == 1 ? "Hoạt động" : "Vô Hiệu Hóa" %></th>
                <th>
                    <button class="btn <%= a.getActive() == 1 ? "btn-warning" : "btn-success" %>" data-toggle="modal" data-target="#toggleDisableModal<%=a.getId()%>">
                        <i class="fas <%= a.getActive() == 1 ? "fa-ban" : "fa-check" %>"></i>
                    </button>
                </th>
            </tr>

            <!-- vô hiệu hóa, kích hoạt lại người dùng-->
             <div class="modal fade" id="toggleDisableModal<%=a.getId()%>" tabindex="-1" role="dialog" aria-labelledby="disableModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title"><%= a.getActive() == 1 ? "Xác nhận vô hiệu hóa" : "Xác nhận kích hoạt lại" %></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Bạn có chắc chắn muốn <%= a.getActive() == 1 ? "vô hiệu hóa" : "kích hoạt lại" %> tài khoản của <%=a.getUsername()%>?</p>
                            <p class="text-warning"><small>Bấm "Hủy" để dừng lại</small></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                            <button type="button" class="btn <%= a.getActive() == 1 ? "btn-danger" : "btn-success" %>" onclick="toggleDisableUser(<%=a.getId()%>, <%=a.getActive()%>)"><%= a.getActive() == 1 ? "Vô hiệu hóa" : "Kích hoạt lại" %></button>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<!-- Thêm người dùng -->
<div class="modal fade" id="addEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm người dùng</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <form id="addUserForm" action="<%=request.getServletContext().getContextPath()%>/insertUser" method="post" accept-charset="UTF-8">
                    <div class="form-group">
                        <label>Tên tài khoản</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>
                    <div class="form-group row">
                        <div class="col">
                            <label>Họ</label>
                            <input type="text" name="lastname" class="form-control" required>
                        </div>
                        <div class="col">
                            <label>Tên (Nhập tên lót)</label>
                            <input type="text" name="surname" class="form-control" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Mật khẩu</label>
                        <div class="input-group">
                            <input type="password" name="pass" id="passwordInput" class="form-control" required>
                            <div class="input-group-append">
                                    <span class="input-group-text" id="togglePassword">
                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="text" name="phone" class="form-control" required>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-success">Thêm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
        // Xuất file excel
        $('#exportButton').on('click', function() {
            var tableElement = document.getElementById('quanlyTable');
            var wb = XLSX.utils.table_to_book(tableElement, {sheet: "Sheet1"});
            var roleInt2 = $('#roleInt2').val(); // Lấy giá trị roleInt2 từ input ẩn
            var fileName = (roleInt2 == 1 ? 'EmployeeDetails.xlsx' : 'CustomerDetails.xlsx');
            XLSX.writeFile(wb, fileName);
        });
        // thêm người dùng
        $('#addUserForm').submit(function(e) {
            e.preventDefault();
            $.ajax({
                type: "POST",
                url: $(this).attr('action'),
                data: $(this).serialize(),
                success: function(response) {
                    alert('Người dùng đã được thêm thành công!');
                    loadContent($('#waiterManagementLink'));
                },
                error: function() {
                    alert('Lỗi xảy ra khi thêm người dùng!');
                }
            });
        });

        // logic toggle disable user
        window.toggleDisableUser = function(userID, currentState) {
            var action = currentState == 1 ? 'disableUser' : 'cancelDisableUser';
            $.ajax({
                url: '/' + action,
                type: "POST",
                data: { 'userID': userID },
                success: function(data) {
                    alert(currentState == 1 ? 'Tài khoản đã được vô hiệu hóa!' : 'Tài khoản đã được kích hoạt lại!');
                    loadUserTable();
                },
                error: function(xhr, error) {
                    alert('Lỗi xảy ra khi ' + (currentState == 1 ? 'vô hiệu hóa' : 'kích hoạt lại') + ' tài khoản! Lỗi: ' + xhr.responseText);
                }
            });
        };

        // Load user table
        function loadUserTable() {
            var roleInt2 = $('#roleInt2').val();
            $.ajax({
                url: '/maUser?roleID=' + roleInt2 + '&uid=1',
                type: "GET",
                success: function(data) {
                    $('#userContent').html($(data).find('#userContent').html());
                    // Re-initialize the DataTable
                    $('#quanlyTable').DataTable();
                },
                error: function(xhr, error) {
                    alert('Lỗi xảy ra khi tải lại bảng người dùng! Lỗi: ' + xhr.responseText);
                }
            });
        }

        const passwordInput = document.getElementById('passwordInput');
        const togglePassword = document.getElementById('togglePassword');
        togglePassword.addEventListener('click', function() {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            togglePassword.innerHTML = type === 'password' ? '<i class="fa fa-eye" aria-hidden="true"></i>' : '<i class="fa fa-eye-slash" aria-hidden="true"></i>';
        });
    });
</script>
</body>
</html>
