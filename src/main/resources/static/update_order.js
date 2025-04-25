function updateOrder() {
  const cardEl = document.querySelector('.card.mb-4');
  const orderId = cardEl.dataset.orderId;

  const address = document.getElementById("address").value.trim();
  const zipcode = document.getElementById("zipcode").value.trim();

  if (!address || !zipcode) {
    alert("주소와 우편번호를 모두 입력해주세요.");
    return;
  }

  if (!/^\d{5}$/.test(zipcode)) {
    alert("우편번호는 숫자 5자리여야 합니다.");
    return;
  }

  fetch(`/orders/${orderId}`, {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify({address, zipcode})
  })
  .then(res => {
    if (res.ok) {
      alert("주문 정보가 수정되었습니다.");
      window.location.href = `/orders/${orderId}`;
    } else {
      alert("주문 수정에 실패했습니다.");
    }
  })
  .catch(err => {
    alert("에러가 발생했습니다: " + err.message);
  });
}
