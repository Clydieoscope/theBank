document.addEventListener('DOMContentLoaded', function() {

    const loginform = document.getElementById('login-form');
    loginform.addEventListener('submit', function(e) {
        e.preventDefault();

        const credentials = {
            password: document.getElementById('password').value,
            email: document.getElementById('email').value
        };

        authenticateCustomer(credentials);
    });
});

function authenticateCustomer(credentials) {
    fetch('/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(credentials)
    })
    .then(response => {
        if (response.ok) {
            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"), 
                createElement("span", {}, " Welcome to the Bank")
            );

            const alter = document.getElementById('alert');
            alert.innerHTML = ''; 
            alter.appendChild(alertHTML);
            window.location.href = response.url;
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"), 
                createElement("span", {}, " We can't find your account")
            );
            const alter = document.getElementById('alert');
            alert.innerHTML = ''; 
            alter.appendChild(alertHTML);
        }
    })
    .catch(error => console.error('Error authenticating customer:', error));
}