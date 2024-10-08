<%@ page import="java.util.List" %>
<%@ page import="bean.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bo.CategoryBO" %>
<%@ page import="bean.Products" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Chỉnh sửa sản phẩm</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
  <style type="text/css">
    body{
      margin-top:20px;
      background:#353b48
    }
  </style>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/ckeditor/4.18.0/ckeditor.js"></script>

  <script>
    function chooseFile(fileInput){
      if(fileInput.files && fileInput.files[0]){
        var reader=new FileReader();
        reader.onload = function (e) {
          $('#image').attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
      }
    }
  </script>
</head>
<body>
<%   CategoryBO cb = new CategoryBO();%>
<%Products products= (Products) session.getAttribute("product");
if(products==null) products=new Products();%>
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
                    <h1>Sửa sản phẩm</h1>
                  </div>
                  <div class="col d-flex flex-column flex-sm-row justify-content-between mb-3">
                  </div>
                </div>
                <div class="tab-content pt-3">
                  <div class="tab-pane active">
                    <form id="editUserForm" class="form" action="./editPro" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
                      <div class="row">
                        <div class="col">
                          <div class="row">
                            <div class="col">
                              <div class="form-group">
                                <label>Tên sản phẩm</label>
                                <input class="form-control" id="productName" type="text" name="productName" value="<%=products.getProduct_name()%>">
                              </div>
                              <div class="form-group">
                                <label for="productCate">Danh mục</label>
                                <select class="form-control" id="productCate" name="productCate">
                                  <% for(Category a : cb.getListCategory()) { %>
                                  <option value="<%= a.getId() %>" <%= (a.getId() == products.getId_category()) ? "selected" : "" %>><%= a.getNameCategory() %></option>
                                  <% } %>
                                </select>
                              </div>
                                <div class="form-group">
                                    <label>Số lượng trong kho</label>
                                    <input class="form-control" id="inventory_quantity" type="number" name="inventory" value="<%= products.getInventory_quantity() %>">
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-group">
                                    <label>Ảnh</label>
                                    <img id="image" src="<%= products.getImage() != null ? products.getImage() : "" %>" alt="" style="width: 302px; height: 200px; border-style: dashed; border-width: 0.1mm">
                                    <input type="file" name="imageFile" id="imageFile" onchange="chooseFile(this)" accept="image/jpg, image/jpeg, image/png">
                                </div>
                            </div>
                          </div>

                          <div class="row">
                            <div class="col">
                              <div class="form-group">
                                <label>Giá sản phẩm</label>
                                <input class="form-control" id="price" type="text" name="price" value="<%=products.getPrice()%>" oninput="formatNumber()">
                              </div>
                            </div>
                            <div class="col">
                              <div class="form-group">
                                <label for="activeInput">Trạng thái</label>
                                <input id="activeInput" class="form-control" name="active" style="cursor: pointer" type="text" value="<%=products.statusString()%>" onclick="toggleActive()" readonly>
                              </div>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col mb-3"></div>
                          </div>

                            <div class="row">
                                <div class="col">
                                    <div class="form-group">
                                        <label>Mô tả</label>
                                        <textarea class="form-control ckeditor" id="cktext1" placeholder="" name="proDesc" rows="3"><%= products.getDes() %></textarea>
                                    </div>
                                </div>
                            </div>

                        </div>
                      </div>
                      <div class="row">
                        <div class="col d-flex justify-content-start">
                          <button class="btn btn-primary" type="submit" style="background-color: #7fad39; border: #7fad39;">Save Changes</button>
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
                  <span><a href="./admin_dashboard" style="text-decoration: none; text-underline: none; color: white;">Quay lại</a></span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"></script>

<script>
  function toggleActive() {
    var activeInput = document.getElementById("activeInput");
    // Thay đổi giá trị của input giữa 0 và 1 khi input được click
    if (activeInput.value === "Mở bán") {
      activeInput.value = "Hủy bán";
    } else {
      activeInput.value = "Mở bán";
    }
  }
</script>
<script>
  function formatNumber() {
    // Lấy giá trị nhập vào
    let inputValue = document.getElementById("price").value;

    // Loại bỏ tất cả các ký tự không phải số
    let numericValue = inputValue.replace(/\D/g, '');

    // Chia chuỗi thành các phần có độ dài là 3 và nối chúng lại bằng dấu chấm
    let formattedValue = numericValue.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

    // Gán giá trị đã được định dạng vào ô input
    document.getElementById("price").value = formattedValue;
  }
  function prepareData() {
    // Trước khi submit form, xóa dấu chấm khỏi giá trị nhập vào
    let inputValue = document.getElementById("price").value;
    let numericValue = inputValue.replace(/\D/g, '');
    document.getElementById("price").value = numericValue;
  }
</script>
</body>
</html>