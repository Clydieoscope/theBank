document.addEventListener('DOMContentLoaded', loadInformation());

async function loadInformation() {
    const user = await getData('/user');
    const loans = await getData('/loan/');

    console.log(user);
    console.log(loans);

    document.getElementById('user_name').innerText = user.firstName + ' ' + user.lastName;
    
    const loan_list = document.querySelector('#loan_list');
    total_loan_balance = 0;
    loans.data.forEach(loan => {
        const loanHTML = createElement(
            "div", { class: "panel panel-default" },
            createElement("div", { class: "panel-body" },
                createElement("div", { class: "container-fluid" },
                    createElement("div", { class: "row" },
                        createElement("strong", {}, capitalize(loan.type) + " (" + pad(loan.id) + ")"),
                        createElement("span", { class: "float-right" }, usd(loan.balance))
                    ),
                    createElement("div", { class: "row" },
                        createElement("i", {}, "Interest rate " + loan.rate + "%"),
                        createElement("a", { href: "/loan.html?id=" + loan.id, class: "float-right" }, "view")
                    )
                )
            )
        );
        loan_list.appendChild(loanHTML);
        total_loan_balance += loan.balance;
    });

    document.querySelectorAll('.total_loan_balance').forEach( e => e.innerText = decimal(total_loan_balance));
}