function cancelOrder() {
  if (!confirm('정말 주문을 취소하시겠습니까?')) {
    return;
  }

  const card = document.querySelector('.card[data-order-id]');
  if (!card) {
    alert('주문 정보를 찾을 수 없습니다.');
    return;
  }

  const orderId = card.getAttribute('data-order-id');
  const email = card.getAttribute('data-order-email');

  fetch(`/orders/${orderId}/cancel`, {
    method: 'PUT'
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('주문 취소에 실패했습니다.');
    }
    window.location.href = `/orders/${orderId}`;
  })
  .catch(err => {
    alert(err.message);
  });
}
