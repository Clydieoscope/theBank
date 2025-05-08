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
        if (params.start) link += `&start=${params.start}`;
        if (params.end) link += `&end=${params.end}`;
        if (params.min) link += `&min=${params.min}`;
        if (params.max) link += `&max=${params.max}`;

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
    if (params.start) link += `&start=${params.start}`;
    if (params.end) link += `&end=${params.end}`;
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
            createElement("span", {}, decimal(loan.amount))
        )
    );

    const transaction_table = document.querySelector('#transaction_table tbody');
    previousAmount = loan.amount;
    transactionData.data.forEach(transaction => {
        const transactionHTML = createElement("tr", {},
            createElement("td", {}, transaction.date),
            createElement("td", {class: (transaction.type === "PAYMENT") ? 'text-success' : 'text-danger'}, ((transaction.type === "DEPOSIT") ? '+' : '-') + usd(transaction.amount)),
        );

        if (transaction.type === "INTEREST") {
            previousAmount -= transaction.amount;
        } else {
            previousAmount += transaction.amount;
        }

        transaction_table.appendChild(transactionHTML);
    });
}