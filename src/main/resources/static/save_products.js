document.addEventListener("DOMContentLoaded", function () {

    document.getElementById('priceInput').addEventListener('input', function(e) {
      let value = e.target.value.replace(/,/g, '').replace(/[^0-9]/g, '');
      e.target.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    });

  document.getElementById('fileInput').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = function(e) {
        // 새 이미지 미리보기 보여주기
        document.getElementById('productImage').src = e.target.result;
      };
      reader.readAsDataURL(file); // 파일을 Base64로 변환!
    }
  });


});
