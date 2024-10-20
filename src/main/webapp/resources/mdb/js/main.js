
function initInputs() {
    document.querySelectorAll('.form-outline').forEach((formOutline) => {
        new mdb.Input(formOutline).init();
    });
}

function fixRadio() {
    document.querySelectorAll('input[type="radio"]').forEach((input) => {
        input.classList.toggle('form-check-input');
    })
}

function onReset() {
    setTimeout(initInputs, 0);
}

document.addEventListener("DOMContentLoaded", function(){
    initInputs();
    fixRadio();
});
