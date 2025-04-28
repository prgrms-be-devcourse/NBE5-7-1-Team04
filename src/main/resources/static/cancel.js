function cancelOrder() {
  if (!confirm('정말 주문을 취소하시겠습니까?')) {
    return;
  }

  const card = document.querySelector('.card[data-order-id]');
  if (!card) {
    alert('주문 정보를 찾을 수 없습니다.');
    return;
  }
  const orderId = card.dataset.orderId;
  const orderStatus = card.dataset.orderStatus;

  if (orderStatus === 'COMPLETED' || orderStatus === 'CANCELED') {
    alert('배송이 완료되거나 취소 상태인 주문은 취소하실 수 없습니다.');
    return;
  }

  fetch(`/orders/${orderId}/cancel`, {method: 'PUT'})
  .then(res =>
      res
      .json()
      .catch(() => ({}))
      .then(body => ({res, body}))
  )
  .then(({res, body}) => {
    if (res.ok) {
      alert('주문이 취소되었습니다.');
      window.location.href = `/orders/${orderId}`;
    } else {
      alert(body.message || `주문 취소에 실패했습니다. (status: ${res.status})`);
    }
  })
  .catch(err => {
    alert('에러가 발생했습니다: ' + err.message);
  });
}
