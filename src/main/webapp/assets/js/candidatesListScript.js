
function openModal($el) {
    $el.classList.add('is-active');
}

function closeModal($el) {
    $el.classList.remove('is-active');
}

document.addEventListener('DOMContentLoaded', function() {

    (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .button') || []).forEach(($close) => {
        const $target = $close.closest('.modal');

        $close.addEventListener('click', () => {
            closeModal($target);
        });
    });

    const buttons = document.querySelectorAll('.rate');

    const modal = document.getElementById("rate-modal");
    buttons.forEach(item => {
        item.addEventListener('click', function () {
            let activeItems;
            if (modal != null) {
                openModal(modal);
                const parentItems = document.querySelector('.stars');
                const allItems = document.querySelectorAll('.stars .star');
                activeItems = document.querySelectorAll('.stars .star.active');

                const cStars = function (nowPos) {
                    for (let k = 0; k < allItems.length; k++) {
                        allItems[k].classList.remove('active');
                    }

                    for (let i = 0; i < nowPos + 1; i++) {
                        allItems[i].classList.toggle('active');
                    }
                }


                parentItems.addEventListener('click', function (e) {
                    const myTarget = e.target;
                    let k = allItems.length;
                    while (k--) {
                        if (allItems[k] === myTarget) {
                            var currentIndex = k;
                            break;
                        }
                    }
                    cStars(currentIndex);
                    activeItems = document.querySelectorAll('.stars .star.active');

                    document.getElementById("id-rated").value = item.value;
                    document.getElementById("star-count").value = activeItems.length.toString();
                });

                parentItems.addEventListener('mouseover', function (e) {
                        const currentIndex = k;
                        const myTarget = e.target;
                        let k = allItems.length;

                        while (k--) {
                            if (allItems[k] === myTarget) {
                                break;
                            }
                        }
                        cStars(currentIndex);
                    }
                );

                parentItems.addEventListener('mouseleave', function () {
                    cStars(activeItems.length - 1);
                })
            }
        })
    })

    const text = document.getElementById("comment-text");
    const comment = document.getElementById("comment")
    if (text != null) {
        text.addEventListener("input", function () {
            comment.value = text.value;
        });
    }


});

