document.addEventListener("DOMContentLoaded", function () {
  const deleteBtn = document.getElementById("deleteBtn");

  deleteBtn.addEventListener('click', function () {
    const id = document.getElementById("productId").value;
    deleteProduct(id);
  })
});

function deleteProduct(id) {
  if (confirm('정말 삭제하시겠습니까?')) {
    fetch('/admin/products/' + id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.status === 204) {
        alert('삭제되었습니다.');
        window.location.href = '/admin/products/list'; // 목록으로 이동
      } else {
        alert('오류 발생. 삭제 실패 ');
      }
    });
  }
}