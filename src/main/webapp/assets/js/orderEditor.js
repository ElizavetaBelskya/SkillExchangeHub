// функция для получения значения куки по ее названию
function getCookieValue(cookieName) {
    const cookieArray = document.cookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        const cookie = cookieArray[i].trim();
        if (cookie.startsWith(cookieName + '=')) {
            return cookie.substring(cookieName.length + 1);
        }
    }
    return null;
}


async function updateOrders() {
    // получаем все заявки
    let result = await fetch(contextName + "/api/orders/all", {
        method: 'GET'
    });

    // получаем все возможные предметы
    let skills = await fetch(contextName + "/api/orders/skills", {
        method: 'GET'
    }).then(response => response.json());

    // если получаем не найдено, то студент видит пустой экран
    if (result.status === 404) {
        const carousel = document.querySelector(".carousel");
        carousel.innerHTML = '';
        const editform = document.querySelector('.edit-order-form');
        editform.innerHTML = ''
        editform.style.display = 'none';
        const container = document.querySelector('.no-container');
        container.style.display = 'flex';
    }

    // если все хорошо, пользователь видит список своих заявок
    if (result.ok) {
        let orders = await result.json();

        const container = document.querySelector(".carousel-inner");
        container.innerHTML = '';

        // вот здесь закидываем в карусель карточки заказов
        for (let i = 0; i < orders.length; i += 3) {
            const item = document.createElement("div");
            item.classList.add("carousel-item");
            if (i === 0) {
                item.classList.add("active");
            }
            const row = document.createElement("div");
            row.classList.add("row");

            for (let j = i; j < i + 3 && j < orders.length; j++) {
                order = orders[j];
                const col = document.createElement("div");
                col.classList.add("col");
                let card = document.createElement("div");
                card.classList.add("card", "order-card-for-delete");
                card.innerHTML = `
                <div class="card-body">
                    <h6 class="card-subtitle mb-2 text-muted">Order #${order.id}</h6>
                    <h5 class="card-title order-card-editable">${order.skill}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${order.description}</h6>
                    <ul class="card-subtitle mb-2 text-muted">
            
                        <li class="list-group-item">${order.get == null ? "I am looking for a helper" : "The order is closed"}</li>
                    </ul>
                    
                    <input type="hidden" class="order-id" value="${order.id}">
                    <input type="submit" value="Edit" class="button is-primary edit-btn"/>
                    <input type="submit" value="Delete" class="button is-primary delete-btn"/>
                </div>
            `;
                let deleteBtn = card.querySelector('.delete-btn')
                let updateBtn = card.querySelector('.edit-btn')
                // на кнопку updateBtn вешаем действие формирования окошка редактирования
                updateBtn.addEventListener('click', (function(order) {
                    return function() {
                        createOrderForm(order, skills);
                    }
                })(order));


                // тут вешаем на кнопку deleteBtn удаление заказа
                let orderId = card.querySelector(".order-id").value;
                deleteBtn.addEventListener("click", async function (e) {
                    e.preventDefault();
                    const csrfToken = getCookieValue("XSRF-TOKEN");
                    const headers = new Headers();
                    headers.append('X-XSRF-TOKEN', csrfToken);
                    let result = await fetch(contextName + "/api/orders/" + orderId, {
                        method: 'DELETE', headers: headers
                    })

                    if (result.ok) {
                        // когда заказ удалился успешно, обновляем список заказов
                        console.log("Order deleted");
                        updateOrders();
                    } else {
                        let reason = await result.json().catch(error => console.log(error));
                        console.log("Error:", reason);
                    }
                });

                col.appendChild(card);
                row.appendChild(col);
            }
            item.appendChild(row);
            container.appendChild(item);

        }

    }
}


function createOrderForm(order, skills) {

    const genderRadios = document.querySelectorAll('input[name="gender"]');
    for (let radio of genderRadios) {
        if (radio.value === order.recipientGender) {
            radio.checked = true;
            break;
        }
    }

    const rating = document.getElementById('rating');
    rating.checked = order.minRating >= 4.0;
    const description = document.getElementById('description');
    description.innerText = order.description

    const skillSelect = document.getElementById("skill-select");
    skillSelect.innerHTML = '';
    skills.forEach((skill, index) => {
        const option = document.createElement("option");
        option.text = skill.title;
        option.value = skill.title;
        skillSelect.add(option);

        if (skill.title === order.skill) {
            skillSelect.selectedIndex = index;
        }
    });

    let container = document.querySelector('.edit-order-form');
    container.style.display = 'flex';

    const editButton = document.querySelector('.btn-edit-this');
    editButton.addEventListener('click', async (event) => {
        event.preventDefault();
        const skill = document.getElementById('skill-select').value;
        const gender = document.querySelector('input[name="gender"]:checked').value;
        const description = document.getElementById('description').value;
        const minRating = document.getElementById('rating').value;

        const updatedOrder = {
            skill,
            recipientGender: gender,
            rating: minRating,
            description
        };

        const csrfToken = getCookieValue("XSRF-TOKEN");
        const headers = new Headers();
        headers.append('X-XSRF-TOKEN', csrfToken);
        headers.append('Content-Type', 'application/json')

        try {
            let result = await fetch(contextName + "/api/orders/" + order.id, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(updatedOrder)
            });

            if (result.status === 200) {
                container.style.display = 'none';
                updateOrders();
            } else {
                let reason;
                try {
                    reason = await result.json();
                    reason.errors.forEach(error => {
                        console.log("Error:", error.message);
                    });
                    const errorMessages = reason.errors.map(error => error.message);
                    const errorMessageString = errorMessages.join(', ');
                } catch (error) {
                    console.log("Error while parsing JSON response:", error);
                }
            }
        } catch (error) {
            console.log("Error:", error);
        }

    });

}


document.addEventListener('DOMContentLoaded', function(){
    updateOrders();
});


