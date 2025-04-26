const cart = new Map();
let totalAmount = 0;

function addToCart(button) {
  const id = button.getAttribute('product-id');
  const name = button.getAttribute('product-name');
  const price = parseInt(button.getAttribute('product-price'));

  if (cart.has(id)) {
    let quantity = cart.get(id);
    quantity = quantity + 1;
    cart.set(id, quantity); // 증가된 수량을 cart에 다시 저장

    document.getElementById(`product-quantity-${id}`).innerText = quantity
        + '개';
  } else {
    cart.set(id, 1);

    const cartProductHTML = document.createElement('div');
    cartProductHTML.innerHTML = `
        <div class="row">
          <h6 class="p-0">${name} <span class="badge bg-dark" id="product-quantity-${id}">1개</span></h6>
        </div>
      `;

    document.getElementById('cart').appendChild(cartProductHTML);
  }

  totalAmount += price;
  document.getElementById('totalPrice').innerText = totalAmount.toLocaleString() + '원';
}

const paymentButton = document.getElementById("paymentButton");
paymentButton.addEventListener("click", payment);

// 결제하기
async function payment() {
  const email = document.getElementById("email").value;
  const address = document.getElementById("address").value;
  const zipcode = document.getElementById("zipcode").value;

  // requestDto 내부 products
  console.log(cart)
  const products = [];
  for (const [id, quantity] of cart.entries()) {
    products.push({
      id: parseInt(id),
      quantity: quantity
    });
  }

  // requestDto
  const request = {email, address, zipcode, products};

  fetch("/orders", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(request)
  })
  .then(response => {
    if (!response.ok) {
      alert("주문 처리에 실패했습니다.");
      throw new Error("주문 실패");
    } else {
      return response.json();
    }
  })
  .then(data => {
    alert(
        "주문이 완료되었습니다!\n" +
        "당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다."
    );

    window.location.replace("/orders/" + data.ordersId);
  });
}

function checkForm() {
  const email = document.getElementById("email");
  const address = document.getElementById("address");
  const zipcode = document.getElementById("zipcode");
  const cartError = document.getElementById("cart-error");
  const paymentButton = document.getElementById("paymentButton");

  let isValid = true;

  // 이메일 유효성 검사
  if (!email.value || !email.checkValidity()) {
    email.classList.add("is-invalid");
    isValid = false;
  } else {
    email.classList.remove("is-invalid");
  }

  // 주소 유효성 검사
  if (!address.value) {
    address.classList.add("is-invalid");
    isValid = false;
  } else {
    address.classList.remove("is-invalid");
  }

  // 우편번호 유효성 검사 (5자리 숫자)
  if (!zipcode.value || !zipcode.checkValidity()) {
    zipcode.classList.add("is-invalid");
    isValid = false;
  } else {
    zipcode.classList.remove("is-invalid");
  }

  // 장바구니 유효성 검사
  if (cart.size === 0) {
    cartError.classList.add("is-invalid");
    cartError.style.display = "block";
    isValid = false;
  } else {
    cartError.classList.remove("is-invalid");
    cartError.style.display = "none";
  }

  // 폼이 유효하면 결제 버튼 활성화
  paymentButton.disabled = !isValid;
}

document.addEventListener('DOMContentLoaded', function() {
  goProductPage(1); // 페이지 첫 로딩
});

async function goProductPage(page) {
  fetch('/products?page=' + (page - 1) + '&size=5')
  .then(response => {
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    return response.text();
  })
  .then(fragment => {
    const parser = new DOMParser();
    const doc = parser.parseFromString(fragment, 'text/html');
    const newContent = doc.querySelector('#product-list');
    const oldContent = document.querySelector('#product-list');
    if (newContent && oldContent) {
      oldContent.replaceWith(newContent);
    }
  })
  .catch(error => {
    console.error('Fetch error:', error);
  });
}