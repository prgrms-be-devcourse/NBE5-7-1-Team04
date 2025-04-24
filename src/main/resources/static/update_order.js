function updateOrder() {
  const match = window.location.pathname.match(/\/orders\/(\d+)\/edit/);
  const orderId = match ? match[1] : null;
  const address = document.getElementById("address").value.trim();
  const zipcode = document.getElementById("zipcode").value.trim();

  if (!address || !zipcode) {
    alert("주소와 우편번호를 모두 입력해주세요.");
    return;
  }

  const zipRegex = /^\d{5}$/;
  if (!zipRegex.test(zipcode)) {
    alert("우편번호는 숫자 5자리여야 합니다.");
    return;
  }

  fetch(`/orders/${orderId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      address: address,
      zipcode: zipcode
    })
  })
  .then(response => {
    if (response.ok) {
      alert("주문 정보가 수정되었습니다.");
      window.location.href = `/orders/${orderId}`;
    } else {
      alert("주문 수정에 실패했습니다.");
    }
  })
  .catch(error => {
    alert("에러가 발생했습니다: " + error.message);
  });
}
