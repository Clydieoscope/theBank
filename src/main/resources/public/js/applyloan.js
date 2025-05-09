document.addEventListener('DOMContentLoaded', function() {

    loadInformation();

    document.getElementById('loan_application_form').addEventListener('submit', function(e) {
        e.preventDefault();

        const details = {
            type: document.getElementById('type').value,
            term: document.getElementById('term').value,
            interest: document.getElementById('interest').value,
            amount: document.getElementById('amount').value,
        };

        console.log(details);
        processApplication(details);
    });
});

async function loadInformation() {
    const user = await getData('/user');

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
}

function processApplication(details) {
    fetch('/loan/apply', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        if (response.ok) {

            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " Application recieved."),
                createElement("a", {href:'loans.html'}, " View loan")
            );

            const alter = document.getElementById('alert');
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"),
                createElement("span", {}, " Something went wrong.")
            );
            const alter = document.getElementById('alert');
            alert.innerHTML = '';
            alter.appendChild(alertHTML);
        }
    }).catch(error => console.error('Error processing application:', error));
}