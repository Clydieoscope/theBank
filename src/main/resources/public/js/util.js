window.addEventListener('resize', () => {
    const width = window.innerWidth;

    if (width < 768) {
        document.querySelector('nav').classList.remove('navbar-fixed-top')
    } else {
        document.querySelector('nav').classList.add('navbar-fixed-top')
    }
});

async function getData(url) {
    try {
        const response = await fetch(url);
        const data = await response.json();
        
        return data;
    } catch (error) {
        console.error('Error:', error);
    }
}

function usd(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
      }).format(amount);      
}

function decimal(amount) {
    return new Intl.NumberFormat('en-US', { style: 'decimal' }).format(amount);
}

function capitalize(str) {
    return str.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

function pad(num) {
    return num.toString().padStart(4, '0');
}

function formatPhone(phone) {
  return phone.replace(/(\d{3})(\d{3})(\d{4})/, '($1)$2-$3');
}

function createElement(tag, attrs = {}, ...children) {
    const el = document.createElement(tag);
    for (const [key, value] of Object.entries(attrs)) {
        if (key.startsWith("on")) {
            el.addEventListener(key.substring(2).toLowerCase(), value);
        } else if (key === "class") {
            el.className = value;
        } else {
            el.setAttribute(key, value);
        }
    }
    for (const child of children) {
        el.append(child instanceof Node ? child : document.createTextNode(child));
    }
    return el;
}

function isIncome(transaction) {
    const income = ['DEPOSIT', 'RECEIVED']
    if (income.some(type => type.toLowerCase() === transaction.toLowerCase())) {
        return true;
    }

    return false;
}

function calculateLoanPayment(principal, interestRate, term) {
    // get monthly interest rate
    const monthlyInterestRate = interestRate / 100 / 12;
    
    // calcualte number of months
    const totalPayments = term * 12;

    // amortization formula to calculate the monthly payment
    const monthlyPayment = (principal * monthlyInterestRate) / 
                           (1 - Math.pow(1 + monthlyInterestRate, -totalPayments));
                           
    return monthlyPayment;
}

