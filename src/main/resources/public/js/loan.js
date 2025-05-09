document.addEventListener('DOMContentLoaded', function() {
    loadInformation();

    document.getElementById('filter_form').addEventListener('submit', function(e) {
        e.preventDefault();

        const params = {
            id: new URL(window.location.href).searchParams.get('id'),
            start: encodeURIComponent(document.getElementById('start').value),
            end: encodeURIComponent(document.getElementById('end').value),
            min: document.getElementById('min').value,
            max: document.getElementById('max').value
        };

        link = '/loan.html?'
        if (params.id) link += `&id=${params.id}`;
        if (params.start) link += `&start=${encodeURIComponent(params.start)}`;
        if (params.end) link += `&end=${encodeURIComponent(params.end)}`;
        if (params.min) link += `&min=${params.min}`;
        if (params.max) link += `&max=${params.max}`;
        console.log(link);

        window.location.href = link;
    });

    document.getElementById('reset').addEventListener('click', function(e) {
        window.location.href = '/loan.html?id=' + new URL(window.location.href).searchParams.get('id');
    });
});

async function loadInformation() {

    const id = new URL(window.location.href).searchParams.get('id');
    const user = await getData('/user');
    const loanData = await getData('/loan/' + id);
    const loan = JSON.parse(loanData.message);

    const url = new URL(window.location.href);
    const params = {
        id: url.searchParams.get('id'),
        start: url.searchParams.get('start'),
        end: url.searchParams.get('end'),
        min: url.searchParams.get('min'),
        max: url.searchParams.get('max')
    };

    link = `/transaction/loan/${params.id}?`;
    if (params.start) link += `&start=${encodeURIComponent(params.start)}`;
    if (params.end) link += `&end=${encodeURIComponent(params.end)}`;
    if (params.min) link += `&min=${params.min}`;
    if (params.max) link += `&max=${params.max}`;

    console.log(link);
    const transactionData = await getData(link);

    console.log(transactionData);
    console.log(user);
    console.log(loan);

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
    
    document.getElementById('loan_type').appendChild(
        createElement("span", {}, 
            createElement("span", {}, capitalize(loan.type)),
            createElement("small", {}, " current APY " + loan.rate + "%")
        )
    );

    document.getElementById('total_amount').appendChild(
        createElement("span", {}, 
            createElement("sup", {}, "$"),
            createElement("span", {}, decimal(loan.balance))
        )
    );

    const transaction_table = document.querySelector('#transaction_table tbody');
    previousAmount = loan.balance;
    transactionData.data.forEach(transaction => {
        const transactionHTML = createElement("tr", {},
            createElement("td", {}, transaction.date),
            createElement("td", 
                {class: (!isIncome(transaction.type)) ? 'text-success' : 'text-danger'}, 
                (!(isIncome(transaction.type)) ? '+' : '-') + usd(transaction.amount))
        );

        if (!isIncome(transaction.type)) {
            previousAmount -= transaction.amount;
        } else {
            previousAmount += transaction.amount;
        }

        transaction_table.appendChild(transactionHTML);
    });

    if (loan.balance === 0) {
        const messageHTML = createElement(
            "div", { class: "panel panel-success" },
            createElement("div", { class: "panel-heading" }, "Congratulations!"),
            createElement("div", { class: "panel-body" },
                createElement("p", {}, "You have paid off this loan."),
            ),
            createElement("div", { class: "panel-footer" },
                createElement("button", { id:"delete", type:"button", class:"btn btn-danger" }, "Delete?")
            )
        );

        document.querySelector("#message").appendChild(messageHTML);
        document.getElementById("delete").addEventListener('click', deleteLoan);
    }
}

function deleteLoan() {
    const id = new URL(window.location.href).searchParams.get('id');

    fetch('/loan/' + id, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => {
        if (response.ok) {
            document.getElementById('message').innerHTML = '';

            const alertHTML = createElement(
                "div", { class: "alert alert-success alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Success!"),
                createElement("span", {}, " You'll no longer see this loan."),
                createElement("a", {href:'loans.html'}, " Return to Loans")
            );

            const alter = document.getElementById('message');
            alter.appendChild(alertHTML);
        } else {
            const alertHTML = createElement(
                "div", { class: "alert alert-danger alert-dismissible" },
                createElement("a", { href: "#", class: "close", 'data-dismiss':"alert", 'aria-label':"close"}, 'x'),
                createElement("strong", {}, "Oops!"),
                createElement("span", {}, " Something went wrong.")
            );
            const alter = document.getElementById('message');
            alter.appendChild(alertHTML);
        }
    }).catch(error => console.error('Error processing payment:', error));
}