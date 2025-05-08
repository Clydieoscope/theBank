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

