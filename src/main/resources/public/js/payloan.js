document.addEventListener('DOMContentLoaded', function() {

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

function processLoanPayment(details) {
    fetch('/loan/pay', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(details)
    }).then(response => {
        if (response.ok) {

        } else {

        }
    }).catch(error => console.error('Error processing payment:', error));
}