document.addEventListener('DOMContentLoaded', () => {

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


});

