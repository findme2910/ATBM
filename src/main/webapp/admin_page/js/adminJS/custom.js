// custom.js

// Function to load content and set active class
function loadContent(link) {
    const url = $(link).data('url');
    $('.list-unstyled .active').removeClass('active');
    $(link).parent().addClass('active');

    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            $('#mainContent').html(data);
            initializeDataTable();
        }
    });
}

function initializeDataTable() {
    if ($.fn.dataTable.isDataTable('#quanlyTable')) {
        $('#quanlyTable').DataTable().destroy();
        $('#quanlyTable').empty(); // Clear the table body to remove old data
    }

    $('#quanlyTable').DataTable({
        "language": {
            "lengthMenu": "Hiển thị _MENU_ bản ghi mỗi trang",
            "zeroRecords": "Không tìm thấy kết quả",
            "info": "Hiển thị trang _PAGE_ trong tổng số _PAGES_",
            "infoEmpty": "Không có bản ghi nào",
            "infoFiltered": "(lọc từ _MAX_ bản ghi)",
            "search": "Tìm kiếm:",
            "paginate": {
                "first": "Đầu",
                "last": "Cuối",
                "next": "Sau",
                "previous": "Trước"
            },
        }
    });
}

// Ensure the loadContent function is accessible from any script
window.initializeDataTable = initializeDataTable;
window.loadContent = loadContent;
