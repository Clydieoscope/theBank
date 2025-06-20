document.addEventListener('DOMContentLoaded', function() {

    loadInformation();

    document.getElementById('payment_form').addEventListener('submit', function(e) {
        e.preventDefault();

        const details = {
            loanID: document.getElementById('loan').value,
            accountID: document.getElementById('account').value,
            amount: document.getElementById('amount').value,
        };

        processLoanPayment(details);
    });
});

async function loadInformation() {

    // const id = new URL(window.location.href).searchParams.get('id');
    const user = await getData('/user');
    const accounts = await getData('/account/');
    const loans = await getData('/loan/');

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;

    const account_options = document.querySelector('#account');
    accounts.data.forEach(account => {
        const optionHTML = createElement("option", {value:account.id}, `${capitalize(account.type)} (${pad(account.id)}) - ${usd(account.balance)}`);

        account_options.appendChild(optionHTML);
    });
    
    const loan_options = document.querySelector('#loan');
    loans.data.forEach(loan => {
        const optionHTML = createElement("option", {value:loan.id}, `${capitalize(loan.type)} (${pad(loan.id)})`);

        loan_options.appendChild(optionHTML);
    });
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