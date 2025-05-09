document.addEventListener('DOMContentLoaded', function() {

    loadInformation();

    document.getElementById('transfer_form').addEventListener('submit', function(e) {
        e.preventDefault();

        val = document.getElementById('acc_number').value;
        if (val === '') val = '0';

        const details = {
            from: document.getElementById('from').value,
            to: document.getElementById('to').value,
            account_number: val,
            amount: document.getElementById('amount').value,
        };

        console.log(details);
        processTransfer(details);
    });

    const toSelect = document.getElementById('to');
    toSelect.addEventListener('change', function(event) {
        const acc_numElement = document.getElementById('acc_number');

        if (event.target.value === "-1") {
            acc_numElement.classList.remove('hidden');
        } else {
            acc_numElement.classList.add('hidden');
        }
    });
});

async function loadInformation() {
    const user = await getData('/user');
    const accounts = await getData('/account/');

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;

    const to_options = document.querySelector('#to_option');
    accounts.data.forEach(account => {
        const optionHTML = createElement("option", {value:account.id}, `${capitalize(account.type)} (${pad(account.id)}) - ${usd(account.balance)}`);
        to_options.appendChild(optionHTML);
    });
    
    const from_options = document.querySelector('#from');
    accounts.data.forEach(account => {
        const optionHTML = createElement("option", {value:account.id}, `${capitalize(account.type)} (${pad(account.id)}) - ${usd(account.balance)}`);
        from_options.appendChild(optionHTML);
    });
}

function processTransfer(details) {
    fetch('/transaction/transfer', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        if (response.ok) {
            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " Transfer processed."),
                createElement("a", {href:`account.html?id=${document.getElementById('from').value}`}, " View account")
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
    }).catch(error => console.error('Error processing payment:', error));
}