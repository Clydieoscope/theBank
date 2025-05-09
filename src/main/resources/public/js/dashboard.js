document.addEventListener('DOMContentLoaded', loadInformation());

async function loadInformation() {
    const user = await getData('/user');
    const accounts = await getData('/account/');
    const loans = await getData('/loan/');

    console.log(user);
    console.log(accounts);
    console.log(loans);

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
    document.getElementById('user_firstname').innerText = user.firstName;
    
    const account_list = document.querySelector('#account_list');
    total_account_balance = 0;
    accounts.data.forEach(account => {
        const accountHTML = createElement("li", {class:'list-group-item'},
            createElement('a', {href: '/account.html?id=' + account.id}, capitalize(account.type) + ' (' + pad(account.id) + ')'),
            createElement('small', {}, usd(account.balance))
        );

        account_list.appendChild(accountHTML);                        
        total_account_balance += account.balance;
    });

    document.querySelectorAll('.total_account_balance').forEach( e => e.innerText = decimal(total_account_balance));

    const loan_list = document.querySelector('#loan_list');
    total_loan_balance = 0;
    loans.data.forEach(loan => {
        const loanHTML = createElement("li", {class:'list-group-item'},
            createElement('a', {href: '/loan.html?id=' + loan.id}, capitalize(loan.type) + ' (' + pad(loan.id) + ')'),
            createElement('small', {}, usd(loan.balance))
        );

        loan_list.appendChild(loanHTML); 
        total_loan_balance += loan.balance;
    });
    
    document.querySelectorAll('.total_loan_balance').forEach( e => e.innerText = decimal(total_loan_balance));

}