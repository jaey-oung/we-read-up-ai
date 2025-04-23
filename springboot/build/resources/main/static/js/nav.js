const navIcon = document.getElementById('nav-icon');
const table = document.getElementById('table');

navIcon.addEventListener('mouseover', function() {
    table.style.display = 'block';
})

navIcon.addEventListener('mousedown', function() {
    table.style.display = 'block';
})

table.addEventListener('mouseover', function() {
    table.style.display = 'block';
})

table.addEventListener('mousedown', function() {
    table.style.display = 'block';
})

navIcon.addEventListener('mouseout', function() {
    table.style.display = 'none';
})

table.addEventListener('mouseout', function() {
    table.style.display = 'none';
})