document.addEventListener('DOMContentLoaded', function() {

    loadInformation();

    document.getElementById('change_address_form').addEventListener('submit', function(e) {
        e.preventDefault();

        changeAddress({
            address: document.getElementById('address_input').value,
            phone: '',
            email: ''
        });
    });

    document.getElementById('change_contact_info_form').addEventListener('submit', function(e) {
        e.preventDefault();

        changeContactInfo({
            address: '',
            phone: document.getElementById('phone_input').value,
            email: document.getElementById('email_input').value
        });
    });
});

async function loadInformation() {

    const user = await getData('/user');
    const customer = await getData('/customer/' + user.id);

    document.getElementById('user_name').innerText = customer.firstName + ' ' + customer.lastName;
    document.getElementById('full_name').innerText = customer.firstName + ' ' + customer.lastName;
    
    document.getElementById('address').innerText = customer.address;
    document.getElementById('address_input').value = customer.address;

    document.getElementById('email').innerText = customer.email;
    document.getElementById('email_input').value = customer.email;

    document.getElementById('phone').innerText = formatPhone(customer.phone);
    document.getElementById('phone_input').value = customer.phone;
}

function processLoanPayment(details) {
    fetch('/loan/pay', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        if (response.ok) {
            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " Payment processed."),
                createElement("a", {href:`loan.html?id=${document.getElementById('loan').value}`}, " View loan")
            );

            const alter = document.getElementById('alert');
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"),
                createElement("span", {}, " Something went wrong")
            );
            const alter = document.getElementById('alert');
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        }
    }).catch(error => console.error('Error processing payment:', error));
}


function changeAddress(details) {
    fetch('/customer/address', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        const alter = document.getElementById('address_alert');

        if (response.ok) {
            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " Address changed."),
                createElement("a", {href:'profile.html'}, " Refresh")
            );

            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"),
                createElement("span", {}, " Something went wrong")
            );
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        }
    }).catch(error => console.error('Error changing address:', error));
}

function changeContactInfo(details) {
    fetch('/customer/contact', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        const alter = document.getElementById('contact_alert');

        if (response.ok) {
            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " Contact information changed."),
                createElement("a", {href:'profile.html'}, " Refresh")
            );

            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"),
                createElement("span", {}, " Something went wrong")
            );
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        }
    }).catch(error => console.error('Error changing contact info:', error));
}