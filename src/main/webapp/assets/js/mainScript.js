document.addEventListener('DOMContentLoaded', function() {

    const signIn = document.getElementById('sign-in-menu');
    const back = document.getElementById('gray');

    if (signIn != null) {
        document.getElementById('log-in-btn').addEventListener('click', function() {
            signIn.style.display = 'block';
            back.style.display = 'block';
        });

        back.addEventListener('click', function() {
            if (back.style.display == 'block') {
                back.style.display = 'none';
                signIn.style.display = 'none'
            }
        });

    }


    var alertElement = document.querySelector('.alert');

    if (alertElement && alertElement.childNodes.length > 0) {
        signIn.style.display = 'block';
        back.style.display = 'block';
    }

    function closeModal($el) {
        console.log("close")
        $el.classList.remove('is-active');
    }


    (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .button') || []).forEach(($close) => {
        const $target = $close.closest('.modal');

        $close.addEventListener('click', () => {
            closeModal($target);
        });
    });



})


