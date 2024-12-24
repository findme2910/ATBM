<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Key</title>
    <link rel="stylesheet" href="/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #ffffff;
            padding: 2rem;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
            width: 100%;
            max-width: 400px;
        }

        h1 {
            font-size: 30px;
            color: #333;
            margin-bottom: 1rem;
        }

        p {
            font-size: 22px;
            color: #666;
            margin-bottom: 1.5rem;
        }



        .alert {
            margin-top: 1rem;
            font-size: 14px;
            color: green;
        }

        .alert.error {
            color: red;
        }
        /* From Uiverse.io by MuhammadHasann */
        .button {

            --border_radius: 9999px;
            --transtion: 0.3s ease-in-out;
            --offset: 2px;

            cursor: pointer;
            position: relative;

            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size:19px;
            transform-origin: center;

            padding: 0.5rem 1rem;

            border: none;
            border-radius: var(--border_radius);
            transform: scale(calc(1 + (var(--active, 0) * 0.1)));

            transition: transform var(--transtion);
        }

        .button::before {
            content: "";
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 100%;
            height: 100%;

            border-radius: var(--border_radius);
            box-shadow: inset 0 0.5px hsl(0, 0%, 100%), inset 0 -1px 2px 0 hsl(0, 0%, 0%),
            0px 4px 10px -4px hsla(0 0% 0% / calc(1 - var(--active, 0))),
            0 0 0 calc(var(--active, 0) * 0.375rem) hsl(53.13deg 5.89% 2.97% / 75%);

            transition: all var(--transtion);
            z-index: 0;
        }

        .button::after {
            content: "";
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);

            width: 100%;
            height: 100%;


            radial-gradient(at 100% 100%, hsla(266, 36%, 60%, 1) 0px, transparent 50%),
            radial-gradient(at 22% 91%, hsla(266, 36%, 60%, 1) 0px, transparent 50%);
            background-position: top;

            opacity: var(--active, 0);
            border-radius: var(--border_radius);
            transition: opacity var(--transtion);
            z-index: 2;
        }

        .button:is(:hover, :focus-visible) {
            --active: 1;
        }
        .button:active {
            transform: scale(1);
        }

        .button .dots_border {
            --size_border: calc(100% + 2px);

            overflow: hidden;

            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);

            width: var(--size_border);
            height: var(--size_border);
            background-color: transparent;

            border-radius: var(--border_radius);
            z-index: -10;
        }

        .button .dots_border::before {
            content: "";
            position: absolute;
            top: 30%;
            left: 50%;
            transform: translate(-50%, -50%);
            transform-origin: left;
            transform: rotate(0deg);

            width: 100%;
            height: 2rem;
            background-color: white;

            mask: linear-gradient(transparent 0%, white 120%);
            animation: rotate 2s linear infinite;
        }

        @keyframes rotate {
            to {
                transform: rotate(360deg);
            }
        }

        .button .sparkle {
            position: relative;
            z-index: 10;

            width: 1.75rem;
        }

        .button .sparkle .path {
            fill: currentColor;
            stroke: currentColor;

            transform-origin: center;

            color: hsl(0, 0%, 100%);
        }

        .button:is(:hover, :focus) .sparkle .path {
            animation: path 1.5s linear 0.5s infinite;
        }

        .button .sparkle .path:nth-child(1) {
            --scale_path_1: 1.2;
        }
        .button .sparkle .path:nth-child(2) {
            --scale_path_2: 1.2;
        }
        .button .sparkle .path:nth-child(3) {
            --scale_path_3: 1.2;
        }

        @keyframes path {
            0%,
            34%,
            71%,
            100% {
                transform: scale(1);
            }
            17% {
                transform: scale(var(--scale_path_1, 1));
            }
            49% {
                transform: scale(var(--scale_path_2, 1));
            }
            83% {
                transform: scale(var(--scale_path_3, 1));
            }
        }

        .button .text_button {
            position: relative;
            z-index: 10;
            background-image: linear-gradient(
                    90deg,
                    hsla(0 0% 100% / 1) 0%,
                    hsla(0 0% 100% / var(--active, 0)) 120%
            );
            background-clip: text;
            color: transparent;
        }

        /* Modal Styles */
        .modal {
            display: none; /* Ẩn modal mặc định */
            position: fixed;
            z-index: 9999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5); /* Hiệu ứng làm mờ nền */
        }

        .modal-content {
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            border-radius: 8px;
            width: 30%;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .close-button {
            float: right;
            font-size: 24px;
            font-weight: bold;
            cursor: pointer;
            color: #aaa;
        }

        .close-button:hover,
        .close-button:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <h1>Tạo Key Cho người dùng</h1>
    <div style="display: flex; justify-content: space-between; margin-bottom: 20px;">
        <button id="autoGenKey" class="button">Auto GenKey</button>
        <button id="openToolButton" class="button">Tool Tạo Key</button>
    </div>

    <form id="publicKeyForm">
        <div style="margin-bottom: 10px; font-size: 19px;">
            <label for="publicKeyInput">Public Key:</label>
            <textarea id="publicKeyInput"  name="publicKey" placeholder="Nhập publicKey ở đây" style="width: 100%; height: 100px;font-size: 18px"></textarea>
        </div>
        <div style="margin-bottom: 20px; font-size: 19px">
            <label for="fileInput">Hoặc chọn file chứa Public Key:</label>
            <input type="file" id="fileInput" accept=".txt">
        </div>
        <button type="button" id="savePublicKeyButton" class="button" style="margin:auto">Xác nhận</button>
    </form>
    <div id="alert" class="alert"></div>
    <!-- Modal -->
    <div id="genKeyModal" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close-button" id="closeModal">&times;</span>
            <h2>Chọn thư mục lưu khóa</h2>
            <div style="margin-bottom: 20px;">
                <button id="selectPublicKeyDir" class="button" style ="margin-left:25%;">Chọn thư mục Public Key</button>
                <p id="publicKeyPathDisplay">Chưa chọn thư mục</p>
            </div>
            <div style="margin-bottom: 20px;">
                <button id="selectPrivateKeyDir" class="button" style ="margin-left:25%;">Chọn thư mục Private Key</button>
                <p id="privateKeyPathDisplay">Chưa chọn thư mục</p>
            </div>
            <button id="confirmGenKey" class="button" style ="margin-left:39%;">Xác nhận</button>
        </div>
    </div>
</div>


<script>
    $(document).ready(function () {

        let publicKeyDirHandle = null;
        let privateKeyDirHandle = null;
        // Hiển thị modal khi click "Auto GenKey"
        $('#autoGenKey').click(function () {
            $('#genKeyModal').fadeIn();
        });

        // Đóng modal
        $('#closeModal').click(function () {
            $('#genKeyModal').fadeOut();
        });

        // Chọn thư mục lưu Public Key
        $('#selectPublicKeyDir').click(async function () {
            try {
                publicKeyDirHandle = await window.showDirectoryPicker();
                $('#publicKeyPathDisplay').text(`Thư mục Public Key đã chọn: ${publicKeyDirHandle.name}`);
            } catch (err) {
                console.error(err);
                alert("Không thể chọn thư mục Public Key!");
            }
        });

        // Chọn thư mục lưu Private Key
        $('#selectPrivateKeyDir').click(async function () {
            try {
                privateKeyDirHandle = await window.showDirectoryPicker();
                $('#privateKeyPathDisplay').text(`Thư mục Private Key đã chọn: ${privateKeyDirHandle.name}`);
            } catch (err) {
                console.error(err);
                alert("Không thể chọn thư mục Private Key!");
            }
        });

        // Xác nhận và thực hiện lưu file
        $('#confirmGenKey').click(async function () {
            if (!publicKeyDirHandle || !privateKeyDirHandle) {
                alert("Vui lòng chọn cả thư mục lưu Public Key và Private Key!");
                return;
            }
            try {
                const response = await $.ajax({
                    url: '/SignOrder',
                    method: 'GET',
                    data: {
                        action: 'genkey',
                        publicKeyPath: publicKeyDirHandle.name,
                        privateKeyPath: privateKeyDirHandle.name,
                    },
                    dataType: 'json',
                });
                const publicKeyContent = response.publicKey;
                const privateKeyContent = response.privateKey;

                // Lưu Public Key
                const publicKeyFile = await publicKeyDirHandle.getFileHandle('public_key.txt', { create: true });
                const publicKeyWritable = await publicKeyFile.createWritable();
                await publicKeyWritable.write(publicKeyContent);
                await publicKeyWritable.close();

                // Lưu Private Key
                const privateKeyFile = await privateKeyDirHandle.getFileHandle('private_key.txt', { create: true });
                const privateKeyWritable = await privateKeyFile.createWritable();
                await privateKeyWritable.write(privateKeyContent);
                await privateKeyWritable.close();
                alert("Tạo và lưu khóa thành công!");
                $('#genKeyModal').fadeOut();
                window.location.href = '/HomePageController';
            } catch (err) {
                console.error("Lỗi khi tạo khóa:", err);
                alert("Đã xảy ra lỗi khi tạo khóa.");
            }
        });




        // Xử lý nút "Tool Tạo Key"
        $("#openToolButton").click(function () {
            $.ajax({
                url: 'Tool',
                type: 'GET',
                success: function (data) {
                    console.log(data);
                }
            });
        });


        // Xử lý tải file lên
        $('#fileInput').change(function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    $('#publicKeyInput').val(e.target.result);
                };
                reader.readAsText(file);
            }
        });

        // Xử lý nút "Xác nhận"
        $('#savePublicKeyButton').click(function () {
            const publicKey = $('#publicKeyInput').val();
            if (publicKey.trim() === '') {
                $('#alert').html("Vui lòng nhập hoặc tải Public Key.").removeClass('alert').addClass('error');
                return;
            }
            $.ajax({
                url: '/SignOrder',
                method: 'POST',
                data: { action: 'savePublicKey', publicKey: publicKey },
                success: function () {
                    alert("Tạo khóa thành công!")
                    window.location.href = '/HomePageController';
                },
                error: function () {
                    $('#alert').html("Lỗi khi lưu Public Key.").removeClass('alert').addClass('error');
                }
            });
        });
    });
</script>
</body>
</html>
