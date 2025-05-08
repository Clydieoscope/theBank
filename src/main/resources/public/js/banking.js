document.addEventListener('DOMContentLoaded', loadInformation());

async function loadInformation() {
    const user = await getData('/user');
    const accounts = await getData('/account/');

    console.log(user);
    console.log(accounts);

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
    
    const account_list = document.querySelector('#account_list');
    total_account_balance = 0;
    accounts.data.forEach(account => {
        const accountHTML = createElement(
            "div", { class: "panel panel-default" },
            createElement("div", { class: "panel-body" },
                createElement("div", { class: "container-fluid" },
                    createElement("div", { class: "row" },
                        createElement("strong", {}, capitalize(account.type) + " (" + pad(account.id) + ")"),
                        createElement("span", { class: "float-right" }, usd(account.balance))
                    ),
                    createElement("div", { class: "row" },
                        createElement("i", {}, "Current APY " + account.interest + "%"),
                        createElement("a", { href: "/account.html?id=" + account.id, class: "float-right" }, "view")
                    )
                )
            )
        );
        account_list.appendChild(accountHTML);
        total_account_balance += account.balance;
    });

    document.querySelectorAll('.total_account_balance').forEach( e => e.innerText = decimal(total_account_balance));
}