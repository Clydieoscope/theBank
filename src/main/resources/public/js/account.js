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

        link = '/account.html?'
        if (params.id) link += `&id=${params.id}`;
        if (params.start) link += `&start=${params.start}`;
        if (params.end) link += `&end=${params.end}`;
        if (params.min) link += `&min=${params.min}`;
        if (params.max) link += `&max=${params.max}`;

        window.location.href = link;
    });

    document.getElementById('reset').addEventListener('click', function(e) {
        window.location.href = '/account.html?id=' + new URL(window.location.href).searchParams.get('id');
    });
});

async function loadInformation() {

    const id = new URL(window.location.href).searchParams.get('id');
    const user = await getData('/user');
    const accountData = await getData('/account/' + id);
    const account = JSON.parse(accountData.message);

    const url = new URL(window.location.href);
    const params = {
        id: url.searchParams.get('id'),
        start: url.searchParams.get('start'),
        end: url.searchParams.get('end'),
        min: url.searchParams.get('min'),
        max: url.searchParams.get('max')
    };

    link = `/transaction/${params.id}?`;
    if (params.start) link += `&start=${params.start}`;
    if (params.end) link += `&end=${params.end}`;
    if (params.min) link += `&min=${params.min}`;
    if (params.max) link += `&max=${params.max}`;

    console.log(link);
    const transactionData = await getData(link);

    console.log(transactionData);
    console.log(user);
    console.log(account);

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
    
    document.getElementById('account_type').appendChild(
        createElement("span", {}, 
            createElement("span", {}, capitalize(account.type)),
            createElement("small", {}, " current APY " + account.interest + "%")
        )
    );

    document.getElementById('total_balance').appendChild(
        createElement("span", {}, 
            createElement("sup", {}, "$"),
            createElement("span", {}, decimal(account.balance))
        )
    );

    const transaction_table = document.querySelector('#transaction_table tbody');
    previousBalance = account.balance;
    transactionData.data.forEach(transaction => {
        const transactionHTML = createElement("tr", {},
            createElement("td", {}, transaction.date),
            createElement("td", {}, capitalize(transaction.type)),
            createElement("td", {class: (transaction.type === "DEPOSIT") ? 'text-success' : 'text-danger'}, ((transaction.type === "DEPOSIT") ? '+' : '-') + usd(transaction.amount)),
            createElement("td", {}, usd(previousBalance)),
        );

        if (transaction.type === "DEPOSIT") {
            previousBalance -= transaction.amount;
        } else {
            previousBalance += transaction.amount;
        }

        transaction_table.appendChild(transactionHTML);
    });
}