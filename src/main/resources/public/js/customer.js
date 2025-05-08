document.addEventListener('DOMContentLoaded', function() {
    loadCustomers();

    const form = document.getElementById('customerForm');
    form.addEventListener('submit', function(e) {
        e.preventDefault(); // Stop form from refreshing page

        const customer = {
            first_name: document.getElementById('first_name').value,
            last_name: document.getElementById('last_name').value,
            address: document.getElementById('address').value,
            phoneNumber: document.getElementById('phone').value,
            email: document.getElementById('email').value
        };

        addCustomer(customer);
    });
});

function loadCustomers() {
    fetch('/customers')
        .then(response => response.json())
        .then(customers => {
            const tableBody = document.querySelector('#customerTable tbody');
            tableBody.innerHTML = '';
            customers.forEach(customer => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${customer.id}</td>
                    <td>${customer.first_name + ' ' + customer.last_name}</td>
                    <td>${customer.address}</td>
                    <td>${customer.phoneNumber}</td>
                    <td>${customer.email}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error loading customers:', error));
}

function addCustomer(customer) {
    fetch('/customers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(customer)
    })
    .then(response => {
        if (response.ok) {
            document.getElementById('message').textContent = 'Customer added!';
            document.getElementById('customerForm').reset();
            loadCustomers();
        } else {
            document.getElementById('message').textContent = 'Error adding customer.';
        }
    })
    .catch(error => console.error('Error adding customer:', error));

    loadCustomers();
}
